package pl.edu.agh.goodsim.document;

import java.io.Serializable;
import java.util.Date;

import pl.edu.agh.goodsim.entity.Good;

/**
 * @author Jaroslaw Szczesniak (null@student.agh.edu.pl)
 */
public class Supply implements Serializable {

	private static final long serialVersionUID = 1L;
	private Date dueDate;
	private Good good;
	
	public Supply() {
		
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
