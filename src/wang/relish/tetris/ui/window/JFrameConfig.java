package wang.relish.tetris.ui.window;

import wang.relish.tetris.control.GameControl;
import wang.relish.tetris.ui.Img;
import wang.relish.tetris.util.FrameUtil;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.*;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map.Entry;
import java.util.Set;

@SuppressWarnings("serial")
public class JFrameConfig extends JFrame {
    /**
     * 生成一个确定按钮
     */
    private JButton btnOK = new JButton("确定");
    /**
     * 生成一个取消按钮
     */
    private JButton btnCancel = new JButton("取消");
    /**
     * 生成一个应用按钮
     */
    private JButton btnUser = new JButton("应用");
    /**
     * 错误信息警告标签
     */
    private JLabel errorMsg = new JLabel();
    /**
     * 皮肤列表
     */
    private JList skinList = null;
    /**
     * 皮肤试图
     */
    private JPanel skinView = null;
    /**
     * 默认列表模型
     */
    private DefaultListModel skinData = new DefaultListModel();
    /**
     * 游戏控制器
     */
    private GameControl gameControl;
    /**
     * 游戏配置键的8个按钮
     */
    private TextCtrl[] keyText = new TextCtrl[8];
    /**
     * 配置器图片
     */
    private final static Image IMG_PSP = new ImageIcon("data/psp.jpg").getImage();
    /**
     * 皮肤列表
     */
    private Image[] skinViewList = null;
    /**
     * 配置文件的路径
     */
    private final static String PATH = "data/control.dat";
    /**
     * 方法名数组
     */
    private final static String[] METHOD_NAMES = {
            "keyRight", "keyUp", "keyLeft", "keyDown"
            , "keyFunLeft", "keyFunUp", "keyFunRight", "keyFunDown"
    };

    /**
     * 初始化配置
     *
     * @param gameControl
     */
    public JFrameConfig(GameControl gameControl) {
        //获得游戏控制器对象
        this.gameControl = gameControl;
        //设置布局管理器为边界布局
        this.setLayout(new BorderLayout());
        //设置标题
        this.setTitle("设置");
        //初始化按键输入框
        this.initKeyText();
        //添加主面板
        this.add(this.createMainPanel(), BorderLayout.CENTER);
        //添加按钮面板
        this.add(this.createButtonPanel(), BorderLayout.SOUTH);
        //设置窗口大小
        this.setSize(600, 340);
        //设置窗口不能移动大小
        this.setResizable(false);
        //居中
        FrameUtil.setFrameCenter(this);
		/*//TODOp测试专用
		this.setDefaultCloseOperation(3);
		this.setVisible(true);*/
    }

