package pl.edu.agh.goodsim.document;

import java.util.LinkedList;
import java.util.List;

import pl.edu.agh.goodsim.entity.Good;

public class ClientOffer {
	private String ClientId;
	private List<Good> InputGoods;
	private List<Good> OutputGoods;

	public ClientOffer() {
		super();
	}

	public String getClientId() {
		return ClientId;
	}

	public List<String> getInputGoodsTypes() {
		List<String> inputGoodTypes = new LinkedList<String>();
		for(Good good : InputGoods) {
			inputGoodTypes.add(good.getTypeOfGoodName());
		}
		return inputGoodTypes;
	}

	public List<String> getOutputGoodsTypes() {
		List<String> outputGoodTypes = new LinkedList<String>();
		for(Good good : OutputGoods) {
			outputGoodTypes.add(good.getTypeOfGoodName());
		}
		return outputGoodTypes;
	}

	public List<Good> getInputGoods() {
		return InputGoods;
	}

	public List<Good> getOutputGoods() {
		return OutputGoods;
	}

}
