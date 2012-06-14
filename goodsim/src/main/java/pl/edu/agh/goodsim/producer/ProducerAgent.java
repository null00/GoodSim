package pl.edu.agh.goodsim.producer;

import pl.edu.agh.goodsim.client.ClientAgent;
import pl.edu.agh.goodsim.document.ClientOffer;
import pl.edu.agh.goodsim.entity.Good;

import java.util.List;

public class ProducerAgent extends ClientAgent {
    private List<Good> storedGoods;

   @Override
   protected void setup() {
      System.out.println("Hello! I am the ProducerAgent: " + getAID().getName());
   }

   @Override
   protected void takeDown() {
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
