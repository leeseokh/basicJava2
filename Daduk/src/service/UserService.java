package service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import util.View;
import util.Jdbcutil;
import util.sc;
import controller.Controller;
import dao.UserDao;

public class UserService {

	// 싱글톤 패턴
	private UserService() {
	}

	private static UserService instance;

	public static UserService getInstence() {
		if (instance == null) {
			instance = new UserService();
		}
		return instance;
	}

	private UserDao userdao = UserDao.getInstence();
	private Jdbcutil jdbc = Jdbcutil.getInstance();

	// 회원가입
	public int join() {
		System.out.println("────────── 회원 가입 ──────────");
		System.out.print("아이디 :");
		String userId = sc.nextLine();
		Jdbcutil jdbc = Jdbcutil.getInstance();
		String sql = "SELECT * FROM CLIENT WHERE USER_ID = ?";
		ArrayList<Object> param1 = new ArrayList<>();
		param1.add(userId);
		List<Map<String, Object>> list = jdbc.selectList(sql, param1);

		for (int i = 0; i < list.size(); i++) {
			Map<String, Object> row = list.get(i);
			for (String key : row.keySet()) {
				if (row.get(key) == null) {
				} else if (row.get(key) != null) {
					System.out.println("중복된 아이디 입니다.");
					return 2;
				}
			}
		}
		String regex = "[a-z0-9]{5,20}";
		Pattern p = Pattern.compile(regex);
		Matcher m = p.matcher(userId);
		if (m.matches() == false) {
			System.out.println("5~20자의 영문 소문자와 숫자만 사용가능합니다.");
			return 2;
		} else {
			System.out.println("ok");
		}
		System.out.print("비밀번호 :");
		String PassWord = sc.nextLine();
		String regex1 = "[a-z0-9]{5,20}";
		Pattern p1 = Pattern.compile(regex1);
		Matcher m1 = p1.matcher(PassWord);
		if (m1.matches() == false) {
			System.out.println("5~20자의 영문 소문자와 숫자만 사용가능합니다.");
			return 2;
		} else {
			System.out.println("ok");

		}
		System.out.print("비밀번호 재입력:");
		String RePassWord = sc.nextLine();
		String regex3 = "[a-z0-9]{5,20}";
		Pattern p3 = Pattern.compile(regex3);
		Matcher m3 = p3.matcher(RePassWord);
		if (m1.matches() == false) {
			System.out.println("5~20자의 영문 소문자와 숫자만 사용가능합니다.");
			return 2;
		} else if (PassWord.equals(RePassWord)) {
			System.out.println("비밀번호가 일치합니다.");
		} else {
			System.out.println("비밀번호가 일치하지 않습니다.");
			return 2;
		}
		System.out.print("이름 :");
		String UserName = sc.nextLine();
		String regex2 = "[가-힣A-Za-z]{2,10}";
		Pattern p2 = Pattern.compile(regex2);
		Matcher m2 = p2.matcher(UserName);
		if (m2.matches() == false) {
			System.out.println("2~8글자의 한글 또는 영문만 사용가능합니다.");
			return 2;
		} else {
			System.out.println("ok");
		}
		System.out.print("전화번호(ex : 010-1234-5678) : ");
		String call = sc.nextLine();
		String regex4 = "^01([0|1|6|7|8|9])-?([0-9]{3,4})-?([0-9]{4})$";
		Pattern p4 = Pattern.compile(regex4);
		Matcher m4 = p4.matcher(call);
		if (m4.matches() == false) {
			System.out.println("형식에 맞지 않는 번호입니다.");
			return 2;
		} else {
			System.out.println("ok");
		}
		System.out.print("카드번호(- 없이 숫자로만 입력해 주세요) :");
		String card = sc.nextLine();
		String regex5 = "[0-9]{16}";
		Pattern p5 = Pattern.compile(regex5);
		Matcher m5 = p5.matcher(card);
		if (m5.matches() == false) {
			System.out.println("형식에 맞지 않는 번호입니다.");
			return 2;
		} else {
			System.out.println("ok");
		}
		Map<String, Object> param = new HashMap<>();
		param.put("USER_ID", userId);
		param.put("PASSWORD", PassWord);
		param.put("USER_NAME", UserName);
		param.put("USER_CALL", call);
		param.put("USER_CARDNUM", card);

		int result = userdao.insertUser(param);

		if (0 < result) {
			System.out.println("회원가입 성공");
			String MemberShipInsert = "INSERT INTO MEMBERSHIP VALUES(0,?)";

			ArrayList<Object> ms = new ArrayList<>();
			ms.add(card);

			Object result1 = jdbc.update(MemberShipInsert, ms);
		} else {
			System.out.println("회원가입 실패");
		}
		return View.home;

	}

