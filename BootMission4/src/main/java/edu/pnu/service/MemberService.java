package edu.pnu.service;

import java.util.List;
import java.util.Map;

import edu.pnu.dao.LogDao;
import edu.pnu.dao.MemberDao;
import edu.pnu.domain.MemberDTO;

public class MemberService {
	private MemberDao memberDao = null;
	private LogDao logDao = null;
	
	public MemberService() {
		memberDao = new MemberDao();
		logDao = new LogDao();
	}
	
	public List<MemberDTO> getAllMember() {
		Map<String, Object> temp = memberDao.getAllMember();
		logDao.addLog(temp);
		@SuppressWarnings("unchecked")
		List<MemberDTO> result = (List<MemberDTO>) temp.get("dto");
		return result;
	}
	
	public MemberDTO getMemberById(Integer id) {
		Map<String, Object> temp = memberDao.getMemberById(id);
		logDao.addLog(temp);
		MemberDTO result = (MemberDTO) temp.get("dto");
		return result;
	}
	
	public MemberDTO postMember(MemberDTO memberDTO) {
		Map<String, Object> temp = memberDao.postMember(memberDTO);
		logDao.addLog(temp);
		MemberDTO result = (MemberDTO) temp.get("dto");
		return result;
	}
	
	public MemberDTO putMember(Integer id, MemberDTO memberDTO) {
		Map<String, Object> temp = memberDao.putMember(id, memberDTO);
		logDao.addLog(temp);
		MemberDTO result = (MemberDTO) temp.get("dto");
		return result;
	}
	
	public MemberDTO patchMember(Integer id, MemberDTO memberDTO) {
		Map<String, Object> temp = memberDao.patchMember(id, memberDTO);
		logDao.addLog(temp);
		MemberDTO result = (MemberDTO) temp.get("dto");
		return result;
	}
	
	public void deleteMember(Integer id) {
		Map<String, Object> temp = memberDao.deleteMember(id);
		logDao.addLog(temp);
	}
	
}
