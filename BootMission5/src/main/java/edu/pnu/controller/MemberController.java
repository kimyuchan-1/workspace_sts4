package edu.pnu.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import edu.pnu.domain.MemberDTO;
import edu.pnu.service.MemberService;

@RestController
@RequestMapping("/api")
public class MemberController {
	
	private MemberService memberservice;
	
	@Autowired
	public MemberController(MemberService memberservice) {
		this.memberservice = memberservice;
	}
	
	@GetMapping("/member")
	public List<MemberDTO> getAllMember() {
		return memberservice.getAllMember();
	}
	
	@GetMapping("/member/{id}")
	public MemberDTO getMemberById(@PathVariable Integer id) {
		return memberservice.getMemberById(id);	
	}
	
	@PostMapping("/member")
	public MemberDTO postMember(@RequestBody MemberDTO memberDTO) {
		return memberservice.postMember(memberDTO);
	}
	
	@PutMapping("/member/{id}")
	public MemberDTO putMember(@PathVariable Integer id, @RequestBody MemberDTO memberDTO) {
		return memberservice.putMember(id, memberDTO);
	}
	
	@PatchMapping("/member/{id}")
	public MemberDTO patchMember(@PathVariable Integer id, @RequestBody MemberDTO memberDTO) {
		return memberservice.patchMember(id, memberDTO);
	}
	
	@DeleteMapping("/member/{id}")
	public void deleteMember(@PathVariable Integer id) {
		memberservice.deleteMember(id);
	}

}
