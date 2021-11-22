package com.github.manojesus.bugtracker.registration.validator;

import com.github.manojesus.bugtracker.registration.service.RegistrationRequest;

import java.util.function.Function;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static com.github.manojesus.bugtracker.registration.validator.UserValidator.ValidationResult.*;

public interface UserValidator extends Function<RegistrationRequest, UserValidator.ValidationResult> {
    Pattern VALID_EMAIL_ADDRESS_PATTERN = Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$", Pattern.CASE_INSENSITIVE);
    Pattern VALID_PASSWORD_ADDRESS_PATTERN = Pattern.compile("^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=])(?=\\S+$).{8,}$", Pattern.CASE_INSENSITIVE);

    static UserValidator validadeEmail(){
        return user ->{
            Matcher matcher = VALID_EMAIL_ADDRESS_PATTERN.matcher(user.getEmail());
            return matcher.find() ? SUCCESS : ERROR_INVALID_EMAIL;
        };
    }

    static UserValidator validatePassword(){
        return user -> {
            Matcher matcher = VALID_PASSWORD_ADDRESS_PATTERN.matcher(user.getPassword());
            return matcher.find() ? SUCCESS : ERROR_INVALID_PASSWORD;
        };
    }


    default UserValidator and(UserValidator otherMethod){
        return user -> {
            ValidationResult result = this.apply(user);
            return result.equals(SUCCESS) ? otherMethod.apply(user) : this.apply(user);
        };
    }

    enum ValidationResult{
        SUCCESS, ERROR_INVALID_EMAIL, ERROR_INVALID_PASSWORD
    }
}
