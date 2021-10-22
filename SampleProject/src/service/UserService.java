package service;

import java.util.HashMap;
import java.util.Map;

import controller.Controller;
import util.ScanUtil;
import util.View;
import dao.UserDao;

public class UserService {
   
   //싱글톤 패턴
      private UserService(){} 
      private static UserService instance; 
      public static UserService getInstance(){ 
         if(instance == null ) { 
               instance = new UserService();            
      }
      return instance;   
   }
      
   private UserDao userDao = UserDao.getInstance();   
      
   public int join(){
      System.out.println("===========회원가입============");
      
      System.out.print("아이디:");
      String userId = ScanUtil.nextLine();
      System.out.print("비밀번호");
      String password = ScanUtil.nextLine();
      System.out.print("이름");
      String userName = ScanUtil.nextLine();
      //아이디 중복 확인 생략
      //비밀번호 확인 생략
      //정규표현식(유효성검사) 생략
      
      Map<String, Object> param = new HashMap<>();   //해쉬맵으로 받기 
      param.put("USER_ID", userId);
      param.put("PASSWORD", password);
      param.put("USER_NAME", userName);

      int result = userDao.insertUser(param);
      
      if(0<result){
         System.out.println("회원가입 성공!");
      }else{
         System.out.println("회원가입 실패!");
      }
      
      return View.HOME; // 회원가입 끝. 다음은 로그인.         
   }
   
   public int login(){
      System.out.println("====================로그인=================");
      System.out.println("아이디>:");
      String userId = ScanUtil.nextLine();
      System.out.println("비밀번호>:");
      String password = ScanUtil.nextLine();             //이것을 끝내고 데이터베이스 가서 아이디와 비밀번호 일치하는지 확인  > 다오로가자
   
      Map<String, Object> user = userDao.selectUser(userId, password);
      
      if(user == null){
         System.out.println("아이디 혹은 비밀번호를 잘못 입력하셨습니다.");
      }else{
         System.out.println("로그인 성공");
         
         Controller.LoginUser = user;      //로그인을 했으면 로그인 정보를 저장해야함. 컨트롤러에 저장.
         
         return View.BOARD_LIST;            //로그인 > 게시판      
      }
      return View.LOGIN;
}
   
   
   
   
   
   
   
}