import java.io.Serializable;
import java.util.Date;
import java.util.Map;

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
	private Map<Good, Date> goodsIn;
	private Map<Good, Date> goodsOut;

	public Offer() {

	}

	public int getSessionId() {
		return this.sessionId;
	}

	public void setSessionId(int sessionId) {
		this.sessionId = sessionId;
	}

	public Map<Good, Date> getGoodsIn() {
		return goodsIn;
	}

	public void setGoodsIn(Map<Good, Date> goodsIn) {
		this.goodsIn = goodsIn;
	}

	public Map<Good, Date> getGoodsOut() {
		return goodsOut;
	}

	public void setGoodsOut(Map<Good, Date> goodsOut) {
		this.goodsOut = goodsOut;
	}

}
