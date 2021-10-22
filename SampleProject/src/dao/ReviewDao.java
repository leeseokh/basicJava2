package dao;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import controller.Controller;
import util.JDBCUtillllll;
import util.ScanUtil;

public class ReviewDao {
   
   //싱글톤 패턴
   private ReviewDao(){} 
   private static ReviewDao instance; 
   public static ReviewDao getInstance(){ 
      if(instance == null ) { 
            instance = new ReviewDao();            
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
}