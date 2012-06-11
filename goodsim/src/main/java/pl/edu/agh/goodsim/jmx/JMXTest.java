package pl.edu.agh.goodsim.jmx;

import java.lang.management.ManagementFactory;

import javax.management.MBeanServer;
import javax.management.ObjectName;

import pl.edu.agh.goodsim.client.ClientAgent;

public class JMXTest {

	/**
	 * @param args
	 */
	public static void main(String[] args) throws Exception {

		MBeanServer mbs = ManagementFactory.getPlatformMBeanServer();
		ObjectName name = new ObjectName("pl.edu.agh.goodsim.client:type=ClientAgent");
		ClientAgent mbean = new ClientAgent();
		mbs.registerMBean(mbean, name);

		System.out.println("Waiting forever...");
		Thread.sleep(Long.MAX_VALUE);
	}
}
