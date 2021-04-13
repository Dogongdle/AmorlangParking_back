package com.parkinglot.account;

import java.util.List;

import org.springframework.boot.configurationprocessor.json.JSONObject;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.RequiredArgsConstructor;

@RestController // Json 형태로 객체 데이터를 반환
@RequiredArgsConstructor
public class AccountController {
	
	private final SignUpFormValidator signUpFormValidator;
	private final AccountService accountService;
	private final ObjectMapper objectMapper;
	
	@InitBinder("signUpForm")
	public void InitBinder(WebDataBinder webDataBinder) {
		webDataBinder.addValidators(signUpFormValidator);
	}
	
	// 회원가입
	@GetMapping("/sign-up")
	@ResponseBody
	public ResponseEntity<SignUpForm> signUpForm(SignUpForm signUpForm) {
		
		return new ResponseEntity<SignUpForm>(signUpForm,HttpStatus.OK);
		
		// ResponseEntity는 개발자가 직접 결과 데이터와 HTTP 상태 코드를 직접 제어할 수 있는 클래스로 
		// 개발자는 404나 500같은 HTTP상태 코드를 전송하고 싶은 데이터와 함께 전송할수 있기 때문에 좀더 세밀한 제어가 필요한 경우 사용
		// {"nickname":null,"email":null,"password":null,"passwordcheck":null}
	}
	
	@PostMapping("/sign-up")
	public ResponseEntity<SignUpForm> SignUpSubmit(@RequestBody @Validated SignUpForm signUpForm) {
		
		Account account=accountService.makeNewAccount(signUpForm);
		accountService.login(account);

		return new ResponseEntity<SignUpForm>(signUpForm,HttpStatus.OK);
		
		// 회원가입 이후, 자동 login
	}

}
