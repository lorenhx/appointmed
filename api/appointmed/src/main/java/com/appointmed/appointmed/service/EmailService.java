package com.appointmed.appointmed.service;

import jakarta.mail.MessagingException;
import org.springframework.stereotype.Service;

@Service
public interface EmailService {
    void sendEmail(String from, String to, String subject, String body);

    void sendHtmlEmail(String from, String to, String subject, String html) throws MessagingException, MessagingException;

}
