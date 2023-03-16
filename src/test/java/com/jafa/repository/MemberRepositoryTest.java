package com.jafa.repository;

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
public class MemberRepositoryTest {
	
	@Autowired
	MemberRepository memberRepository;
	
	@Autowired
	AuthRepository authRepository;
	
	@Test
	public void test() {
		log.info("Junit Test");
	}
	
	@Test
	@Ignore
	public void test0010() {
		MemberVO vo = MemberVO.builder()
				.memberId("leekwanghyup")
				.password("1234")
				.email("lee@naver.com")
				.build();
		memberRepository.save(vo);
		AuthVO authVO = AuthVO.builder()
				.memberId(vo.getMemberId())
				.memberType(MemberType.ROLE_ASSOCIATE_MEMBER)
				.ordinal(MemberType.ROLE_ASSOCIATE_MEMBER.ordinal())
				.build();
		authRepository.save(authVO);
	}
	
	@Test
	public void test2() {
		MemberVO read = memberRepository.read("leekwanghyup");
		log.info(read);
	}
}
