package service;

import util.View;
import util.sc;

public class MapService {
	private MapService() {
	}

	private static MapService instance;

	public static MapService getInstence() {
		if (instance == null) {
			instance = new MapService();
		}
		return instance;
	}

	public int MAP() {
		System.out.println("━━━━━━━━━━━━━━━━MINI MAP━━━━━━━━━━━━━━━━");
		System.out.println("┏━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━┓");
		System.out.println("┃ 1.🎠	       2.🎃            	9.🎪                        입구 ");
		System.out.println("┃       3.🎇                                                                                  ┗━ ");
		System.out.println("┃                 5.🎡   	   4.🎆               8.출구 ");
		System.out.println("┃  1🎪                                                                                                    ┏━ ");
		System.out.println("┃                  6.🎢                                                 ┗━ ");
		System.out.println("┃12.🛍 	   7.🎄                                         11.🍔🍕                     13.🚙🚕🏍   ");
		System.out.println("┗━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━┛");
		System.out.println("━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━┓");
		System.out.println("입구 1.회전목마   2.유령의집   3.자이드롭   4.드롭자이   5.대관람차   6.롤러코스터");
		System.out.println("   7.숲속체험관   8.출구   9화장실   11.식당   12.기념품 가게   13.주차장 ");
		System.out.println("━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━┛");
		System.out.println("뒤로가기(0)");
		System.out.print("확인 하실 시설 번호를 입력해주세요 (1-7) >>");
		int select = sc.nextInt();
		if (select == 0) {
			return View.main;
		}
		if (select == 1) {
			System.out.println("🎠-회전목마: 영유아는 보호자의 보호아래 같이 탑승 가능합니다.");
			System.out.println("돌아가기(0)");
			int input1 = sc.nextInt();
			if (input1 == 0) {
				return View.MAP;
			}
		}
		if (select == 1) {
			System.out.println("🎠-회전목마: 영유아는 보호자의 보호아래 같이 탑승 가능합니다.");
			System.out.println("돌아가기(0)");
			int input1 = sc.nextInt();
			if (input1 == 0) {
				return View.MAP;
			}
		}
		if (select == 2) {
			System.out.println("🎃-유령의 집: 심장이 약하신분은 입장을 삼가해주세요.");
			System.out.println("돌아가기(0)");
			int input1 = sc.nextInt();
			if (input1 == 0) {
				return View.MAP;
			}
		}
		if (select == 3) {
			System.out.println("🎇-자이드롭: 신장 135cm이하는 입장을 삼가해주세요. ");
			System.out.println("돌아가기(0)");
			int input1 = sc.nextInt();
			if (input1 == 0) {
				return View.MAP;
			}
		}
		if (select == 4) {
			System.out.println("🎆-드롭자이: 신장 135cm이하는 입장을 삼가해주세요. ");
			System.out.println("돌아가기(0)");
			int input1 = sc.nextInt();
			if (input1 == 0) {
				return View.MAP;
			}
		}
		if (select == 5) {
			System.out.println("🎡-대관람차: 영유아는 보호자의 보호아래 같이 탑승 가능합니다.");
			System.out.println("돌아가기(0)");
			int input1 = sc.nextInt();
			if (input1 == 0) {
				return View.MAP;
			}
		}
		if (select == 6) {
			System.out.println("🎢-롤러코스터: 신장 145cm이하는 입장을 삼가해주세요.");
			System.out.println("돌아가기(0)");
			int input1 = sc.nextInt();
			if (input1 == 0) {
				return View.MAP;
			}
		}
		if (select == 7) {
			System.out.println("🎄-숲속체험관: 식물 전시관으로 숲속 체험이 가능합니다.");
			System.out.println("돌아가기(0)");
			int input1 = sc.nextInt();
			if (input1 == 0) {
				return View.MAP;
			}
		}
		return View.MAP;
	}
}
