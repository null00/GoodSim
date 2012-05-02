package pl.edu.agh.goodsim.client;

import java.util.List;
import java.util.Map;

import pl.edu.agh.goodsim.entity.Good;
import pl.edu.agh.goodsim.document.Contract;

/**
 * @author Mateusz Rudnicki <rudnicki@student.agh.edu.pl>
 */
public class TaskValues {
	private Contract contract;
	private List<List<Map<String, OfferStatus>>> offers;
	private List<Good> goodsForSale;
	private List<Good> goodsReceived;
}
