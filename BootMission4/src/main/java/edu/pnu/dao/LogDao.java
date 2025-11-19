package edu.pnu.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Map;


public class LogDao {
	
	private Connection con = null;
	
	public LogDao() {
		try {
			
			Class.forName("com.mysql.cj.jdbc.Driver");
			
			String url = "jdbc:mysql://localhost:3306/bootmission";
			String user = "musthave";
			String pwd = "tiger";
			
			con = DriverManager.getConnection(url, user, pwd);
			
		} catch(Exception e){
			System.out.println("DB 연결 중 에러");
			e.printStackTrace();
		}
	}
	
	public void addLog(Map<String, Object> map) {
		String sql = "insert into dblog (method, sqlstring, success) values (?,?,?)";
		
		try {
			PreparedStatement psmt = con.prepareStatement(sql);
			psmt.setString(1, String.valueOf(map.get("method")));
			psmt.setString(2, String.valueOf(map.get("sqlstring")));
			psmt.setBoolean(3, (Boolean) map.get("success"));
			
			psmt.executeUpdate();
			
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("addLog() 호출 중 오류");
		}
	}
}
