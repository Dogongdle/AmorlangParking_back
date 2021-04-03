package com.parkinglot.account;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Lob;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter @Setter @EqualsAndHashCode(of="id")
@Builder @AllArgsConstructor @NoArgsConstructor
public class Account {
	
	@Id @GeneratedValue
	private Long id;
	
	@Column(unique=true)
	private String email;
	
	@Column(unique=true)
	private String nickname;
	
	private String password;
	
	private String passwordcheck;	//비밀번호 확인
	
	private String emailVerified;	// 이메일 인증
	
	private String emailCheckToken;	// 이메일 인증 토큰
	
	@Lob @Basic(fetch=FetchType.EAGER)
	private String profileImage;
	
	private String bio;			// 자기 소개
	
	private String carNumber;	// 차량 번호
	
	private String location;	// 거주 지역
	
	private String phoneNumber;	// 핸드폰 번호
	
	private boolean carEntry;	// 입차 알림
		
	private boolean carExit;	// 출차 알림
	
	private boolean five_minutes;	// 5분 예약
	

}
