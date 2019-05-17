package wang.relish.tetris.ui;

import wang.relish.tetris.config.GameConfig;

import java.awt.*;

public class LayerBackGround extends Layer {


    public LayerBackGround(int x, int y, int w, int h) {
        super(x, y, w, h);
    }


    @Override
    public void paint(Graphics g) {
        int bgIdx = this.dto.getNowlevel() % Img.BG_LIST.size();
        g.drawImage(Img.BG_LIST.get(bgIdx), 0, 0,
                GameConfig.getFrameConfig().getWidth(), GameConfig.getFrameConfig().getHeight(), null);
    }

}
