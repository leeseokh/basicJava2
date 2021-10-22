package dao;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import util.Jdbcutil;

public class UserDao {
	private UserDao(){}
	private static UserDao instance;
	public static UserDao getInstence(){
		if(instance == null){
			instance = new UserDao();
		}
		return instance;
	}
	private Jdbcutil jdbc = Jdbcutil.getInstance();
	
	public int insertUser(Map<String, Object> param){
		String sql = "INSERT INTO CLIENT VALUES (?,?,?,?,?,?)";
		
		List<Object> p = new ArrayList<>();
		p.add(param.get("USER_ID"));
		p.add(param.get("PASSWORD"));
		p.add(param.get("USER_NAME"));
		p.add(param.get("USER_CALL"));
		p.add(param.get("USER_CARDNUM"));
		p.add(param.get("2"));
		
		return jdbc.update(sql, p);
	}
	public Map<String, Object> selectUser(String userId, String password){
		String sql = "SELECT USER_ID, PASSWORD, USER_NAME FROM CLIENT WHERE USER_ID = ? AND PASSWORD = ?";
			List<Object> param = new ArrayList<>();
			param.add(userId);
			param.add(password);
			
			return jdbc.selectOne(sql,param);
	}
	public Map<String, Object> selectAdmin(String adminId, String password){
		String sql = "SELECT ADMIN_ID, PASSWORD, ADMIN_NAME FROM ADMIN WHERE ADMIN_ID = ? AND PASSWORD = ?";
		List<Object> param = new ArrayList<>();
		param.add(adminId);
		param.add(password);
		
		return jdbc.selectOne(sql,param);
	}
	public List<Map<String, Object>> MyPage(){
		String sql = "SELECT A.USER_ID, A.PASSWORD, A.USER_NAME, A.USER_CALL, A.USER_CARDNUM, B.POINT"
				+ " FROM CLIENT A"
				+ " LEFT OUTER JOIN MEMBERSHIP B"
				+ " ON A.USER_CARDNUM = B.USER_CARDNUM";
		
		return jdbc.selectList(sql);
				
	}
}
