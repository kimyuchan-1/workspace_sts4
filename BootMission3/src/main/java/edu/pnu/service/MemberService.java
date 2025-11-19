package edu.pnu.service;

import java.util.List;

import edu.pnu.dao.MemberDao;
import edu.pnu.domain.MemberDTO;

public class MemberService {
	private MemberDao dao = null;
	
	public MemberService() {
		dao = new MemberDao();
	}
	
	public List<MemberDTO> getAllMember() {
		return dao.getAllMember();
	}
	
	public MemberDTO getMemberById(Integer id) {
		return dao.getMemberById(id);
	}
	
	public MemberDTO postMember(MemberDTO memberDTO) {
		return dao.postMember(memberDTO);
	}
	
	public MemberDTO putMember(Integer id, MemberDTO memberDTO) {
		return dao.putMember(id, memberDTO);
	}
	
	public MemberDTO patchMember(Integer id, MemberDTO memberDTO) {
		return dao.patchMember(id, memberDTO);

	}
	
	public void deleteMember(Integer id) {
		dao.deleteMember(id);
	}
	
}
