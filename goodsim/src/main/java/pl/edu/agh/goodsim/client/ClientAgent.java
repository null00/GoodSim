package pl.edu.agh.goodsim.client;

import com.thoughtworks.xstream.XStream;
import jade.core.Agent;
import pl.edu.agh.goodsim.document.ClientOffer;
import pl.edu.agh.goodsim.document.Contract;
import pl.edu.agh.goodsim.document.Offer;
import pl.edu.agh.goodsim.document.Supply;
import pl.edu.agh.goodsim.entity.Good;
import pl.edu.agh.goodsim.type.ContractStatusEnum;
import pl.edu.agh.goodsim.type.OfferStatusEnum;

import java.util.*;
import java.util.Map.Entry;

/**
 * @author Mateusz Rudnicki <rudnicki@student.agh.edu.pl>
 */
public class ClientAgent extends Agent {

	private static final long serialVersionUID = 1L;
	private Map<String, TaskValues> contracts;

	public ClientAgent() {

	}

	@Override
	protected void setup() {
		// XXX: Jade Agent initialization
		System.out.println("Hello! I am the ClientAgent: " + getAID().getName());
	}

	@Override
	protected void takeDown() {
		// XXX: Clean up the agent
		System.out.println("I'm dead!");
	}

	/*
	 * ==================== MAS FUNCTIONS (JADE) ====================
	 */

	public void receiveOffer(Offer offer) {
		TaskValues taskValues = contracts.get(offer.getSessionId());
		String contractorID = offer.getContractorId();
		LinkedList<Map<String, List<OfferStatus>>> offers = (LinkedList<Map<String, List<OfferStatus>>>) taskValues.getOffers();
		Map<String, List<OfferStatus>> offerStatusMap = offers.getLast();
		LinkedList<OfferStatus> offerStatusList = (LinkedList<OfferStatus>) offerStatusMap.get(contractorID);
		OfferStatus offerStatus = offerStatusList.getLast();
		offerStatus.setOffer(offer);
		offerStatus.setStatus(OfferStatusEnum.OFFER_RECEIVED);
	}

	public void receiveRefusal(String sessionId, String contractorId, int offerCount) {
		TaskValues taskValues = contracts.get(sessionId);
		LinkedList<Map<String, List<OfferStatus>>> offers = (LinkedList<Map<String, List<OfferStatus>>>) taskValues.getOffers();
		Map<String, List<OfferStatus>> offerStatusMap = offers.getLast();
		LinkedList<OfferStatus> offerStatusList = (LinkedList<OfferStatus>) offerStatusMap.get(contractorId);
		OfferStatus offerStatus = offerStatusList.getLast();
		offerStatus.setStatus(OfferStatusEnum.CANCEL);
		if(checkRefusalStatuses(taskValues)) {
			// XXX: przerwanie kontraktu 
			taskValues.getContract().setContractStatus(ContractStatusEnum.CLOSED);
		}
	}

	private boolean checkRefusalStatuses(TaskValues taskValues) {
		LinkedList<Map<String, List<OfferStatus>>> offers = (LinkedList<Map<String, List<OfferStatus>>>) taskValues.getOffers();
		Map<String, List<OfferStatus>> offerStatusMap = offers.getLast();
		for (Entry<String, List<OfferStatus>> pairs : offerStatusMap.entrySet()) {
			List<OfferStatus> statuses = pairs.getValue();
			for (OfferStatus status : statuses) {
				if (status.getStatus() != OfferStatusEnum.CANCEL)
					return false;
			}
		}
		return true;

		//		receiveRefusal - tak samo to - ale tu widz� na ko�cu tej metody wykonanie metody prywatnej nazywaj�cej si� np. CheckRefusalStatuses. Jej funkcj� by�o by:
		//		1. Przelecie� si� po wszystkich contractorId z mapy taskValues.offers.getLast().
		//		2. Sprawdzi� czy wszystkie s� w stanie 0x9
		//		3. Jak tak to znaczy, �e le�� wszystkie negocjacje i trzeba przerwa� w zale�no�ci w jakim stanie jest kontrakt - negocjacje, anulowa� wykonanie, przerwa� wykonanie.

	}

	public void reciveContractSign(String sessionId, String contractorId) {
		TaskValues taskValues = contracts.get(sessionId);
		LinkedList<Map<String, List<OfferStatus>>> offers = (LinkedList<Map<String, List<OfferStatus>>>) taskValues.getOffers();
		Map<String, List<OfferStatus>> offerStatusMap = offers.getLast();
		LinkedList<OfferStatus> offerStatusList = (LinkedList<OfferStatus>) offerStatusMap.get(contractorId);
		OfferStatus offerStatus = offerStatusList.getLast();
		offerStatus.setStatus(OfferStatusEnum.SIGNED);
	}

