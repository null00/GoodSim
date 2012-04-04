import java.util.List;

public class ClientOffer {
	private int ClientId;
	private List<Good> InputGoods;
	private List<Good> OutputGoods;

	public ClientOffer() {
		super();
	}
	
	public int getClientId() {
		return ClientId;
	}

	public List<TypeOfGood> getInputGoodsTypes() {
		//TODO
		return null;
	}

	public List<TypeOfGood> getOutputGoodsTypes() {
		//TODO
		return null;
	}
	
	public List<Good> getInputGoods() {
		return InputGoods;
	}

	public List<Good> getOutputGoods() {
		return OutputGoods;
	}

}
