package pl.edu.agh.goodsim.client;

import jade.core.Agent;

import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import pl.edu.agh.goodsim.document.ClientOffer;
import pl.edu.agh.goodsim.document.Contract;
import pl.edu.agh.goodsim.document.Offer;
import pl.edu.agh.goodsim.document.Supply;
import pl.edu.agh.goodsim.entity.Good;
import pl.edu.agh.goodsim.type.ContractStatus;

import com.thoughtworks.xstream.XStream;

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
	 * 	 ====================
	 *	 MAS FUNCTIONS (JADE)
	 * 	 ====================
	 */
	
	public void receiveOffer(Offer offer) {
		Contract contract = new Contract(offer);
		contracts.put(offer.getSessionId(), new TaskValues(contract));
	}

	public void receiveRefusal(String sessionId, String contractorId, int offerCount) {
		// TODO: where to put the refusal info?
		TaskValues contract = contracts.get(sessionId);

	}

	public void reciveContractSign(String sessionId) {
		// TODO: confirmation from the contractor and moving the contract to the
		// awaiting phase
	}

	public void reciveRenegotiation(Offer offer) {
		// TODO: first use?
		// TODO: init new negotiation -> TaskValues.offers && prepareTask
		Contract contract = new Contract(offer);
		contracts.put(offer.getSessionId(), new TaskValues(contract));
	}

	/*
	 * 	 =====================================================
	 *	 MAS/JMX FUNCTIONS (What we can delegate to the agent)
	 * 	 =====================================================
	 */
	
	public void receiveGood(Good good, String sessionID, int container) {
		// XXX: is the good verification ok?
		TaskValues contract = contracts.get(sessionID);
		Offer offer = contract.getContract().getOffer();
		switch (container) {
		case 1: {
			if(inSupplies(good, offer.getSuppliesOut())) {
				contract.addGoodForSale(good);
			}
			break;
		}
		case 2: {
			if(inSupplies(good, offer.getSuppliesIn())) {
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
		while(iter.hasNext()) {
			Supply supply = iter.next();
			if(supply.getGood().equals(good)) {
				return true;
			}
		}
		return false;
	}
	
	/*
	 * 	 =====================================================
	 *	 MAS/JMX FUNCTIONS (What we can delegate to the agent)
	 * 	 =====================================================
	 */

	public Set<String> getContractList(ContractStatus status) {
		// TODO: is this the way it should work?
		Set<String> filteredContracts = new HashSet<String>();
		Iterator<Map.Entry<String, TaskValues>> it = contracts.entrySet().iterator();
		while (it.hasNext()) {
			Entry<String, TaskValues> pairs = it.next();
			String sessionID = (String) pairs.getKey();
			TaskValues contract = (TaskValues) pairs.getValue();
			 if(contract.getContract().getContractStatus() == status)
				 filteredContracts.add(sessionID);
		}
		return filteredContracts;
	}
	
	/*
	 * 	 =============
	 *	 JMX FUNCTIONS
	 * 	 =============
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
		// TODO:
	}

	public void prepareIntention(String intention) {
		// TODO:
	}

	public void sendIntentions(String sessionId, int renegotiation, int offerNumber, String contractorId) {
		// TODO:
	}
	
	/*
	 * 	 ==========================================
	 *	 MAS/JMX EVENTS (What can agent do himself)
	 * 	 ==========================================
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
