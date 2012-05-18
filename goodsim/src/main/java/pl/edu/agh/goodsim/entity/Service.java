package pl.edu.agh.goodsim.entity;
import java.util.Map;
import pl.edu.agh.goodsim.type.ServiceType;

public class Service {

	private ServiceType type;

	private Map<String, Object> requiredFeaturesByNames;

	public Map<String, Object> getRequiredFeaturesByNames() {
		return requiredFeaturesByNames;
	}

}
