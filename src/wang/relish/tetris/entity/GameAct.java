package wang.relish.tetris.entity;

import java.awt.Point;
import java.util.List;

import wang.relish.tetris.config.GameConfig;
public class GameAct {

	/**
	 * 方块数组
	 */
	private Point[] actPoints;
	/**
	 * 方块编号
	 */
	private int typeCode;

	private final static int MIN_X = GameConfig.getSystemConfig().getMinX();
	private final static int MAX_X = GameConfig.getSystemConfig().getMaxX();
	private final static int MIN_Y = GameConfig.getSystemConfig().getMinY();
	private final static int MAX_Y = GameConfig.getSystemConfig().getMaxY();

	private final static List<Point[]> TYPE_CONFIG = GameConfig.getSystemConfig().getTypeConfig();
	private final static List<Boolean> TYPE_ROUND = GameConfig.getSystemConfig().getTypeRound();

	public GameAct(int typeCode)
	{
		this.init(typeCode);
	}
	public void init(int typeCode){
		this.typeCode = typeCode;
		//根据typeCode刷新方法
		Point[] points = TYPE_CONFIG.get(typeCode);
		actPoints = new Point[points.length];
		for (int i = 0; i < points.length; i++) {
			actPoints[i] = new Point(points[i].x,points[i].y);
		}
	}
	public Point[] getActPoints() {
		return actPoints;
	}
	/**
	 * 方块的偏移量
	 * @param movX x方向的偏移量
	 * @param movY y方向的偏移量
	 */
	public boolean move(int movX,int movY,boolean[][] gameMap){
		//移动处理
		for (int i = 0; i < actPoints.length; i++) {
			int newX = actPoints[i].x + movX;
			int newY = actPoints[i].y + movY;
			if(this.isOverZone(newX, newY,gameMap))
				return false;
		}
		for (int i = 0; i < actPoints.length; i++) {
			actPoints[i].x += movX;
			actPoints[i].y += movY;
		}
		return true;
	}
	/**
	 * 旋转方块
	 * 顺时针算法
	 * A.x = O.y + O.x - B.y;
	 * A.y = O.y - O.x + B.x;
	 */
	public void round(boolean[][] gameMap){
		//正方形方块不需要转
		if(!TYPE_ROUND.get(this.typeCode)){
			return;
		}
		for (int i = 1; i < actPoints.length; i++) {
			int newX = actPoints[0].y + actPoints[0].x - actPoints[i].y;
			int newY = actPoints[0].y - actPoints[0].x + actPoints[i].x;
			if(this.isOverZone(newX, newY,gameMap)){
				return ;
			}
		}
		for (int i = 1; i < actPoints.length; i++) {
			int newX = actPoints[0].y + actPoints[0].x - actPoints[i].y;
			int newY = actPoints[0].y - actPoints[0].x + actPoints[i].x;
			actPoints[i].x = newX;
			actPoints[i].y = newY;
		}
	}
	/**
	 * 判断是否超出边界
	 * @param x
	 * @param y
	 * @return
	 */
	private boolean isOverZone(int x,int y,boolean[][] gameMap){
		return x < MIN_X || x > MAX_X || y < MIN_Y || y > MAX_Y || gameMap[x][y];
	}
	public int getTypeCode() {
		return typeCode;
	}
}