	public void reciveRenegotiation(Offer offer) {
		TaskValues taskValues = contracts.get(offer.getSessionId());
		String contractorID = offer.getContractorId();
		LinkedList<Map<String, List<OfferStatus>>> offers = (LinkedList<Map<String, List<OfferStatus>>>) taskValues.getOffers();
		Map<String, List<OfferStatus>> offerStatusMap = offers.getLast();
		LinkedList<OfferStatus> offerStatusList = (LinkedList<OfferStatus>) offerStatusMap.get(contractorID);
		OfferStatus offerStatus = offerStatusList.getLast();
		offerStatus.setOffer(offer);
		offerStatus.setStatus(OfferStatusEnum.OFFER_RECEIVED);
	}

	/*
	 * ===================================================== 
	 * MAS/JMX FUNCTIONS (What we can delegate to the agent)
	 * =====================================================
	 */

	public void receiveGood(Good good, String sessionID, int container) {
		TaskValues contract = contracts.get(sessionID);
		Offer offer = contract.getContract().getOffer();
		switch (container) {
		case 1: {
			if (inSupplies(good, offer.getSuppliesOut())) {
				contract.addGoodForSale(good);
			}
			break;
		}
		case 2: {
			if (inSupplies(good, offer.getSuppliesIn())) {
				contract.addGoodReceived(good);
			}
			break;
		}
		default: {
			System.err.println("Unknown container ID.");
			break;
		}
		}
	}

	private boolean inSupplies(Good good, List<Supply> supplies) {
		Iterator<Supply> iter = supplies.iterator();
		while (iter.hasNext()) {
			Supply supply = iter.next();
			if (supply.getGood().equals(good)) {
				return true;
			}
		}
		return false;
	}

	/*
	 * ===================================================== 
	 * MAS/JMX FUNCTIONS (What we can delegate to the agent)
	 * =====================================================
	 */

	public Set<String> getContractList(ContractStatusEnum status) {
		Set<String> filteredContracts = new HashSet<String>();
		for (Entry<String, TaskValues> pairs : contracts.entrySet()) {
			ContractStatusEnum contractStatus = pairs.getValue().getContract().getContractStatus();
			if ((contractStatus.getValue() & status.getValue()) != 0) {
				String sessionID = pairs.getKey();
				filteredContracts.add(sessionID);
			}
		}
		return filteredContracts;
	}

	/*
	 * ============= JMX FUNCTIONS =============
	 */

	public String getTaskValues(String sessionID) {
		TaskValues contract = (TaskValues) contracts.get(sessionID);
		XStream xStream = new XStream();
		xStream.alias("TaskValues", TaskValues.class);
		String xml = xStream.toXML(contract);
		return xml;
	}

	public String getContract(String sessionID) {
		TaskValues contract = (TaskValues) contracts.get(sessionID);
		Contract _contract = contract.getContract();
		XStream xStream = new XStream();
		xStream.alias("Contract", Contract.class);
		String xml = xStream.toXML(_contract);
		return xml;
	}

	public int getRenegotiationCount(String sessionID) {
		// FIXME: dunno if this is the way this should go
		TaskValues contract = contracts.get(sessionID);
		return contract.getContract().getOffer().getNegotiationCount();
	}

	public int getOfferCount(String sessionid, int renegotiation) {
		// TODO: first we have to change the offers list from TaskValues
		return 0;
	}

	public List<String> getOffer() {
		// TODO: first we have to change the offers list from TaskValues
		return null;
	}

	public String getOffer(String sessionid, int renegotiation, int countOffer, String contractorid) {
		// TODO: Zwraca zserializowaną do XML ofertę.
		// TODO: first we have to change the offers list from TaskValues
		return null;
	}

	public void prepareTask(String sessionId, String mainSessionId, List<String> goodsTypes) {
		// FIXME:
		Offer offer = new Offer();
		Contract contract = new Contract(offer);
		contracts.put(sessionId, new TaskValues(contract));
	}

	public void prepareIntention(String intention) {
		// TODO:
	}

	public void sendIntentions(String sessionId, int renegotiation, int offerNumber, String contractorId) {
		// TODO:
	}

	/*
	 * ========================================== 
	 * MAS/JMX EVENTS (What can agent do himself) 
	 * ==========================================
	 */

	public void getServices(List<String> goodsTypes) {
		// TODO:
	}

	void sendIntention(ClientOffer intention) {
		// TODO:
	}

	void sendCounterOffer(ClientOffer clientOffer) {
		// TODO:
	}

	void sendNegotiationBreak(String sessionid, String contractorid) {
		// TODO:
	}

	void signContract(Contract contract) {
		// TODO:
	}

	void sendRenegotiation(ClientOffer clientOffer) {
		// TODO:
	}

	void sendGood(String sessionid, String contractorid, Good good) {
		// TODO:
	}
}
