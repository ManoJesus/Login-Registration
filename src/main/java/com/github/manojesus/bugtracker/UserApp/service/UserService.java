package com.github.manojesus.bugtracker.UserApp.service;

import com.github.manojesus.bugtracker.UserApp.entity.Users;
import com.github.manojesus.bugtracker.UserApp.repository.UserRepository;
import com.github.manojesus.bugtracker.registration.token.service.ConfirmationTokenService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@Slf4j
public class UserService implements UserDetailsService {

    private  final String USER_NOT_FOUND = "User by email  %s not found";

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private ConfirmationTokenService conTokenService;


    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        return  userRepository.findByEmail(s)
                .orElseThrow(() -> new UsernameNotFoundException(String.format(USER_NOT_FOUND, s)));
    }

    public Optional<Users> findByEmail(String email){
        log.info("Getting a user of the email {} from database", email);
        return userRepository.findByEmail(email);
    }


    public Users singUp(Users user){

        String encodedPass = bCryptPasswordEncoder.encode(user.getPassword());
        user.setPassword(encodedPass);
        userRepository.save(user);
        return user;
    }

    public void setEnable(String email){
        log.info("Enabling the user {}",email);
        userRepository.setEnable(email);
    }
}
