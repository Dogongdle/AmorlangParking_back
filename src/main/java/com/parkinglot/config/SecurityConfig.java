package com.parkinglot.config;

import javax.sql.DataSource;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.authentication.rememberme.JdbcTokenRepositoryImpl;
import org.springframework.security.web.authentication.rememberme.PersistentTokenRepository;

import com.parkinglot.account.AccountService;

import lombok.RequiredArgsConstructor;

/*
@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig extends WebSecurityConfigurerAdapter{
	
	private final AccountService accountService;
	private final DataSource dataSource;

	@Bean
	@Override
	public AuthenticationManager authenticationManagerBean() throws Exception {
		return super.authenticationManagerBean();
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {

		http.httpBasic().disable();	// security에서 기본으로 생성하는 login페이지 사용 안함

		http.authorizeRequests()
				.mvcMatchers("/","/login","/sign-up","/auth/*","/signup","/authenticate").permitAll()	// 허용해주는 URL
				.anyRequest().authenticated();	// 나머지는 로그인을 통해 접근
		
		http.formLogin()
				.loginPage("/login").permitAll();
		
		http.logout()
				.logoutSuccessUrl("/");
		
		http.rememberMe()					
				.userDetailsService(accountService)
				.tokenRepository(tokenRepository());	// 로그인 기억하기

		http.csrf().disable(); // postmapping 에러
	}

	@Bean
	public PersistentTokenRepository tokenRepository() {
		JdbcTokenRepositoryImpl jdbcTokenRepositoryImpl=new JdbcTokenRepositoryImpl();
		jdbcTokenRepositoryImpl.setDataSource(dataSource);
		return jdbcTokenRepositoryImpl;
	}
	


}
*/