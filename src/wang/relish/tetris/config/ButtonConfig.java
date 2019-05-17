package wang.relish.tetris.config;

import java.io.Serializable;

import org.dom4j.Element;

@SuppressWarnings("serial")
public class ButtonConfig implements Serializable{
	/**
	 * 按键的宽
	 */
	private final int buttonW;
	/**
	 * 按键的高
	 */
	private final int buttonH;
	/**
	 * 开始按键的x坐标
	 */
	private final int startX;
	/**
	 * 开始按键的y坐标
	 */
	private final int startY;
	/**
	 * 配置按键的x坐标
	 */
	private final int userConfigX;
	/**
	 * 配置按键的y坐标
	 */
	private final int userConfigY;
	/**
	 * 初始化按键
	 * @param button
	 */
	public ButtonConfig(Element button){
		//反射确定按键的宽与配置文件
		buttonW = Integer.parseInt(button.attributeValue("w"));
		//反射确定按键的高与配置文件
		buttonH = Integer.parseInt(button.attributeValue("h"));
		//反射确定开始按键的x坐标与配置文件
		startX = Integer.parseInt(button.element("start").attributeValue("x"));
		//反射确定开始按键的y坐标与配置文件
		startY = Integer.parseInt(button.element("start").attributeValue("y"));
		//反射确定配置按键的x坐标与配置文件
		userConfigX = Integer.parseInt(button.element("userConfig").attributeValue("x"));
		//反射确定配置按键的y坐标与配置文件
		userConfigY = Integer.parseInt(button.element("userConfig").attributeValue("y"));
	}
	public int getButtonW() {
		return buttonW;
	}
	public int getButtonH() {
		return buttonH;
	}
	public int getStartX() {
		return startX;
	}
	public int getStartY() {
		return startY;
	}
	public int getUserConfigX() {
		return userConfigX;
	}
	public int getUserConfigY() {
		return userConfigY;
	}
}
