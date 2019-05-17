package wang.relish.tetris.config;

import java.awt.Point;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.dom4j.Element;



@SuppressWarnings("serial")
public class SystemConfig implements Serializable{
	/**
	 * 方块的边界x的最小值
	 */
	private final int minX;
	/**
	 * 方块的边界x的最大值
	 */
	private final int maxX;
	/**
	 * 方块的边界y的最小值
	 */
	private final int minY;
	/**
	 * 方块的边界y的最大值
	 */
	private final int maxY;
	/**
	 * 等级
	 */
	private final int levelUp;
	/**
	 * 游戏输的方块号
	 */
	private final int loseIdx;
	/**
	 * 方块类型的确认
	 */
	private final List<Point[]> typeConfig;
	/**
	 * 方块旋转方式的确认
	 */
	private final List<Boolean> typeRound;
	/**
	 * 加分，即图片数字与整形数字的对应关系
	 */
	private final Map<Integer, Integer>plusPoint;
	/**
	 * 初始化系统的配置
	 * @param system
	 */
	public SystemConfig(Element system){
		//反射获得x最小值与配置文件的对应关系
		this.minX = Integer.parseInt(system.attributeValue("minX"));
		//反射获得x最大值与配置文件的对应关系
		this.maxX = Integer.parseInt(system.attributeValue("maxX"));
		//反射获得y最小值与配置文件的对应关系
		this.minY = Integer.parseInt(system.attributeValue("minY"));
		//反射获得y最大值与配置文件的对应关系
		this.maxY = Integer.parseInt(system.attributeValue("maxY"));
		//反射获得升级与配置文件的对应关系
		this.levelUp = Integer.parseInt(system.attributeValue("levelUp"));
		//反射获得游戏失败后方块与配置文件的对应关系
		this.loseIdx = Integer.parseInt(system.attributeValue("loseIdx"));
		@SuppressWarnings("unchecked")
		List<Element> rects = system.elements("rect");
		typeConfig = new ArrayList<Point[]>(rects.size());
		typeRound = new ArrayList<Boolean>(rects.size());
		for (Element rect : rects) {
			//是否旋转
			this.typeRound.add(Boolean.parseBoolean(rect.attributeValue("round")));
			//获得坐标对象
			@SuppressWarnings("unchecked")
			List<Element> pointConfig = rect.elements("point");
			//创建Point对象数组
			Point[] points = new Point[pointConfig.size()];
			//初始化Point对象
			for (int i = 0; i < points.length; i++) {
				int x = Integer.parseInt(pointConfig.get(i).attributeValue("x"));
				int y = Integer.parseInt(pointConfig.get(i).attributeValue("y"));
				points[i] = new Point(x,y);
			}
			//把Point对象数组放到typeConfig中
			typeConfig.add(points);
		}
		//获得连消加分配置
		this.plusPoint = new HashMap<Integer,Integer>();
		@SuppressWarnings("unchecked")
		List<Element> plusPointCfg = system.elements("plusPoint");
		for (Element cfg : plusPointCfg) {
			int rm = Integer.parseInt(cfg.attributeValue("rm"));
			int point = Integer.parseInt(cfg.attributeValue("point"));
			this.plusPoint.put(rm, point);
		}
	}
	public int getMinX() {
		return minX;
	}

	public int getMaxX() {
		return maxX;
	}

	public int getMinY() {
		return minY;
	}

	public int getLevelUp() {
		return levelUp;
	}
	public int getMaxY() {
		return maxY;
	}

	public List<Point[]> getTypeConfig() {
		return typeConfig;
	}
	public List<Boolean> getTypeRound() {
		return typeRound;
	}
	public Map<Integer, Integer> getPlusPoint() {
		return plusPoint;
	}
	public int getLoseIdx() {
		return loseIdx;
	}

}
