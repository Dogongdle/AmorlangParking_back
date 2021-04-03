package com.parkinglot.main;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.parkinglot.account.Account;
import com.parkinglot.account.CurrentUser;

@RestController
public class MainController {

	@GetMapping("/main")
	public Account home(@CurrentUser Account account) {
		
		return account;
	}
}
