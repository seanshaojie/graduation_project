package com.example.service;

import javax.mail.MessagingException;
import java.util.List;

public interface SendMailSevice {
    void sendMail(List<String> toUser, String subject, String text);

    void sendMail(String toUser, String subject, String text) throws MessagingException;
}
