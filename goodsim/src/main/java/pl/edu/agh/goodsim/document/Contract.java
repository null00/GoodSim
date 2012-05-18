package pl.edu.agh.goodsim.document;
import java.io.Serializable;

import pl.edu.agh.goodsim.type.ContractStatus;

/**
 * @author Jaroslaw Szczesniak (null@student.agh.edu.pl)
 */
public class Contract implements Serializable {

	private static final long serialVersionUID = 1L;
	private String sessionId;
	private String customerId;
	private String contractorId;
	private ContractStatus status;
	private Offer offer;

	public Contract() {

	}
	
	public Contract(Offer offer) {
		this.offer = offer;
		initContract();
	}
	
	private void initContract() {
		this.sessionId = this.offer.getSessionId();
		this.contractorId = this.offer.getContractorId();
	}

	public String getSessionId() {
		return sessionId;
	}

	public void setSessionId(String sessionId) {
		this.sessionId = sessionId;
	}

	public String getCustomerId() {
		return customerId;
	}

	public void setCustomerId(String customerId) {
		this.customerId = customerId;
	}

	public String getContractorId() {
		return contractorId;
	}

	public void setContractorId(String contractorId) {
		this.contractorId = contractorId;
	}

	public Offer getOffer() {
		return offer;
	}

	public void setOffer(Offer offer) {
		this.offer = offer;
	}
	
	public void setContractStatus(ContractStatus status) {
		this.status = status;
	}
	
	public ContractStatus getContractStatus() {
		return this.status;
	}

}
