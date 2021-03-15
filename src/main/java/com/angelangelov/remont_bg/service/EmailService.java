package com.angelangelov.remont_bg.service;

public interface EmailService {

    /**
     *
     * @param Send message to
     * @param subject
     * @param text
     * @param setForm
     */
    void sendSimpleMessage(String to, String subject, String text, String setForm);
}