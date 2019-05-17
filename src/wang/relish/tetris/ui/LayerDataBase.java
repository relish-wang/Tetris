package wang.relish.tetris.ui;

import java.awt.*;

public class LayerDataBase extends LayerData {


    public LayerDataBase(int x, int y, int w, int h) {
        super(x, y, w, h);


    }

    public void paint(Graphics g) {
        this.creatWindow(g);
        this.showData(Img.DB, this.dto.getDbRecode(), g);
    }
}

