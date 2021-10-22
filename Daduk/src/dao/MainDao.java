package dao;

import java.util.List;
import java.util.Map;

import util.Jdbcutil;

public class MainDao {
	MainDao(){}
	private static MainDao instance;
	public static MainDao getInstence(){
		if(instance == null){
			instance = new MainDao();
		}
		return instance;
	}
	
	private Jdbcutil jdbc = Jdbcutil.getInstance();
				
	}
	
