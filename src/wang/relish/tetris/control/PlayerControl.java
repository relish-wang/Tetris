package wang.relish.tetris.control;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class PlayerControl extends KeyAdapter {

    private GameControl gameControl = null;

    /**
     * 构造器，传进游戏控制器给玩家
     *
     * @param gameControl
     */
    public PlayerControl(GameControl gameControl) {
        this.gameControl = gameControl;
    }

    /**
     * 键盘按下事件
     */
    public void keyPressed(KeyEvent e) {
        //监听按下的键码
        this.gameControl.actionByKeyCode(e.getKeyCode());
    }
}



