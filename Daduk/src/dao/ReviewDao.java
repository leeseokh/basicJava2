package dao;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import util.Jdbcutil;

public class ReviewDao {
	private ReviewDao() {

	}

	// 인스턴트를 보관할 변수
	private static ReviewDao instance;

	// 인스턴트를 빌려주는 메서드
	public static ReviewDao getInstence() {
		if (instance == null) {
			instance = new ReviewDao();
		}
		return instance;
	}
	
	//리뷰 등록
	
	
	private Jdbcutil jdbc = Jdbcutil.getInstance();
	public  int insertREVIEW(Map<String, Object> param) {
		String sql = " INSERT INTO REVIEW values((SELECT NVL(MAX(REVIEW_NUM), 0) + 1 FROM REVIEW),?,?,?,?,SYSDATE)";
	
		List<Object> p = new ArrayList<>();
		p.add(param.get("USER_NAME"));
		p.add(param.get("REVIEW_TITLE"));
		p.add(param.get("REVIEW_CONTENT"));
	    p.add(param.get("REVIEW_GRADE"));
	    
	    return jdbc.update(sql, p);
	}
	

	

	//리뷰 조회
	
	public List<Map<String, Object>> selectREVIEW() {
		String sql = "SELECT A.REVIEW_NUM, A.REVIEW_TITLE, A.REVIEW_CONTENT, A.USER_NAME, REVIEW_DATE"
				+ " FROM REVIEW A"
				+ " LEFT OUTER JOIN CLIENT B"
				+ " ON A.USER_NAME = B.USER_NAME"
				+ " ORDER BY A.REVIEW_NUM DESC" ;
		
	return jdbc.selectList(sql);
}
//리뷰 삭제	
public int deleteReview(Map<String, Object> param) {
	String sql = " DELETE FROM REVIEW " 
				+ "WHERE REVIEW_NUM= ?";
	List<Object> p = new ArrayList<>();
	p.add(param.get("REVIEW_NUM"));
	
	return jdbc.update(sql, p);
}
}
