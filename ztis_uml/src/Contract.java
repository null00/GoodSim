import java.io.Serializable;

/**
 * @author Jaroslaw Szczesniak
 * @author null@student.agh.edu.pl
 */
public class Contract implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String sessionId;
	private String customerId;
	private String contractorId;
	private Offer offer;

	public Contract() {

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

}
