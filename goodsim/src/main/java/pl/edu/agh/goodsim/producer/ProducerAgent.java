package pl.edu.agh.goodsim.producer;

import jade.jademx.util.MBeanUtil;
import pl.edu.agh.goodsim.client.ClientAgent;
import pl.edu.agh.goodsim.document.ClientOffer;
import pl.edu.agh.goodsim.entity.Good;
import pl.edu.agh.goodsim.serviceregistry.ServiceRegistry;

import java.util.List;

public class ProducerAgent extends ClientAgent {
   private static final String DESCRIPTION = "Producer Agent";
   private List<Good> storedGoods;

   public ProducerAgent(){
      super();
      _serviceRegistry = ServiceRegistry.getInstance();
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
   }

   @Override
   protected void takeDown() {
      super.takeDown();
      System.out.println("Producer is dead!");
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
