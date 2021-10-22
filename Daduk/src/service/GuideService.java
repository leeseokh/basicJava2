package service;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import controller.Controller;
import util.Jdbcutil;
import util.View;
import util.sc;
import dao.GuideDao;
import dao.ReviewDao;

public class GuideService {
	private GuideService() {
	}

	private static GuideService instance;

	public static GuideService getInstence() {
		if (instance == null) {
			instance = new GuideService();
		}
		return instance;
	}
	private GuideDao guidedao = GuideDao.getInstence();

	Jdbcutil jdbc = Jdbcutil.getInstance();
	SimpleDateFormat format = new SimpleDateFormat("yy/MM/dd");
	int selectArticle = 0;
	public int selectNotice() { // 공지사항
		List<Map<String, Object>> NOTICE = guidedao.selectAdBoardList();

		System.out.println("──────────────────────────────────────────────");
		System.out.println("번호\t제목\t 등록시간");
		System.out.println("──────────────────────────────────────────────");
		for (Map<String, Object> notice : NOTICE) {
			System.out.println(notice.get("NOTICE_NUM") + "\t" + notice.get("NOTICE_TITLE")
					+ "\t" + format.format(notice.get("NOTICE_DATE")));
			System.out.println("──────────────────────────────────────────────");

		}
		System.out.println();
		System.out.print("0.돌아가기\t 1.조회");

		String sql4 = "SELECT CODE FROM CLIENT WHERE USER_ID = ?";
		ArrayList<Object> param2 = new ArrayList<>();
		param2.add(Controller.LoginUser.get("ADMIN_ID"));
		List<Map<String, Object>> list1 = jdbc.selectList(sql4, param2);
		for (int i = 0; i < list1.size(); i++) {
			Map<String, Object> row = list1.get(i);
			for (String key : row.keySet()) {
				if (row.get(key).equals("1")) {						
					System.out.print("\t2.등록"); //관리자 전용
				}
			}
		}

		System.out.println();
		int input3 = sc.nextInt();
		if(input3 == 0){
			return View.main;
		}
		if(input3 == 1){			
			System.out.print("조회할 게시물 번호>");
			selectArticle = sc.nextInt();
			String noticeNo = "SELECT * FROM NOTICE WHERE NOTICE_NUM = ? ";
			ArrayList<Object> param = new ArrayList<>();
			param.add(selectArticle);
			List<Map<String, Object>> noticeList = jdbc.selectList(noticeNo, param);
			System.out.println("──────────────────────────────────────────────");
			System.out.println("번호\t제목\t 등록시간");
			System.out.println("──────────────────────────────────────────────");
			for (Map<String, Object> notice : noticeList) {
				System.out.println(notice.get("NOTICE_NUM") + "\t" + notice.get("NOTICE_TITLE")
						+ "\t" + format.format(notice.get("NOTICE_DATE")));
				System.out.println("──────────────────────────────────────────────");
				System.out.println(notice.get("NOTICE_CONTENT"));
			}
			System.out.println();
			System.out.println("──────────────────────────────────────────────"); // 공지사항 기능
			System.out.print("0.목록");
			sql4 = "SELECT CODE FROM CLIENT WHERE USER_ID = ?";
			param2 = new ArrayList<>();
			param2.add(Controller.LoginUser.get("ADMIN_ID"));
			list1 = jdbc.selectList(sql4, param2);
			for (int i = 0; i < list1.size(); i++) {
				Map<String, Object> row = list1.get(i);
				for (String key : row.keySet()) {
					if (row.get(key).equals("1")) {						
						System.out.print("\t1.수정\t2.삭제"); //관리자 전용
					}
				}
			}
			System.out.println();
			int NoticeButton = sc.nextInt();

			switch (NoticeButton) {
			case 1: // 수정
				updateNotice();
				break;
			case 2: // 삭제
				deleteNotice();
				break;
			case 0: // 가이드 목록
				return View.NOTICE;
			}
		}else if(input3 == 2){
			noticeInsert();
		}
		return View.NOTICE;
	}

