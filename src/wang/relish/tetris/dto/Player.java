package wang.relish.tetris.dto;
import java.io.Serializable;

@SuppressWarnings("serial")
public class Player implements Comparable<Player>,Serializable{
	/**
	 * 玩家的姓名
	 */
	private String name;
	/**
	 * 玩家的分数
	 */
	private int point;

	public Player(String name, int point) {
		super();
		this.name = name;
		this.point = point;
	}

	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getPoint() {
		return point;
	}
	public void setPoint(int point) {
		this.point = point;
	}
	/**
	 * 实现可比较方法（可序列化）
	 */
	@Override
	public int compareTo(Player pla) {
		return pla.point - this.point;
	}

}
