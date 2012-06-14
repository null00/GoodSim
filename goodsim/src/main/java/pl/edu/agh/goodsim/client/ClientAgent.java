package pl.edu.agh.goodsim.client;

import com.thoughtworks.xstream.XStream;
import jade.jademx.agent.JademxAgent;
import jade.jademx.util.MBeanUtil;
import pl.edu.agh.goodsim.document.ClientOffer;
import pl.edu.agh.goodsim.document.Contract;
import pl.edu.agh.goodsim.document.Offer;
import pl.edu.agh.goodsim.document.Supply;
import pl.edu.agh.goodsim.entity.Good;
import pl.edu.agh.goodsim.serviceregistry.Reputation;
import pl.edu.agh.goodsim.serviceregistry.ServiceRegistry;
import pl.edu.agh.goodsim.type.ContractStatusEnum;
import pl.edu.agh.goodsim.type.OfferStatusEnum;

import javax.management.*;
import java.util.*;
import java.util.Map.Entry;

/**
 * @author Mateusz Rudnicki <rudnicki@student.agh.edu.pl>
 */
public class ClientAgent extends JademxAgent {

   private static final long serialVersionUID = 1L;
   private static final String DESCRIPTION = "Client Agent";
   protected static final String SAY_HELLO_OPER_NAME = "sayHello";
   protected static final String SAY_HELLO_OPER_DESC = "says hello";
   private Map<String, TaskValues> contracts;
   protected ServiceRegistry _serviceRegistry;

   /**
    * MBeanInfo for this class and superclass(es)
    */
   protected MBeanInfo mBeanInfoMerged = null;
   /**
    * MBeanInfo for superclass(es)
    */
   protected MBeanInfo mBeanInfoSuper = null;
   /**
    * MBeanInfo for for this class but not superclass(es)
    */
   protected MBeanInfo mBeanInfoThisLevel = null;

   public ClientAgent() {
      super();
      _serviceRegistry = ServiceRegistry.getInstance();
      mBeanInfoThisLevel = constructMBeanInfo();
      mBeanInfoMerged = MBeanUtil.mergeMBeanInfo(
         getClass().getName(), DESCRIPTION,
         mBeanInfoThisLevel, null);
   }

   public MBeanInfo getMBeanInfo() {
      return mBeanInfoMerged;
   }

   @Override
   protected void setup() {
      super.setup();
      System.out.println("Hello! I am the ClientAgent: " + getAID().getName());
   }

   @Override
   protected void takeDown() {
      super.takeDown();
      System.out.println("Client is dead!");
   }

   protected MBeanInfo constructMBeanInfo() {

      // attributes

//      MBeanAttributeInfo aI[] = new MBeanAttributeInfo[]{
//         new MBeanAttributeInfo(
//            SOME_ATTR_NAME,
//            String.class.getName(),
//            SOME_ATTR_DESC,
//            true, true, false)
//      };

      // constructors

      MBeanConstructorInfo cI[] = new MBeanConstructorInfo[0];

      // operations

//      MBeanParameterInfo pIPing[] = {
//         new MBeanParameterInfo(
//            OPER_PING_AGENT_FULL_NAME_PARM_NAME,
//            OPER_PING_AGENT_FULL_NAME_PARM_TYPE,
//            OPER_PING_AGENT_FULL_NAME_PARM_DESC)
//      };
      MBeanOperationInfo oI[] = new MBeanOperationInfo[]{
         new MBeanOperationInfo(
            SAY_HELLO_OPER_NAME,
            SAY_HELLO_OPER_DESC,
            null,
            String.class.getName(),
            MBeanOperationInfo.INFO)
      };

      // notifications

//      String notifications[] = {
//         NOTIF_PINGED_NAME
//      };
//      String NOTIF_INFO_DESCRIPTION =
//         "notification set for " + getClass().getName();
//      MBeanNotificationInfo nI[] = new MBeanNotificationInfo[] {
//         new MBeanNotificationInfo(
//            notifications,
//            Notification.class.getName(),
//            NOTIF_INFO_DESCRIPTION )
//      };

      // now, MBeanInfo for this level of class hierarchy

      return new MBeanInfo(getClass().getName(),
         DESCRIPTION,
         null, cI, oI, null);

   }


   /*
     * ==================== MAS FUNCTIONS (JADE) ====================
     */

   public void receiveOffer(Offer offer) {
      TaskValues taskValues = contracts.get(offer.getSessionId());
      String contractorID = offer.getContractorId();
      OfferStatus offerStatus = taskValues.getLastOfferStatus(contractorID);
      offerStatus.setOffer(offer);
      offerStatus.setStatus(OfferStatusEnum.OFFER_RECEIVED);
   }