	private void updateNotice() { // 공지사항 수정
		String UpdatenoitceBoard = " UPDATE NOTICE SET NOTICE_TITLE = ?,"
				+ " NOTICE_CONTENT = ? WHERE NOTICE_NUM = ?";
		System.out.println("제목 변경");
		String title = sc.nextLine();
		System.out.println("내용 수정");
		String content = sc.nextLine();
		ArrayList<Object> param = new ArrayList<>();
		param.add(title);
		param.add(content);
		param.add(selectArticle);

		int result = jdbc.update(UpdatenoitceBoard, param);
		if (result == 1) {
			System.out.println("변경이 완료 되었습니다.");
		}

	}

	private void deleteNotice() { // 공지사항 삭제
		String DeleteNotice = "DELETE FROM NOTICE WHERE NOTICE_NUM = ? ";
		ArrayList<Object> deleteNo = new ArrayList<>();
		deleteNo.add(selectArticle);
		int result = jdbc.update(DeleteNotice, deleteNo);
		if (result == 1) {
			System.out.println("삭제되었습니다.");
		} else {
			System.out.println("삭제할 게시물이 없습니다.");
		}
	}

	public void noticeInsert() { // 공지사항 등록

		String insertNotice = "INSERT INTO NOTICE VALUES((SELECT NVL(MAX(NOTICE_NUM),0) + 1 FROM NOTICE),?,?,SYSDATE,?) ";
		System.out.print("제목>");
		String title = sc.nextLine();
		System.out.print("내용>");
		String content = sc.nextLine();

		Object rn3 = null;
		String rn = "SELECT USER_NAME FROM CLIENT WHERE USER_ID = ?";
		ArrayList<Object> rn1 = new ArrayList<>();
		rn1.add(Controller.LoginUser.get("USER_ID"));
		List<Map<String, Object>> rn2 = jdbc.selectList(rn, rn1);
		for (int i = 0; i < rn2.size(); i++) {
			Map<String, Object> map = rn2.get(i);
			rn3 = map.get("USER_NAME");

		}

		List<Object> param = new ArrayList<>();

		param.add(title);
		param.add(content);
		param.add(rn3);

		jdbc.update(insertNotice, param);

	}
	int selectEvent = 0;
	public int EVENTselect() { // 이벤트 게시판 조회
		List<Map<String, Object>> EVENT = guidedao.selectAdevent();

		System.out.println("──────────────────────────────────────────────");
		System.out.println("번호\t제목");
		System.out.println("──────────────────────────────────────────────");
		for (Map<String, Object> event : EVENT) {
			System.out.println(event.get("EVENT_NUM") + "\t" + event.get("EVENT_TITLE"));
			System.out.println("──────────────────────────────────────────────");

		}

		System.out.print("0.메인메뉴로돌아가기\t 1.조회");
		String sql4 = "SELECT CODE FROM CLIENT WHERE USER_ID = ?";
		ArrayList<Object> param2 = new ArrayList<>();
		param2.add(Controller.LoginUser.get("ADMIN_ID"));
		List<Map<String, Object>> list1 = jdbc.selectList(sql4, param2);
		for (int i = 0; i < list1.size(); i++) {
			Map<String, Object> row = list1.get(i);
			for (String key : row.keySet()) {
				if (row.get(key).equals("1")) {						
					System.out.print("\t2.등록"); //관리자 전용
				}
			}
		}
		System.out.println();
		int input4 = sc.nextInt();
		if (input4 == 0){
			return View.main;
		}
		else if(input4 == 1){
			System.out.print("조회할 게시물 번호>");
			selectEvent = sc.nextInt();

			String eventNo = "SELECT * FROM EVENT WHERE EVENT_NUM = ? ";
			ArrayList<Object> param = new ArrayList<>();
			param.add(selectEvent);
			List<Map<String, Object>> eventList = jdbc.selectList(eventNo, param);


			System.out.println("───────────────────────────────────────────────────────────────────");
			System.out.printf(" 번호\t제목 \t\t\t작성자\t\t등록시간");
			System.out.println();
			System.out.println("───────────────────────────────────────────────────────────────────");
			for (Map<String, Object> ent : eventList) {
				System.out.printf( ent.get("EVENT_NUM") + "\t" + ent.get("EVENT_TITLE")
						+ "\t" + ent.get("ADMIN_NAME") + "\t" + format.format(ent.get("EVENT_DATE")));
				System.out.println();
				System.out.println("───────────────────────────────────────────────────────────────────");
				System.out.println(ent.get("EVENT_CONTENT"));
				System.out.println("───────────────────────────────────────────────────────────────────");
			}
			System.out.println("");
			System.out.print("0.뒤로가기");
			sql4 = "SELECT CODE FROM CLIENT WHERE USER_ID = ?";
			param2 = new ArrayList<>();
			param2.add(Controller.LoginUser.get("ADMIN_ID"));
			list1 = jdbc.selectList(sql4, param2);
			for (int i = 0; i < list1.size(); i++) {
				Map<String, Object> row = list1.get(i);
				for (String key : row.keySet()) {
					if (row.get(key).equals("1")) {						
						System.out.print("\t1.수정\t2.삭제"); //관리자 전용
					}
				}
			}
		}
		if(input4 == 2){
			EVENTinsert();
			return View.EVENT;
		}
		System.out.println();
		int EventButton = sc.nextInt();

		switch (EventButton) {
		case 1: // 수정
			updateEvent();
			break;
		case 2: // 삭제
			deleteEvent();
			break;
		case 0: // 가이드 목록

		}
		return View.EVENT;
	}

