package com.parkinglot.service;

import com.parkinglot.domain.User;
import com.parkinglot.dto.UserDto;
import com.parkinglot.token.JwtTokenUtil;
import com.parkinglot.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

// DB에서 UserDetails에 대한 정보를 얻어와 이를 AuthenticationManager에게 제공

@Service
@RequiredArgsConstructor
public class JwtUserDetailsService implements UserDetailsService {

    private final JwtTokenUtil jwtTokenUtil;
    private final UserRepository userRepository;

    // infoDto 회원정보가 들어있는 dto
    // return : 저장되는 회원의 pk

    @Transactional
    public Long save(UserDto infoDto) {

        return userRepository.save(User.builder()
                .serviceId(infoDto.getServiceId())
                //.token(infoDto.getToken())
                //.password(infoDto.getPassword())
                .username(infoDto.getUsername())
                .provider(infoDto.getProvider()).build()).getServiceId();
    }


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException((username)));
    }

}