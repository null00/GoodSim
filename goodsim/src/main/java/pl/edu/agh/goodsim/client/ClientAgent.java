package pl.edu.agh.goodsim.client;

import com.thoughtworks.xstream.XStream;
import jade.core.AID;
import jade.domain.AMSService;
import jade.domain.FIPAAgentManagement.AMSAgentDescription;
import jade.domain.FIPAAgentManagement.SearchConstraints;
import jade.jademx.agent.JademxAgent;
import jade.jademx.util.MBeanUtil;
import jade.lang.acl.ACLMessage;
import pl.edu.agh.goodsim.document.ClientOffer;
import pl.edu.agh.goodsim.document.Contract;
import pl.edu.agh.goodsim.document.Offer;
import pl.edu.agh.goodsim.document.Supply;
import pl.edu.agh.goodsim.entity.Good;
import pl.edu.agh.goodsim.helper.MethodEnvelope;
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
    private static final String SERVICE_REGISTRY_NAME = "ServiceRegistry";
    protected static final String SAY_HELLO_OPER_NAME = "sayHello";
    protected static final String SAY_HELLO_OPER_DESC = "says hello";
    public static final String CURRENT_INTENTION_ATTR_NAME = "currentIntention";
    public static final String CURRENT_INTENTION_ATTR_DESC = "intention prepared for sending";
    public static final String CURRENT_INTENTION_ATTR_TYPE = ClientOffer.class.getName();
    private Map<String, TaskValues> contracts;
    protected ServiceRegistry _serviceRegistry;
    protected AID serviceRegistryAID;
    ClientOffer currentIntention;

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
    private static final String PREPARE_INTENTION_PARAM_NAME = "intention";
    private static final String PREPARE_INTENTION_PARAM_TYPE = String.class.getName();
    private static final String PREPARE_INTENTION_PARAM_DESC = "prepared intention";
    private static final String PREPARE_INTENTION_OPER_NAME = "prepareIntention";
    private static final String PREPARE_INTENTION_OPER_DESC = "method preparing intention";
    private static final String PREPARE_INTENTION_OPER_TYPE = Void.class.getName();
    private static final String GET_SERVICES_OPER_NAME = "getServices";
    private static final String GET_SERVICES_OPER_DESC = "gets services from ServiceRegistry";
    private static final String GET_SERVICES_OPER_TYPE = List.class.getName();
    private static final String GOODS_TYPE_NAME = "goodsTypes";
    private static final String GOODS_TYPE_TYPE = String.class.getName();
    private static final String GOODS_TYPE_DESC = "goods Types";
    private static final String SEND_INTENTION_OPER_NAME = "sendIntention";
    private static final String SEND_INTENTION_OPER_DESC = "sends intention to producers";
    private static final String SEND_INTENTION_OPER_TYPE = Void.class.getName();


    public ClientAgent() {
        super();
//      _serviceRegistry = ServiceRegistry.getInstance();
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
        serviceRegistryAID = getServiceRegistryAID();

        System.out.println("Hello! I am the " + getClass().getSimpleName() + " : " + getAID().getName());
    }

    @Override
    protected void takeDown() {
        super.takeDown();
        System.out.println("Client is dead!");
    }

    protected MBeanInfo constructMBeanInfo() {

        // attributes

        MBeanAttributeInfo aI[] = new MBeanAttributeInfo[]{
                new MBeanAttributeInfo(
                        CURRENT_INTENTION_ATTR_NAME,
                        CURRENT_INTENTION_ATTR_TYPE,
                        CURRENT_INTENTION_ATTR_DESC,
                        true, true, false
                )
        };

        MBeanConstructorInfo cI[] = new MBeanConstructorInfo[0];

        // operations

        MBeanParameterInfo prepareIntention[] = {
                new MBeanParameterInfo(
                        PREPARE_INTENTION_PARAM_NAME,
                        PREPARE_INTENTION_PARAM_TYPE,
                        PREPARE_INTENTION_PARAM_DESC)
        };

        MBeanParameterInfo[] getServicesSignature = {
                new MBeanParameterInfo(
                        GOODS_TYPE_NAME,
                        GOODS_TYPE_TYPE,
                        GOODS_TYPE_DESC
                )
        };
        MBeanOperationInfo oI[] = new MBeanOperationInfo[]{
                new MBeanOperationInfo(
                        SAY_HELLO_OPER_NAME,
                        SAY_HELLO_OPER_DESC,
                        null,
                        String.class.getName(),
                        MBeanOperationInfo.INFO),
                new MBeanOperationInfo(
                        PREPARE_INTENTION_OPER_NAME,
                        PREPARE_INTENTION_OPER_DESC,
                        prepareIntention,
                        PREPARE_INTENTION_OPER_TYPE,
                        MBeanOperationInfo.ACTION
                ),
                new MBeanOperationInfo(
                        GET_SERVICES_OPER_NAME,
                        GET_SERVICES_OPER_DESC,
                        getServicesSignature,
                        GET_SERVICES_OPER_TYPE,
                        MBeanOperationInfo.ACTION_INFO
                ),
                new MBeanOperationInfo(
                        SEND_INTENTION_OPER_NAME,
                        SEND_INTENTION_OPER_DESC,
                        null,
                        SEND_INTENTION_OPER_TYPE,
                        MBeanOperationInfo.ACTION
                )
        };

        return new MBeanInfo(getClass().getName(),
                DESCRIPTION,
                aI, cI, oI, null);

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
        ClientOffer clientOffer = new ClientOffer("client", new ArrayList<Good>(), new ArrayList<Good>());
        XStream xStream = new XStream();
        xStream.alias("ClientOffer", ClientOffer.class);
        xStream.fromXML(intention, clientOffer);
        currentIntention = clientOffer;
    }

    public void sendIntentions(String sessionId, int renegotiation, int offerNumber, String contractorId) {
        // TODO:
    }

    /*
    * MAS/JMX EVENTS (What can agent do himself)
    */

    private List<String> getServices(String goodType) {
        List<String> result = new LinkedList<String>();

        MethodEnvelope me = new MethodEnvelope();
        me.setFunctionName("getServices");
        me.addArgument(goodType);
        String msgContent = me.toXML();

        //send message
        ACLMessage msg = new ACLMessage(ACLMessage.REQUEST);
        msg.setContent(msgContent);
        msg.addReceiver(serviceRegistryAID);
        send(msg);

        //receive result
        ACLMessage reply = blockingReceive();
        if (reply != null) {
            MethodEnvelope replyEnvelope = MethodEnvelope.fromXML(reply.getContent());
            result = replyEnvelope.getArgument(0);
        }

        return result;
    }

    private void sendIntention(ClientOffer intention) {
        MethodEnvelope me = new MethodEnvelope();
        me.setFunctionName("intention");
        me.addArgument(intention);
        String msgContent = me.toXML();

        ACLMessage msg = new ACLMessage(ACLMessage.REQUEST);
        msg.setContent(msgContent);

        for (String agentName : getServices(intention.getInputGoodsNames().get(0))) {
            msg.addReceiver(new AID(agentName, AID.ISLOCALNAME));
        }
        send(msg);
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
        switch (attribute) {
            case CURRENT_INTENTION_ATTR_NAME:
                return getCurrentIntention();
            default:
                o = super.getAttribute(attribute);
                break;
        }
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
        switch (attributeName) {
            case CURRENT_INTENTION_ATTR_NAME:
                ClientOffer offer;
                try {
                    offer = (ClientOffer) v;
                } catch (ClassCastException ex) {
                    throw new InvalidAttributeValueException("for attribute " +
                            " need " + String.class.getName() + " got " +
                            v.getClass().getName());
                }
                try {
                    setCurrentIntention(offer);
                } catch (Exception e) {
                    throw new MBeanException(e,
                            "exception setting " + CURRENT_INTENTION_ATTR_NAME + " attribute");
                }

                break;

        }
        super.setAttribute(attribute);
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
        switch (actionName) {
            case SAY_HELLO_OPER_NAME:
                o = sayHello();
                break;
            case PREPARE_INTENTION_OPER_NAME:
                prepareIntention((String) params[0]);
                break;
            case GET_SERVICES_OPER_NAME:
                o = getServices((String) params[0]);
                break;
            case SEND_INTENTION_OPER_NAME:
                sendIntention(currentIntention);
                break;
            default:
                super.invoke(actionName, params, signature);
        }
        return o;
    }

    public AID getServiceRegistryAID() {
        AMSAgentDescription[] agents = null;

        try {
            SearchConstraints c = new SearchConstraints();
            c.setMaxResults(new Long(-1));
            agents = AMSService.search(this, new AMSAgentDescription(), c);
            for (AMSAgentDescription agentDescription : agents) {
                AID agentAID = agentDescription.getName();
                if (agentAID.getName().contains(SERVICE_REGISTRY_NAME))
                    return agentAID;
            }
        } catch (Exception e) {
            System.out.println(e.toString());
        }

        return null;
    }

    public ClientOffer getCurrentIntention() {
        return currentIntention;
    }

    public void setCurrentIntention(ClientOffer currentIntention) {
        this.currentIntention = currentIntention;
    }
}
