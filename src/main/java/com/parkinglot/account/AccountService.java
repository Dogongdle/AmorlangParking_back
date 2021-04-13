package com.parkinglot.account;

import java.util.List;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AccountService implements UserDetailsService{
	
	private final AccountRepository accountRepository;
	private final PasswordEncoder passwordEncoder;

	public  Account makeNewSocial(SocialForm socialForm){
		Account account=Account.builder()
				.username(socialForm.getUsername())
				.provider(socialForm.getProvider())
				.service_id(socialForm.getService_id())
				.build();

		Account newAccount=accountRepository.save(account);
		return newAccount;
	}


	// Basic 로그인
	public Account makeNewAccount(SignUpForm signUpForm) {
		Account account=Account.builder()
				.email(signUpForm.getEmail())
				.nickname(signUpForm.getNickname())
				.password(passwordEncoder.encode(signUpForm.getPassword()))
				.build();
		
		Account newAccount=accountRepository.save(account);
		return newAccount;
	}
	
	public void login(Account account) {
		UsernamePasswordAuthenticationToken token=new UsernamePasswordAuthenticationToken(
				new UserAccount(account),
				account.getPassword(),
				List.of(new SimpleGrantedAuthority("ROLE_USER")));
		SecurityContextHolder.getContext().setAuthentication(token);
	}

	@Override
	public UserDetails loadUserByUsername(String nickname) throws UsernameNotFoundException {
		Account account=accountRepository.findByNickname(nickname);
		
		if(account==null) {
			throw new UsernameNotFoundException(nickname);
		}
		
		return new UserAccount(account);
	}


}
