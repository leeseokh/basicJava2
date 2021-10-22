package dao;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import util.JDBCUtillllll;

public class UserDao {

	//싱글톤 패턴
			private UserDao(){} 
			private static UserDao instance; 
			public static UserDao getInstance(){ 
				if(instance == null ) { 
						instance = new UserDao();				
			}
			return instance;	
		}
	
		private JDBCUtillllll jdbc = JDBCUtillllll.getInstance();
		
		public int insertUser(Map<String,Object> param){
				String sql = "INSERT INTO TB_JDBC_USER VALUES (?, ?, ?)";
				
				List<Object> p = new ArrayList<>(); 
				p.add(param.get("USER_ID")); 
				p.add(param.get("PASSWORD")); 
				p.add(param.get("USER_NAME")); 
			
				return jdbc.update(sql, p);				
			}
			
			public Map<String, Object> selectUser(String userId, String password){
				String sql = "SELECT USER_ID, PASSWORD, USER_NAME "
						+"FROM TB_JDBC_USER "
						+"WHERE USER_ID = ? "
						+"AND PASSWORD = ? ";
				
				List<Object> param = new ArrayList<>();
				param.add(userId);
				param.add(password);			//? 값들
				
				return jdbc.selectOne(sql, param); // 연결, 조회된 회원 리턴.
				
	
				
		}

}
			
			
			
			

