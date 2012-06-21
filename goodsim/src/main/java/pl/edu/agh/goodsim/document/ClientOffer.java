package pl.edu.agh.goodsim.document;

import pl.edu.agh.goodsim.entity.Good;

import java.beans.ConstructorProperties;
import java.util.LinkedList;
import java.util.List;

public class ClientOffer {
    private String clientId;
    private List<Good> inputGoods;
    private List<Good> outputGoods;

    @ConstructorProperties({"clientId", "inputGoods", "outputGoods"})
    public ClientOffer(String clientId, List<Good> inputGoods, List<Good> outputGoods) {
        this.clientId = clientId;
        this.inputGoods = inputGoods;
        this.outputGoods = outputGoods;
    }

    public ClientOffer(){
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

    public List<String> getInputGoodsNames() {
    	List<String> inputGoodsNames = new LinkedList<String>();
    	for(Good inputGood : inputGoods) {
    		inputGoodsNames.add( inputGood.getTypeOfGoodName() );
    	}

    	return inputGoodsNames;
    }

    public String getClientId() {
        return clientId;
    }

    public void setClientId(String clientId) {
        this.clientId = clientId;
    }

    public List<Good> getInputGoods() {
        return inputGoods;
    }

    public void setInputGoods(List<Good> inputGoods) {
        this.inputGoods = inputGoods;
    }

    public List<Good> getOutputGoods() {
        return outputGoods;
    }

    public void setOutputGoods(List<Good> outputGoods) {
        this.outputGoods = outputGoods;
    }
}
