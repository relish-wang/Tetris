package wang.relish.tetris.config;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.dom4j.Element;

/**
 * @author dell
 *
 */

@SuppressWarnings("serial")
public class FrameConfig implements Serializable{
	/**
	 * 标题属性
	 */
	private final String title;
	/**
	 * 窗口上升高度
	 */
	private final int windowUp;
	/**
	 * 窗口宽度
	 */
	private final int width;
	/**
	 * 窗口高度
	 */
	private final int height;
	/**
	 * 窗口挡板
	 */
	private final int padding;
	/**
	 * 窗口边界
	 */
	private final int border;
	/**
	 * 方块大小
	 */
	private final int sizeRol;
	/**
	 * 游戏失败图片
	 */
	private final int loseIdx;
	/**
	 * 按钮属性
	 */
	private final ButtonConfig buttonConfig;
	/**
	 * 图层属性
	 */
	private List<LayerConfig> layersConfig;

	//title="俄罗斯方块" windowUp="32" width="1162" height="686" padding="16" border="7"
	public FrameConfig(Element frame){
		//获取窗口宽度
		this.width = Integer.parseInt(frame.attributeValue("width"));
		//获取窗口高度
		this.height = Integer.parseInt(frame.attributeValue("height"));
		//获取边框粗细
		this.border = Integer.parseInt(frame.attributeValue("border"));
		//获取边框边内距
		this.padding = Integer.parseInt(frame.attributeValue("padding"));
		//获取标题
		this.title = frame.attributeValue("title");
		//获取窗口拔高
		this.windowUp = Integer.parseInt(frame.attributeValue("windowUp"));
		//获得图片尺寸位偏移量
		this.sizeRol = Integer.parseInt(frame.attributeValue("sizeRol"));
		//游戏失败索引号
		this.loseIdx = Integer.parseInt(frame.attributeValue("loseIdx"));
		//获取窗体属性
		@SuppressWarnings("unchecked")
		List<Element> layers = frame.elements("layer");
		layersConfig = new ArrayList<LayerConfig>();
		//获取所有窗体属性
		for(Element layer: layers){
			//设置单个窗体属性
			LayerConfig lc = new LayerConfig(
					layer.attributeValue("className"),
					Integer.parseInt(layer.attributeValue("x")),
					Integer.parseInt(layer.attributeValue("y")),
					Integer.parseInt(layer.attributeValue("w")),
					Integer.parseInt(layer.attributeValue("h"))
			);
			layersConfig.add(lc);
		}
		//初始化按钮属性
		buttonConfig = new ButtonConfig(frame.element("button"));
	}

	public int getSizeRol() {
		return sizeRol;
	}

	public String getTitle() {
		return title;
	}

	public int getWindowUp() {
		return windowUp;
	}

	public int getWidth() {
		return width;
	}

	public int getHeight() {
		return height;
	}

	public int getPadding() {
		return padding;
	}

	public int getBorder() {
		return border;
	}

	public int getLoseIdx() {
		return loseIdx;
	}

	public List<LayerConfig> getLayersConfig() {
		return layersConfig;
	}

	public ButtonConfig getButtonConfig() {
		return buttonConfig;
	}

}
