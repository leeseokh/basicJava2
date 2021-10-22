package service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import controller.Controller;
import util.Jdbcutil;
import util.sc;
import util.View;
import dao.FAQDao;

public class FAQService {

	private FAQService() {
	}

	private static FAQService instance;

	public static FAQService getInstence() {
		if (instance == null) {
			instance = new FAQService();
		}
		return instance;

	}

	Jdbcutil jdbc = Jdbcutil.getInstance();
	private FAQDao FAQdao = FAQDao.getInstence();

	public int FAQList() {
		List<Map<String, Object>> FAQ = FAQdao.FAQview();

		System.out.println("──────────────────────────────────────────────");
		System.out.println("번호\t제목");
		System.out.println("──────────────────────────────────────────────");
		for (Map<String, Object> faq : FAQ) {
			System.out.println(faq.get("FAQ_NUM") + "\t" + faq.get("FAQ_TITLE"));
		}
		System.out.println("──────────────────────────────────────────────");
			System.out.print("0.메인메뉴로돌아가기\t 1.조회");
			String sql4 = "SELECT CODE FROM CLIENT WHERE USER_ID = ?";
			ArrayList<Object> param2 = new ArrayList<>();
			param2.add(Controller.LoginUser.get("ADMIN_ID"));
			List<Map<String, Object>> list1 = jdbc.selectList(sql4, param2);
			for (int i = 0; i < list1.size(); i++) {
				Map<String, Object> row = list1.get(i);
				for (String key : row.keySet()) {
					if (row.get(key).equals("1")) {						
						System.out.print("\t2.등록\t3.삭제");
					}
				}
			}	
			System.out.println();
		int input = sc.nextInt();
		switch (input) {

		case 1:
			System.out.println("조회하실 번호를 입력해 주세요");
			int num = sc.nextInt();
			String sql1 = "SELECT * FROM FAQ WHERE FAQ_NUM = ? ";
			ArrayList<Object> FAQVIEW = new ArrayList<>();
			FAQVIEW.add(num);
			List<Map<String, Object>> faqnum = jdbc.selectList(sql1, FAQVIEW);

			System.out.println("─────────────────────────────────────────────");
			System.out.println("번호\t제목");
			for (int i = 0; i < faqnum.size(); i++) {
				Map<String, Object> map = faqnum.get(i);
				System.out.println(map.get("FAQ_NUM") + "\t" + map.get("FAQ_TITLE"));
				System.out.println("──────────────────────────────────────────────");
				System.out.println(map.get("FAQ_CONTENT"));
				System.out
						.println("──────────────────────────────────────────────");
			}
			System.out.println("0.뒤로가기");
			int back = sc.nextInt();
			if(back == 0){
				return View.FAQ;
			}
			

		case 0:
			return View.main;

		case 2:
			String sql = " INSERT INTO FAQ(FAQ_NUM,ADMIN_ID,FAQ_TITLE,FAQ_CONTENT)"
					+ "	values((SELECT NVL(MAX(FAQ_NUM), 0) + 1 FROM FAQ),?,?,?)";
			List<Object> p = new ArrayList<>();
			System.out.println(" 작성을 시작합니다.");
			System.out.print("☞  FAQ 제목 : ");
			String FAQ_TITLE = sc.nextLine();
			System.out.print("☞  FAQ 내용 : ");
			String FAQ_CONTENT = sc.nextLine();

			Map<String, Object> param = new HashMap<>();
			param.put("ADMIN_ID", 1);
			param.put("FAQ_TITLE", FAQ_TITLE);
			param.put("FAQ_CONTENT", FAQ_CONTENT);

			int result = FAQdao.insertFAQ(param);

			if (0 < result) {
				System.out.println();
				System.out.println("등록 성공");
			} else {

				System.out.println("등록 실패");

			}
		case 3:
			System.out.println("삭제하실 번호를 입력해 주세요");
			int faqdelete = sc.nextInt(); 
			String FAQDELETE = "DELETE FROM FAQ WHERE FAQ_NUM = ?";
			
			ArrayList<Object> fd = new ArrayList<>();
			fd.add(faqdelete);
			
			int result1 = jdbc.update(FAQDELETE, fd);
			if(result1 > 0) {
				System.out.println("삭제가 완료되었습니다.");
			}
			return View.FAQ;
		}
		return View.main;

	}

}
