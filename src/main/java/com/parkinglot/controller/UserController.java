package com.parkinglot.controller;

import com.parkinglot.dto.UserDto;
import com.parkinglot.service.JwtUserDetailsService;
import com.parkinglot.domain.Response;
import com.parkinglot.repository.UserRepository;
import lombok.RequiredArgsConstructor;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class UserController {

    private final JwtUserDetailsService userService;
    private final UserRepository userRepository;


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

        /*
        try {
            userService.save(infoDto);  // db에 저장
            response.setResponse("signin");
            response.setMessage("회원가입을 성공적으로 완료했습니다.");
        } catch (Exception e) {
            response.setResponse("signup");
            response.setMessage("회원가입을 하는 도중 오류가 발생했습니다.");
        }
        */
        return response;
    }

}