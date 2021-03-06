package com.parkinglot.controller;

import com.parkinglot.domain.Response;
import com.parkinglot.domain.User;
import com.parkinglot.dto.UserDto;
import com.parkinglot.repository.UserRepository;
import com.parkinglot.service.JwtUserDetailsService;
import com.parkinglot.token.JwtRequest;
import com.parkinglot.token.JwtResponse;
import com.parkinglot.token.JwtTokenUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

// 회원가입, Social Login에 대한 기능을 다루는 컨트롤러
// jwt token을 생성하여 사용자를 인식하고, 이로부터 username을 받아 login을 한다.

@RestController
@RequiredArgsConstructor
public class SignUpController {

    private final JwtUserDetailsService userService;
    private final UserRepository userRepository;
    private final JwtUserDetailsService userDetailsService;
    private final JwtTokenUtil jwtTokenUtil;

    // 회원가입
    @PostMapping("/signup")
    public Response signup(@RequestBody UserDto infoDto) { // 회원 추가

        Response response = new Response();

        if(!userRepository.existsByServiceId(infoDto.getServiceId())){
            userService.save(infoDto);  // db에 저장
            response.setResponse("signin");
            response.setMessage("회원가입을 성공적으로 완료했습니다.");
        } else {
            response.setResponse("signup");
            response.setMessage("이미 가입된 회원입니다.");
        }
        return response;
    }

    // 로그인
    @PostMapping("/signin")
    public ResponseEntity<?> createAuthenticationToken(@RequestBody JwtRequest jwtRequest) throws Exception {

        final String token = getString(jwtRequest);
        User user = userRepository.findByUsername(jwtRequest.getUsername()).get();
        user.setDeviceToken(jwtRequest.getDeviceToken());
        user.setPlatform(jwtRequest.getPlatform());
        userRepository.save(user);

        return ResponseEntity.ok(new JwtResponse(token));

    }

    //JWT 토큰 생성 메서드
    private String getString(JwtRequest jwtRequest) {
        final UserDetails userDetails = userDetailsService
                .loadUserByUsername(jwtRequest.getUsername());

        final String token = jwtTokenUtil.generateToken(userDetails);
        return token;
    }

}