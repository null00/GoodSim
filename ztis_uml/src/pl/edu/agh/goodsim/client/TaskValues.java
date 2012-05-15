package pl.edu.agh.goodsim.client;

import java.io.Serializable;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import pl.edu.agh.goodsim.document.ClientOffer;
import pl.edu.agh.goodsim.document.Contract;
import pl.edu.agh.goodsim.document.Offer;
import pl.edu.agh.goodsim.entity.Good;
import pl.edu.agh.goodsim.type.ContractStatus;

/**
 * @author Mateusz Rudnicki <rudnicki@student.agh.edu.pl>
 */
public class TaskValues implements Serializable {

	private static final long serialVersionUID = 1L;
	private Contract contract;
	private List<List<Map<String, OfferStatus>>> offers;
	private List<Good> goodsForSale;
	private List<Good> goodsReceived;

//	public TaskValues(Offer offer, ClientOffer intention) {
//		init();
//		Contract contract = new Contract();
//		contract.setContractStatus(ContractStatus.NEGOTIATION);
//		OfferStatus os = new OfferStatus(offer.getContractorId(), intention);
//		Map<String, OfferStatus> mapa = ((LinkedList<Map<String, OfferStatus>>) ((LinkedList<List<Map<String, OfferStatus>>>) offers).getLast()).getLast();
//		mapa.put(offer.getContractorId(), os);
//	}
	
	public TaskValues(Contract contract) {
		this.contract = contract;
		// offers = new LinkedList<List<Map<String, OfferStatus>>>();
		init();
	}

	public void init() {
		goodsForSale = new LinkedList<Good>();
		goodsReceived = new LinkedList<Good>();
		
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

	public void addGoodForSale(Good goodForSale) {
		this.goodsForSale.add(goodForSale);
	}

	public void addGoodReceived(Good goodReceived) {
		this.goodsReceived.add(goodReceived);
	}
	
}