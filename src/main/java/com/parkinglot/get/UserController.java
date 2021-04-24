package com.parkinglot.get;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.*;

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

    @GetMapping("/data/a_sector")
    public ResponseEntity<List<Parking>> generateDataA(){
        List<Parking> list=new ArrayList<>();
        for(int i=1;i<16;i++){
            Parking parking=new Parking();
            parking.setParkingSeat(i);
            if (i%3==0) parking.setEnable(true);
            else parking.setEnable(false);
            list.add(parking);
        }
        return new ResponseEntity<>(list, HttpStatus.NOT_FOUND);
    }

    @GetMapping("/data/b_sector")
    public ResponseEntity<List<Parking>> generateDataB(){
        List<Parking> list=new ArrayList<>();
        for(int i=1;i<16;i++){
            Parking parking=new Parking();
            parking.setParkingSeat(i);
            if (i%4==0) parking.setEnable(true);
            else parking.setEnable(false);
            list.add(parking);
        }
        return new ResponseEntity<>(list, HttpStatus.NOT_FOUND);
    }

    @GetMapping("/data/c_sector")
    public ResponseEntity<List<Parking>> generateDataC(){
        List<Parking> list=new ArrayList<>();
        for(int i=1;i<16;i++){
            Parking parking=new Parking();
            parking.setParkingSeat(i);
            if (i%5==0) parking.setEnable(true);
            else parking.setEnable(false);
            list.add(parking);
        }
        return new ResponseEntity<>(list, HttpStatus.NOT_FOUND);
    }

    @GetMapping("/data/d_sector")
    public ResponseEntity<List<Parking>> generateDataD(){
        List<Parking> list=new ArrayList<>();
        for(int i=1;i<16;i++){
            Parking parking=new Parking();
            parking.setParkingSeat(i);
            if (i%6==0) parking.setEnable(true);
            else parking.setEnable(false);
            list.add(parking);
        }
        return new ResponseEntity<>(list, HttpStatus.NOT_FOUND);
    }

    @PostMapping("/verify")
    public Response verfiyCode(@RequestBody ApartCode apartCode){

        Response response=new Response();

        if(apartCode.getApart().equals("광교아이파크")){
            if (apartCode.getCode().equals("sayharahihello")){
                response.setResponse("유효");
                response.setMessage("코드가 정확합니다.");
            }
            else{
                response.setResponse("무효");
                response.setMessage("코드가 틀렸습니다.");
            }
        }

        return response;

    }



    /*
    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @Autowired
    private JwtUserDetailsService userDetailsService;

    @PostMapping("/signup")
    public ResponseEntity<?> signup(@RequestBody UserDto infoDto, @RequestBody JwtRequest authenticationRequest) throws Exception {

        authenticate(authenticationRequest.getUsername(), authenticationRequest.getPassword());

        final UserDetails userDetails = userDetailsService
                .loadUserByUsername(authenticationRequest.getUsername());

        final String token = jwtTokenUtil.generateToken(userDetails);

        return ResponseEntity.ok(new JwtResponse(token));

    }

    private void authenticate(String username, String password) throws Exception {
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
        } catch (DisabledException e) {
            throw new Exception("USER_DISABLED", e);
        } catch (BadCredentialsException e) {
            throw new Exception("INVALID_CREDENTIALS", e);
        }
    }
    */

}