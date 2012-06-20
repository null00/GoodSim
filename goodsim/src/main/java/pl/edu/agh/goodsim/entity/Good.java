package pl.edu.agh.goodsim.entity;

import com.google.common.collect.Maps;

import java.util.Map;

/**
 * @author Krzysztof Mycek
 */
public class Good {

    private String typeOfGoodName;
    private Map<String, Double> attributeValues;

    public String getTypeOfGoodName() {
        return typeOfGoodName;
    }

    public Map<String, Double> getAttributeValues() {
        return attributeValues;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Good good = (Good) o;

        if (typeOfGoodName != null ? !typeOfGoodName.equals(good.typeOfGoodName) : good.typeOfGoodName != null)
            return false;

        return Maps.difference(this.attributeValues, good.attributeValues).areEqual();
    }

    public void setTypeOfGoodName(String typeOfGoodName) {
        this.typeOfGoodName = typeOfGoodName;
    }

    public void setAttributeValues(Map<String, Double> attributeValues) {
        this.attributeValues = attributeValues;
    }
}
