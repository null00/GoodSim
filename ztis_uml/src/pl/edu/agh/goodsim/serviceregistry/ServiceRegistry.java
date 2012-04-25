package pl.edu.agh.goodsim.serviceregistry;

import java.util.List;
import java.util.LinkedList;
import java.util.Map;
import java.util.HashMap;

/**
 * @author Mateusz Rudnicki <rudnicki@student.agh.edu.pl>
 */
public class ServiceRegistry {
	private Map<String, List<String>> registeredServices;
	private Map<String, Reputation> clientReputations;
	private Map<String, Reputation> producentReputations;

	public ServiceRegistry() {
		super();
		registeredServices = new HashMap<String, List<String>>();
		clientReputations = new HashMap<String, Reputation>();
		producentReputations = new HashMap<String, Reputation>();
	}

	public void registerService(String serviceTypeName, String agentName) {
		List<String> agentsNames = registeredServices.get(serviceTypeName);
		if (agentsNames == null) {
			agentsNames = new LinkedList<String>();
			registeredServices.put(serviceTypeName, agentsNames);
		}
		if (!agentsNames.contains(agentName)) {
			agentsNames.add(agentName);
		}
	}

	public void unregisterService(String serviceTypeName, String agentName) {
		List<String> agentsNames = registeredServices.get(serviceTypeName);
		if (agentsNames != null) {
			agentsNames.remove(agentName);
		}
	}

	public Map<String, List<Reputation>> getServices(List<String> goodsTypes) {
		// TODO waiting for specification
		return null;
	}

	public Reputation getClientComment(String clientAgentName) {
		return clientReputations.get(clientAgentName);
	}

	public void putClientComment(String clientAgentName, int points) {
		clientReputations.get(clientAgentName).putComment(points);
	}

	public void putProducentComment(String producentAgentName, int points) {
		producentReputations.get(producentAgentName).putComment(points);
	}
}
