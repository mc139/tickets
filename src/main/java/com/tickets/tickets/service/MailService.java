package com.tickets.tickets.service;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Scope;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Scope
@Service
@RequiredArgsConstructor
public class MailService {

    private final JavaMailSender mailSender;

    public void sendEmail(String adress, String subject, String text) {
        SimpleMailMessage smm = new SimpleMailMessage();
        smm.setSubject(subject);
        smm.setTo(adress);
        smm.setText(text);
        mailSender.send(smm);
    }
}
