package wang.relish.tetris.service;

public interface GameService {

    /**
     * 方向上键
     */
    boolean keyUp();

    /**
     * 方向下键
     */
    boolean keyDown();

    /**
     * 方向左键
     */
    boolean keyLeft();

    /**
     * 方向右键
     */
    boolean keyRight();

    /**
     * 三角
     */
    boolean keyFunUp();

    /**
     * 大叉
     */
    boolean keyFunDown();

    /**
     * 方块
     */
    boolean keyFunLeft();

    /**
     * 圆圈
     */
    boolean keyFunRight();

    /**
     * 游戏主行为
     */
    void mainAction();

    /**
     * 启动主线程
     */
    void startGame();
}
