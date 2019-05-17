package wang.relish.tetris.ui;

import java.awt.*;

public class LayerLevel extends Layer {

    /**
     * 标题的宽度
     */
    private static final int IMG_LV_W = Img.LV.getWidth(null);

    /**
     * 构造函数
     *
     * @param x
     * @param y
     * @param w
     * @param h
     */
    public LayerLevel(int x, int y, int w, int h) {
        super(x, y, w, h);
    }

    public void paint(Graphics g) {
        this.creatWindow(g);
        //窗口标题
        int centerX = (this.w - IMG_LV_W >> 1);
        g.drawImage(Img.LV, this.x + centerX, this.y + PADDING, null);
        //显示等级
        drawNumberLeftPad(centerX, 64, this.dto.getNowlevel(), 2, g);
    }
}
