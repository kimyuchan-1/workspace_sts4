package edu.pnu.dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import edu.pnu.domain.MemberDTO;

public class MemberDao {
	
	private Connection con;
	
	public MemberDao() {
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
	
	public List<MemberDTO> getAllMember() {
		
		List<MemberDTO> result = new ArrayList<>();
		String sql = "select * from member";
		
		try (PreparedStatement psmt = con.prepareStatement(sql);
			 ResultSet rs = psmt.executeQuery()) {
			
			
			while (rs.next()) {
				MemberDTO dto = new MemberDTO();
				dto.setId(rs.getInt(1));
				dto.setPass(rs.getString(2));
				dto.setName(rs.getString(3));
				dto.setRegidate(rs.getDate(4));
				result.add(dto);
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("getAllMember() 호출 중 오류");
		} 
		
		return result;
		
	}
	
	public MemberDTO getMemberById(Integer id) {
		String sql = "select * from member where id = ?";
		MemberDTO result = new MemberDTO();
		
		try (PreparedStatement psmt = con.prepareStatement(sql)) {
			psmt.setInt(1, id);
			ResultSet rs = psmt.executeQuery();
			
			while (rs.next()) {
				result.setId(rs.getInt(1));
				result.setPass(rs.getString(2));
				result.setName(rs.getString(3));
				result.setRegidate(rs.getDate(4));
			}
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("getMemberById() 호출 중 오류");
		}
		
		return result;
	}
	
	public MemberDTO postMember(MemberDTO memberDTO) {
		String sql = "insert into member (pass, name) values (?,?)";
		MemberDTO result = new MemberDTO();
		
		try {
			PreparedStatement psmt = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
			psmt.setString(1, memberDTO.getPass());
			psmt.setString(2, memberDTO.getName());
			
			if (psmt.executeUpdate() == 1) {
				ResultSet rs = psmt.getGeneratedKeys();
				if (rs.next()) {
					result = getMemberById(rs.getInt(1));
				}
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("postMember() 호출 중 오류");
		}
		
		return result;
	}
	
	public MemberDTO putMember(Integer id, MemberDTO memberDTO) {
		String sql = "update member set pass = ?, name = ?, regidate = ? where id = ?";
		
		MemberDTO result = new MemberDTO();
		
		try {
			PreparedStatement psmt = con.prepareStatement(sql);
			psmt.setString(1, memberDTO.getPass());
			psmt.setString(2, memberDTO.getName());
			LocalDate today = LocalDate.now(); // 현재 날짜
			Date sqlDate = Date.valueOf(today); // LocalDate를 java.sql.Date로 변환
			psmt.setDate(3, sqlDate);
			psmt.setInt(4, id);
			
			if (psmt.executeUpdate() == 1) {
				result = getMemberById(id);
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("putMember() 호출 중 오류");
		}
		
		return result;
	}
	
	public MemberDTO patchMember(Integer id, MemberDTO memberDTO) {
	    StringBuilder sb = new StringBuilder("update member set ");
	    List<Object> params = new ArrayList<>();
	    MemberDTO result = new MemberDTO();

	    if (memberDTO.getId() != null) {
	        sb.append("id = ?, ");
	        params.add(memberDTO.getId());
	    }
	    if (memberDTO.getPass() != null) {
	        sb.append("pass = ?, ");
	        params.add(memberDTO.getPass());
	    }
	    if (memberDTO.getName() != null) {
	        sb.append("name = ?, ");
	        params.add(memberDTO.getName());
	    }
	    if (memberDTO.getRegidate() != null) {
	        sb.append("regidate = ?, ");
	        params.add(memberDTO.getRegidate());
	    } else {
	    	sb.append("regidate = ?, ");
	    	LocalDate today = LocalDate.now(); // 현재 날짜
			Date sqlDate = Date.valueOf(today); // LocalDate를 java.sql.Date로 변환
	    	params.add(sqlDate);
	    }

	    if (params.isEmpty()) {
	        return getMemberById(id);
	    }


	    sb.setLength(sb.length() - 2);
	    sb.append(" where id = ?");

	    try (PreparedStatement psmt = con.prepareStatement(sb.toString())) {
	        int idx = 1;
	        for (Object p : params) {
	            if (p instanceof Integer) {
	            	psmt.setInt(idx++, (Integer) p);
	            } else if (p instanceof java.sql.Date) {
	            	psmt.setDate(idx++, (java.sql.Date) p);
	            } else {
	            	psmt.setString(idx++, p.toString());
	            }
	        }
	        psmt.setInt(idx, id); 
	        
	        if (psmt.executeUpdate() == 1) {
				result = getMemberById(id);
			}
	        
	    } catch(Exception e) {
	    	e.printStackTrace();
	    	System.out.println("patchMember() 호출 중 오류");
	    }
	    
	    return result;
	}
	
	public void deleteMember(Integer id) {
		String sql = "delete from member where id = ?";
		
		try {
			PreparedStatement psmt = con.prepareStatement(sql);
			psmt.setInt(1, id);
			psmt.executeUpdate();
			
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("deleteMember() 호출 중 오류");
		}
	}
	
	public void close() {
		try {
			if (con != null) {
				con.close();
			}
		} catch(Exception e) {
			e.printStackTrace();
			System.out.println("DB 연결 해제 중 에러");
		}
		
		
	}
	
	
}	
