package dao;

import java.util.List;
import java.util.Map;
import util.Jdbcutil;

public class GuideDao {
	private GuideDao() {
	}

	private static GuideDao instance;

	public static GuideDao getInstence() {
		if (instance == null) {
			instance = new GuideDao();
		}
		return instance;
	}

	private Jdbcutil jdbc = Jdbcutil.getInstance();

	public List<Map<String, Object>> selectAdBoardList() { // 공지사항 게시판
		String sql = "SELECT A.NOTICE_NUM, A.NOTICE_TITLE, A.NOTICE_CONTENT, NOTICE_DATE, A.ADMIN_NAME "
				+ "FROM NOTICE A "
				+ "LEFT OUTER JOIN ADMIN B "
				+ "ON A.ADMIN_NAME = B.ADMIN_NAME " + "ORDER BY A.NOTICE_NUM DESC ";

		return jdbc.selectList(sql);

	}

	public List<Map<String, Object>> selectAdevent() { // 이벤트 게시판
		String sql = "SELECT A.EVENT_NUM, A.EVENT_DATE, A.ADMIN_NAME, A.EVENT_TITLE, A.EVENT_CONTENT"
				+ " FROM EVENT A"
				+ " LEFT OUTER JOIN ADMIN B"
				+ " ON A.ADMIN_NAME = B.ADMIN_NAME"
				+ " ORDER BY A.EVENT_NUM DESC";

		return jdbc.selectList(sql);
	}

}
