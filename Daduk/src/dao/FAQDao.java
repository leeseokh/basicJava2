package dao;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import util.Jdbcutil;

public class FAQDao {
	private FAQDao() {
	}
	private static FAQDao instance;
	Jdbcutil jdbc = Jdbcutil.getInstance();

	public static FAQDao getInstence() {
		if (instance == null) {
			instance = new FAQDao();
		}
		return instance;
	}
	
		public List<Map<String, Object>> FAQview() {
			String sql = "SELECT A.FAQ_NUM, A.FAQ_TITLE, A.FAQ_CONTENT, B.ADMIN_ID"
					+ " FROM FAQ A"
					+ " LEFT OUTER JOIN ADMIN B"
					+ " ON A.ADMIN_ID = B.ADMIN_ID"
					+ " ORDER BY A.FAQ_NUM DESC" ;;
			
		return jdbc.selectList(sql);
			
	
}
		public  int insertFAQ(Map<String, Object> param) {
			String sql = " INSERT INTO FAQ(FAQ_NUM,ADMIN_ID,FAQ_TITLE,FAQ_CONTENT)"
					+ "	values((SELECT NVL(MAX(FAQ_NUM), 0) + 1 FROM FAQ),?,?,?)";
		
			List<Object> p = new ArrayList<>();
			p.add(param.get("ADMIN_ID"));
			p.add(param.get("FAQ_TITLE"));
		    p.add(param.get("FAQ_CONTENT"));
		    
		    return jdbc.update(sql, p);
		}
		
}
