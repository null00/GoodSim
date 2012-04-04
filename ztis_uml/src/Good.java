import java.util.Map;

/**
 * @author Krzysztof Mycek
 */
public class Good {

   private TypeOfGood typeOfGood;
   private Map<String, Object> attributeValues;

   public TypeOfGood getTypeOfGood() {
      return typeOfGood;
   }

   public Map<String, Object> getAttributeValues() {
      return attributeValues;
   }

   public Object getAttributeValue(String attributeName) {
      return attributeValues.get(attributeName);
   }

}
