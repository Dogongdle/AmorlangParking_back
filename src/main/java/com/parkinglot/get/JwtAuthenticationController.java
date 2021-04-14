package com.parkinglot.get;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import lombok.Builder;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Optional;

//JwtRequest를 Json 형식으로 받아 인증을 통해 토큰 발급

@RestController
@CrossOrigin
@RequiredArgsConstructor
public class JwtAuthenticationController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @Autowired
    private JwtUserDetailsService userDetailsService;

    private final UserRepository userRepository;

    private final JwtUserDetailsService userService;



    @RequestMapping(value = "/signin", method = RequestMethod.POST)
    public ResponseEntity<?> createAuthenticationToken(@RequestBody JwtRequest jwtRequest) throws Exception {

        //authenticate(jwtRequest.getUsername(),jwtRequest.getPassword());

        final String token = getString(jwtRequest);

        /*

        try{
            if(userRepository.existsById(infoDto.getService_id())){ // db에 있는 아이디인지 검사
                infoDto.setService_id(infoDto.getService_id());
                infoDto.setUsername(infoDto.getUsername());
                infoDto.setProvider(infoDto.getToken());
                infoDto.setToken(token);
                userService.saveToken(infoDto);
            }

        }catch (Exception e){
            e.printStackTrace();
        }
         */

        return ResponseEntity.ok(new JwtResponse(token));
    }

    private String getString(JwtRequest jwtRequest) {
        final UserDetails userDetails = userDetailsService
                .loadUserByUsername(jwtRequest.getUsername());

        final String token = jwtTokenUtil.generateToken(userDetails);
        return token;
    }

    @GetMapping("/user")
    public UserApart showUser(@RequestHeader("token") String jwt) {
        UserApart userApart = new UserApart();
        String username = this.jwtTokenUtil.getUsernameFromToken(jwt);
        if (this.userRepository.findByUsername(username).isPresent()) {
            Optional<User> user = this.userRepository.findByUsername(username);
            userApart.setUsername(username);
            userApart.setApart(((User)user.get()).getApart());
        }

        return userApart;
    }

    //final String token = getString(jwtRequest); // 토큰 받기

        /*
        final String token = getString(jwtRequest); // 토큰 받기
        String username=jwtTokenUtil.getUsernameFromToken(token);
        
        if(userRepository.findByUsername(username).equals(user.getUsername())){
            userApart.setUsername(user.getUsername());
            userApart.setApart(userApart.getApart());
        }
         */

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