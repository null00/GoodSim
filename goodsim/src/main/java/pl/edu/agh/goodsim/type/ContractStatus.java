package pl.edu.agh.goodsim.type;


/**
 * @author Jaroslaw Szczesniak (null@student.agh.edu.pl)
 */
public enum ContractStatus {
    NEGOTIATION(0x1),
    AWAITING(0x2),
    REALISATION(0x4),
    SETTLEMENT(0x8),
    BEFORE_NEGOTIATION(0x10),
    CLOSED(0x20),
    WAIT_FOR_RESPONSE(0x40);

    private int value;

    ContractStatus(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }

}
