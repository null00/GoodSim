package pl.edu.agh.goodsim.producer;

import jade.core.AID;
import jade.jademx.util.MBeanUtil;
import jade.lang.acl.ACLMessage;
import pl.edu.agh.goodsim.client.ClientAgent;
import pl.edu.agh.goodsim.document.ClientOffer;
import pl.edu.agh.goodsim.entity.Good;
import pl.edu.agh.goodsim.helper.MethodEnvelope;
import pl.edu.agh.goodsim.serviceregistry.ServiceRegistry;

import java.util.List;

public class ProducerAgent extends ClientAgent {
   private static final String DESCRIPTION = "Producer Agent";
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
      System.out.println("Hello! I am the ProducerAgent: " + getAID().getName());

      registerService();
   }

   @Override
   protected void takeDown() {
      super.takeDown();
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
	   // TODO how to obtain serviceRegistry AID? from AMS? how?
	   msg.addReceiver(new AID("ServiceRegistry@invincible:1098/JADE", AID.ISLOCALNAME) );
	   send(msg);
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
