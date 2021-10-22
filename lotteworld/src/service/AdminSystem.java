package service;

import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Map;

import util.AdminView;
import util.ScanUtil;
import controller.ADController;
import dao.AdBoardDao;

public class AdminSystem {


	private AdminSystem() {
	}

	private static AdminSystem instance;

	public static AdminSystem getInstance() {
		if (instance == null) {
			instance = new AdminSystem();
		}
		return instance;
	}

	private AdBoardDao adBoardDao = AdBoardDao.getInstance();
	private GuideService guideService = GuideService.getInstance();
	SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");

	public int guide_list() { // 관리자 관리 프로그램
		System.out.println("┌───────────────────────┐");
		System.out.println("│1.공지사항  2.FAQ  3.이벤트    │ ");
		System.out.println("└───────────────────────┘");
		System.out.println("선택>>");
		while (true) {
			int number = ScanUtil.nextInt();
			switch (number) {

			case 1:
				notice();
				break;

			case 2:
//				faq();
//				break;

			case 3:
				event();
				break;

			case 0:
				ADController.LoginAdmin = null; // 로그아웃
				return AdminView.HOME;
			}
			return AdminView.GUIDE_LIST; // 선택 > 게시판
		}
	}

	public int notice() { // 1. 공지사항
		List<Map<String, Object>> notice = adBoardDao.selectAdBoardList();

		System.out.println("───────────────┌────────┐────────────────");
		System.out.println("───────────────│공 지    사 항│────────────────");
		System.out.println("───────────────└────────┘────────────────");
		System.out.println("┏━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━┓");
		System.out.println("┃1.글 번호    2.제목    3.글 날짜    4.관리자 아이디      ┃");
		System.out.println("┗━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━┛");
		for (Map<String, Object> noticeList : notice) { // 대입받을 변수 정의 : 배열명
			System.out.println("1."
					+ (noticeList.get("NOTICE_NUM"))
					+ "\t"
					+ ("2." + noticeList.get("NOTICE_TITLE"))
					+ "\t"
					+ ("3." + simpleDateFormat.format(noticeList
							.get("NOTICE_DATE"))) + "\t"
					+ ("4." + noticeList.get("ADMIN_ID")));
		}
		System.out.println("┌───────────────────┐");
		System.out.println("│ 1.조회  2.등록 0.뒤로가기│");
		System.out.println("└───────────────────┘");
		System.out.println("선택>>");
		int num1 = ScanUtil.nextInt();

		switch (num1) {
		case 1:
			guideService.selectNotice();
			break;

		case 2:
			guideService.noticeInsert();
			break;

		case 0:
			ADController.LoginAdmin = null; // 로그아웃
			return AdminView.HOME;
		}
		return AdminView.GUIDE_LIST;
	}

//	public int faq() { // 2.FAQ
//		List<Map<String, Object>> faq = adBoardDao.selectAdFAQ();
//
//		System.out.println("────────────┌─────────┐──────────────");
//		System.out.println("────────────│   FAQ   │──────────────");
//		System.out.println("────────────└─────────┘──────────────");
//		System.out.println("┏━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━┓");
//		System.out.println("┃1.글 번호   4.관리자 아이디    3.제목    4.내용  ┃");
//		System.out.println("┗━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━┛");
//		for (Map<String, Object> faqList : faq) { // 대입받을 변수 정의 : 배열명
//			System.out.println(faqList.get("FAQ_NUMBER") + "\t"
//					+ faqList.get("ADMIN_ID") + "\t" + faqList.get("FAQ_TITLE")
//					+ "\t" + faqList.get("FAQ_CONTENT"));
//		}
//		System.out.println("┌───────────────────┐");
//		System.out.println("│ 1.조회  2.등록 0.뒤로가기│");
//		System.out.println("└───────────────────┘");
//		System.out.println("선택>>");
//		int num2 = ScanUtil.nextInt();
//
//		switch (num2) {
//		// case 1:
//		// guideService.selectFAQ();
//		// break;
//		//
//		// case 2:
//		// guideService.FAQInsert();
//		// break;
//
//		case 0:
//			ADController.LoginAdmin = null; // 로그아웃
//			return AdminView.HOME;
//		}
//		return AdminView.GUIDE_LIST;
//	}

	public int event() { // 3.EVENT
		List<Map<String, Object>> event = adBoardDao.selectAdevent();

		System.out.println("────────────────┌─────────┐─────────────────");
		System.out.println("────────────────│  EVENT  │─────────────────");
		System.out.println("────────────────└─────────┘─────────────────");
		System.out.println("┏━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━┓");
		System.out.println("┃1.글 번호   2.등록 날짜             3.관리자아이디           4.제목       ┃");
		System.out.println("┗━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━┛");
		for (Map<String, Object> eventList : event) { // 대입받을 변수 정의 : 배열명
			System.out.println("1."
					+ (eventList.get("EVENT_NUMBER"))
					+ "\t"
					+ ("2." + (simpleDateFormat.format(eventList
							.get("EVENT_DATE")))) + "\t"
					+ ("3." + (eventList.get("ADMIN_ID"))) + "\t"
					+ ("4." + (eventList.get("EVENT_TITLE"))));

		}
		System.out.println("┌───────────────────┐");
		System.out.println("│ 1.조회  2.등록 0.뒤로가기│");
		System.out.println("└───────────────────┘");
		System.out.println("선택>>");
		int num3 = ScanUtil.nextInt();

		switch (num3) {
		case 1:
			guideService.EVENTselect();
			break;

		case 2:
			guideService.EVENTinsert();
			break;

		case 0:
			ADController.LoginAdmin = null; // 로그아웃
			return AdminView.HOME;
		}
		return AdminView.GUIDE_LIST;
	}
}
