package com.webscrap.service;

import com.webscrap.data.MailUser;
import com.webscrap.data.SearchFilterData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class EmailSenderService
{
    private JavaMailSender javaMailSender;

    @Autowired
    public EmailSenderService(JavaMailSender javaMailSender)
    {
        this.javaMailSender = javaMailSender;
    }

    public void sendNotification(MailUser mailUser, List<SearchFilterData> item) throws MailException
    {
        List<String> content = new ArrayList<>();
        SimpleMailMessage mail = new SimpleMailMessage();
        mail.setTo(mailUser.getEmailAdress());
        mail.setFrom("firsat.sahibinden@gmail.com");
        mail.setSubject("Yeni firsatınız var");

        for (SearchFilterData searchFilterData : item)
        {
            content.add("sahibinden.com" + searchFilterData.getUrl());
        }

        mail.setText("Baglantilar: " + content);
        javaMailSender.send(mail);

    }
}
