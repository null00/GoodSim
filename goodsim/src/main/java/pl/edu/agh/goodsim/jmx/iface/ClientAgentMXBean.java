package pl.edu.agh.goodsim.jmx.iface;

public interface ClientAgentMXBean {

	public String sayHello();

	public String getName();

	public int getCacheSize();

	public void setCacheSize(int size);

}
