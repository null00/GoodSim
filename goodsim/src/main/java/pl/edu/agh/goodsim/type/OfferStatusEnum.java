package pl.edu.agh.goodsim.type;

public enum OfferStatusEnum {
	
    PREPARING_INTENTION(0x0), // stan początkowy (czekamy aż przez JMX
                              // zostanie wypełniona intencja)
    INTENTIN_READY(0x1),      // stan po wprowadzeniu treści intencji
                              // przez JMX, można wysłać do producenta
    WAITING_FOR_OFFER(0x2),   // stan po wysłaniu intencji do producenta
                              // jak przyjdzie refusal to przejście do
                              // 0x9
    OFFER_RECEIVED(0x3),      // stan po odebraniu oferty od producenta
                              // z tego stanu mozna przejsc do:
                              // 0x4, 0x6 lub 0x9
    PREPAING_COUNTOFFER(0x4), // czekamy az przez JMX zostanie
                              // wypełniona kontroferta
    COUNTOFFER_READY(0x5),    // stan po wprowadzeniu tresci
                              // kontroferty przez JMX, można wysłać i
                              // wtedy przejście do 0x2
    SIGING(0x6),              // stan po wyslaniu checi podpisania, jak
                              // zostanie odebrone refusal to przejscie
                              // do 0x9
    SIGNED(0x7),              // stan po odebraniu potwierdzenia
                              // podpisania
    CANCEL(0x9);
    
	private int value;
    
    OfferStatusEnum(int value) {
    	this.value = value;
    }
    
    public int getValue() {
    	return value;
    }
}