package com.jafa.repository;

import java.util.List;

import com.jafa.domain.MemberVO;

public interface MemberRepository {
	// 회원가입
	void save(MemberVO vo);
	
	MemberVO read(String memberId);

	List<MemberVO> memberList();
}