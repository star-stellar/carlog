package com.jafa.service;

import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import com.jafa.cofig.RootConfig;
import com.jafa.cofig.SecurityConfig;
import com.jafa.cofig.ServletConfig;
import com.jafa.domain.AuthVO;
import com.jafa.domain.MemberType;
import com.jafa.domain.MemberVO;

import lombok.extern.log4j.Log4j;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {RootConfig.class, SecurityConfig.class, ServletConfig.class})
@WebAppConfiguration
@Log4j
public class MemberServiceTest {

	@Autowired
	MemberService memberService;
	
	@Test
	@Ignore
	public void test() {
		MemberVO vo = MemberVO.builder()
				.memberId("leekwanghyup")
				.password("1234")
				.email("lee@naver.com")
				.build();
		memberService.join(vo);
	}
	
	@Test
	public void test2() {
		AuthVO auth = new AuthVO("admin", MemberType.ROLE_ADMIN);
		memberService.updateMemberType(auth);
	}
	
	@Test
	public void test3() {
		AuthVO auth = new AuthVO("leekwanghyup", MemberType.ROLE_REGULAR_MEMBER);
		memberService.updateMemberType(auth);
	}
}
