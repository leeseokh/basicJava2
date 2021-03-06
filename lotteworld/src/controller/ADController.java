package controller;

import java.util.Map;
import service.AdminSystem;
import util.AdminView;
import util.ScanUtil;
import dao.AdminDao;

public class ADController {

	public static void main(String[] args) {

		new ADController().start();
	}

	public static Map<String, Object> LoginAdmin; 

	private AdminDao adminDao = AdminDao.getInstance();
	private AdminSystem adminSystem = AdminSystem.getInstance();

	private void start() { // 화면 이동
		int adminView = AdminView.HOME;

		while (true) {
			switch (adminView) {
			case AdminView.HOME:
				adminView = home();
				break;
			case AdminView.LOGIN:
				adminView = login();
				break;
			case AdminView.NOTICE:
				adminView = adminSystem.notice();
				break;
			case AdminView.FAQ:
//				adminView = adminSystem.faq();
//				break;
			case AdminView.EVENT:
				adminView = adminSystem.event();
				break;
			case AdminView.GUIDE_LIST:
				adminView = adminSystem.guide_list();
				break;

			}
		}
	}

	private int home() {
		System.out.println("────────대덕 🎡  랜드───────");
		System.out.println("───────관리자  프로그램──────");
		System.out.println("┌────────────────────┐");
		System.out.println("│1.로그인    2.프로그램 종  료  │");
		System.out.println("└────────────────────┘");
		System.out.print("선택>>");

		int input = ScanUtil.nextInt();

		switch (input) {
		case 1:
			return AdminView.LOGIN;
		case 2:
			System.out.println("프로그램이 종료되었습니다.");
			System.exit(0);
		}
		return AdminView.HOME; 
	}

	private int login() {

		System.out.println("├──────관리자 로그인──────┤");
		System.out.println("아이디>:");
		String adminId = ScanUtil.nextLine();
		System.out.println("비밀번호>:");
		String password = ScanUtil.nextLine(); 

		Map<String, Object> admin = adminDao.selectAdmin(adminId, password);

		if (admin == null) {
			System.out.println("아이디 혹은 비밀번호를 잘못 입력하셨습니다.");
			return AdminView.LOGIN;

		} else {
			System.out.println("-로그인 확인되었습니다.-");
		}
		ADController.LoginAdmin = admin;
	
		return AdminView.GUIDE_LIST; 

	}

}