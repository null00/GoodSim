package pl.edu.agh.goodsim.producer;

import jade.core.AID;
import jade.domain.AMSService;
import jade.domain.FIPAAgentManagement.AMSAgentDescription;
import jade.domain.FIPAAgentManagement.SearchConstraints;
import jade.jademx.util.MBeanUtil;
import jade.lang.acl.ACLMessage;

import java.util.List;

import pl.edu.agh.goodsim.client.ClientAgent;
import pl.edu.agh.goodsim.document.ClientOffer;
import pl.edu.agh.goodsim.entity.Good;
import pl.edu.agh.goodsim.helper.MethodEnvelope;

public class ProducerAgent extends ClientAgent {
   private static final String SERVICE_REGISTRY_NAME = "ServiceRegistry";
   private static final String DESCRIPTION = "Producer Agent";
   private AID serviceRegistryAID;
   private List<Good> storedGoods;

   public ProducerAgent(){
      super();
//      _serviceRegistry = ServiceRegistry.getInstance();
      mBeanInfoThisLevel = null;
      mBeanInfoSuper = super.getMBeanInfo();
      mBeanInfoMerged = MBeanUtil.mergeMBeanInfo(
         getClass().getName(), DESCRIPTION,
         mBeanInfoThisLevel, mBeanInfoSuper);
   }

   @Override
   protected void setup() {
	   super.setup();

	   serviceRegistryAID = getServiceRegistryAID();
	   if(serviceRegistryAID != null) {
		   registerService();		   
	   } else {
		   System.out.println("Cannot obtain ServiceRegistry AID");
	   }
   }

   @Override
   protected void takeDown() {
      super.takeDown();
      if(serviceRegistryAID != null) {
    	  unregisterService();
      }
      System.out.println("Producer is dead!");
   }

   /*
    * ==================== MAS FUNCTIONS (JADE) ====================
    */

   public void registerService() {
	   MethodEnvelope me = new MethodEnvelope();
	   me.setFunctionName("registerService");
	   me.addArgument("ServiceTypeName");
	   me.addArgument(getAID().getName());
	   String msgContent = me.toXML();

	   ACLMessage msg = new ACLMessage( ACLMessage.REQUEST );
	   msg.setContent( msgContent );
	   msg.addReceiver(serviceRegistryAID);
	   send(msg);
   }

   public void unregisterService() {
	   MethodEnvelope me = new MethodEnvelope();
	   me.setFunctionName("unregisterService");
	   me.addArgument("ServiceTypeName");
	   me.addArgument(getAID().getName());
	   String msgContent = me.toXML();

	   ACLMessage msg = new ACLMessage( ACLMessage.REQUEST );
	   msg.setContent( msgContent );
	   msg.addReceiver(serviceRegistryAID);
	   send(msg);
   }

   public AID getServiceRegistryAID() {
	   AMSAgentDescription [] agents = null;

	   try {
		   SearchConstraints c = new SearchConstraints();
		   c.setMaxResults ( new Long(-1) );
		   agents = AMSService.search( this, new AMSAgentDescription (), c );
		   for(AMSAgentDescription agentDescription : agents) {
			   AID agentAID = agentDescription.getName();
			   if( agentAID.getName().contains(SERVICE_REGISTRY_NAME) )
				   return agentAID;
		   }
	   }
	   catch (Exception e) { 
		   System.out.println(e.toString());
	   }

	   return null;
   }

    public void receiveIntention(ClientOffer offer) {
        // TODO
    }

    public void receiveCounteroffer(ClientOffer counterOffer) {
        //TODO
    }

    public void receiveSignOffer(ClientOffer signOffer) {
        //TODO
    }

    public void storeGood(Good good) {
        storedGoods.add(good);
    }

}