    /**
     * 初始化按键输入框
     */
    private void initKeyText() {
        int x = 38;
        int y = 48;
        int w = 64;
        int h = 20;
        for (int i = 0; i < 4; i++) {
            keyText[i] = new TextCtrl(x, y, w, h, METHOD_NAMES[i]);
            y += 30;
        }
        x = 519;
        y = 55;
        for (int i = 4; i < 8; i++) {
            keyText[i] = new TextCtrl(x, y, w, h, METHOD_NAMES[i]);
            y += 28;
        }
        try {
            ObjectInputStream ois = new ObjectInputStream(new FileInputStream(PATH));
            @SuppressWarnings("unchecked")
            HashMap<Integer, String> cfgSet = (HashMap<Integer, String>) ois.readObject();
            ois.close();
            Set<Entry<Integer, String>> entrySet = cfgSet.entrySet();
            for (Entry<Integer, String> e : entrySet) {
                for (TextCtrl tc : keyText) {
                    if (tc.getMethodName().equals(e.getValue())) {
                        tc.setKeyCode(e.getKey());
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 创建按钮面板
     *
     * @return
     */
    private JPanel createButtonPanel() {
        //创建按钮面板，流式布局
        JPanel jp = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        //给确定按钮增加事件监听
        this.btnOK.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (writeConfig()) {
                    setVisible(false);
                    gameControl.setOver();
                }
            }
        });
        jp.add(this.errorMsg);
        this.setForeground(Color.RED);
        jp.add(this.btnOK);
        //给取消按钮增加事件监听
        this.btnCancel.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                setVisible(false);
                gameControl.setOver();
            }
        });
        jp.add(this.btnCancel);
        //给应用按钮增加事件监听
        this.btnUser.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                writeConfig();
                gameControl.repaint();
            }
        });
        jp.add(this.btnUser);
        return jp;
    }

    /**
     * 创建主面板
     *
     * @return
     */
    private JTabbedPane createMainPanel() {
        JTabbedPane jtp = new JTabbedPane();
        jtp.addTab("控制设置", this.createControlPanel());
        jtp.addTab("皮肤设置", this.createSkinPanel());
        return jtp;
    }

    /**
     * 玩家皮肤面板
     *
     * @return
     */
    private JPanel createSkinPanel() {
        //创建面板
        JPanel panel = new JPanel(new BorderLayout());
        //创建目录
        File dir = new File(Img.GRAPHICS_PATH);
        //创建文件数组
        File[] files = dir.listFiles();
        if (files != null) Arrays.sort(files);
        this.skinViewList = new Image[files.length];
        for (int i = 0; i < files.length; i++) {
            //增加选项
            this.skinData.addElement(files[i].getName());
            //增加预览图
            this.skinViewList[i] = new ImageIcon(files[i].getPath() + File.separator + "view.jpg").getImage();
        }
        //添加列表数据到列表
        this.skinList = new JList(this.skinData);
        //设置默认选中第一个
        this.skinList.setSelectedIndex(0);
        this.skinList.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseReleased(MouseEvent e) {
                repaint();

            }
        });
        this.skinView = new JPanel() {
            @Override
            public void paintComponent(Graphics g) {
                Image showImg = skinViewList[skinList.getSelectedIndex()];
                int x = (this.getWidth() - showImg.getWidth(null)) >> 1;
                int y = (this.getHeight() - showImg.getHeight(null)) >> 1;
                g.drawImage(showImg, x, y, null);
            }
        };
        panel.add(new JScrollPane(this.skinList), BorderLayout.WEST);
        panel.add(this.skinView, BorderLayout.CENTER);
        return panel;
    }

    /**
     * 玩家控制设计面板
     *
     * @return
     */
    private JPanel createControlPanel() {
        JPanel jp = new JPanel() {
            @Override
            public void paintComponent(Graphics g) {
                g.drawImage(IMG_PSP, 0, 0, null);
            }
        };
        //设置布局管理器
        jp.setLayout(null);
        for (int i = 0; i < keyText.length; i++) {
            jp.add(keyText[i]);
        }
        return jp;
    }

    /**
     * 写入游戏配置
     */
    private boolean writeConfig() {
        HashMap<Integer, String> keySet = new HashMap<Integer, String>();
        for (int i = 0; i < this.keyText.length; i++) {
            int keyCode = this.keyText[i].getKeyCode();
            if (keyCode == 0) {
                this.errorMsg.setText("您按键错误了");
                return false;
            }
            keySet.put(this.keyText[i].getKeyCode(), this.keyText[i].getMethodName());
        }
        if (keySet.size() != 8) {
            this.errorMsg.setText("您按键重复了");
            return false;
        }

        try {
            //切换皮肤
            Img.setSkin(this.skinData.get(this.skinList.getSelectedIndex()).toString() + File.separator);
            this.gameControl.getPanelGame().getBtnStart().setIcon(Img.BTN_START);
            this.gameControl.getPanelGame().getBtnConfig().setIcon(Img.BTN_CONFIG);

            //写入控制配置
            ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(PATH));
            oos.writeObject(keySet);
            oos.close();
            System.out.println("写入成功");
        } catch (Exception e) {
            this.errorMsg.setText(e.getMessage());
            return false;
        }
        this.errorMsg.setText(null);
        return true;
    }
	/*public static void main(String[] args) {
		new FrameConfig(gameControl);
	}*/
}
