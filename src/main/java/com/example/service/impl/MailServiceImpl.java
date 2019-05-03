package com.example.service.impl;

import com.example.dao.MailDao;
import com.example.model.Mail;
import com.example.service.MailService;
import com.example.service.SendMailSevice;
import com.example.utils.UserUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class MailServiceImpl implements MailService {
    @Autowired
    private SendMailSevice sendMailSevice;
    @Autowired
    private MailDao mailDao;

    @Override
    @Transactional
    public void save(Mail mail, List<String> toUser) {
        mail.setUserId(UserUtil.getLoginUser().getId());
        mailDao.save(mail);

        toUser.forEach(u -> {
            int status = 1;
            try {
                sendMailSevice.sendMail(u, mail.getSubject(), mail.getContent());
            } catch (Exception e) {
                status = 0;
            }

            mailDao.saveToUser(mail.getId(), u, status);
        });

    }
}
