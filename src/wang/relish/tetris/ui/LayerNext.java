package wang.relish.tetris.ui;

import java.awt.*;


public class LayerNext extends Layer {

    public LayerNext(int x, int y, int w, int h) {
        super(x, y, w, h);
    }

    public void paint(Graphics g) {
        this.creatWindow(g);
        //如果是开始状态才允许开始画图
        if (this.dto.isStart()) {
            this.drawImageAtCenter(Img.NEXT_ACT[this.dto.getNext()], g);
        }

    }
}
