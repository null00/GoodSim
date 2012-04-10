/**
 * @author Krzysztof Mycek
 */
public class TypeOfAttribute {

   private String name;
   private Class type;

   public TypeOfAttribute(String name, Class type) {
      this.name = name;
      this.type = type;
   }

   public String getName() {
      return name;
   }

   public Class getType() {
      return type;
   }
}
