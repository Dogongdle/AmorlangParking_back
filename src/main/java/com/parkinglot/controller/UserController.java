package com.parkinglot.controller;

import com.parkinglot.domain.User;
import com.parkinglot.dto.UserApart;
import com.parkinglot.repository.UserRepository;
import com.parkinglot.token.JwtTokenUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

//JwtRequest를 Json 형식으로 받아 인증을 통해 토큰 발급

@RestController
@CrossOrigin
@RequiredArgsConstructor
public class UserController {

    private final JwtTokenUtil jwtTokenUtil;
    private final UserRepository userRepository;

    // 사용자 정보
    @GetMapping("/user")
    public UserApart showUser(@RequestHeader("authorization") String jwt) {
        UserApart userApart = new UserApart();
        jwt = jwt.substring(7);
        String username = jwtTokenUtil.getUsernameFromToken(jwt);
        if (userRepository.findByUsername(username).isPresent()) {
            Optional<User> user = userRepository.findByUsername(username);
            userApart.setUsername(username);
            userApart.setApart(user.get().getApart());
            userApart.setProvider(user.get().getProvider());
            userApart.setReserved(user.get().isReserved());
        }

        return userApart;
    }

    //사용자 정보 수정
    @PostMapping("/user")
    public void saveUser(@RequestHeader("authorization") String jwt,
                         @RequestBody UserApart userApart){

        User user=new User();

        jwt = jwt.substring(7);
        String username = this.jwtTokenUtil.getUsernameFromToken(jwt);

        Optional<User> opUser = userRepository.findByUsername(username); // 사용자 정보

        user.setId(opUser.get().getId());
        user.setUsername(opUser.get().getUsername());
        user.setServiceId(opUser.get().getServiceId());
        user.setProvider(opUser.get().getProvider());
        user.setDeviceToken(opUser.get().getDeviceToken());
        user.setPlatform(opUser.get().getPlatform());
        user.setApart(userApart.getApart());
        userRepository.save(user);
    }

}