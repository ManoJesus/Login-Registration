package com.github.manojesus.bugtracker.registrationTeste;

import com.github.manojesus.bugtracker.UserApp.entity.UserRole;
import com.github.manojesus.bugtracker.UserApp.entity.Users;
import com.github.manojesus.bugtracker.UserApp.repository.UserRepository;
import com.github.manojesus.bugtracker.UserApp.service.UserService;
import com.github.manojesus.bugtracker.registration.token.service.ConfirmationTokenService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.time.LocalDate;
import java.util.Optional;

import static java.time.Month.MAY;
import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(SpringExtension.class)
public class UserServiceTest {

    @TestConfiguration
    public static class UserServiceConfiguration {
        @Bean
        public UserService userService(){
            return new UserService();
        }
    }
    @Autowired
    private UserService userService;

    @MockBean
    private UserRepository userRepository;

    @MockBean
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @MockBean
    private ConfirmationTokenService conTokenService;


    @BeforeEach
    void setUp(){
        Optional<Users> user = Optional.of(new Users(
                "Mano",
                "Jesus",
                LocalDate.of(2000, MAY,25),
                "jesus@email.com",
                "123123",
                UserRole.COMMON_USER
        ));
        Mockito.when(userRepository.findByEmail(user.get().getEmail())).thenReturn(user);
    }

    @Test
    @DisplayName("Testing method find by email from user service")
    public void testFindByEmail(){
        String email = "jesus@email.com";

        Optional<Users> userFoundOptional = userService.findByEmail(email);
        Users userFound = userFoundOptional.orElse(new Users());

        assertThat(userFound.getEmail()).isEqualTo(email);
    }

    @Test
    @DisplayName("Testing method sing up from user service")
    public void testSingUp(){

    }
}
