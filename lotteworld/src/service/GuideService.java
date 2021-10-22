package service;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import util.AdminView;
import util.JDBCUtillllll;
import util.ScanUtil;
import dao.AdBoardDao;

public class GuideService {
	private GuideService() {
	}

	private static GuideService instance;

	public static GuideService getInstance() {
		if (instance == null) {
			instance = new GuideService();
		}
		return instance;
	}

	JDBCUtillllll jdbc = JDBCUtillllll.getInstance();
	SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");

	public int selectNotice() { // 공지사항 조회

		System.out.print("조회할 게시물 번호>");
		int selectArticle = ScanUtil.nextInt();
		String noticeNo = "SELECT * FROM NOTICE WHERE NOTICE_NUM = ? ";
		ArrayList<Object> param = new ArrayList<>();
		param.add(selectArticle);
		List<Map<String, Object>> noticeList = jdbc.selectList(noticeNo, param);
		System.out.println("┏━━━━━━━━━━┓");
		System.out.println("┃1.글 번호        ┃");
		System.out.println("┃2.제목            ┃");
		System.out.println("┃3.내용            ┃");
		System.out.println("┃4.글 날짜        ┃");
		System.out.println("┃5.관리자아이디┃");
		System.out.println("┗━━━━━━━━━━┛");

		for (int i = 0; i < noticeList.size(); i++) {
			Map<String, Object> map = noticeList.get(i);
			System.out.println("1." + map.get("NOTICE_NUM"));
			System.out.println("2.제목: " + map.get("NOTICE_TITLE"));
			System.out.println("3.내용: " + map.get("NOTICE_CONTENT"));
			System.out.println("4.날짜: "
					+ (simpleDateFormat.format(map.get("NOTICE_DATE"))) + "\t"
					+ ("5.글쓴이: " + map.get("ADMIN_ID")));

		}

		System.out.println("");
		System.out.println("      ─기능 선택─     ");
		System.out.println("┌──────────────────┐"); // 공지사항 기능
		System.out.println("│1.수정   2.삭제   0.가이드│");
		System.out.println("└──────────────────┘");
		System.out.print("입력>");
		int NoticeButton = ScanUtil.nextInt();

		switch (NoticeButton) {
		case 1: // 수정
			updateNotice();
			break;
		case 2: // 삭제
			deleteNotice();
			break;
		case 0: // 가이드 목록
		}
		return AdminView.GUIDE_LIST;
	}

	private void updateNotice() { // 공지사항 수정
		System.out.println("수정할 번호를 선택해주세요.");
		int NOTICE_UPDATE = ScanUtil.nextInt();

		String UpdatenoitceBoard = " UPDATE NOTICE SET NOTICE_TITLE = ?,"
				+ " NOTICE_CONTENT = ? WHERE NOTICE_NUM = ?";
		System.out.print("제목 변경");
		String title = ScanUtil.nextLine();
		System.out.print("내용 수정");
		String content = ScanUtil.nextLine();
		ArrayList<Object> param = new ArrayList<>();
		param.add(title);
		param.add(content);
		param.add(NOTICE_UPDATE);

		int result = jdbc.update(UpdatenoitceBoard, param);
		if (result == 1) {
			System.out.println("변경이 완료 되었습니다.");
		}

	}

	private void deleteNotice() { // 공지사항 삭제
		String DeleteNotice = "DELETE FROM NOTICE" + " WHERE NOTICE_NUM = ? ";
		System.out.println("삭제하실 게시물");
		int NOTICE_NUM = ScanUtil.nextInt();
		ArrayList<Object> deleteNo = new ArrayList<>();
		deleteNo.add(NOTICE_NUM);
		int result = jdbc.update(DeleteNotice, deleteNo);
		if (result == 1) {
			System.out.println("삭제되었습니다.");
		} else {
			System.out.println("삭제할 게시물이 없습니다.");
		}
	}

	public void noticeInsert() { // 공지사항 등록

		String insertNotice = "INSERT INTO NOTICE(NOTICE_NUM, NOTICE_TITLE, NOTICE_CONTENT, NOTICE_DATE, ADMIN_ID) "
				+ "VALUES((SELECT NVL(MAX(NOTICE_NUM),0) + 1 FROM NOTICE),?,?, SYSDATE,?) ";

		Map<String, Object> param = new HashMap<>();
		System.out.print("제목>");
		String title = ScanUtil.nextLine();
		System.out.print("내용>");
		String content = ScanUtil.nextLine();
		System.out.print("관리자ID>");
		String admin = ScanUtil.nextLine();
		param.put("NOTICE_TITLE", title);
		param.put("NOTICE_CONTENT", content);
		param.put("ADMIN_ID", admin);

		List<Object> p = new ArrayList<>();

		p.add(param.get("NOTICE_TITLE"));
		p.add(param.get("NOTICE_CONTENT"));
		p.add(param.get("ADMIN_ID"));

		jdbc.update(insertNotice, p);

	}

