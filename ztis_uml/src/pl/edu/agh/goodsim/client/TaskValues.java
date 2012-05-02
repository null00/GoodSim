package pl.edu.agh.goodsim.client;

import java.util.HashMap;
import java.util.LinkedList;
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

	public TaskValues(Contract contract) {
		this.contract = contract;
		// offers = new LinkedList<List<Map<String, OfferStatus>>>();
		initOffers();
		goodsForSale = new LinkedList<Good>();
		goodsReceived = new LinkedList<Good>();
	}

	public void initOffers() {
		List<Map<String, OfferStatus>> negotiation = new LinkedList<Map<String, OfferStatus>>();
		Map<String, OfferStatus> offerStatusMap = new HashMap<String, OfferStatus>();

		offers = new LinkedList<List<Map<String, OfferStatus>>>();
		offers.add(negotiation);
		negotiation.add(offerStatusMap);
	}

	public Contract getContract() {
		return contract;
	}

	public void setContract(Contract contract) {
		this.contract = contract;
	}

	public List<List<Map<String, OfferStatus>>> getOffers() {
		return offers;
	}

	public List<Good> getGoodsForSale() {
		return goodsForSale;
	}

	public List<Good> getGoodsReceived() {
		return goodsReceived;
	}
}
