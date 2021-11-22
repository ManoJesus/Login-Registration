package com.github.manojesus.bugtracker.registration.validator;

import org.springframework.http.HttpStatus;

public class PasswordNotValidException extends Exception{
    public PasswordNotValidException(HttpStatus conflict, String message){
        super(message);
    }
}
