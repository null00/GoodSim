import java.util.List;


public class ServiceType {
    private List<TypeOfGood> inGoods;

    private List<TypeOfGood> outGoods;

    private List<String> negotiableFeatures;


    public List<TypeOfGood> getInGoodsTypes() {
        return inGoods;
    }

    public List<TypeOfGood> getOutGoodsTypes() {
        return outGoods;
    }

    public List<String> getNegotiableFeatures() {
        return negotiableFeatures;
    }



}
