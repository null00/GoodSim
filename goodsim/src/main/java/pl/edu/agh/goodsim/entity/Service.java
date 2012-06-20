package pl.edu.agh.goodsim.entity;
import java.util.Map;

import pl.edu.agh.goodsim.type.ServiceType;

public class Service {

	private ServiceType type;

	private Map<String, Double> requiredFeaturesByNames;

	public Map<String, Double> getRequiredFeaturesByNames() {
		return requiredFeaturesByNames;
	}

}
