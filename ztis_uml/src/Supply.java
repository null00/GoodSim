import java.io.Serializable;
import java.util.Date;

/**
 * @author Jaroslaw Szczesniak
 * @author null@student.agh.edu.pl
 */
public class Supply implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Date dueDate;
	private Good good;
	
	public Supply() {
		
	}
	
	public Supply(Good good, Date dueDate) {
		this.good = good;
		this.dueDate = dueDate;
	}
	
	public Date getDueDate() {
		return dueDate;
	}
	public void setDueDate(Date dueDate) {
		this.dueDate = dueDate;
	}
	public Good getGood() {
		return good;
	}
	public void setGood(Good good) {
		this.good = good;
	}
	
	
	
}