	public int EVENTselect() { // 이벤트 게시판 조회
		System.out.print("조회할 게시물 번호>");
		int selectEvent = ScanUtil.nextInt();

		String eventNo = "SELECT * FROM EVENT WHERE EVENT_NUMBER = ? ";
		ArrayList<Object> param = new ArrayList<>();
		param.add(selectEvent);
		List<Map<String, Object>> eventList = jdbc.selectList(eventNo, param);
		System.out.println("┏━━━━━━━━━━┓");
		System.out.println("┃1.글 번호        ┃");
		System.out.println("┃2.날짜            ┃");
		System.out.println("┃3.관리자아이디┃");
		System.out.println("┃4.제목            ┃");
		System.out.println("┃5.내용            ┃");
		System.out.println("┗━━━━━━━━━━┛");

		for (int i = 0; i < eventList.size(); i++) {
			Map<String, Object> map = eventList.get(i);
			System.out.println("1." + map.get("EVENT_NUMBER"));
			System.out.println("2.날짜 "
					+ (simpleDateFormat.format(map.get("EVENT_DATE"))) + "\t"
					+ ("3.글쓴이 " + map.get("ADMIN_ID")));
			System.out.println("4.제목 " + map.get("EVENT_TITLE"));
			System.out.println("5.내용 " + map.get("EVENT_CONTENT"));
		}
		System.out.println("");
		System.out.println("      ─기능 선택─     ");
		System.out.println("┌──────────────────┐"); // 이벤트공지사항 기능
		System.out.println("│1.수정   2.삭제   0.가이드│");
		System.out.println("└──────────────────┘");
		System.out.print("입력>");
		int EventButton = ScanUtil.nextInt();

		switch (EventButton) {
		case 1: // 수정
			updateEvent();
			break;
		case 2: // 삭제
			deleteEvent();
			break;
		case 0: // 가이드 목록
		}
		return AdminView.GUIDE_LIST;
	}

	private void updateEvent() { // 이벤트 수정
		System.out.println("수정할 번호를 선택해주세요.");
		int input = ScanUtil.nextInt();
		String UpdateEventBoard = " UPDATE EVENT SET EVENT_TITLE = ?,"
				+ " EVENT_CONTENT = ? WHERE EVENT_NUMBER = ?";
		System.out.println("제목 변경");
		String title = ScanUtil.nextLine();
		System.out.println("내용 수정");
		String content = ScanUtil.nextLine();
		ArrayList<Object> param = new ArrayList<>();
		param.add(title);
		param.add(content);
		param.add(input);

		int result = jdbc.update(UpdateEventBoard, param);
		if (result == 1) {
			System.out.println("변경이 완료 되었습니다.");
		}

	}

	private void deleteEvent() { // 이벤트 게시물 삭제
		String DeleteEvent = "DELETE FROM EVENT" + " WHERE EVENT_NUMBER = ? ";
		System.out.println("삭제하실 게시물");
		int EVENT_NUMBER = ScanUtil.nextInt();
		ArrayList<Object> deleteEv = new ArrayList<>();
		deleteEv.add(EVENT_NUMBER);
		int result = jdbc.update(DeleteEvent, deleteEv);
		if (result == 1) {
			System.out.println("삭제되었습니다.");
		} else {
			System.out.println("삭제할 게시물이 없습니다.");
		}
	}

	public void EVENTinsert() { // 이벤트 등록
		String insertEvent = "INSERT INTO EVENT(EVENT_NUMBER, EVENT_DATE, ADMIN_ID, EVENT_TITLE, EVENT_CONTENT) "
				+ "VALUES((SELECT NVL(MAX(EVENT_NUMBER),0) + 1 FROM EVENT),SYSDATE ,?, ?, ?) ";

		Map<String, Object> param = new HashMap<>();
		System.out.print("관리자ID>");
		String admin = ScanUtil.nextLine();
		System.out.print("제목>");
		String title = ScanUtil.nextLine();
		System.out.print("내용>");
		String content = ScanUtil.nextLine();
		param.put("ADMIN_ID", admin);
		param.put("EVENT_TITLE", title);
		param.put("EVENT_CONTENT", content);

		List<Object> p = new ArrayList<>();

		p.add(param.get("ADMIN_ID"));
		p.add(param.get("EVENT_TITLE"));
		p.add(param.get("EVENT_CONTENT"));

		jdbc.update(insertEvent, p);

	}

}