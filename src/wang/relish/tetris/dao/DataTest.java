package wang.relish.tetris.dao;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import wang.relish.tetris.dto.Player;

public class DataTest implements Data{
	/**
	 * 初始化测试数据
	 * @param parm
	 */
	public DataTest(HashMap<String,String> parm){}
	/**
	 * 导入数据（测试方法）
	 */
	@Override
	public List<Player> loadData() {
		List<Player> players = new ArrayList<Player>();
		players.add(new Player("托尼托尼·乔巴",1000));
		players.add(new Player("克洛格",1100));
		players.add(new Player("米法",203));
		players.add(new Player("琦玉",3340));
		players.add(new Player("旗木·五五开",4213));
		return players;
	}
	/**
	 * 保存数据（实际上每次只进行调用，无需保存）
	 */
	@Override
	public void saveData(Player players) {}
}
