package dao;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import util.JDBCUtillllll;


public class BoardDao {
   
   //싱글톤 패턴
   private BoardDao(){} 
   private static BoardDao instance; 
   public static BoardDao getInstance(){ 
      if(instance == null ) { 
            instance = new BoardDao();            
   }
   return instance;   
}

   private JDBCUtillllll jdbc = JDBCUtillllll.getInstance();
   
   public List<Map<String, Object>> selectBoardList(){
      String sql = "SELECT A.BOARD_NO, A.TITLE, A.CONTENT, B.USER_NAME, A.REG_DATE "
            + "FROM TB_JDBC_BOARD A "
            + "LEFT OUTER JOIN TB_JDBC_USER B "
            + "ON A.USER_ID = B.USER_ID "
            + "ORDER BY A.BOARD_NO DESC ";
      
      return jdbc.selectList(sql);
            
   }
   
//   private void ClientReview() {
// 	  
// 	  String sql= "insert into TB_JDBC_BOARD(BOARD_NO,TITLE,CONTENT,USER_ID,REG_DATE,REVEIWGRADE) values((SELECT NVL(MAX(BOARD_NO), 0) +"
//   	   		+ " 1 FROM TB_JDBC_BOARD),?,?,?,SYSDATE,?)";
//   	      ArrayList<Object> param = new ArrayList<>();
//   	      System.out.println("제목을 입력하세요");
//   	      String title = ScanUtil.nextLine();
//   	      param.add(title);
//   	    
//   	      System.out.println("내용을 작성해 주세요");
//   	      String content = ScanUtil.nextLine();
//   	      param.add(content);
//   	     
//   	      param.add(Controller.LoginUser.get("USER_ID"));
//   	      
//   	      
//   	      //구매번호
//   	      
//   	      int result = jdbc.update(sql,param);
//   
//   
//   }
}