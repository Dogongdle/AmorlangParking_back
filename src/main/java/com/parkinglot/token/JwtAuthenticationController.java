package com.parkinglot.token;

import com.parkinglot.user.User;
import com.parkinglot.user.UserApart;
import com.parkinglot.user.UserRepository;
import com.parkinglot.user.UserService;
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

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @Autowired
    private JwtUserDetailsService userDetailsService;

    private final UserRepository userRepository;

    private final UserService userService;

    private final JwtUserDetailsService jwtUserDetailsService;



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
    public UserApart showUser(@RequestHeader("authorization") String jwt) {
        UserApart userApart = new UserApart();
        jwt = jwt.substring(7);
        String username = this.jwtTokenUtil.getUsernameFromToken(jwt);
        if (this.userRepository.findByUsername(username).isPresent()) {
            Optional<User> user = this.userRepository.findByUsername(username);
            userApart.setUsername(username);
            userApart.setApart(((User)user.get()).getApart());
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

//        userRepository.save(user);
//        profile.setApart(userApart);
//        profile.setPhoneNumber(opUser.get().getPhoneNumber());
//        profile.setCarNumber(opUser.get().getCarNumber());
//        profile.setEmail(opUser.get().getEmail());
//        profile.setBio(opUser.get().getBio());

    }


    /*
    @PostMapping("/user")
    public User showUserForm(@RequestHeader("authorization") String jwt, User user,
                             Profile profile, @RequestBody String apart){

        jwt = jwt.substring(7);
        String username = this.jwtTokenUtil.getUsernameFromToken(jwt);
        if (this.userRepository.findByUsername(username).isPresent()) {
            Optional<User> users = this.userRepository.findByUsername(username); // 사용자 정보
            users.get().setApart(apart);    // 아파트 정보 입력
            // builder로써 가져와야 하나?
            userService.updateProfile(user,profile);
        }

        return user;
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