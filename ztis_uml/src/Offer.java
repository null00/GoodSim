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
	private int sessionId;
	private int contractorId;
	private int negotiationCount;
	private AbstractList<Supply> suppliesIn;
	private AbstractList<Supply> suppliesOut;

	public Offer() {

	}

	public int getSessionId() {
		return this.sessionId;
	}
	
	public void setSessionId(int sessionId) {
		this.sessionId = sessionId;
	}

	public int getContractorId() {
		return contractorId;
	}

	public void setContractorId(int contractorId) {
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
