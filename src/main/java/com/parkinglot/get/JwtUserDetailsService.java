package com.parkinglot.get;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

// db에서 userdetails를 얻어와 AuthenticationManager에게 제공


@Service
@RequiredArgsConstructor
public class JwtUserDetailsService implements UserDetailsService {

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    private final UserRepository userRepository;

    // infoDto 회원정보가 들어있는 dto
    // return : 저장되는 회원의 pk

    @Transactional
    public Long save(UserDto infoDto) {
        //BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        //infoDto.setPassword(encoder.encode(infoDto.getPassword()));

        return userRepository.save(User.builder()
                .service_id(infoDto.getService_id())
                //.token(infoDto.getToken())
                //.password(infoDto.getPassword())
                .username(infoDto.getUsername())
                .provider(infoDto.getProvider()).build()).getService_id();
    }

    public Long saveToken(UserDto infoDto){

        return userRepository.save(User.builder()
                .token(infoDto.getToken()).build()).getService_id();
    }


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException((username)));
    }

}