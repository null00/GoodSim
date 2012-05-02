package pl.edu.agh.goodsim.entity;
import java.util.Map;

/**
 * @author Krzysztof Mycek
 */
public class Good {

   private String typeOfGoodName;
   private Map<String, Object> attributeValues;

   public String getTypeOfGoodName() {
      return typeOfGoodName;
   }

   public Map<String, Object> getAttributeValues() {
      return attributeValues;
   }

}