   public void receiveRefusal(String sessionId, String contractorID, int offerCount) {
      TaskValues taskValues = contracts.get(sessionId);
      OfferStatus offerStatus = taskValues.getLastOfferStatus(contractorID);
      offerStatus.setStatus(OfferStatusEnum.CANCEL);
      if (checkRefusalStatuses(taskValues)) {
         // XXX: przerwanie kontraktu
         taskValues.getContract().setContractStatus(ContractStatusEnum.CLOSED);
      }
   }

   private boolean checkRefusalStatuses(TaskValues taskValues) {
      LinkedList<Map<String, List<OfferStatus>>> offers = (LinkedList<Map<String, List<OfferStatus>>>) taskValues.getOffers();
      Map<String, List<OfferStatus>> offerStatusMap = offers.getLast();
      for (Entry<String, List<OfferStatus>> pairs : offerStatusMap.entrySet()) {
         List<OfferStatus> statuses = pairs.getValue();
         for (OfferStatus status : statuses) {
            if (status.getStatus() != OfferStatusEnum.CANCEL)
               return false;
         }
      }
      return true;
   }

   public void reciveContractSign(String sessionID, String contractorID) {
      TaskValues taskValues = contracts.get(sessionID);
      OfferStatus offerStatus = taskValues.getLastOfferStatus(contractorID);
      offerStatus.setStatus(OfferStatusEnum.SIGNED);
   }

   public void reciveRenegotiation(Offer offer) {
      TaskValues taskValues = contracts.get(offer.getSessionId());
      String contractorID = offer.getContractorId();
      OfferStatus offerStatus = taskValues.getLastOfferStatus(contractorID);
      offerStatus.setOffer(offer);
      offerStatus.setStatus(OfferStatusEnum.OFFER_RECEIVED);
   }

   /*
     * MAS/JMX FUNCTIONS (What we can delegate to the agent)
     */

   public void receiveGood(Good good, String sessionID, int container) {
      TaskValues contract = contracts.get(sessionID);
      Offer offer = contract.getContract().getOffer();
      switch (container) {
         case 1: {
            if (inSupplies(good, offer.getSuppliesOut())) {
               contract.addGoodForSale(good);
            }
            break;
         }
         case 2: {
            if (inSupplies(good, offer.getSuppliesIn())) {
               contract.addGoodReceived(good);
            }
            break;
         }
         default: {
            System.err.println("Unknown container ID.");
            break;
         }
      }
   }

   private boolean inSupplies(Good good, List<Supply> supplies) {
      Iterator<Supply> iter = supplies.iterator();
      while (iter.hasNext()) {
         Supply supply = iter.next();
         if (supply.getGood().equals(good)) {
            return true;
         }
      }
      return false;
   }

   /*
     * MAS/JMX FUNCTIONS (What we can delegate to the agent)
     */

   public Set<String> getContractList(ContractStatusEnum status) {
      Set<String> filteredContracts = new HashSet<String>();
      for (Entry<String, TaskValues> pairs : contracts.entrySet()) {
         ContractStatusEnum contractStatus = pairs.getValue().getContract().getContractStatus();
         if ((contractStatus.getValue() & status.getValue()) != 0) {
            String sessionID = pairs.getKey();
            filteredContracts.add(sessionID);
         }
      }
      return filteredContracts;
   }

   /*
     * ============= JMX FUNCTIONS =============
     */

   public String getTaskValues(String sessionID) {
      TaskValues contract = (TaskValues) contracts.get(sessionID);
      XStream xStream = new XStream();
      xStream.alias("TaskValues", TaskValues.class);
      String xml = xStream.toXML(contract);
      return xml;
   }

   public String getContract(String sessionID) {
      TaskValues contract = (TaskValues) contracts.get(sessionID);
      Contract _contract = contract.getContract();
      XStream xStream = new XStream();
      xStream.alias("Contract", Contract.class);
      String xml = xStream.toXML(_contract);
      return xml;
   }

   public int getRenegotiationCount(String sessionID) {
      // FIXME: dunno if this is the way this should go
      TaskValues contract = contracts.get(sessionID);
      return contract.getContract().getOffer().getNegotiationCount();
   }

   public int getOfferCount(String sessionID, int renegotiation, String contractorID) {
      TaskValues taskValues = contracts.get(sessionID);
      List<OfferStatus> offerStatusList = taskValues.getContractorOffers(renegotiation, contractorID);
      return offerStatusList.size();
   }

   public List<String> getOffer() {
      // TODO: first we have to change the offers list from TaskValues
      return null;
   }

   public String getOffer(String sessionID, int renegotiation, int countOffer, String contractorID) {
      // FIXME: Is using of renegotiation and countOffer OK?
      TaskValues taskValues = contracts.get(sessionID);
      List<OfferStatus> offerStatusList = taskValues.getContractorOffers(renegotiation, contractorID);
      OfferStatus offerStatus = offerStatusList.get(countOffer);
      Offer offer = offerStatus.getOffer();
      XStream xStream = new XStream();
      xStream.alias("Offer", Offer.class);
      String xml = xStream.toXML(offer);
      return xml;
   }

