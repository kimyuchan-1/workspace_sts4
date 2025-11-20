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
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import edu.pnu.domain.MemberDTO;

@Repository
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
	
	public Map<String, Object> getAllMember() {
		
		List<MemberDTO> temp = new ArrayList<>();
		String sql = "select * from member";
		Map<String, Object> result = new HashMap<>();
		
		result.put("method", "Get");
		result.put("sqlstring", sql);
		
		try (PreparedStatement psmt = con.prepareStatement(sql);
			 ResultSet rs = psmt.executeQuery()) {
			
			while (rs.next()) {
				MemberDTO dto = new MemberDTO();
				dto.setId(rs.getInt(1));
				dto.setPass(rs.getString(2));
				dto.setName(rs.getString(3));
				dto.setRegidate(rs.getDate(4));
				temp.add(dto);
			}
			
			result.put("success", true);
			result.put("dto", temp);
			
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("getAllMember() 호출 중 오류");

			result.put("success", false);
			result.put("dto", null);
		} 
		
		return result;
		
	}
	
	public Map<String, Object> getMemberById(Integer id) {
		String sql = "select * from member where id = ?";
		MemberDTO temp = new MemberDTO();
		Map<String, Object> result = new HashMap<>();
		
		result.put("method", "Get");
		
		try (PreparedStatement psmt = con.prepareStatement(sql)) {
			psmt.setInt(1, id);
			result.put("sqlstring", psmt.toString().split(":",2)[1].trim());
			ResultSet rs = psmt.executeQuery();
			
			while (rs.next()) {
				temp.setId(rs.getInt(1));
				temp.setPass(rs.getString(2));
				temp.setName(rs.getString(3));
				temp.setRegidate(rs.getDate(4));
			}

			result.put("success", true);
			result.put("dto", temp);
			
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("getMemberById() 호출 중 오류");
			
			result.put("success", false);
			result.put("dto", null);
		}
		
		return result;
	}
	
	public Map<String, Object> postMember(MemberDTO memberDTO) {
		String sql = "insert into member (pass, name) values (?,?)";
		MemberDTO temp = new MemberDTO();
		Map<String, Object> result = new HashMap<>();
		
		result.put("method", "Post");
		
		try {
			PreparedStatement psmt = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
			psmt.setString(1, memberDTO.getPass());
			psmt.setString(2, memberDTO.getName());
			result.put("sqlstring", psmt.toString().split(":",2)[1].trim());
			
			if (psmt.executeUpdate() == 1) {
				ResultSet rs = psmt.getGeneratedKeys();
				if (rs.next()) {
					temp = (MemberDTO) getMemberById(rs.getInt(1)).get("dto");
				}
			}
			result.put("success", true);
			result.put("dto", temp);
			
			
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("postMember() 호출 중 오류");
			result.put("success", false);
			result.put("dto", null);
		}
		
		return result;
	}
	
	public Map<String, Object> putMember(Integer id, MemberDTO memberDTO) {
		String sql = "update member set pass = ?, name = ?, regidate = ? where id = ?";
		Map<String, Object> result = new HashMap<>();
		MemberDTO temp = new MemberDTO();
		
		result.put("method", "Put");
		
		try {
			PreparedStatement psmt = con.prepareStatement(sql);
			psmt.setString(1, memberDTO.getPass());
			psmt.setString(2, memberDTO.getName());
			LocalDate today = LocalDate.now(); // 현재 날짜
			Date sqlDate = Date.valueOf(today); // LocalDate를 java.sql.Date로 변환
			psmt.setDate(3, sqlDate);
			psmt.setInt(4, id);
			result.put("sqlstring", psmt.toString().split(":",2)[1].trim());
			
			if (psmt.executeUpdate() == 1) {
				temp = (MemberDTO) getMemberById(id).get("dto");
			}
			
			result.put("success", true);
			result.put("dto", temp);
			
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("putMember() 호출 중 오류");
			
			result.put("success", false);
			result.put("dto", null);
		}
		
		return result;
	}
	
	public Map<String, Object> patchMember(Integer id, MemberDTO memberDTO) {
	    StringBuilder sb = new StringBuilder("update member set ");
	    List<Object> params = new ArrayList<>();
	    MemberDTO temp = new MemberDTO();
	    Map<String, Object> result = new HashMap<>();
	    
	    result.put("method", "Patch");


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
	        result.put("sqlstring", psmt.toString().split(":",2)[1].trim());
	        if (psmt.executeUpdate() == 1) {
				temp = (MemberDTO) getMemberById(id).get("dto");
			}
	        
	        result.put("success", true);
	        result.put("dto", temp);
	        
	    } catch(Exception e) {
	    	e.printStackTrace();
	    	System.out.println("patchMember() 호출 중 오류");
	    	result.put("success", false);
	        result.put("dto", null);
	    }
	    
	    return result;
	}
	
	public Map<String, Object> deleteMember(Integer id) {
		String sql = "delete from member where id = ?";
		Map<String, Object> result = new HashMap<>();
		
		result.put("method", "Delete");
		
		try {
			PreparedStatement psmt = con.prepareStatement(sql);
			psmt.setInt(1, id);
			result.put("sqlstring", psmt.toString().split(":",2)[1].trim());
			psmt.executeUpdate();
			result.put("success", true);
			
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("deleteMember() 호출 중 오류");
			result.put("success", false);
		}
		return result;
	}	
	
}	