	private void updateEvent() { // 이벤트 수정
		String UpdateEventBoard = "UPDATE EVENT SET EVENT_TITLE = ?, EVENT_CONTENT = ? WHERE EVENT_NUM = ?";
		System.out.println("제목 변경");
		String title = sc.nextLine();
		System.out.println("내용 수정");
		String content = sc.nextLine();
		ArrayList<Object> param = new ArrayList<>();
		param.add(title);
		param.add(content);
		param.add(selectEvent);

		int result = jdbc.update(UpdateEventBoard, param);
		if (result == 1) {
			System.out.println("변경이 완료 되었습니다.");
		}

	}

	private void deleteEvent() { // 이벤트 게시물 삭제
		String DeleteEvent = "DELETE FROM EVENT WHERE EVENT_NUM = ? ";
		ArrayList<Object> deleteEv = new ArrayList<>();
		deleteEv.add(selectEvent);
		int result = jdbc.update(DeleteEvent, deleteEv);
		if (result == 1) {
			System.out.println("삭제되었습니다.");
		} else {
			System.out.println("삭제할 게시물이 없습니다.");
		}
	}	
	public void EVENTinsert() { // 이벤트 등록
		String insertEvent = "INSERT INTO EVENT VALUES((SELECT NVL(MAX(EVENT_NUM),0) + 1 FROM EVENT), ?, ?, ?, SYSDATE) ";
		System.out.print("제목>");
		String title = sc.nextLine();
		System.out.print("내용>");
		String content = sc.nextLine();

		Object rn3 = null;
		String rn = "SELECT ADMIN_NAME FROM ADMIN WHERE ADMIN_ID = ?";
		ArrayList<Object> rn1 = new ArrayList<>();
		rn1.add(Controller.LoginUser.get("ADMIN_ID"));
		List<Map<String, Object>> rn2 = jdbc.selectList(rn, rn1);
		for (int i = 0; i < rn2.size(); i++) {
			Map<String, Object> map = rn2.get(i);
			rn3 = map.get("ADMIN_NAME");

		}
		ArrayList<Object> param = new ArrayList<>();
		param.add(title);
		param.add(content);
		param.add(rn3);

		int result = jdbc.update(insertEvent, param);
		if(result == 1){
			System.out.println("등록이 완료 되었습니다.");
		}
	}

}