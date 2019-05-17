package wang.relish.tetris.service;

import java.awt.Point;
import java.util.Map;
import java.util.Random;
import wang.relish.tetris.config.GameConfig;
import wang.relish.tetris.dto.GameDto;
import wang.relish.tetris.entity.GameAct;

public class GameMyTrix implements GameService{
	/**
	 * 游戏数据对象
	 */
	private GameDto dto = null;
	/**
	 * 随机数生成
	 */
	private Random random = new Random();

	/**
	 * 方块种类个数
	 */
	private static final int MAX_TYPE = GameConfig.getSystemConfig().getTypeConfig().size() - 1;
	/**
	 * 等级数
	 */
	private static int LEVEL_UP = GameConfig.getSystemConfig().getLevelUp();
	/**
	 * 连续消行分数
	 */
	private static final Map<Integer,Integer> PLUS_POINT = GameConfig.getSystemConfig().getPlusPoint();

	public GameMyTrix(GameDto dto) {
		this.dto = dto;
	}
	/**
	 * 按控制键向上
	 */
	public boolean keyUp() {
		if(this.dto.isPause()){
			return true;
		}
		synchronized (this.dto) {
			this.dto.getGameAct().round(this.dto.getGameMap());
		}
		return true;
	}
	/**
	 * 按控制键向下
	 */
	public boolean keyDown() {
		if(this.dto.isPause()){
			return true;
		}
		synchronized (this.dto) {
			//方向向下移动，并判断是否移动成功
			if(this.dto.getGameAct().move(0, 1,this.dto.getGameMap())){
				return false;
			}
			//获得游戏地图对象
			boolean[][] map = this.dto.getGameMap();
			//获得方块对象
			Point[] act = this.dto.getGameAct().getActPoints();
			//将方块堆积到地图上
			for (int i = 0; i < act.length; i++) {
				map[act[i].x][act[i].y] = true;
			}
			//判断消行，并获得经验值
			int plusExp = this.plusExp();
			//如果发生消行
			if (plusExp > 0){
				//增加经验值
				this.plusPoint(plusExp);
			}
			//刷新新的方块
			this.dto.getGameAct().init(this.dto.getNext());
			//随机生成再下一个方块
			this.dto.setNext(random.nextInt(MAX_TYPE));
			//检查游戏是否失败
			if(this.isLose()){
				//结束游戏
				this.dto.setStart(false);
			}
		}
		return true;
	}
	/**
	 * 检查游戏是否失败
	 */
	private boolean isLose(){
		//获得现在的活动方块
		Point[] actPoints = this.dto.getGameAct().getActPoints();
		//获得现在的游戏地图
		boolean[][] map = this.dto.getGameMap();
		for (int i = 0; i < actPoints.length; i++) {
			if(map[actPoints[i].x][actPoints[i].y]){
				return true;
			}
		}
		return false;
	}
	/**
	 * 升级操作
	 * @param exp
	 */
	private void plusPoint(int plusExp) {
		//获得现在的等级
		int lv = this.dto.getNowlevel();
		//获得现在消行总数
		int rmLine = this.dto.getNowRemoveLine();
		//获得现在得分
		int point = this.dto.getNowPoint();
		//计算是否升级
		if (rmLine % LEVEL_UP + plusExp >= LEVEL_UP) {
			//等级增加
			this.dto.setNowlevel(++lv);
		}
		this.dto.setNowRemoveLine(rmLine + plusExp);
		this.dto.setNowPoint(point + PLUS_POINT.get(plusExp));
	}
	/**
	 * 按控制键向左
	 */
	public boolean keyLeft() {
		if(this.dto.isPause()){
			return true;
		}
		synchronized (this.dto) {
			this.dto.getGameAct().move(-1,0,this.dto.getGameMap());
		}
		return true;
	}
	/**
	 * 按控制键向右
	 */
	public boolean keyRight() {
		if(this.dto.isPause()){
			return true;
		}
		synchronized (this.dto) {
			this.dto.getGameAct().move(1,0,this.dto.getGameMap());
		}
		return true;
	}
	/**
	 * 消行操作
	 */
	private int plusExp() {
		//获得游戏地图
		boolean[][] map = this.dto.getGameMap();
		int exp = 0;
		//扫描游戏地图，看是否可以消行
		for (int y = 0; y < GameDto.GAMEZONE_H; y++) {
			//判断是否可以消行
			if(this.isCanRemoveLine(y,map)){
				//消行
				this.removeLine(y,map);
				//增加经验值
				exp++;
			}
		}
		return exp;
	}
	/**
	 * 消行处理
	 * @param y
	 * @param map
	 */
	private void removeLine(int rowNumber, boolean[][] map) {
		for (int x = 0; x < GameDto.GAMEZONE_W; x++) {
			for (int y = rowNumber; y > 0; y--) {
				map[x][y] = map[x][y-1];
			}
			map[x][0] = false;
		}
	}
	/**
	 *判断某一行是否可以消除
	 */
	private boolean isCanRemoveLine(int y,boolean[][] map){
		//单行内对每一个单元格进扫描
		for (int x = 0; x < GameDto.GAMEZONE_W; x++) {
			if (!map[x][y]) {
				//如果有一个方格为false,则直接跳到下一行
				return false;
			}
		}
		return true;
	}
	/**
	 * 测试快速加分键
	 */
	@Override
	public boolean keyFunUp() {
		/*try {
			this.dto.getMusic().showMusic(sound);
		} catch (Exception e1) {
			e1.printStackTrace();
		}*/
		this.dto.setCheat(true);
		this.plusPoint(4);
		return true;
	}
	/**
	 * 快速下降键
	 */
	@Override
	public boolean keyFunDown() {
		if(this.dto.isPause()){
			return true;
		}
		while(!this.keyDown());
		return true;
	}
	/**
	 * 控制阴影键
	 */
	@Override
	public boolean keyFunLeft() {
		this.dto.changeShowShadow();
		return true;
	}
	/**
	 * 暂停键
	 */
	@Override
	public boolean keyFunRight() {
		if(this.dto.isStart()){
			this.dto.changePause();
		}
		return true;
	}
	/**
	 * 开始游戏
	 */
	@Override
	public void startGame() {
		//随机生成下一个方块
		this.dto.setNext(random.nextInt(MAX_TYPE));
		//随机生成现在方块
		this.dto.setGameAct(new GameAct(random.nextInt(MAX_TYPE)));
		//把游戏状态设为开始
		this.dto.setStart(true);
		//dto初始化
		this.dto.dtoInit();
	}
	/**
	 * 游戏主行为
	 */
	@Override
	public void mainAction() {
		this.keyDown();
	}
}
