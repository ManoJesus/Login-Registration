package com.github.manojesus.bugtracker.registrationTeste;


import com.github.manojesus.bugtracker.registration.service.RegistrationRequest;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static com.github.manojesus.bugtracker.registration.validator.UserValidator.validadeEmail;
import static com.github.manojesus.bugtracker.registration.validator.UserValidator.validatePassword;
import static java.time.Month.MAY;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;


public class UserValidatorTest {


    @Test
    void testValidation(){
        RegistrationRequest request = new RegistrationRequest(
                "Mano",
                "Jesus",
                "jesus@email.com",
                LocalDate.of(2000, MAY,25),
                "Lucas922@"
        );
        String testResult = validadeEmail().and(validatePassword()).apply(request).toString().toLowerCase();
        System.out.println(testResult);
        assertThat("success", is(testResult));
    }
}
