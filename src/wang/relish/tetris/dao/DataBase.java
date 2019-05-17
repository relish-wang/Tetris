package wang.relish.tetris.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import wang.relish.tetris.dto.Player;

public class DataBase implements Data{

	/**
	 * 访问数据库的地址
	 */
	private final String dbUrl;
	/**
	 * 访问数据库的用户名
	 */
	private final String dbUser;
	/**
	 * 访问数据库的密码
	 */
	private final String dbPwd;
	/**
	 * 导出数据库中玩家的姓名，分数
	 */
	private static String LOAD_SQL = "SELECT TOP 5 user_name,point FROM user_point WHERE type_id = 1 ORDER by point DESC";
	/**
	 * 将游戏记录导入到数据库中
	 */
	private static String SAVE_SQL = "INSERT INTO user_point(user_name,point,type_id) VALUES(?,?,?)";

	/**
	 * 类加载时运行，连接数据库
	 * @param param
	 */
	public DataBase(HashMap<String,String> param){
		//获得访问数据库的地址
		this.dbUrl = param.get("dbUrl");
		//获得访问数据库的用户名
		this.dbUser = param.get("dbUser");
		//获得访问数据库的密码
		this.dbPwd = param.get("dbPwd");
		try {
			//安装驱动
			Class.forName(param.get("driver"));
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}
	/**
	 * 导入数据库
	 */
	@Override
	public List<Player> loadData() {
		//初始化连接
		Connection conn = null;
		//初始化导入条件
		PreparedStatement stmt = null;
		//初始化执行
		ResultSet rs = null;
		List<Player> players = new ArrayList<Player>();
		try {
			//连接数据库
			conn = DriverManager.getConnection(dbUrl,dbUser, dbPwd);
			//导入SQL语言，进行查询
			stmt = conn.prepareStatement(LOAD_SQL);
			//执行数据库命令
			rs = stmt.executeQuery();
			while(rs.next()){
				//增加玩家记录
				players.add(new Player(rs.getString(1),rs.getInt(2)));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			try{
				//关闭连接端口
				if(conn != null) conn.close();
				//关闭查询端口
				if(stmt != null) stmt.close();
				//关闭执行端口
				if(rs != null) rs.close();
			}
			catch (Exception e) {
				e.printStackTrace();
			}
		}
		return players;
	}
	/**
	 * 导入数据
	 */
	@Override
	public void saveData(Player players) {
		//初始化连接端口
		Connection conn = null;
		//初始化导入数据端口
		PreparedStatement stmt = null;
		try {
			//连接数据库
			conn = DriverManager.getConnection(dbUrl,dbUser, dbPwd);
			//保存SQL语句
			stmt = conn.prepareStatement(SAVE_SQL);
			//增加玩家的姓名
			stmt.setObject(1, players.getName());
			//增加玩家的分数
			stmt.setObject(2, players.getPoint());
			//确认游戏编号
			stmt.setObject(3, 1);
			//执行保存命令
			stmt.execute();
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			try{
				//关闭连接端口
				if(conn != null) conn.close();
				//关闭执行端口
				if(stmt != null) stmt.close();
			}
			catch (Exception e) {
				e.printStackTrace();
			}
		}

	}
	//----------------------测试专用
	/*public static void main(String[] args) throws ClassNotFoundException, SQLException {

		System.out.println("链接成功");
		conn.close();
	}*/
	/*public static void main(String[] args) {
		DataBase db = new DataBase();
		List<Player> players = db.loadData();
		for (Player p : players) {
			System.out.println(p.getName() + ": " + p.getPoint());
			
		}
		System.out.println(123);
	}*/
	/*public static void main(String[] args) {
		DataBase db = new DataBase();
		db.saveData(new Player("GM",99999));
		System.out.println (1);
	}*/
}
