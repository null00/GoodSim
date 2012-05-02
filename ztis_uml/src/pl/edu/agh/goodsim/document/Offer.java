package pl.edu.agh.goodsim.document;
import java.io.Serializable;
import java.util.AbstractList;

/**
 * @author Jaroslaw Szczesniak
 * @author null@student.agh.edu.pl
 */
public class Offer implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String sessionId;
	private String contractorId;
	private int negotiationCount;
	private AbstractList<Supply> suppliesIn;
	private AbstractList<Supply> suppliesOut;

	public Offer() {

	}

	public String getSessionId() {
		return this.sessionId;
	}
	
	public void setSessionId(String sessionId) {
		this.sessionId = sessionId;
	}

	public String getContractorId() {
		return contractorId;
	}

	public void setContractorId(String contractorId) {
		this.contractorId = contractorId;
	}

	public int getNegotiationCount() {
		return negotiationCount;
	}

	public void setNegotiationCount(int negotiationCount) {
		this.negotiationCount = negotiationCount;
	}
	
	public void addSupplyIn(Supply supplyIn) {
		this.suppliesIn.add(supplyIn);
	}

	public AbstractList<Supply> getSuppliesIn() {
		return suppliesIn;
	}

	public void setSuppliesIn(AbstractList<Supply> suppliesIn) {
		this.suppliesIn = suppliesIn;
	}

	public AbstractList<Supply> getSuppliesOut() {
		return suppliesOut;
	}

	public void setSuppliesOut(AbstractList<Supply> suppliesOut) {
		this.suppliesOut = suppliesOut;
	}
}
