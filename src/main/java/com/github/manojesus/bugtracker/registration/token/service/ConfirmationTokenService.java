package com.github.manojesus.bugtracker.registration.token.service;

import com.github.manojesus.bugtracker.UserApp.entity.Users;
import com.github.manojesus.bugtracker.registration.token.entity.ConfirmationToken;
import com.github.manojesus.bugtracker.registration.token.repository.ConfirmationTokenRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
@Slf4j
@AllArgsConstructor
public class ConfirmationTokenService {

    private ConfirmationTokenRepository confirmationTokenRepository;

    public String saveConfirmationToken(Users user){
        final long minutesToConfirm = 15L;
        String token = UUID.randomUUID().toString();
        ConfirmationToken confToken = new ConfirmationToken(
                token,
                LocalDateTime.now(),
                LocalDateTime.now().plusMinutes(minutesToConfirm),
                user
        );
        log.info("Saving the confirmation token to the user {}",user.getFirstName());
        confirmationTokenRepository.save(confToken);

        return token;
    }

    public ConfirmationToken getConfToken(Users user) {
        return confirmationTokenRepository.findByToken(user).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, String.format("token %s not found",user)));
    }
    public ConfirmationToken getConfToken(String token) {
        return confirmationTokenRepository.findByToken(token).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, String.format("token %s not found",token)));
    }
    public void setConfirmedAt(String token){
        confirmationTokenRepository.updateConfirmDate(token, LocalDateTime.now());
    }
}
