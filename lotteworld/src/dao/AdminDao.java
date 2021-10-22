package dao;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import util.JDBCUtillllll;

public class AdminDao {			

	
			private AdminDao(){} 
			private static AdminDao instance; 
			public static AdminDao getInstance(){ 
				if(instance == null ) { 
						instance = new AdminDao();				
			}
			return instance;	
		}
	
		private JDBCUtillllll jdbc = JDBCUtillllll.getInstance();
		
			
			public Map<String, Object> selectAdmin(String adminId, String adminPassword){
				String sql = "SELECT ADMIN_ID, ADMIN_PASSWORD, ADMIN_NAME "
						+"FROM ADMIN "
						+"WHERE ADMIN_ID = ? "
						+"AND ADMIN_PASSWORD = ? ";
				
				List<Object> param = new ArrayList<>();
				param.add(adminId);
				param.add(adminPassword);			//? 값들
				
				return jdbc.selectOne(sql, param); // 연결, 조회된 회원 리턴.
				
	
				
		}

}
			
			
			
			

