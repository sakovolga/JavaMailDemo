package com.javarangers.javamaildemo.service;


import com.javarangers.javamaildemo.entity.Mail;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.AddressException;

public interface EmailService {

    void sendSimpleEmail(Mail mail);

    void sendHTMLEmail(Mail mail) throws MessagingException;

    void sendEmailWithThymeLeaf(Mail mail) throws MessagingException;

    void sendEmailWithAttachment(Mail mail) throws MessagingException;
}
