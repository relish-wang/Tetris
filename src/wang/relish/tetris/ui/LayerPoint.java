package wang.relish.tetris.ui;

import wang.relish.tetris.config.GameConfig;

import java.awt.*;


public class LayerPoint extends Layer {
    /**
     * 分数最大值
     */
    private static final int POINT_BIT = 5;

    /**
     * 等级数
     */
    private static int LEVEL_UP = GameConfig.getSystemConfig().getLevelUp();

    /**
     * 消行标题高度
     */
    @SuppressWarnings("unused")
    private int IMG_RMLINE_H;
    /**
     * 消行的空边距
     */
    private int rmLineY;
    /**
     * 共同X坐标
     */
    private int comX;
    /**
     * 分数Y坐标
     */
    private int pointY;
    /**
     * 经验值Y坐标
     */
    private int expY;


    /**
     * 构造方法
     *
     * @param x
     * @param y
     * @param w
     * @param h
     */
    public LayerPoint(int x, int y, int w, int h) {
        super(x, y, w, h);
        //初始化分数X
        this.comX = this.w - IMG_NUMBER_W * POINT_BIT - PADDING;
        //初始化分数Y
        this.pointY = PADDING;
        //消行数目初始化
        this.rmLineY = this.pointY + Img.POINT.getHeight(null) + PADDING;
        //消行高度初始化
        IMG_RMLINE_H = Img.RMLINE.getHeight(null);
        //经验条初始化
        this.expY = this.rmLineY + Img.RMLINE.getHeight(null) + PADDING;
    }

    /**
     * 绘制分数界面
     */
    public void paint(Graphics g) {
        this.creatWindow(g);
        //标题分数
        //int centerX =(this.w - IMG_LV_W >> 1);
        g.drawImage(Img.POINT, this.x + PADDING, this.y + pointY, null);
        //显示分数
        this.drawNumberLeftPad(comX, pointY, this.dto.getNowPoint(), POINT_BIT, g);
        //标题消行
        g.drawImage(Img.RMLINE, this.x + PADDING, this.y + rmLineY, null);
        //显示消行
        this.drawNumberLeftPad(comX, rmLineY, this.dto.getNowRemoveLine(), POINT_BIT, g);
        //绘制值槽（经验值）
        int rmline = this.dto.getNowRemoveLine();
        drawRect(expY, "下一级", null, (double) (rmline % LEVEL_UP) / (double) LEVEL_UP, g);
        //TODO
        //g.fillRect(this.x + PADDING + 2 , this.y + expY + 2, w, h - 4);
    }

}
