package wang.relish.tetris.config;

import java.io.Serializable;

@SuppressWarnings("serial")
public class LayerConfig implements Serializable{
	/**
	 * 类名
	 */
	private String className;
	/**
	 * 窗口的x坐标
	 */
	private int x;
	/**
	 * 窗口的y坐标
	 */
	private int y;
	/**
	 * 窗口的宽
	 */
	private int w;
	/**
	 * 窗口的高
	 */
	private int h;

	public String getClassName() {
		return className;
	}

	public LayerConfig(String className, int x, int y, int w, int h) {
		this.className = className;
		this.x = x;
		this.y = y;
		this.w = w;
		this.h = h;
	}

	public void setClassName(String className) {
		this.className = className;
	}

	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}

	public int getW() {
		return w;
	}

	public int getH() {
		return h;
	}
}
