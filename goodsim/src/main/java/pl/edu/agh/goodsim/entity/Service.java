package pl.edu.agh.goodsim.entity;
import java.util.Map;

import pl.edu.agh.goodsim.type.ServiceType;

public class Service {

	private ServiceType type;

	private Map<Double, Object> requiredFeaturesByNames;

	public Map<Double, Object> getRequiredFeaturesByNames() {
		return requiredFeaturesByNames;
	}

}
