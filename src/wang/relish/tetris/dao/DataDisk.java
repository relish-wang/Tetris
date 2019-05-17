package wang.relish.tetris.dao;

import wang.relish.tetris.dto.Player;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DataDisk implements Data {

    /**
     * 路径
     */
    private final String filePath;

    /**
     * 初始化本地记录路径
     *
     * @param param
     */
    public DataDisk(HashMap<String, String> param) {
        this.filePath = param.get("path");
    }

    /**
     * 导入数据
     */
    @SuppressWarnings("unchecked")
    @Override
    public List<Player> loadData() {
        //数据的输入流初始化
        ObjectInputStream ois = null;
        //玩家列表初始化
        List<Player> players = null;
        try {
            //确定输入流的路径
            ois = new ObjectInputStream(new FileInputStream(filePath));
            //玩家读取本地数据
            players = (List<Player>) ois.readObject();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                //关闭输入流
                ois.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return players;
    }

    /**
     * 保存数据
     */
    @Override
    public void saveData(Player pla) {
        //先取出数据
        List<Player> players = this.loadData();
        //追加新纪录
        players.add(pla);
        //重新写入
        ObjectOutputStream oos = null;
        try {
            //确定输出流的路径
            oos = new ObjectOutputStream(new FileOutputStream(filePath));
            //将数据进入玩家本地记录
            oos.writeObject(players);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                //输出流关闭
                oos.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

    }
    //------------------测试专用
	/*public static void main(String[] args) throws Exception
	{
		DataDisk dd = new DataDisk();
		List<Player> players = new ArrayList<Player>();
		players.add(new Player("托尼托尼·乔巴",1000));
		players.add(new Player("克洛格",1100));
		players.add(new Player("米法",203));
		players.add(new Player("琦玉",3340));
		players.add(new Player("旗木·五五开",4213));

		dd.saveData(players);
		System.out.println("保存完毕");
		
		List<Player> dataFromDisk = dd.loadData();
		
		for (Player p : dataFromDisk) {
			System.out.println(p.getName() + ": " + p.getPoint());
			
		}
	}*/

}
