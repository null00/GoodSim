package pl.edu.agh.goodsim.client;

import java.io.Serializable;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import pl.edu.agh.goodsim.document.Contract;
import pl.edu.agh.goodsim.entity.Good;

/**
 * @author Mateusz Rudnicki <rudnicki@student.agh.edu.pl>
 */
public class TaskValues implements Serializable {

	private static final long serialVersionUID = 1L;
	private Contract contract;
	private List<Map<String, List<OfferStatus>>> offers;
	private List<Good> goodsForSale;
	private List<Good> goodsReceived;
	
	public TaskValues(Contract contract) {
		this.contract = contract;
		goodsForSale = new LinkedList<Good>();
		goodsReceived = new LinkedList<Good>();
	}

	public void initNegotiationHistory(String contractorId) {
		offers = new LinkedList<Map<String, List<OfferStatus>>>();
		Map<String, List<OfferStatus>> offerStatusMap = new HashMap<String, List<OfferStatus>>();
		List<OfferStatus> offerStatusList = new LinkedList<OfferStatus>();
		offerStatusMap.put(contractorId, offerStatusList);
		offers.add(offerStatusMap);
	}

	public Contract getContract() {
		return contract;
	}

	public void setContract(Contract contract) {
		this.contract = contract;
	}

	public List<Map<String, List<OfferStatus>>> getOffers() {
		return offers;
	}

	public List<Good> getGoodsForSale() {
		return goodsForSale;
	}

	public List<Good> getGoodsReceived() {
		return goodsReceived;
	}

	public void addGoodForSale(Good goodForSale) {
		this.goodsForSale.add(goodForSale);
	}

	public void addGoodReceived(Good goodReceived) {
		this.goodsReceived.add(goodReceived);
	}
	
}