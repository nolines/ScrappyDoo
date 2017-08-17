package com.webscrap.service;

import com.webscrap.data.SearchFilterData;
import com.webscrap.data.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EmailSenderService
{
    private JavaMailSender javaMailSender;

    @Autowired
    public EmailSenderService(JavaMailSender javaMailSender){
        this.javaMailSender = javaMailSender;
    }

    public void sendNotification (User user , List<SearchFilterData> item) throws MailException
    {

        SimpleMailMessage mail = new SimpleMailMessage();
        mail.setTo(user.getEmailAdress());
        mail.setFrom("xectrol@gmail.com");
        mail.setSubject("Gel vatandas ucuza araba var!!");
        mail.setText(String.valueOf(item));

        javaMailSender.send(mail);

    }
}
