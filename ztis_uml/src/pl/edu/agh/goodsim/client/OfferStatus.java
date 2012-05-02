package pl.edu.agh.goodsim.client;

import java.util.Date;

import pl.edu.agh.goodsim.document.ClientOffer;
import pl.edu.agh.goodsim.document.Offer;

/**
 * @author Mateusz Rudnicki <rudnicki@student.agh.edu.pl>
 */
public class OfferStatus {
	private String contractorid;
	private ClientOffer intention;
	private Date sendedIntentionDate;
	private Date receivedDate;
	private Offer offer;
	private Boolean refusal;
}
