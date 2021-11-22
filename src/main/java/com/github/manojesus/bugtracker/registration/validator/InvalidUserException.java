package com.github.manojesus.bugtracker.registration.validator;

public class InvalidUserException extends Exception {
    public InvalidUserException() {    }

    public InvalidUserException(String message) {
        super(message);
    }
}
