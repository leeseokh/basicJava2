package controller;

import java.util.Map;

import service.BoardService;
import service.UserService;
import util.ScanUtil;
import util.View;

public class Controller {

   public static void main(String[] args) {
      /*
       * 발표순서: 조 소개 > 주제 소개 > 주제 선정 배경> 메뉴 구조 > 시연 > 소감
       * 발표인원: 발표자 1명, ppt 및 시연도우미 1명
       * 
       * Controller : 화면 이동
       * Service :화면 기능
       * Dao : 쿼리 작성      데이터베이스 접속 객체 온리 접속만.
       */

      new Controller().start();      
   }
   
   public static Map<String, Object> LoginUser; //로그인저장 함수
   
      private UserService userService = UserService.getInstance();
      private BoardService boardService = BoardService.getInstance(); 
   
      private void start(){             //화면 이동, 화면 구분엔 숫자 붙이기.
         int view = View.HOME;
         
         while(true){               //화면 하나 끝나고 다시 다음화면.  view안에 HOME이 들어있다.
            switch(view){
            case View.HOME : view = home(); break;   
            case View.LOGIN :view = userService.login(); break;
            case View.JOIN : view = userService.join(); break;      //회원가입 완료시 여기로 돌아옴.
            case View.BOARD_LIST : view = boardService.boardList(); break;  
         }
      }   
   }

      private int home() {
         System.out.println("-------------------------------------------------");
         System.out.println("1.로그인\t2.회원가입\t0.프로그램 종료");
         System.out.println("-------------------------------------------------");
         System.out.println("번호입력>");
         
         int input = ScanUtil.nextInt();
         
         switch(input) {
         case 1: return View.LOGIN;
         case 2: return View.JOIN;
         case 0: 
              System.out.println("프로그램이 종료되었습니다.");
              System.exit(0);
         }
         return View.HOME;      //다른 숫자를 입력시  HOME으로 리턴   //모든 화면은 다음 화면으로  리턴을 해줘야함. ex) LOGIN, JOIN ...
      }   
}














