package com.github.manojesus.bugtracker.registration.validator;

import org.springframework.http.HttpStatus;

public class EmailNotValidException extends Exception{
    public EmailNotValidException(HttpStatus conflict, String message){
        super(message);
    }
}
