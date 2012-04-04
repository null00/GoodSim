import java.util.List;

public class DokumentIntencji {
   private int idKonsumenta;
   private List<Good> goodIn;
   private List<Good> goodOut;

   public DokumentIntencji() {
      super();
   }

   public DokumentIntencji(int idKonsumenta, List<Good> goodIn, List<Good> goodOut) {
      super();
      this.idKonsumenta = idKonsumenta;
      this.goodIn = goodIn;
      this.goodOut = goodOut;
   }

   public int getIdKonsumenta() {
      return idKonsumenta;
   }

   public void setIdKonsumenta(int idKonsumenta) {
      this.idKonsumenta = idKonsumenta;
   }

   public List<Good> getGoodIn() {
      return goodIn;
   }

   public void setGoodIn(List<Good> goodIn) {
      this.goodIn = goodIn;
   }

   public List<Good> getGoodOut() {
      return goodOut;
   }

   public void setGoodOut(List<Good> goodOut) {
      this.goodOut = goodOut;
   }

}
