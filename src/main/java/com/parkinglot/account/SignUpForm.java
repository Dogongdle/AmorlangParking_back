package com.parkinglot.account;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.Length;

import lombok.Data;

@Data
public class SignUpForm {
	
	@NotBlank
	@Length(min=5, max=20)
	@Pattern(regexp="^[ㄱ-ㅎ가-힣a-z0-9_-]{3,20}$")
	private String nickname;	// 아이디
	
	@Email
	@NotBlank
	private String email;
	
	@NotBlank
	@Length(min=8, max=20)
	private String password;
	
	@NotBlank
	@Length(min=8, max=20)
	private String passwordcheck;	//비밀번호 확인

}
