package controller;

import java.util.Map;

import service.FAQService;
import service.GuideService;
import service.MainService;
import service.MapService;
import service.ReviewService;
import service.TicketService;
import service.UserService;
import util.View;
import util.sc;

public class Controller {

	public static void main(String[] args) {
		/*
		 * 발표순서 : 조 소개 > 주제 소개 > 주제 선정 배경 > 메뉴 구조 > 시연 > 소감
		 * 발표인원 : 발표자 1명 , ppt 및 시연도우미 1명
		 * Controller : 화면이동
		 * Service : 화면기능
		 * Dao : 쿼리 작성
		 */
		new Controller().start();	
	}
	
	public static Map<String, Object> LoginUser; //글 작성시 작성자에 끌어다 씀
	
	private UserService userservice = UserService.getInstence();
	private TicketService ticketService = TicketService.getInstence();
	private MainService mainService = MainService.getInstence();
	private FAQService faqService = FAQService.getInstence();
	private ReviewService reviewService = ReviewService.getInstence();
	private GuideService guideService = GuideService.getInstence();
	private MapService mapService = MapService.getInstence();
	private void start() {
		int view = View.home;
		
		while(true){
			switch(view){
			case View.home : 
				view = home();
				break;
			case View.login :
				view = userservice.login();
				break;
			case View.join : 
				view = userservice.join();
				break;			
			case View.Ticket :
				view = ticketService.TicketList(); 
				break; 
			case View.MyPage :
				view = userservice.MyPage(); 
				break; 
			case View.logout :
				view = userservice.Logout(); 
				break; 
			case View.main :
				view = mainService.MainList(); 
				break; 
			case View.FAQ :
				view = faqService.FAQList(); 
				break; 
			case View.Review :
				view = reviewService.reviewlist(); 
				break; 
			case View.NOTICE :
				view = guideService.selectNotice(); 
				break; 
			case View.EVENT :
				view = guideService.EVENTselect(); 
				break; 
			case View.MAP :
				view = mapService.MAP(); 
				break; 
			}
		}
	}

	private int home() { //어느화면으로 갈지 정해줌	
		System.out.println("─────────────────────────────────────");
		System.out.println("1.로그인\t 2.회원가입 \t 0.프로그램 종료");
		System.out.println("─────────────────────────────────────");
		System.out.println("번호를 입력하세요");
		
		int input = sc.nextInt();
		
		switch (input) {
		case 1: 
			return View.login;
		case 2: 
			return View.join;
		case 0: 
			System.out.println("프로그램이 종료되었습니다.");
			System.exit(0);
		}
		return View.home; //switch문에 적힌 숫자와 다를시 발동
	}

}
