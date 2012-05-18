package pl.edu.agh.goodsim.type;
import java.util.List;
import java.util.Map;


public class ServiceType {
    private List<TypeOfGood> inGoods;

    private List<TypeOfGood> outGoods;

    private List<String> negotiableFeatures;

    private Map<TypeOfGood, List<String>> negotiableFeaturesByTypeOfGood;


    public List<TypeOfGood> getInGoodsTypes() {
        return inGoods;
    }

    public List<TypeOfGood> getOutGoodsTypes() {
        return outGoods;
    }

    public List<String> getNegotiableFeatures() {
        return negotiableFeatures;
    }

    public Map<TypeOfGood, List<String>> getNegotiableFeaturesByTypeOfGood() {
        return negotiableFeaturesByTypeOfGood;
    }



}
