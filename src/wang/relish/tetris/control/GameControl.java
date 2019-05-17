package wang.relish.tetris.control;

import java.io.FileInputStream;
import java.io.ObjectInputStream;
import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import wang.relish.tetris.config.DataInterfaceConfig;
import wang.relish.tetris.config.GameConfig;
import wang.relish.tetris.dao.Data;
import wang.relish.tetris.dto.GameDto;
import wang.relish.tetris.dto.Player;
import wang.relish.tetris.service.GameMyTrix;
import wang.relish.tetris.service.GameService;
import wang.relish.tetris.ui.window.JFrameConfig;
import wang.relish.tetris.ui.window.JFrameGame;
import wang.relish.tetris.ui.window.JFrameSavePoint;
import wang.relish.tetris.ui.window.JPanelGame;

/**
 * 接收玩家键盘时间
 * 控制画面
 * 控制游戏逻辑
 *
 */

public class GameControl {
	/**
	 * 数据访问接口A
	 */
	private Data dataA;
	/**
	 * 数据访问接口B
	 */
	private Data dataB;
	/**
	 * 游戏逻辑层
	 */
	private GameService gameService;
	/**
	 * 游戏界面层
	 */
	private JPanelGame panelGame;
	/**
	 * 游戏控制设置窗口
	 */
	private JFrameConfig frameConfig;
	/**
	 * 保存分数窗口
	 */
	private JFrameSavePoint frameSavePoint;
	/**
	 * 游戏数据源
	 */
	private GameDto dto = null;
	/**
	 * 游戏行为控制
	 */
	private Map<Integer,Method> actionList;
	/**
	 * 游戏线程
	 */
	private Thread gameThread = null;


	public GameControl(){

		//创建游戏数据源
		this.dto = new GameDto();
		//创建游戏逻辑块（业务逻辑，连接游戏数据源）
		this.gameService = new GameMyTrix(dto);
		//获得类对象,创建数据接口A
		this.dataA = createDataObject(GameConfig.getDataConfig().getDataA());
		//设置数据库记录到游戏
		this.dto.setDbRecode(dataA.loadData());
		//从数据库接口B获得本地磁盘数据
		this.dataB = createDataObject(GameConfig.getDataConfig().getDataB());
		//设置本地记录到游戏
		this.dto.setDiskRecode(dataB.loadData());
		//创建游戏面板
		this.panelGame = new JPanelGame(this,dto);
		//读取用户控制设置
		this.setControlConfig();
		//初始化用户配置窗口
		this.frameConfig = new JFrameConfig(this);
		//初始化保存分数窗口
		this.frameSavePoint = new JFrameSavePoint(this);
		//初始化游戏主窗口，(安装游戏面板)
		new JFrameGame(this.panelGame);
	}
	/**
	 * 读取用户控制设置
	 */
	private void setControlConfig(){
		//创建键盘码与方法名的映射
		this.actionList = new HashMap<Integer,Method>();
		try {
			ObjectInputStream ois = new ObjectInputStream(new FileInputStream("data/control.dat"));
			@SuppressWarnings("unchecked")
			HashMap<Integer,String> cfgSet = (HashMap<Integer,String>)ois.readObject();
			Set<Entry<Integer,String>> entryset = cfgSet.entrySet();
			for (Entry<Integer, String> e : entryset) {
				actionList.put(e.getKey(), this.gameService.getClass().getMethod(e.getValue()));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	/**
	 * 创建数据对象
	 * @param cfg
	 */
	private Data createDataObject(DataInterfaceConfig cfg){
		try {
			Class<?> cls = Class.forName(cfg.getClassName());
			//获得哈希构造器
			Constructor<?> ctr = cls.getConstructor(HashMap.class);
			//创建对象
			return (Data)ctr.newInstance(cfg.getParm());
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}

	}
	/**
	 * 根据玩家控制来决定行为
	 * @param keyCode
	 */
	public void actionByKeyCode(int keyCode){
		try {
			if(this.actionList.containsKey(keyCode)){
				this.actionList.get(keyCode).invoke(this.gameService);
			}
		}catch (Exception e) {
			e.printStackTrace();
		}
		this.panelGame.repaint();
	}
	/**
	 * 显示玩家控制窗口
	 */
	public void showUserConfig() {
		this.frameConfig.setVisible(true);
	}

	/**
	 * 子窗口关闭事件
	 */
	public void setOver() {
		this.panelGame.repaint();
		this.setControlConfig();
	}
	/**
	 * 开始按钮事件
	 */
	public void start() {
		//面板按钮设置为不可点击，防止游戏运行中造成逻辑混乱
		this.panelGame.buttonSwitch(false);
		//关闭配置窗口，防止游戏运行中造成逻辑混乱
		this.frameConfig.setVisible(false);
		this.frameSavePoint.setVisible(false);
		//游戏数据初始化
		this.gameService.startGame();
		this.panelGame.repaint();
		//创建线程对象
		this.gameThread = new MainThread();
		//启动线程
		this.gameThread.start();
		//刷新画面
		this.panelGame.repaint();
	}
	/**
	 * 保存分数
	 * @param name
	 */
	public void savaPoint(String name) {
		Player pla = new Player(name,this.dto.getNowPoint());
		//保存在数据库
		this.dataA.saveData(pla);
		//保存到本地记录
		this.dataB.saveData(pla);
		//设置数据库记录到游戏
		this.dto.setDbRecode(dataA.loadData());
		//设置本地记录到游戏
		this.dto.setDiskRecode(dataB.loadData());
		//刷新界面
		this.panelGame.repaint();
	}
	/**
	 * 失败之后的处理
	 */
	public void afterLose(){
		if(!this.dto.isCheat()){
			//显示得分数据
			this.frameSavePoint.show(this.dto.getNowPoint());
		}
		//使按钮可以点击，让玩家可以进行新一轮游戏
		this.panelGame.buttonSwitch(true);
	}
	/**
	 *
	 */
	/*public boolean startMusic(){
		this.backGroundMusic
		return true;
	}*/
	/**
	 * 刷新画面
	 */
	public void repaint(){
		this.panelGame.repaint();
		this.frameConfig.requestFocusInWindow();
	}
	/**
	 * 获得游戏面板配置，建立配置与主面板间的联系
	 * @return
	 */
	public JPanelGame getPanelGame() {
		return panelGame;
	}
	/**
	 * 游戏主线程
	 * @author dell
	 */
	private class MainThread extends Thread{
		@Override
		public void run(){
			//刷新画面
			panelGame.repaint();
			//主循环
			while(dto.isStart()){
				try {
					//等待0.5秒
					Thread.sleep(dto.getSleepTime());
					//如果暂停，不执行主行为
					if(dto.isPause()){
						continue;
					}
					//游戏主行为
					gameService.mainAction();
					//刷新画面
					panelGame.repaint();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			afterLose();
		}
	}
}
