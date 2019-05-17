package wang.relish.tetris.config;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.dom4j.Element;

@SuppressWarnings("serial")
public class DataInterfaceConfig implements Serializable{
	/**
	 * 类名
	 */
	private final String className;
	/**
	 * 确定类名与className对应
	 */
	private final Map<String,String> parm;

	public DataInterfaceConfig(Element dataInterfaceConfig){
		this.className = dataInterfaceConfig.attributeValue("className");
		this.parm = new HashMap<String,String>();

		@SuppressWarnings("unchecked")
		List<Element> parms = dataInterfaceConfig.elements("parm");
		for (Element e : parms) {
			this.parm.put(e.attributeValue("key"),e.attributeValue("value"));
		}
	}

	public String getClassName() {
		return className;
	}

	public Map<String, String> getParm() {
		return parm;
	}
}
