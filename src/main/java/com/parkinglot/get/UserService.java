package com.parkinglot.get;

import com.parkinglot.profile.Profile;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    private User saveNewUser(User user,UserDto userDto){
        user.setUsername(userDto.getUsername());
        user.setApart(userDto.getApart());
        User newUser=userRepository.save(user);
        return newUser;
    }



}
