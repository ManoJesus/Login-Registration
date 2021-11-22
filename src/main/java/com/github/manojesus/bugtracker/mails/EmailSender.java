package com.github.manojesus.bugtracker.mails;

import com.github.manojesus.bugtracker.registration.service.RegistrationRequest;

public interface EmailSender {
    void send(RegistrationRequest request, String link);
}
