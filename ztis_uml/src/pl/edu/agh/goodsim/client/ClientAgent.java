package pl.edu.agh.goodsim.client;

import java.beans.XMLDecoder;
import java.beans.XMLEncoder;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.io.Writer;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import pl.edu.agh.goodsim.document.ClientOffer;
import pl.edu.agh.goodsim.document.Contract;
import pl.edu.agh.goodsim.document.Offer;
import pl.edu.agh.goodsim.entity.Good;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;

/**
 * @author Mateusz Rudnicki <rudnicki@student.agh.edu.pl>
 */
public class ClientAgent {
	private Map<String, TaskValues> contracts;

	public ClientAgent() {

	}

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

	public void receiveGood(Good good, String sessionID, int container) {
		// TODO: check the type of good with the intention document
		// (ClientOffer)
		TaskValues contract = contracts.get(sessionID);
		switch (container) {
		case 1: {
			contract.addGoodForSale(good);
			break;
		}
		case 2: {
			contract.addGoodReceived(good);
			break;
		}
		default: {
			System.err.println("Unknown container ID.");
			break;
		}
		}
	}

	public List<String> getContractList(int attributes) {
		// TODO: filter the contracts list
		// TODO: need the "phase" field in Contract!

		List<String> filteredContracts = new LinkedList<String>();
		Iterator it = contracts.entrySet().iterator();
		while (it.hasNext()) {
			Map.Entry pairs = (Map.Entry) it.next();
			String sessionID = (String) pairs.getKey();
			TaskValues contract = (TaskValues) pairs.getValue();
			// if(contract.getContract().getPhase() == attributes)
			// filteredContracts.add(sessionID);
		}
		return filteredContracts;
	}

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
		// TODO: dunno if this is the way this should go
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
