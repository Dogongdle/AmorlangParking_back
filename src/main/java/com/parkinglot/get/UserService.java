package com.parkinglot.get;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    private User saveNewUser(UserDto userDto){
        User user=User.builder()
                .service_id(userDto.getService_id())
                .username(userDto.getUsername())
                .provider(userDto.getProvider())
                .build();

        User newUser=userRepository.save(user);
        return newUser;
    }

}