	// 로그인
	public int login() {
		System.out.println("────────── 로그인 ──────────");
		System.out.println("아이디가 없으시면 2를 입력해주세요");
		System.out.print("아이디 : ");
		String User_Id = sc.nextLine();
		if (User_Id.equals("2")) {
			return View.join;
		}
		System.out.print("비밀번호 : ");
		String PassWord = sc.nextLine();
		// 둘다 일치하는사람 찾기
		
		if(User_Id.equals("1") && PassWord.equals("1")){
			Map<String, Object> user = userdao.selectAdmin(User_Id, PassWord);
			if (user == null) {

				System.out.println("아이디 혹은 비밀번호를 잘못 입력했습니다.");
			} else {
				System.out.println("로그인 성공");

				Controller.LoginUser = user; // 로그인 정보를 controller에 있는 loginuser에 저장

				return View.main; //
			}
		}
		else {
		Map<String, Object> user = userdao.selectUser(User_Id, PassWord);
		if (user == null) {

			System.out.println("아이디 혹은 비밀번호를 잘못 입력했습니다.");
		} else {
			System.out.println("로그인 성공");

			Controller.LoginUser = user; // 로그인 정보를 controller에 있는 loginuser에 저장

			return View.main; //
		}
		}
		return View.login;
	}
	public int Logout() {
		return View.home;
	}

