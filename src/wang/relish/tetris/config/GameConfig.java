package wang.relish.tetris.config;

import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import java.io.FileInputStream;
import java.io.ObjectInputStream;
import java.io.Serializable;

/**
 * 游戏配置器
 */
@SuppressWarnings("serial")
public class GameConfig implements Serializable {
    /**
     * 初始化游戏窗口
     */
    private static FrameConfig FRAME_CONFIG = null;
    /**
     * 初始化数据接口
     */
    private static DataConfig DATA_CONFIG = null;
    /**
     * 初始化系统接口
     */
    private static SystemConfig SYSTEM_CONFIG = null;
    /**
     * 是否在调试数据
     */
    private final static boolean IS_DEBUG = false;//true;

    static {
        try {
            if (IS_DEBUG) {
                //创建XML读取器
                SAXReader reader = new SAXReader();
                //读取XML文件
                Document doc = reader.read("data/cfg.xml");
                //从文件获得XML文件的根节点
                Element game = doc.getRootElement();
                //创建界面配置对象
                FRAME_CONFIG = new FrameConfig(game.element("frame"));
                //创建系统对象
                SYSTEM_CONFIG = new SystemConfig(game.element("system"));
                //创建数据访问配置对象
                DATA_CONFIG = new DataConfig(game.element("data"));
            } else {
                //打开文件输入流，创建新的dat文件存储游戏窗口配置
                ObjectInputStream ois = new ObjectInputStream(new FileInputStream("data/framecfg.dat"));
                //将游戏窗口数据读入
                FRAME_CONFIG = (FrameConfig) ois.readObject();
                //打开文件输入流，创建新的dat文件存储系统配置
                ois = new ObjectInputStream(new FileInputStream("data/systemcfg.dat"));
                //将系统配置读入
                SYSTEM_CONFIG = (SystemConfig) ois.readObject();
                //打开文件输入流，创建新的dat文件存储数据配置
                ois = new ObjectInputStream(new FileInputStream("data/datacfg.dat"));
                //将数据配置读入
                DATA_CONFIG = (DataConfig) ois.readObject();
                ois.close();
                System.out.println("读取成功");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    /**
     * 构造器私有化
     */
    private GameConfig() {
    }

    /**
     * 获得窗口配置
     *
     * @return
     */
    public static FrameConfig getFrameConfig() {
        return FRAME_CONFIG;
    }

    /**
     * 获得系统配置
     *
     * @return
     */
    public static SystemConfig getSystemConfig() {
        return SYSTEM_CONFIG;
    }

    /**
     * 获得数据访问配置
     *
     * @return
     */
    public static DataConfig getDataConfig() {
        return DATA_CONFIG;
    }
    //--------------测试专用（写入游戏配置）
	/*public static void main(String[] args) throws Exception, IOException {
		ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("data/framecfg.dat"));

		oos.writeObject(FRAME_CONFIG);

		oos = new ObjectOutputStream(new FileOutputStream("data/systemcfg.dat"));

		oos.writeObject(SYSTEM_CONFIG);

		oos = new ObjectOutputStream(new FileOutputStream("data/datacfg.dat"));

		oos.writeObject(DATA_CONFIG);

	}*/


}