package com.jafa.repository;

import com.jafa.domain.AuthVO;

public interface AuthRepository {

	void save(AuthVO vo);
	
	void remove(String memberId);
}
