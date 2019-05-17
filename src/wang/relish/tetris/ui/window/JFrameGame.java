package wang.relish.tetris.ui.window;

import javax.swing.JFrame;
import wang.relish.tetris.config.FrameConfig;
import wang.relish.tetris.config.GameConfig;
import wang.relish.tetris.util.FrameUtil;


@SuppressWarnings("serial")
public class JFrameGame extends JFrame{
	public JFrameGame(JPanelGame panelGame)
	{
		//获得游戏配置
		FrameConfig fCfg = GameConfig.getFrameConfig();
		//设置一个名叫“俄罗斯方块”
		this.setTitle("俄罗斯方块");
		//设置一个默认的关闭操作
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		//设置窗体的大小
		this.setSize(fCfg.getWidth(),fCfg.getHeight());
		//不允许用户进行改变窗体的大小
		this.setResizable(false);
		//居中
		FrameUtil.setFrameCenter(this);
		//设置各种窗体的大小
		add(panelGame);
		//显示窗口
		this.setVisible(true);
	}
}
