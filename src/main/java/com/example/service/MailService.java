package com.example.service;

import com.example.model.Mail;

import java.util.List;

public interface MailService {
    void save(Mail mail, List<String> toUser);
}
