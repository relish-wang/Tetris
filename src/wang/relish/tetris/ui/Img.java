package wang.relish.tetris.ui;

import wang.relish.tetris.config.GameConfig;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class Img {
    /**
     * 姓名图片
     */
    public static Image NAME = null;
    /**
     * 定义图片为常量,窗体图片
     */
    public static Image WINDOW = null;
    /**
     * 数字图片 500 * 80
     */
    public static Image NUMBER = null;
    /**
     * 矩形值槽
     */
    public static Image RECT = null;
    /**
     * 数据库图
     */
    public static Image DB = null;
    /**
     * 本地记录图
     */
    public static Image DISK = null;
    /**
     * 游戏方块图
     */
    public static Image ACT = null;
    /**
     * 等级的图片
     */
    public static Image LV = null;
    /**
     * 标题分数
     */
    public static Image POINT = null;
    /**
     * 标题消行
     */
    public static Image RMLINE = null;
    /**
     * 阴影（消行）
     */
    public static Image SHADOW = null;
    /**
     * 开始按钮
     */
    public static ImageIcon BTN_START = null;
    /**
     * 配置按钮
     */
    public static ImageIcon BTN_CONFIG = null;
    /**
     * 暂停
     */
    public static Image PAUSE = null;
    /**
     * 下一个图片数组
     */
    public static Image[] NEXT_ACT = null;
    /**
     * 背景图片数组
     */
    public static List<Image> BG_LIST = null;

    /**
     * 私有化构造器，不允许创建该对象
     */
    private Img() {
    }

    /**
     * 图片路径
     */
    public static final String GRAPHICS_PATH = "graphics/";
    /**
     * 默认图片路径名
     */
    private static final String DEFAULT_PATH = "default/";

    public static void setSkin(String path) {
        String skinPath = GRAPHICS_PATH + path;
        //姓名图片
        NAME = new ImageIcon(skinPath + "string/name.png").getImage();
        //定义图片为常量,窗体图片
        WINDOW = new ImageIcon(skinPath + "window/Window.png").getImage();
        //数字图片 500 * 80
        NUMBER = new ImageIcon(skinPath + "string/num.png").getImage();
        //矩形值槽
        RECT = new ImageIcon(skinPath + "window/rect.png").getImage();
        //数据库图
        DB = new ImageIcon(skinPath + "string/db.png").getImage();
        //本地记录图
        DISK = new ImageIcon(skinPath + "string/disk.png").getImage();
        //游戏方块图
        ACT = new ImageIcon(skinPath + "game/rect.png").getImage();
        //等级的图片
        LV = new ImageIcon(skinPath + "string/level.png").getImage();
        //标题分数
        POINT = new ImageIcon(skinPath + "string/point.png").getImage();
        //标题消行
        RMLINE = new ImageIcon(skinPath + "string/rmline.png").getImage();
        //阴影（消行）
        SHADOW = new ImageIcon(skinPath + "game/shadow.png").getImage();
        //开始按钮
        BTN_START = new ImageIcon(skinPath + "string/start.png");
        //配置按钮
        BTN_CONFIG = new ImageIcon(skinPath + "string/config.png");
        //暂停
        PAUSE = new ImageIcon(skinPath + "string/pause.png").getImage();
        //下一个方块的图片
        NEXT_ACT = new Image[GameConfig.getSystemConfig().getTypeConfig().size()];
        for (int i = 0; i < NEXT_ACT.length; i++) {
            NEXT_ACT[i] = new ImageIcon(skinPath + "game" + File.separator + i + ".png").getImage();
        }
        //背景图片数组
        File dir = new File(skinPath + "background");
        File[] files = dir.listFiles();
        BG_LIST = new ArrayList<Image>();
        for (File file : files) {
            if (!file.isDirectory() && file.getName().endsWith(".jpg")) {
                BG_LIST.add(new ImageIcon(file.getPath()).getImage());
            }
        }
    }

    /**
     * 初始化图片
     */
    static {
        setSkin(DEFAULT_PATH);
    }
}
