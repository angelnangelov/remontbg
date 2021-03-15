package com.angelangelov.remont_bg.service.impl;

import com.angelangelov.remont_bg.service.EmailService;
import com.angelangelov.remont_bg.service.UserService;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;


import java.util.UUID;


@Service
public class MailServiceImpl implements EmailService {

    private final JavaMailSender javaMailSender;

    public MailServiceImpl(JavaMailSender javaMailSender) {
        this.javaMailSender = javaMailSender;
    }

    @Override
    public void sendSimpleMessage(String to, String subject, String text, String setFrom) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom(setFrom);
        message.setTo(to);
        message.setSubject(subject);
        message.setText(text);
        javaMailSender.send(message);
    }

    public static String generateString() {
        return UUID.randomUUID().toString();
    }
}
