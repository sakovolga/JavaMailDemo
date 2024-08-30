package com.javarangers.javamaildemo.controller;

import com.javarangers.javamaildemo.entity.Mail;
import com.javarangers.javamaildemo.service.EmailService;
import jakarta.mail.MessagingException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class EmailController {

    private final EmailService emailService;

    public EmailController(EmailService emailService) {
        this.emailService = emailService;
    }

    @PostMapping("/simple")
    public void sendSimpleEmail(@RequestBody Mail mail){
        emailService.sendSimpleEmail(mail);
    }

    @PostMapping("/html")
    public void sendHTMLEmail(@RequestBody Mail mail) throws MessagingException {
        emailService.sendHTMLEmail(mail);
    }

    @PostMapping("/template")
    public void sendEmailWithThymeLeaf(@RequestBody Mail mail) throws MessagingException {
        emailService.sendEmailWithThymeLeaf(mail);
    }

    @PostMapping("/attachment")
    public void sendEmailWithAttachment(@RequestBody Mail mail) throws MessagingException {
        emailService.sendEmailWithAttachment(mail);
    }
}
