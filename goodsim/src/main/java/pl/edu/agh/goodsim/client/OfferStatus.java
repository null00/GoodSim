package pl.edu.agh.goodsim.client;

import java.util.Date;

import pl.edu.agh.goodsim.document.ClientOffer;
import pl.edu.agh.goodsim.document.Offer;
import pl.edu.agh.goodsim.type.OfferStatusEnum;

/**
 * @author Mateusz Rudnicki <rudnicki@student.agh.edu.pl>
 */
public class OfferStatus {
	private String contractorid;
	private ClientOffer intention;
	private Date sendedIntentionDate;
	private Date receivedDate;
	private Offer offer;
	private OfferStatusEnum status;
	private Boolean refusal;

	public OfferStatus(String contractorid, ClientOffer intention) {
		super();
		this.contractorid = contractorid;
		this.intention = intention;
	}

	public Date getSendedIntentionDate() {
		return sendedIntentionDate;
	}

	public void setSendedIntentionDate(Date sendedIntentionDate) {
		this.sendedIntentionDate = sendedIntentionDate;
	}

	public Date getReceivedDate() {
		return receivedDate;
	}

	public void setReceivedDate(Date receivedDate) {
		this.receivedDate = receivedDate;
	}

	public Offer getOffer() {
		return offer;
	}

	public void setOffer(Offer offer) {
		this.offer = offer;
	}

	public Boolean getRefusal() {
		return refusal;
	}

	public void setRefusal(Boolean refusal) {
		this.refusal = refusal;
	}

	public String getContractorid() {
		return contractorid;
	}

	public ClientOffer getIntention() {
		return intention;
	}

	public void setStatus(OfferStatusEnum status) {
		this.status = status;
	}

	public OfferStatusEnum getStatus() {
		return status;
	}
}
