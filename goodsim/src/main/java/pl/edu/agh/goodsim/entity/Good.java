package pl.edu.agh.goodsim.entity;

import com.google.common.collect.Maps;

import java.beans.ConstructorProperties;
import java.util.Map;

/**
 * @author Krzysztof Mycek
 */
public class Good {

    private String typeOfGoodName;
    private Map<String, Double> attributeValues;

    @ConstructorProperties({"typeOfGoodName", "attributeValues"})
    public Good(String typeOfGoodName, Map<String, Double> attributeValues) {
        this.typeOfGoodName = typeOfGoodName;
        this.attributeValues = attributeValues;
    }

    public Good(){
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

    public String getTypeOfGoodName() {
        return typeOfGoodName;
    }

    public void setTypeOfGoodName(String typeOfGoodName) {
        this.typeOfGoodName = typeOfGoodName;
    }

    public Map<String, Double> getAttributeValues() {
        return attributeValues;
    }

    public void setAttributeValues(Map<String, Double> attributeValues) {
        this.attributeValues = attributeValues;
    }
}
