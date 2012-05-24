package pl.edu.agh.goodsim.producer;

import pl.edu.agh.goodsim.document.ClientOffer;
import pl.edu.agh.goodsim.entity.Good;

import java.util.List;

public class ProducerAgent {
    private List<Good> storedGoods;

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