   public void prepareTask(String sessionId, String mainSessionId, List<String> goodsTypes) {
      // FIXME: We do not have the contractorId here!
      Offer offer = new Offer();
      Contract contract = new Contract(offer);
      TaskValues taskValues = new TaskValues(contract);
      Map<String, List<Reputation>> servicesMap = _serviceRegistry.getServices(goodsTypes);
      taskValues.initNegotiationHistory();
      // TODO: 4. Utworzenie dla każdej znalezionej usługi obiektu klasy
      // OfferStatus i umieszczeniu ich w mapie (w taskValues).
      contracts.put(sessionId, taskValues);
   }

   public void prepareIntention(String intention) {
      // FIXME: What to do with the clientOffer object?
      ClientOffer clientOffer = new ClientOffer();
      XStream xStream = new XStream();
      xStream.alias("ClientOffer", ClientOffer.class);
      xStream.fromXML(intention, clientOffer);
   }

   public void sendIntentions(String sessionId, int renegotiation, int offerNumber, String contractorId) {
      // TODO:
   }

   /*
     * MAS/JMX EVENTS (What can agent do himself)
     */

   private void getServices(List<String> goodsTypes) {
      // TODO:
   }

   private void sendIntention(ClientOffer intention) {
      // TODO:
   }

   private void sendCounterOffer(ClientOffer clientOffer) {
      // TODO:
   }

   private void sendNegotiationBreak(String sessionid, String contractorid) {
      // TODO:
   }

   private void signContract(Contract contract) {
      // TODO:
   }

   private void sendRenegotiation(ClientOffer clientOffer) {
      // TODO:
   }

   private void sendGood(String sessionid, String contractorid, Good good) {
      // TODO:
   }

   private int cacheSize = DEFAULT_CACHE_SIZE;
   private static final int DEFAULT_CACHE_SIZE = 200;

   public String sayHello() {
      return "I, " + getAID().getName() + " welcome you Sir!";
   }

   public int getCacheSize() {
      return this.cacheSize;
   }

   public void setCacheSize(int size) {
      this.cacheSize = size;
      System.out.println("Cache size now " + this.cacheSize);
   }

   // MBEAN INTERFACE TO ATTRIBUTE AND OPERATION

   /**
    * get an attribute value
    *
    * @param attribute name of attribute to get
    * @return attribute value
    * @throws javax.management.AttributeNotFoundException
    *          no such attribute
    * @throws javax.management.MBeanException
    *          exception from getter
    * @throws javax.management.ReflectionException
    *          exception invoking getter
    */
   public Object getAttribute(String attribute)
      throws AttributeNotFoundException,
      MBeanException,
      ReflectionException {
      Object o;
      // attributes known about directly at this level
//      if (attribute.equals("SomeName")) {
//         o = getSomeName();
//      }
      // attributes known by parent class
//      else {
      o = super.getAttribute(attribute);
//      }
      return o;
   }

   /**
    * @param attribute
    * @throws AttributeNotFoundException     no such attribte
    * @throws InvalidAttributeValueException bad attribute value
    * @throws MBeanException                 exception from setter
    * @throws ReflectionException            exception invoking setter
    */
   public void setAttribute(Attribute attribute)
      throws AttributeNotFoundException,
      InvalidAttributeValueException,
      MBeanException,
      ReflectionException {

      String attributeName = attribute.getName();
      Object v = attribute.getValue();
      // attributes known about directly at this level
//      if ("SomeName".equals(attributeName)) {
//         String s;
//         try {
//            s = (String) v;
//         } catch (ClassCastException cce) {
//            throw new InvalidAttributeValueException("for attribute " +
//               " need " + String.class.getName() + " got " +
//               v.getClass().getName());
//         }
//         try {
//            setResponse(s);
//            invoking directly, not indirectly,
//            so can't throw ReflectionException
//         } catch (Exception e) {
//            throw new MBeanException(e,
//               "exception setting " + "SomeName" + " attribute");
//         }
//      }
      // attributes known by parent class
//      else {
      super.setAttribute(attribute);
//      }
   }


   /**
    * invoke an action
    *
    * @param actionName name of action to invoke
    * @param params     action parameters
    * @param signature  action signature
    * @return object result
    * @throws MBeanException      wrap action exception
    * @throws ReflectionException wrap action invocation exception
    */
   public Object invoke(String actionName, Object params[], String signature[])
      throws MBeanException, ReflectionException {
      Object o = null;
      if (SAY_HELLO_OPER_NAME.equals(actionName) &&
         MBeanUtil.signaturesEqual(new String[]{}, signature)) {
         try {
            o = sayHello();
         } catch (Exception e) {
            throw new MBeanException(e,
               "exception invoking " + SAY_HELLO_OPER_NAME + " operation");
         }
      } else {
         super.invoke(actionName, params, signature);
      }
      return o;
   }
}
