package service;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import controller.Controller;
import util.Jdbcutil;
import util.sc;
import util.View;
import dao.ReviewDao;

public class ReviewService {

	private ReviewService() {
	}

	private static ReviewService instance;

	public static ReviewService getInstence() {
		if (instance == null) {
			instance = new ReviewService();
		}
		return instance;
	}

	SimpleDateFormat format = new SimpleDateFormat("yy/MM/dd");
	Jdbcutil jdbc = Jdbcutil.getInstance();
	private ReviewDao reviewdao = ReviewDao.getInstence();
	Object un = null;
	public int reviewlist() {
		List<Map<String, Object>> reviewList = reviewdao.selectREVIEW();
		System.out.println("──────────────────────────────────────────────");
		System.out.println("리뷰번호\t리뷰제목\t작성자\t작성일");
		System.out.println("──────────────────────────────────────────────");
		for (Map<String, Object> review : reviewList) {
			un = review.get("USER_NAME");
			System.out.println(review.get("REVIEW_NUM") + "\t"
					+ review.get("REVIEW_TITLE") + "\t"
					+ un + "\t"
					+ format.format(review.get("REVIEW_DATE")));
		}
		System.out.println("──────────────────────────────────────────────");
		System.out.println("1.조회\t2.등록\t0.메인메뉴로돌아가기");
		int input = sc.nextInt();
		
		switch (input) {
		case 1:
			System.out.println("조회하실 번호를 입력해 주세요");
			int num = sc.nextInt();
			String sql1 = "SELECT REVIEW_NUM,USER_NAME,REVIEW_GRADE,REVIEW_DATE,REVIEW_CONTENT FROM REVIEW WHERE REVIEW_NUM = ? ";
			ArrayList<Object> reviewselect = new ArrayList<>();
			reviewselect.add(num);
			List<Map<String, Object>> reviewnum = jdbc.selectList(sql1,
					reviewselect);

			System.out.println("─────────────────────────────────────────────");
			System.out.println("번호\t작성자\t별점\t작성일");
			System.out
					.println("──────────────────────────────────────────────");
			int back = 0;
			for (int i = 0; i < reviewnum.size(); i++) {
				Map<String, Object> map = reviewnum.get(i);
				System.out.print(map.get("REVIEW_NUM") + "\t"
						+ map.get("USER_NAME") + "\t");
				int g = Integer.parseInt((String) map.get("REVIEW_GRADE"));
				for (int j = 0; j < 1; j++) {
					for (int k = 0; k < 5; k++) {
						if (k < g) {
							System.out.print("★");
						} else {
							System.out.print("☆");
						}
					}
					break;
				}
				System.out.println("\t" + format.format(map.get("REVIEW_DATE")));
				System.out
						.println("──────────────────────────────────────────────");
				System.out.println(map.get("REVIEW_CONTENT"));
				System.out.println();
				System.out.println("");
				System.out.println("0.뒤로가기\t 1.수정 \t2.삭제");
				back = sc.nextInt();
			}
			switch (back) {
			case 0:
				return reviewlist();
			case 1:
				String sql5 = "SELECT USER_NAME FROM CLIENT WHERE USER_ID = ?";
				ArrayList<Object> param2 = new ArrayList<>();
				param2.add(Controller.LoginUser.get("USER_ID"));
				List<Map<String, Object>> list1 = jdbc.selectList(sql5, param2);
				for (int i = 0; i < list1.size(); i++) {
					Map<String, Object> row = list1.get(i);
					for (String key : row.keySet()) {
						if (row.get(key).equals(un)) {
							System.out.println("변경하실 제목을 입력해주세요");
							String title = sc.nextLine();							
							System.out.println("변경하실 내용을 입력해주세요");
							String content = sc.nextLine();
							System.out.println("변경하실 별점을 입력해주세요");
							int grade = sc.nextInt();
							
							String sql2 = "UPDATE REVIEW SET REVIEW_TITLE = ? , REVIEW_CONTENT = ? , REVIEW_GRADE = ? WHERE REVIEW_NUM = ?";
							ArrayList<Object> reviewupdate = new ArrayList<>();
							reviewupdate.add(title);
							reviewupdate.add(content);
							reviewupdate.add(grade);
							reviewupdate.add(num);
							int result1 = jdbc.update(sql2, reviewupdate);
							if (result1 == 1) {
								System.out.println("변경되었습니다");
								break;
							} else {
								System.out.println("변경되지 않았습니다.다시시도해 주세요");
								break;
							}
						} else {
							System.out.println("자신의 리뷰만 변경하실수 있습니다.");
						}
					}
					return View.Review;
				}
				
			case 2:
				String sql3 = "SELECT USER_NAME FROM CLIENT WHERE USER_ID = ?";
				ArrayList<Object> param1 = new ArrayList<>();
				param1.add(Controller.LoginUser.get("USER_ID"));
				List<Map<String, Object>> list = jdbc.selectList(sql3, param1);
				for (int i = 0; i < list.size(); i++) {
					Map<String, Object> row = list.get(i);
					for (String key : row.keySet()) {
						if (row.get(key).equals(un)) {
							String sql2 = "DELETE FROM REVIEW WHERE REVIEW_NUM= ?";
							ArrayList<Object> reviewdelete = new ArrayList<>();
							reviewdelete.add(num);
							int result1 = jdbc.update(sql2, reviewdelete);
							if (result1 == 1) {
								System.out.println("삭제되었습니다");
								break;
							} else {
								System.out.println("삭제되지 않았습니다.다시시도해 주세요");
								break;
							}
						} else {
							System.out.println("자신의 리뷰만 삭제하실수 있습니다.");
						}
					}
				}
				return View.Review;
			}
		case 0:
			return View.main;

		case 2:
			String sql4 = "SELECT USER_ID FROM BUY WHERE USER_ID = ?";
			ArrayList<Object> param2 = new ArrayList<>();
			param2.add(Controller.LoginUser.get("USER_ID"));
			List<Map<String, Object>> list1 = jdbc.selectList(sql4, param2);
			for (int i = 0; i < list1.size(); i++) {
				Map<String, Object> row = list1.get(i);
				for (String key : row.keySet()) {
					if (row.get(key).equals(null)) {
						System.out.println("구매기록이 없습니다.");
					} else {
						String sql = "INSERT INTO REVIEW(REVIEW_NUM,CLIENT_ID,REVIEW_CONTENT,REVIEW_TITLE,REVIEW_GRADE,REVIEW_DATE)"
								+ "	values((SELECT NVL(MAX(REVIEW_NUM), 0) + 1 FROM REVIEW),?,?,?,?,SYSDATE)";

						List<Object> p = new ArrayList<>();
						System.out.println("──────────────────────────────────────────");
						System.out.println("\t\t리뷰작성 ");
						System.out.println("──────────────────────────────────────────");
						System.out.println("리뷰  작성을 시작합니다.");
						System.out.print("☞  리뷰  제목>");
						String REVIEW_TITLE = sc.nextLine();

						System.out.print("☞  리뷰 내용>");
						String REVIEW_CONTENT = sc.nextLine();
						System.out.print("☞  평점(0~5 점수를 주세요) >");
						int REVIEW_GRADE = sc.nextInt();

						for (int j = 0; j < 1; j++) {
							for (int k = 0; k < 5; k++) {
								if (k < REVIEW_GRADE) {
									System.out.print("★");
								} else {
									System.out.print("☆");
								}
							}
						}
						Object rn3 = null;
						String rn = "SELECT USER_NAME FROM CLIENT WHERE USER_ID = ?";
						ArrayList<Object> rn1 = new ArrayList<>();
						rn1.add(Controller.LoginUser.get("USER_ID"));
						List<Map<String, Object>> rn2 = jdbc
								.selectList(rn, rn1);
						for (int l = 0; l < rn2.size(); l++) {
							Map<String, Object> map = rn2.get(l);
							rn3 = map.get("USER_NAME");

						}

						Map<String, Object> param = new HashMap<>();
						param.put("USER_NAME", rn3);
						param.put("REVIEW_TITLE", REVIEW_TITLE);
						param.put("REVIEW_CONTENT", REVIEW_CONTENT);
						param.put("REVIEW_GRADE", REVIEW_GRADE);

						int result = reviewdao.insertREVIEW(param);

						if (0 < result) {
							System.out.println();
							System.out.println("리뷰  등록 성공");
						} else {

							System.out.println("리뷰  등록 실패");

						}

					}
					return reviewlist();
				}
			}
		}
		return reviewlist();

	}
}
