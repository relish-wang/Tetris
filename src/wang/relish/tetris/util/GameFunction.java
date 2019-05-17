package wang.relish.tetris.util;

public class GameFunction {
    /**
     * 计算线程睡眠时间
     */
    public static long getSleepTimeByLevel(int level) {
        long sleep = (-40 * level + 740);
        sleep = sleep < 100 ? 100 : sleep;
        return sleep;
    }

}
