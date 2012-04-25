package pl.edu.agh.goodsim.serviceregistry;

import java.util.List;
import java.util.LinkedList;
import java.util.Map;
import java.util.HashMap;

import pl.edu.agh.goodsim.type.ServiceType;
import pl.edu.agh.goodsim.type.TypeOfAttribute;
import pl.edu.agh.goodsim.type.TypeOfGood;

/**
 * @author Mateusz Rudnicki <rudnicki@student.agh.edu.pl>
 */
public class ServiceRegistry {
	private Map<String, TypeOfAttribute> typesOfAttributes;
	private Map<String, TypeOfGood> typesOfGoods;
	private Map<String, ServiceType> servicesTypes;

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

	// Global Mappings
	public TypeOfAttribute getTypeOfAttribute(String typeOfAttributeName) {
		return typesOfAttributes.get(typeOfAttributeName);
	}

	public TypeOfGood getTypeOfGood(String typeOfGoodName) {
		return typesOfGoods.get(typeOfGoodName);
	}

	public ServiceType getServiceType(String serviceTypeName) {
		return servicesTypes.get(serviceTypeName);
	}
}
