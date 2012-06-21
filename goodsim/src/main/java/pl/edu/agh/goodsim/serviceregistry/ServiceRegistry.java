package pl.edu.agh.goodsim.serviceregistry;

import jade.core.behaviours.CyclicBehaviour;
import jade.jademx.agent.JademxAgent;
import jade.lang.acl.ACLMessage;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import pl.edu.agh.goodsim.helper.MethodEnvelope;
import pl.edu.agh.goodsim.type.ServiceType;
import pl.edu.agh.goodsim.type.TypeOfAttribute;
import pl.edu.agh.goodsim.type.TypeOfGood;

/**
 * @author Mateusz Rudnicki <rudnicki@student.agh.edu.pl>
 */
public class ServiceRegistry extends JademxAgent {
	private static final long serialVersionUID = 1L;
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

	@Override
	protected void setup() {
		super.setup();
		System.out.println("Hello! I am the ServiceRegistryAgent: " + getAID().getName());

		addBehaviour(new CyclicBehaviour(this) {
			public void action() {
				ACLMessage msg = receive();
				if (msg != null && msg.getPerformative() == ACLMessage.REQUEST) {
					ServiceRegistry sr = (ServiceRegistry) myAgent;
					MethodEnvelope me = MethodEnvelope.fromXML(msg.getContent());
					String functionName = me.getFunctionName();
					
					if(functionName.equals("registerService")){
						String serviceTypeName = me.getArgument(0);
						String agentName = me.getArgument(1);
						sr.registerService(serviceTypeName, agentName);

					} else if(functionName.equals("unregisterService")){
						String serviceTypeName = me.getArgument(0);
						String agentName = me.getArgument(1);
						sr.unregisterService(serviceTypeName, agentName);
					} else if(functionName.equals("getServices")){
						String goodType = me.getArgument(0);
						List<String> result = sr.getServices(goodType);
						
						MethodEnvelope replyEnvelope = new MethodEnvelope();
						replyEnvelope.setFunctionName("reply");
						replyEnvelope.addArgument(result);
						ACLMessage reply = msg.createReply();
						reply.setContent( replyEnvelope.toXML() );
						send(reply);
					} else {
						System.out.println( myAgent.getLocalName() + ": Unkown function call");
					}
				} else if (msg != null) {
					System.out.format( "%s: receive msg %n%s%n", myAgent.getLocalName(), msg.getContent() );
				}
				block(1000);
			}
		});
	}

	@Override
	protected void takeDown() {
		super.takeDown();
		System.out.println("ServiceRegistryAgent is dead!");
	}

	public void registerService(String serviceTypeName, String agentName) {
		List<String> agentsNames = registeredServices.get(serviceTypeName);
		if (agentsNames == null) {
			agentsNames = new LinkedList<String>();
			registeredServices.put(serviceTypeName, agentsNames);
		}
		if (!agentsNames.contains(agentName)) {
			agentsNames.add(agentName);
			System.out.format("ServiceRegistry: %s registered as %s%n", agentName, serviceTypeName);
		}
	}

	public void unregisterService(String serviceTypeName, String agentName) {
		List<String> agentsNames = registeredServices.get(serviceTypeName);
		if (agentsNames != null) {
			agentsNames.remove(agentName);
			System.out.format("ServiceRegistry: %s unregistered from %s%n", agentName, serviceTypeName);
		}
	}

	public Map<String, List<Reputation>> getServices(List<String> goodsTypes) {
		Map<String, List<Reputation>> servicesMap = new HashMap<String, List<Reputation>>();
		for (String goodTypeName : goodsTypes) {
			List<Reputation> reputationList = new LinkedList<Reputation>();
			
			List<String> servicesList = registeredServices.get(goodTypeName);
			if (servicesList != null){ 
				for (String agentName : servicesList) {
					reputationList.add(producentReputations.get(agentName));
				}
			servicesMap.put(goodTypeName, reputationList);
			}
		}
		return servicesMap;
	}

	public List<String> getServices(String goodTypeName) {
		List<String> servicesList = registeredServices.get(goodTypeName);

		return (servicesList != null) ? servicesList : new LinkedList<String>();
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
