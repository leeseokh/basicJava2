package service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import util.Jdbcutil;
import util.View;
import util.sc;
import controller.Controller;
import dao.MainDao;

public class MainService {
	private MainService() {
	}
	private static MainService instance;
	Jdbcutil jdbc = Jdbcutil.getInstance();

	public static MainService getInstence() {
		if (instance == null) {
			instance = new MainService();
		}
		return instance;
	}

	private MainDao Maindao = MainDao.getInstence();

	public int MainList() {

		System.out.println("1.내정보\t 2.예매\t 3.리뷰\t 4.FAQ \t 5.공지사항     6.이벤트     7.맵");
		System.out.println("0.로그아웃");
		System.out.print("번호를 입력해 주세요 : ");
		int input = sc.nextInt();
		switch (input) {
		case 1:
			return View.MyPage;
		case 2:
			return View.Ticket;
		case 3:
			return View.Review;
		case 4:
			return View.FAQ;
		case 5:
			return View.NOTICE;
		case 6:
			return View.EVENT;
		case 7:
			return View.MAP;
		case 0:
			return View.logout;
		}
		return View.home;

	}
}
