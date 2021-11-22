package com.github.manojesus.bugtracker.registration.service;

import com.github.manojesus.bugtracker.UserApp.entity.UserRole;
import com.github.manojesus.bugtracker.UserApp.entity.Users;
import com.github.manojesus.bugtracker.UserApp.service.UserService;
import com.github.manojesus.bugtracker.mails.EmailSender;
import com.github.manojesus.bugtracker.registration.token.entity.ConfirmationToken;
import com.github.manojesus.bugtracker.registration.token.service.ConfirmationTokenService;
import com.github.manojesus.bugtracker.registration.validator.EmailNotValidException;
import com.github.manojesus.bugtracker.registration.validator.PasswordNotValidException;
import com.github.manojesus.bugtracker.registration.validator.UserValidator;
import lombok.AllArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import javax.transaction.Transactional;
import java.time.LocalDateTime;

@Service
@AllArgsConstructor
public class RegistrationService {

    private final UserService userService;
    private final UserValidator userValidator;
    private final ConfirmationTokenService confTokenService;
    private final EmailSender emailSender;

    @SneakyThrows
    public String register(RegistrationRequest request) {
        String link = "http://localhost:8080/api/v1.0/register/confirm?token=%s";
        String resultOfValidation = UserValidator.validadeEmail().and(UserValidator.validatePassword()).apply(request).name().toLowerCase();
        boolean userExists = userService.findByEmail(request.getEmail()).isPresent();

        switch(resultOfValidation){
            case "error_invalid_email" -> throw new EmailNotValidException(HttpStatus.CONFLICT, "Email "+request.getEmail()+" not valid");
            case"error_invalid_password" -> throw new PasswordNotValidException(HttpStatus.CONFLICT, "Password not valid");
        }
        if(userExists){
            Users user = userService.findByEmail(request.getEmail()).get();
            ConfirmationToken token = confTokenService.getConfToken(user);
            if(token.getConfirmedAt() == null){
                emailSender.send(request,String.format(link,token.getToken()));
                return token.getToken();
            }else {
                throw new ResponseStatusException(HttpStatus.CONFLICT, String.format("The user with the email %s already exists", user.getEmail()));
            }
        }
        Users user = userService.singUp(
                new Users(
                        request.getFirstName(),
                        request.getLastName(),
                        request.getDob(),
                        request.getEmail(),
                        request.getPassword(),
                        UserRole.COMMON_USER
                )
        );
        String userToken = confTokenService.saveConfirmationToken(user);
        emailSender.send(request,String.format(link,userToken));
        return userToken;
    }

    @Transactional
    public String confirmToken(String token){
        ConfirmationToken confToken = confTokenService.getConfToken(token);
        if(confToken.getConfirmedAt() != null){
            throw new ResponseStatusException(HttpStatus.CONFLICT, "This email have already been confirmed ");
        }
        LocalDateTime expired = confToken.getExpiresAt();
        if(expired.isBefore(LocalDateTime.now())){
            throw new ResponseStatusException(HttpStatus.CONFLICT, "This token have already  expired ");
        }
        confTokenService.setConfirmedAt(token);
        userService.setEnable(confToken.getUser().getEmail());
        return "confirmed";
    }

}
