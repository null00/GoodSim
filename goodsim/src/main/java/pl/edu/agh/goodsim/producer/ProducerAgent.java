package pl.edu.agh.goodsim.producer;

import jade.core.behaviours.CyclicBehaviour;
import jade.jademx.util.MBeanUtil;
import jade.lang.acl.ACLMessage;

import java.util.List;
import pl.edu.agh.goodsim.client.ClientAgent;
import pl.edu.agh.goodsim.document.ClientOffer;
import pl.edu.agh.goodsim.entity.Good;
import pl.edu.agh.goodsim.helper.MethodEnvelope;

public class ProducerAgent extends ClientAgent {
   private static final String DESCRIPTION = "Producer Agent";
   private List<Good> storedGoods;
   protected String serviceTypeName = "ServiceTypeName";

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
	   
	   if(serviceRegistryAID != null) {
		   registerService();		   
	   } else {
		   System.out.println("Cannot obtain ServiceRegistry AID");
	   }
	   
		addBehaviour(new CyclicBehaviour(this) {
			public void action() {
				ACLMessage msg = receive();
				if (msg != null && msg.getPerformative() == ACLMessage.REQUEST) {
					ProducerAgent pa = (ProducerAgent) myAgent;
					MethodEnvelope me = MethodEnvelope.fromXML(msg.getContent());
					String functionName = me.getFunctionName();
					
					if(functionName.equals("intention")){
						ClientOffer intention = me.getArgument(0);
						pa.receiveIntention(intention);
					} else {
						System.out.println( myAgent.getLocalName() + ": Unkown function call");
					}
				} else if (msg != null) {
					System.out.format( "%s: receive msg %n%s%n", myAgent.getLocalName(), msg.getContent() );
				}
				block();
			}
		});
	   
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
	   me.addArgument(serviceTypeName);
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

   public void receiveIntention(ClientOffer offer) {
        // TODO
    	System.out.println(getAID().getName() + ": I receive ClientOffer" + offer.toString());
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
