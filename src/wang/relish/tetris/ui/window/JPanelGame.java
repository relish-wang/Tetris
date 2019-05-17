package wang.relish.tetris.ui.window;

import wang.relish.tetris.config.FrameConfig;
import wang.relish.tetris.config.GameConfig;
import wang.relish.tetris.config.LayerConfig;
import wang.relish.tetris.control.GameControl;
import wang.relish.tetris.control.PlayerControl;
import wang.relish.tetris.dto.GameDto;
import wang.relish.tetris.ui.Img;
import wang.relish.tetris.ui.Layer;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.lang.reflect.Constructor;
import java.util.ArrayList;
import java.util.List;


@SuppressWarnings("serial")
public class JPanelGame extends JPanel {
    /**
     * 按键的宽
     */
    private static final int BTN_SIZE_W = GameConfig.getFrameConfig().getButtonConfig().getButtonW();
    /**
     * 按键的高
     */
    private static final int BTN_SIZE_H = GameConfig.getFrameConfig().getButtonConfig().getButtonH();

    /**
     * 层列表
     */
    private List<Layer> layers = null;
    /**
     * 开始按键
     */
    public JButton btnStart;
    /**
     * 配置按键
     */
    public JButton btnConfig;
    /**
     * 游戏控制器
     */
    private GameControl gameControl = null;

    /**
     * 游戏布局
     */
    public JPanelGame() {

    }

    public JPanelGame(GameControl gameControl, GameDto dto) {
        //连接游戏控制器
        this.gameControl = gameControl;
        //初始化布局管理器为自由布局
        this.setLayout(null);
        //初始化组件
        this.initComponent();
        //初始化层
        this.initLayer(dto);
        //安装键盘监听器
        this.addKeyListener(new PlayerControl(gameControl));
    }

    /**
     * 初始化组件
     */
    public void initComponent() {
        //初始化开始按钮
        this.btnStart = new JButton(Img.BTN_START);
//		System.out.println(Img.BTN_START);
        //设置开始按钮位置
        btnStart.setBounds(
                GameConfig.getFrameConfig().getButtonConfig().getStartX(),
                GameConfig.getFrameConfig().getButtonConfig().getStartY(),
                BTN_SIZE_W, BTN_SIZE_H);
        //给开始按钮增加事件监听
        this.btnStart.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                gameControl.start();
                //返回焦点
                requestFocus();
                //btnStart.setIcon(Img.BTN_START);
                //btnConfig.setIcon(Img.BTN_CONFIG);

            }
        });
        //添加按钮到面板
        this.add(btnStart);
        //初始化配置按钮
        this.btnConfig = new JButton(Img.BTN_CONFIG);
        //设置配置按钮位置
        this.btnConfig.setBounds(
                GameConfig.getFrameConfig().getButtonConfig().getUserConfigX(),
                GameConfig.getFrameConfig().getButtonConfig().getUserConfigY(),
                BTN_SIZE_W, BTN_SIZE_H);
        //给配置按钮增加事件监听
        this.btnConfig.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                gameControl.showUserConfig();
            }
        });
        //添加按钮到面板
        this.add(btnConfig);
    }

    /**
     * 初始化层
     */
    @SuppressWarnings("rawtypes")
    private void initLayer(GameDto dto) {
        try {
            //获得游戏配置
            FrameConfig fCfg = GameConfig.getFrameConfig();
            //获得层配置
            List<LayerConfig> layersCfg = fCfg.getLayersConfig();
            //创建游戏层数组
            layers = new ArrayList<Layer>(layersCfg.size());
            //创建所有层对象
            for (LayerConfig layerCfg : layersCfg) {
                //获得类对象,反射
                Class<?> cls = Class.forName(layerCfg.getClassName());
                //获得构造函数
                Constructor ctr = cls.getConstructor(int.class, int.class, int.class, int.class);
                //调用构造函数，创建对象
                Layer layer = (Layer) ctr.newInstance(layerCfg.getX(),
                        layerCfg.getY(),
                        layerCfg.getW(),
                        layerCfg.getH()
                );
                //设置游戏数据对象
                layer.setDto(dto);
                //把创建的对象放入列表
                layers.add(layer);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void paintComponent(Graphics g) {
        //调用基类方法
        super.paintComponent(g);
        //绘制游戏窗口
        for (int i = 0; i < layers.size(); layers.get(i++).paint(g)) ;

    }

    /**
     * 控制按钮是否点击
     */
    public void buttonSwitch(boolean onOff) {
        this.btnConfig.setEnabled(onOff);
        this.btnStart.setEnabled(onOff);
    }

    public static int getBtnSizeW() {
        return BTN_SIZE_W;
    }

    public static int getBtnSizeH() {
        return BTN_SIZE_H;
    }

    public List<Layer> getLayers() {
        return layers;
    }

    public JButton getBtnStart() {
        return btnStart;
    }

    public JButton getBtnConfig() {
        return btnConfig;
    }
}
