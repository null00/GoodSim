/**
 * @author Jarosław Szczęśniak
 */
public class Contract {

	private int sessionId;
	private int customerId;
	private int contractorId;
	private Offer offer;
		
	public int getSessionId() {
		return this.sessionId;
	}
	
	public int getCustomerId() {
		return this.customerId;
	}
	
	public int getContractorId() {
		return this.contractorId;
	}
	
	public Offer getOffer() {
		return this.offer;
	}
	
	public void renegotiateContract(Counteroffer coffer) {
		this.offer = coffer;
	}
}
