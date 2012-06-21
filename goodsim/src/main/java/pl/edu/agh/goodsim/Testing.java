package pl.edu.agh.goodsim;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;
import pl.edu.agh.goodsim.client.ClientAgent;
import pl.edu.agh.goodsim.document.ClientOffer;
import pl.edu.agh.goodsim.entity.Good;

import javax.management.MBeanServer;
import javax.management.ObjectName;
import java.beans.ConstructorProperties;
import java.lang.management.ManagementFactory;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author krzyho
 */
public class Testing {


    public static void main(String[] args) throws Exception {

        ClientOffer offer = new ClientOffer("deska", new ArrayList<Good>(), new ArrayList<Good>());
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




        MBeanServer mbs = ManagementFactory.getPlatformMBeanServer();
        ObjectName name = new ObjectName("pl.edu.agh.goodsim.client:type=ClientAgent");
        FakeAgent mbean = new FakeAgent(createDeska());
        mbs.registerMBean(mbean, name);

        ObjectName name2 = new ObjectName("pl.edu.agh.goodsim.client:type=ClientAgent2");
        FakeAgent2 mbean2 = new FakeAgent2(new ClientOffer("klient", new ArrayList<Good>(), new ArrayList<Good>()));
        mbs.registerMBean(mbean2, name2);

        System.out.println("Waiting forever...");
        Thread.sleep(Long.MAX_VALUE);
    }

    private static Good createDeska() {
        Good result = new Good("deska", new HashMap<String, Double>());
        return result;
    }

    private static Good createDrewno() {
        Good result = new Good("drewno", new HashMap<String, Double>());
        return result;
    }


    private static class FakeAgent implements FakeAgentMXBean{
        private Good good;

        @ConstructorProperties({"deska"})
        public FakeAgent(Good deska) {
            this.good = deska;
        }

        public FakeAgent(){
        }

        public Good getGood() {
            return good;
        }

        public void setGood(Good good) {
            this.good = good;
        }
    }

    private static class FakeAgent2 implements FakeAgent2MXBean{

        private ClientOffer offer;

        @ConstructorProperties({"clientOffer"})
        public FakeAgent2(ClientOffer offer) {
            this.offer = offer;
        }

        public FakeAgent2(){
        }

        public ClientOffer getOffer() {
            return offer;
        }

        public void setOffer(ClientOffer offer) {
            this.offer = offer;
        }
    }
}
