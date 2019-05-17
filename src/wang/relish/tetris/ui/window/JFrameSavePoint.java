package wang.relish.tetris.ui.window;

import wang.relish.tetris.control.GameControl;
import wang.relish.tetris.util.FrameUtil;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

@SuppressWarnings("serial")
public class JFrameSavePoint extends JFrame {
    /**
     * 分数标签
     */
    private JLabel lbpoint = null;
    /**
     * 名字标签
     */
    private JTextField txName = null;
    /**
     * 确定按钮
     */
    private JButton btnOk = null;
    /**
     * 错误信息提示标签
     */
    private JLabel errMsg = null;
    /**
     * 游戏控制器
     */
    private GameControl gameControl = null;

    public JFrameSavePoint(GameControl gameControl) {
        this.gameControl = gameControl;
        this.setTitle("保存记录");
        this.setSize(256, 128);
        FrameUtil.setFrameCenter(this);
        this.setResizable(false);
        this.setLayout(new BorderLayout());
        this.createCom();
        this.createAction();
    }

    /**
     * 显示窗口
     */
    public void show(int point) {
        this.lbpoint.setText("您的得分： " + point);
        this.setVisible(true);
    }

    /**
     * 创建事件监听
     */
    private void createAction() {
        this.btnOk.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String name = txName.getText();
                if (name.length() > 16 || name == null || "".equals(name)) {
                    errMsg.setText("名字请在1~16位");
                } else {
                    setVisible(false);
                    gameControl.savaPoint(name);
                }
            }
        });

    }

    /**
     * 初始化控件
     */
    public void createCom() {
        //创建北部面板（流式布局）
        JPanel north = new JPanel(new FlowLayout(FlowLayout.LEFT));
        //创建分数文字
        this.lbpoint = new JLabel();
        //添加文件分数到北部面板
        north.add(lbpoint);
        //创建错误信息控件
        this.errMsg = new JLabel();
        this.errMsg.setForeground(Color.RED);
        //添加错误信息到北部面板
        north.add(errMsg);
        //北部面板添加到主面板上
        this.add(north, BorderLayout.NORTH);
        //创建中部面板，(流式布局)
        JPanel center = new JPanel(new FlowLayout(FlowLayout.LEFT));
        //创建文本框
        this.txName = new JTextField(10);
        //设置文字
        center.add(new JLabel("您的名字："));
        //文本框添加到中部面板
        center.add(this.txName);
        //从中部面板添加到主面板
        this.add(center, BorderLayout.CENTER);
        //创建确定按钮
        this.btnOk = new JButton("确定");
        //创建流式布局
        JPanel south = new JPanel(new FlowLayout(FlowLayout.CENTER));
        //按钮添加到南部面板
        south.add(btnOk);
        //南部面板添加到主面板
        this.add(south, BorderLayout.SOUTH);
    }
}