	// 마이페이지
	public int MyPage() {
		List<Map<String, Object>> MyPage = userdao.MyPage();
		String sql = "SELECT C.USER_ID, C.PASSWORD, C.USER_NAME, C.USER_CALL, C.USER_CARDNUM, M.POINT"
				+ " FROM CLIENT C LEFT OUTER JOIN MEMBERSHIP M"
				+ " ON C.USER_CARDNUM = M.USER_CARDNUM"
				+ " WHERE C.USER_ID = ? ";

		ArrayList<Object> param1 = new ArrayList<>();
		param1.add(Controller.LoginUser.get("USER_ID"));

		List<Map<String, Object>> list = jdbc.selectList(sql, param1);

		System.out.println("──────────────────────────────────────");
		System.out.println(" \t\t마이페이지");
		System.out.println("──────────────────────────────────────");
		for (Map<String, Object> mp : list) {
			System.out.println("  아이디  : " + mp.get("USER_ID"));
			System.out.println();
			System.out.println(" 비밀번호 : " + mp.get("PASSWORD"));
			System.out.println();
			System.out.println("  이름    : " + mp.get("USER_NAME"));
			System.out.println();
			System.out.println(" 전화번호 : " + mp.get("USER_CALL"));
			System.out.println();
			String a = (String) mp.get("USER_CARDNUM");
			String b = "";
			for (int i = 0; i < a.length(); i++) { // 7번
				if (i > 0 && (i % 4) == 0) { // i가 하나씩 증가 해서 나머지를 계산
					b = a.charAt(a.length() - i - 1) + "-" + b;
				} else {
					b = a.charAt(a.length() - i - 1) + b;
				}
			}
			System.out.println(" 카드번호 : " + b);
			System.out.println();
			System.out.println(" 포인트   : " + mp.get("POINT") + "점");
			System.out.println("──────────────────────────────────────");

		}

		System.out.println("0.돌아가기\t 1.회원탈퇴\t 2.회원정보수정");
		int input = sc.nextInt();

		if (input == 1) {
			System.out.println("정말로 탈퇴 하시려면 회원탈퇴를 입력해 주세요");
			Object input1 = sc.nextLine();
			if (input1.equals("회원탈퇴")) {

				String sqlDelete = "DELETE FROM CLIENT WHERE USER_ID = ?";

				Object md3 = null;
				String md = "SELECT USER_CARDNUM FROM CLIENT WHERE USER_ID = ?";
				ArrayList<Object> md1 = new ArrayList<>();
				md1.add(Controller.LoginUser.get("USER_ID"));
				List<Map<String, Object>> md2 = jdbc.selectList(md, md1);
				for (int i = 0; i < md2.size(); i++) {
					Map<String, Object> map = md2.get(i);
					md3 = map.get("USER_CARDNUM");

				}

				String sqlDelete2 = "DELETE FROM MEMBERSHIP WHERE USER_CARDNUM = ?";

				ArrayList<Object> param = new ArrayList<>();
				param.add(Controller.LoginUser.get("USER_ID"));
				int de = jdbc.update(sqlDelete, param);

				ArrayList<Object> param3 = new ArrayList<>();
				param3.add(md3);

				int del = jdbc.update(sqlDelete2, param3);
				if (de == 1 && del == 1) {
					System.out.println("정상적으로 탈퇴 되었습니다.");
				}
				return View.home;
			} else {
				return View.home;
			}
		}
		if (input == 0) {
			return View.main;
		}
		if (input == 2) {
			System.out.println("1.비밀번호 변경\t 2.이름변경 \t 3.전화번호변경");

			int input1 = sc.nextInt();
			if (input1 == 1) {
				System.out.println("변경하실 비밀번호를 입력해 주세요");
				String input2 = sc.nextLine();
				String regex1 = "[a-z0-9]{5,20}";
				Pattern p1 = Pattern.compile(regex1);
				Matcher m1 = p1.matcher(input2);
				if (m1.matches() == false) {
					System.out.println("5~20자의 영문 소문자와 숫자만 사용가능합니다.");
					return 2;
				} else {
				String sqlUPDATE = "UPDATE CLIENT SET PASSWORD = ? WHERE USER_ID = ?";
				ArrayList<Object> param = new ArrayList<>();
				param.add(input2);
				param.add(Controller.LoginUser.get("USER_ID"));
				int result = jdbc.update(sqlUPDATE, param);
				if (result == 1) {
					System.out.println("변경이 완료 되었습니다 다시 로그인 해주세요.");
					return View.home;
				}
				}
			} else if (input1 == 2) {
				System.out.println("변경하실 이름을 입력해 주세요");
				Object input2 = sc.nextLine();
				String sqlUPDATE = "UPDATE CLIENT SET USER_NAME = ? WHERE USER_ID = ?";
				ArrayList<Object> param = new ArrayList<>();
				param.add(input2);
				param.add(Controller.LoginUser.get("USER_ID"));
				int result = jdbc.update(sqlUPDATE, param);
				if (result == 1) {
					System.out.println("변경이 완료 되었습니다.");
					return View.MyPage;
				}
			} else if (input1 == 3) {
				System.out.println("변경하실 전화번호를 입력해 주세요");
				String input2 = sc.nextLine();
				String regex4 = "^01([0|1|6|7|8|9])-?([0-9]{3,4})-?([0-9]{4})$";
				Pattern p4 = Pattern.compile(regex4);
				Matcher m4 = p4.matcher(input2);
				if (m4.matches() == false) {
					System.out.println("형식에 맞지 않는 번호입니다.");
					return View.MyPage;
				} else {
				String sqlUPDATE = "UPDATE CLIENT SET USER_CALL = ? WHERE USER_ID = ?";
				ArrayList<Object> param = new ArrayList<>();
				param.add(input2);
				param.add(Controller.LoginUser.get("USER_ID"));
				int result = jdbc.update(sqlUPDATE, param);
				if (result == 1) {
					System.out.println("변경이 완료 되었습니다.");
					return View.MyPage;
					}
				}
			}
		}
		return View.home;
	}
}