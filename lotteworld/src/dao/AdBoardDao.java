package dao;
import java.util.List;
import java.util.Map;

import util.JDBCUtillllll;

public class AdBoardDao {

	
	private AdBoardDao() {
	}

	private static AdBoardDao instance;

	public static AdBoardDao getInstance() {
		if (instance == null) {
			instance = new AdBoardDao();
		}
		return instance;
	}

	private JDBCUtillllll jdbc = JDBCUtillllll.getInstance();

	public List<Map<String, Object>> selectAdBoardList() { // 공지사항 게시판
		String sql = "SELECT A.NOTICE_NUM, A.NOTICE_TITLE, A.NOTICE_CONTENT, NOTICE_DATE, A.ADMIN_ID "
				+ "FROM NOTICE A "
				+ "LEFT OUTER JOIN ADMIN B "
				+ "ON A.ADMIN_ID = B.ADMIN_ID " + "ORDER BY A.NOTICE_NUM DESC ";

		return jdbc.selectList(sql);

	}

	public List<Map<String, Object>> selectAdFAQ() { // faq 게시판
		String faq = "SELECT A.FAQ_NUMBER, A.ADMIN_ID, A.FAQ_TITLE, A.FAQ_CONTENT "
				+ "FROM FAQ A "
				+ "LEFT OUTER JOIN ADMIN B "
				+ "ON A.ADMIN_ID = B.ADMIN_ID " + "ORDER BY A.FAQ_NUMBER DESC ";

		return jdbc.selectList(faq);
	}

	public List<Map<String, Object>> selectAdevent() { // 이벤트 게시판
		String sql = "SELECT A.EVENT_NUMBER, A.EVENT_DATE, A.ADMIN_ID, A.EVENT_TITLE, A.EVENT_CONTENT "
				+ "FROM EVENT A "
				+ "LEFT OUTER JOIN ADMIN B "
				+ "ON A.ADMIN_ID = B.ADMIN_ID "
				+ "ORDER BY A.EVENT_NUMBER DESC ";

		return jdbc.selectList(sql);
	}

}
