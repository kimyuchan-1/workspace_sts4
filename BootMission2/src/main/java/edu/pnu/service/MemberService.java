package edu.pnu.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import edu.pnu.domain.MemberDTO;

public class MemberService {
	private List<MemberDTO> list = new ArrayList<>();
	
	public MemberService() {
		for (int i = 1; i <= 10; i++) {
			list.add(MemberDTO.builder()
							  .id(i).name("name"+i).pass("pass"+i)
							  .regidate(new Date()).build());
		}
	}
	
	public List<MemberDTO> showList() {
		return list;
	}
	
	public MemberDTO selectMemberDTO(Integer id) {
		for (MemberDTO m : list) {
			if (m.getId() == id) {
				return m;
			}
		}
		return null;
	}
	
	public MemberDTO insertMemberDTO(MemberDTO memberDTO) {
		int lastId = 0;
		for (MemberDTO m : list) {
			if (lastId < m.getId()) {
				lastId = m.getId();
			}
		}
		
		memberDTO.setId(lastId+1);
		memberDTO.setRegidate(new Date());
		list.add(memberDTO);
		return list.getLast();
	}
	
	public MemberDTO updateAllMemberDTO(Integer id, MemberDTO memberDTO) {
		MemberDTO temp = null;
		int lastId = 0;
		for (MemberDTO m : list) {
			if (m.getId() == id) {
				temp = m;
			}
			if (lastId < m.getId()) {
				lastId = m.getId();
			}
		}
		
		if (memberDTO.getId() == null) {
			memberDTO.setId(lastId);
		}
		
		list.set(list.indexOf(temp), memberDTO);
		return list.get(list.indexOf(memberDTO));
	}
	
	public MemberDTO updateMemberDTO(Integer id, MemberDTO memberDTO) {
		MemberDTO temp = null;
		for (MemberDTO m : list) {
			if (m.getId() == id) {
				temp = m;
			}
		}
		
		temp.setId(memberDTO.getId() == null ? temp.getId() : memberDTO.getId());
		temp.setName(memberDTO.getName() == null ? temp.getName() : memberDTO.getName());
		temp.setPass(memberDTO.getPass() == null ? temp.getPass() : memberDTO.getPass());
		temp.setRegidate(memberDTO.getRegidate() == null ? temp.getRegidate() : memberDTO.getRegidate());
		
		return list.get(list.indexOf(temp));
	}
	
	public void deleteMemberDTO(Integer id) {
		MemberDTO temp = null;
		for (MemberDTO m : list) {
			if (m.getId() == id) {
				temp = m;
			}
		}
		list.remove(list.indexOf(temp));
	}
	
}
