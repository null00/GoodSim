package pl.edu.agh.goodsim.serviceregistry;

/**
 * @author Mateusz Rudnicki <rudnicki@student.agh.edu.pl>
 */
public class Reputation {
	private String agentName;
	private int positives;
	private int negatives; // negatives is positive int

	public Reputation(String agentName, int positives, int negatives) {
		super();
		this.agentName = agentName;
		this.positives = positives;
		this.negatives = (negatives < 0 ? -negatives : negatives);
	}

	public Reputation(String agentName) {
		this(agentName, 0, 0);
	}

	public void putComment(int points) {
		if (points > 0)
			positives += points;
		else if (points < 0)
			negatives -= points;
		else
			return; // TODO what if points == 0 ?
	}

	public String getAgentName() {
		return agentName;
	}

	public int getPositives() {
		return positives;
	}

	public int getNegatives() {
		return negatives;
	}
}
