package pl.edu.agh.goodsim;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;
import pl.edu.agh.goodsim.document.ClientOffer;
import pl.edu.agh.goodsim.entity.Good;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author krzyho
 */
public class Testing {


    public static void main(String[] args) throws Exception {

        ClientOffer offer = new ClientOffer();
        offer.setClientId("client");
        List<Good> inputGoods = new ArrayList<>();
        inputGoods.add(createDrewno());
        offer.setInputGoods(inputGoods);
        List<Good> outputGoods = new ArrayList<>();
        outputGoods.add(createDeska());
        offer.setOutputGoods(outputGoods);


        XStream xStream = new XStream(new DomDriver());
        String offerString = xStream.toXML(offer);
        System.out.println(offerString);
        ClientOffer actualOffer = (ClientOffer) xStream.fromXML(offerString);
        System.out.println(actualOffer.getClientId());
        System.out.println(actualOffer.getInputGoods());
        System.out.println(actualOffer.getOutputGoods());
    }

    private static Good createDeska() {
        Good result = new Good();
        result.setTypeOfGoodName("deska");
        Map<String, Double> attributeValues = new HashMap<String, Double>();
        result.setAttributeValues(attributeValues);
        return result;
    }

    private static Good createDrewno() {
        Good result = new Good();
        result.setTypeOfGoodName("drewno");
        Map<String, Double> attributeValues = new HashMap<String, Double>();
        result.setAttributeValues(attributeValues);
        return result;
    }


}
