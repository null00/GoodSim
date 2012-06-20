package pl.edu.agh.goodsim.document;

import pl.edu.agh.goodsim.entity.Good;

import java.beans.ConstructorProperties;
import java.util.LinkedList;
import java.util.List;

public class ClientOffer {
    private String clientId;
    private List<Good> inputGoods;
    private List<Good> outputGoods;

    public ClientOffer() {
        super();
    }

    @ConstructorProperties({"clientId", "inputGoods", "outputGoods"})
    public ClientOffer(String clientId, List<Good> inputGoods, List<Good> outputGoods) {
        this.clientId = clientId;
        this.inputGoods = inputGoods;
        this.outputGoods = outputGoods;
    }

    public String getClientId() {
        return clientId;
    }

    public List<String> getInputGoodsTypes() {
        List<String> inputGoodTypes = new LinkedList<String>();
        for (Good good : inputGoods) {
            inputGoodTypes.add(good.getTypeOfGoodName());
        }
        return inputGoodTypes;
    }

    public List<String> getOutputGoodsTypes() {
        List<String> outputGoodTypes = new LinkedList<String>();
        for (Good good : outputGoods) {
            outputGoodTypes.add(good.getTypeOfGoodName());
        }
        return outputGoodTypes;
    }

    public List<Good> getInputGoods() {
        return inputGoods;
    }

    public List<Good> getOutputGoods() {
        return outputGoods;
    }

    public void setClientId(String clientId) {
        this.clientId = clientId;
    }

    public void setInputGoods(List<Good> inputGoods) {
        this.inputGoods = inputGoods;
    }

    public void setOutputGoods(List<Good> outputGoods) {
        this.outputGoods = outputGoods;
    }
}
