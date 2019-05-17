package wang.relish.tetris.dto;

import java.util.ArrayList;
import java.util.List;
import java.util.Collections;

import wang.relish.tetris.config.GameConfig;
import wang.relish.tetris.entity.GameAct;
import wang.relish.tetris.util.GameFunction;

public class GameDto {

	/**
	 * 游戏边界的宽
	 */
	public static final int GAMEZONE_W = GameConfig.getSystemConfig().getMaxX() + 1;
	/**
	 * 游戏边界的高
	 */
	public static final int GAMEZONE_H = GameConfig.getSystemConfig().getMaxY() + 1;
	/**
	 * 数据库记录
	 */
	private List<Player> dbRecode;
	/**
	 * 本地记录
	 */
	private List<Player> diskRecode;
	/**
	 * 游戏地图
	 */
	private boolean[][] gameMap;
	/**
	 * 下落方块
	 */
	private GameAct gameAct;
	/**
	 * 下一个方块
	 */
	private int next;
	/**
	 * 现在等级
	 */
	private int nowlevel;
	/**
	 * 现在分数
	 */
	private int nowPoint;
	/**
	 * 消下一行
	 */
	private int nowRemoveLine;
	/**
	 * 游戏状态时候开始
	 */
	private boolean start;
	/**
	 * 是否显示阴影
	 */
	private boolean showShadow;
	/**
	 * 暂停
	 */
	private boolean pause;
	/**
	 * 是否使用作弊键
	 */
	private boolean cheat;
	/**
	 * 线程等待时间
	 */
	private long sleepTime;

	/**
	 * 获得构造函数
	 */
	public GameDto(){
		dtoInit();
	}
	/**
	 * dto初始化
	 */
	public void dtoInit(){
		//初始化地图
		this.gameMap = new boolean[GAMEZONE_W][GAMEZONE_H];
		//默认等级为0
		this.nowlevel = 0;
		//默认分数为0
		this.nowPoint = 0;
		//默认消行为0
		this.nowRemoveLine = 0;
		//程序开始时，开始按键不起作用
		this.pause = false;
		//开始前，作弊键不起作用
		this.cheat = false;
		//初始化游戏下落时间
		this.sleepTime = GameFunction.getSleepTimeByLevel(this.nowlevel);
	}

	private List<Player> setFillRecode(List<Player> players){
		//如果进来的是空,那么就创建
		if(players == null){
			players = new ArrayList<Player>();
		}
		//如果记录少于5条，就添加到5条
		while(players.size() < 5){
			players.add(new Player("No Data",0));
		}
		Collections.sort(players);
		return players;
	}

	public List<Player> getDbRecode() {
		return dbRecode;
	}

	public void setNowlevel(int nowlevel) {
		this.nowlevel = nowlevel;
		//计算线程睡眠时间（下落速度）
		this.sleepTime = GameFunction.getSleepTimeByLevel(this.nowlevel);
	}

	public void setDbRecode(List<Player> dbRecode) {
		this.dbRecode = this.setFillRecode(dbRecode);
	}


	public List<Player> getDiskRecode() {
		return diskRecode;
	}

	public void setDiskRecode(List<Player> diskRecode) {

		this.diskRecode = this.setFillRecode(diskRecode);
	}

	public boolean[][] getGameMap() {

		return gameMap;
	}

	public void setGameMap(boolean[][] gameMap) {
		this.gameMap = gameMap;
	}

	public GameAct getGameAct() {
		return gameAct;
	}

	public void setGameAct(GameAct gameAct) {
		this.gameAct = gameAct;
	}

	public int getNext() {
		return next;
	}

	public void setNext(int next) {
		this.next = next;
	}

	public int getNowlevel() {
		return nowlevel;
	}

	public int getNowPoint() {
		return nowPoint;
	}

	public void setNowPoint(int nowPoint) {
		this.nowPoint = nowPoint;
	}

	public int getNowRemoveLine() {
		return nowRemoveLine;
	}

	public void setNowRemoveLine(int nowRemoveLine) {
		this.nowRemoveLine = nowRemoveLine;
	}

	public boolean isStart() {
		return start;
	}

	public void setStart(boolean start) {
		this.start = start;
	}

	public boolean isShowShadow() {
		return showShadow;
	}

	public void changeShowShadow() {
		this.showShadow = !this.showShadow;
	}

	public boolean isPause() {
		return pause;
	}

	public void changePause() {
		this.pause = !this.pause;
	}

	public void setCheat(boolean cheat) {
		this.cheat = cheat;
	}

	public boolean isCheat() {
		return cheat;
	}


	public long getSleepTime() {
		return sleepTime;
	}
}
