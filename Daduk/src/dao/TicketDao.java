package dao;

import java.util.List;
import java.util.Map;

import util.Jdbcutil;

public class TicketDao {
	private TicketDao(){}
	private static TicketDao instance;
	public static TicketDao getInstence(){
		if(instance == null){
			instance = new TicketDao();
		}
		return instance;
	}
	
	private Jdbcutil jdbc = Jdbcutil.getInstance();
	
	public List<Map<String, Object>> selectTicketList(){
		String sql = "SELECT * FROM TICKET ORDER BY TICKET_ID" ;
		
		return jdbc.selectList(sql);
}
}
