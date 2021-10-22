package util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Jdbcutil {
	// 싱글톤 패턴 : 인스턴스의 생성을 제한하여 하나의 인스턴스만 사용하는 디자인 패턴
	private Jdbcutil() {

	}

	// 인스턴스를 보관할 변수
	private static Jdbcutil instance;

	// 인스턴스를 빌려주는 메서드
	public static Jdbcutil getInstance() {
		if (instance == null) {
			instance = new Jdbcutil();
		}
		return instance;
	}

	String url = "jdbc:oracle:thin:@localhost:1521:xe"; // @를 기점으로 앞에는 고정 뒤에는 주소
	String user = "seunggu";
	String pw = "java";

	Connection con = null;
	PreparedStatement ps = null;
	ResultSet rs = null;

	/*
	 * Map<String, Object> selectOne(String sql) //한줄일때 사용
	 * Map<String, Object> selectOne(String sql List<Object> param) //여러줄일때 사용
	 * List<Map<String, Object>> selectList(String sql) o
	 * List<Map<String, Object>> selectList(String sql, List<Object> param) o
	 * int update(String sql) //select를 제외한 나머지를 사용할때 사용
	 * int update(String sql, List<Object> param)
	 */

	public List<Map<String, Object>> selectList(String sql, List<Object> param) {
		List<Map<String, Object>> list = new ArrayList<>();

		try {
			con = DriverManager.getConnection(url, user, pw);
			ps = con.prepareStatement(sql);
			for (int i = 0; i < param.size(); i++) {
				ps.setObject(i + 1, param.get(i));
			}
			rs = ps.executeQuery();

			ResultSetMetaData metadata = rs.getMetaData();
			int columnCount = metadata.getColumnCount();

			while (rs.next()) {
				HashMap<String, Object> row = new HashMap<>();
				for (int i = 1; i <= columnCount; i++) {
					String key = metadata.getColumnName(i);
					Object value = rs.getObject(i);
					row.put(key, value);
				}
				list.add(row);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			if (rs != null)try {rs.close();} catch (Exception e) {}
			if (ps != null)try {ps.close();} catch (Exception e) {}
			if (con != null)try {con.close();} catch (Exception e) {}
		}
		return list;

	}
	public List<Map<String, Object>> selectList(String sql) {
		List<Map<String, Object>> list = new ArrayList<>();
		
		try {
			con = DriverManager.getConnection(url, user, pw);
			ps = con.prepareStatement(sql);
			rs = ps.executeQuery();
			
			ResultSetMetaData metadata = rs.getMetaData();
			int columnCount = metadata.getColumnCount();
			
			while (rs.next()) {
				HashMap<String, Object> row = new HashMap<>();
				for (int i = 1; i <= columnCount; i++) {
					String key = metadata.getColumnName(i);
					Object value = rs.getObject(i);
					row.put(key, value);
				}
				list.add(row);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			if (rs != null)try {rs.close();} catch (Exception e) {}
			if (ps != null)try {ps.close();} catch (Exception e) {}
			if (con != null)try {con.close();} catch (Exception e) {}
		}
		return list;
		
	}
	public Map<String, Object> selectOne(String sql, List<Object> param) {
		Map<String, Object> row = null; //값이 나오지않을때 빈값보다 널이 가독성이 좋음

		try {
			con = DriverManager.getConnection(url, user, pw);
			ps = con.prepareStatement(sql);
			for (int i = 0; i < param.size(); i++) {
				ps.setObject(i + 1, param.get(i));
			}
			rs = ps.executeQuery();

			ResultSetMetaData metadata = rs.getMetaData();
			int columnCount = metadata.getColumnCount();

			while (rs.next()) {
				row = new HashMap<>();
				for (int i = 1; i <= columnCount; i++) {
					String key = metadata.getColumnName(i);
					Object value = rs.getObject(i);
					row.put(key, value);
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			if (rs != null)try {rs.close();} catch (Exception e) {}
			if (ps != null)try {ps.close();} catch (Exception e) {}
			if (con != null)try {con.close();} catch (Exception e) {}
		}
		return row;

	}
	public Map<String, Object> selectOne(String sql) {
		Map<String, Object> row = null; //값이 나오지않을때 빈값보다 널이 가독성이 좋음

		try {
			con = DriverManager.getConnection(url, user, pw);
			ps = con.prepareStatement(sql);
			rs = ps.executeQuery();

			ResultSetMetaData metadata = rs.getMetaData();
			int columnCount = metadata.getColumnCount();

			while (rs.next()) {
				row = new HashMap<>();
				for (int i = 1; i <= columnCount; i++) {
					String key = metadata.getColumnName(i);
					Object value = rs.getObject(i);
					row.put(key, value);
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			if (rs != null)try {rs.close();} catch (Exception e) {}
			if (ps != null)try {ps.close();} catch (Exception e) {}
			if (con != null)try {con.close();} catch (Exception e) {}
		}
		return row;

	}
	
	public int update(String sql, List<Object> param) {
	int result = 0;
	
	try {
		con = DriverManager.getConnection(url, user, pw);
		ps = con.prepareStatement(sql);
		for(int i=0; i<param.size(); i++){
			ps.setObject(i+1, param.get(i));
		}
		result = ps.executeUpdate();
	} catch (SQLException e) {
		e.printStackTrace();
	}	finally {
		if (rs != null)try {rs.close();} catch (Exception e) {}
		if (ps != null)try {ps.close();} catch (Exception e) {}
		if (con != null)try {con.close();} catch (Exception e) {}
	}
	return result;
	}
	public int update(String sql) {
	int result = 0;
	
	try {
		con = DriverManager.getConnection(url, user, pw);
		ps = con.prepareStatement(sql);
		result = ps.executeUpdate();
	} catch (SQLException e) {
		e.printStackTrace();
	}	finally {
		if (rs != null)try {rs.close();} catch (Exception e) {}
		if (ps != null)try {ps.close();} catch (Exception e) {}
		if (con != null)try {con.close();} catch (Exception e) {}
	}
	return result;
	}
}
