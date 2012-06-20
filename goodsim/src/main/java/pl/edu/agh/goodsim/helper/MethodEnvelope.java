package pl.edu.agh.goodsim.helper;

import java.util.LinkedList;
import java.util.List;

import com.thoughtworks.xstream.XStream;

public class MethodEnvelope {
	private String functionName;
	private List<String> arguments = new LinkedList<String>();
	private List<Class> argumentsClasses = new LinkedList<Class>();

	// necessary for xml deserialization
	public MethodEnvelope() {
	}

	public void addArgument(Object obj) {
		XStream xstream = new XStream();
		arguments.add(xstream.toXML(obj));
		argumentsClasses.add(obj.getClass());
	}

	public void setFunctionName(String functionName) {
		this.functionName = functionName;
	}

	public String getFunctionName() {
		return functionName;
	}

	// public Object getArgument(int index) {
	// return xstream.fromXML( arguments.get(index) );
	// }

	@SuppressWarnings("unchecked")
	public <T> T getArgument(int index) {
		XStream xstream = new XStream();
		return (T) xstream.fromXML(arguments.get(index));
	}

	public String getArgumentClassName(int index) {
		return argumentsClasses.get(index).getName();
	}

	public String toXML() {
		XStream xstream = new XStream();
		return xstream.toXML(this);
	}

	public static MethodEnvelope fromXML(String xml) {
		XStream xstream = new XStream();
		return (MethodEnvelope) xstream.fromXML(xml);
	}

}
