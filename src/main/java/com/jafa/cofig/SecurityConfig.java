package com.jafa.cofig;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
import org.springframework.security.web.authentication.rememberme.JdbcTokenRepositoryImpl;
import org.springframework.security.web.authentication.rememberme.PersistentTokenRepository;
import org.springframework.security.web.csrf.CsrfFilter;
import org.springframework.web.filter.CharacterEncodingFilter;

import lombok.extern.log4j.Log4j;

@Configuration
@EnableWebSecurity
@Log4j
@ComponentScan("com.jafa.security")
@EnableGlobalMethodSecurity(prePostEnabled = true,securedEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter{
	
	@Autowired
	AccessDeniedHandler accessDeniedHandler;
	
	@Autowired
	AuthenticationFailureHandler authenticationFailureHandler;
	
	@Autowired
	AuthenticationSuccessHandler authenticationSuccessHandler;
	
	@Autowired
	LogoutSuccessHandler logoutSuccessHandler;
	
	@Autowired
	UserDetailsService userDetailsService;


	@Autowired
	DataSource dataSource;
	
	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Bean
	public PersistentTokenRepository persistentTokenRepository() {
		JdbcTokenRepositoryImpl jdbcTokenRepository =  new JdbcTokenRepositoryImpl();
		jdbcTokenRepository.setDataSource(dataSource);
		return jdbcTokenRepository;
	}
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
//		http.authorizeRequests()
//		.antMatchers("/member/all").access("permitAll")
//		.antMatchers("/member/mypage")
//			.access("hasAnyRole('ROLE_REGULAR_MEMBER','ROLE_ASSOCIATE_MEMBER')")
//		.antMatchers("/member/admin")
//			.access("hasAnyRole('ROLE_ADMIN','ROLE_SUB_ADMIN')");
		
		CharacterEncodingFilter filter = new CharacterEncodingFilter();
		filter.setEncoding("UTF-8");
		filter.setForceEncoding(true);
		http.addFilterBefore(filter, CsrfFilter.class);
		
		http.formLogin()
			.loginPage("/member/login") // 로그인 페이지, 기본값 : /login GET요청 
			.loginProcessingUrl("/member/login") // 로그인 처리 , 기본값 : /login POST요청
			.usernameParameter("loginId") // 아이디 name값, 기본값 : username
			.passwordParameter("loginPwd") // 비밀번호 name 값, 기본값 : password
			.failureHandler(authenticationFailureHandler) // 로그인 실패 핸들러
			.successHandler(authenticationSuccessHandler); // 로그인 성공 핸들러
			
        
        http.exceptionHandling()
        	.accessDeniedHandler(accessDeniedHandler);
        
        http.logout()
        	.logoutUrl("/member/logout")
        	.logoutSuccessHandler(logoutSuccessHandler)
        	.deleteCookies("remember-me","JSESSION_ID");
//        	.logoutSuccessUrl("/");
        
		http.rememberMe()
			.key("leekwanghyup") // 인증받은 사용자의 정보로 token을 생성하는데 사용되는 임의의 key값 설정 
			.tokenRepository(persistentTokenRepository())
			.rememberMeParameter("remember-me") // 자동로그인 체크박스의 name속성 지정 : 기본값
			.tokenValiditySeconds(86400); // token만료시간 설정
	}
	
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
//        auth.inMemoryAuthentication().withUser("admin").password("{noop}1234").roles("ADMIN","MEMBER");
//        auth.inMemoryAuthentication().withUser("leekwanghyup").password("{noop}1234").roles("MEMBER");
		auth.userDetailsService(userDetailsService)
		.passwordEncoder(passwordEncoder());
    }
}
