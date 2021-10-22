package service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import util.JDBCUtillllll;
import util.ScanUtil;
import util.View;
import controller.Controller;
import dao.BoardDao;

public class BoardService {
				JDBCUtillllll jdbc = JDBCUtillllll.getInstance();
   //싱글톤 패턴
         private BoardService(){} 
         private static BoardService instance; 
         public static BoardService getInstance(){ 
            if(instance == null ) { 
               instance = new BoardService();          
         }
         return instance;   
      }
      
      private BoardDao boardDao = BoardDao.getInstance();
         
      public int boardList(){               //게시판의 내용을 가져와야함 Dao 필요함 Dao 패키지에 보드 다오클래스 만들어야함.
         List<Map<String, Object>> boardList = boardDao.selectBoardList(); 
         
         System.out.println("==================================");
         System.out.println("번호\t제목\t작성자\t작성일\t구매번호");
         System.out.println("----------------------------------");
         for(Map<String, Object>board : boardList){
            System.out.println(board.get("BOARD_NO")
            + "\t" + board.get("USER_ID")
            + "\t" + board.get("TITLE")
            + "\t" + board.get("CONTENT")
            + "\t" + board.get("REG_DATE"));
          
         }
         System.out.println("==================================");
         System.out.println("1.조회\t2.등록\t3.로그아웃");
         System.out.println("번호입력:");
         int input = ScanUtil.nextInt();
      
         switch(input){
         case 1:
            
            break;
            
         case 2:
        	 
        	 break;
            
         case 3:
            Controller.LoginUser = null; //로그아웃
            return View.HOME;
         }
         return View.BOARD_LIST;
      }

	

      	      
   
		
 
      
}