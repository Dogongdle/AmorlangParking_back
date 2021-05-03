package com.parkinglot.controller;

import com.parkinglot.domain.User;
import com.parkinglot.dto.UserApart;
import com.parkinglot.token.JwtRequest;
import com.parkinglot.token.JwtResponse;
import com.parkinglot.token.JwtTokenUtil;
import com.parkinglot.service.JwtUserDetailsService;
import com.parkinglot.repository.UserRepository;
import com.parkinglot.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

//JwtRequest를 Json 형식으로 받아 인증을 통해 토큰 발급

@RestController
@CrossOrigin
@RequiredArgsConstructor
public class JwtAuthenticationController {

    private final AuthenticationManager authenticationManager;
    private final JwtTokenUtil jwtTokenUtil;
    private final JwtUserDetailsService userDetailsService;
    private final UserRepository userRepository;


    @RequestMapping(value = "/signin", method = RequestMethod.POST)
    public ResponseEntity<?> createAuthenticationToken(@RequestBody JwtRequest jwtRequest) throws Exception {

        final String token = getString(jwtRequest);

        return ResponseEntity.ok(new JwtResponse(token));

    }

    //JWT 토큰 생성 메서드
    private String getString(JwtRequest jwtRequest) {
        final UserDetails userDetails = userDetailsService
                .loadUserByUsername(jwtRequest.getUsername());

        final String token = jwtTokenUtil.generateToken(userDetails);
        return token;
    }

    @GetMapping("/user")
    public UserApart showUser(@RequestHeader("authorization") String jwt) {
        UserApart userApart = new UserApart();
        jwt = jwt.substring(7);
        String username = this.jwtTokenUtil.getUsernameFromToken(jwt);
        if (this.userRepository.findByUsername(username).isPresent()) {
            Optional<User> user = this.userRepository.findByUsername(username);
            userApart.setUsername(username);
            userApart.setApart(user.get().getApart());
            userApart.setProvider(user.get().getProvider());
        }

        return userApart;
    }

    @PostMapping("/user")
    public void saveUser(@RequestHeader("authorization") String jwt,
                         @RequestBody UserApart userApart){

        User user=new User();

        jwt = jwt.substring(7);
        String username = this.jwtTokenUtil.getUsernameFromToken(jwt);

        Optional<User> opUser = this.userRepository.findByUsername(username); // 사용자 정보

        user.setId(opUser.get().getId());
        user.setUsername(opUser.get().getUsername());
        user.setServiceId(opUser.get().getServiceId());
        user.setProvider(opUser.get().getProvider());
        user.setApart(userApart.getApart());
        userRepository.save(user);
    }

    private void authenticate(String username,String password) throws Exception {
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username,password));
        } catch (DisabledException e) {
            throw new Exception("USER_DISABLED", e);
        } catch (BadCredentialsException e) {
            throw new Exception("INVALID_CREDENTIALS", e);
        }
    }
}