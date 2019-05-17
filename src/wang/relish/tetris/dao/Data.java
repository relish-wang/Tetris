package wang.relish.tetris.dao;

import java.util.List;

import wang.relish.tetris.dto.Player;


/**
 * 数据持久层接口
 * @author dell
 *
 */
public interface Data {
	/**
	 * 读取数据
	 * @return
	 */
	List<Player> loadData();
	/**
	 * 存储数据
	 */
	void saveData(Player players);

}
