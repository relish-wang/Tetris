package wang.relish.tetris.config;
import java.io.Serializable;

import org.dom4j.Element;

@SuppressWarnings("serial")
public class DataConfig implements Serializable{
	/**
	 * 最大行
	 */
	private final int maxRow;
	/**
	 * 数据接口确认为dataA
	 */
	private final DataInterfaceConfig dataA;
	/**
	 * 数据接口确认为dataB
	 */
	private final DataInterfaceConfig dataB;
	/**
	 * 初始化接口确认
	 * @param data
	 */
	public DataConfig(Element data){
		this.maxRow = Integer.parseInt(data.attributeValue("maxRow"));
		this.dataA = new DataInterfaceConfig(data.element("dataA"));
		this.dataB = new DataInterfaceConfig(data.element("dataB"));
	}
	public DataInterfaceConfig getDataA() {
		return dataA;
	}
	public DataInterfaceConfig getDataB() {
		return dataB;
	}
	public int getMaxRow() {
		return maxRow;
	}

}
