package com.javarangers.javamaildemo.service.impl;

import com.javarangers.javamaildemo.entity.Mail;
import com.javarangers.javamaildemo.service.EmailService;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.AddressException;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;
import org.springframework.core.io.ClassPathResource;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import java.util.Objects;

@Service
public class EmailServiceImpl implements EmailService {

    private final JavaMailSender mailSender;
    private final TemplateEngine templateEngine;

    public EmailServiceImpl(JavaMailSender mailSender, TemplateEngine templateEngine) {
        this.mailSender = mailSender;
        this.templateEngine = templateEngine;
    }

    @Override
    @Async
    public void sendSimpleEmail(Mail mail) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(mail.getTo());
        message.setSubject(mail.getSubject());
        message.setText(mail.getBody());

        mailSender.send(message);
    }

    @Override
    @Async
    public void sendHTMLEmail(Mail mail) throws MessagingException {
        MimeMessage message = mailSender.createMimeMessage();

        message.setFrom(new InternetAddress("testmail6743@gmail.com"));
        for (String recipient: mail.getTo()){
            message.addRecipients(MimeMessage.RecipientType.TO, recipient);
        }
        message.setSubject(mail.getSubject());
        message.setContent(mail.getBody(), "text/html; charset=utf-8");

        mailSender.send(message);
    }

    @Override
    @Async
    public void sendEmailWithThymeLeaf(Mail mail) throws MessagingException {
        for (String recipient: mail.getTo()){
            Context context = new Context();
            context.setVariable("username", recipient);

            String process = templateEngine.process("ThymeLeafMail", context);

            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message);

            helper.setSubject(mail.getSubject());
            helper.setFrom("testmail6743@gmail.com");
            helper.setText(process, true);
            helper.setTo(recipient);

            mailSender.send(message);
        }
    }

    @Override
    @Async
    public void sendEmailWithAttachment(Mail mail) throws MessagingException {
        for (String recipient: mail.getTo()){
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true);

            helper.setFrom("testmail6743@gmail.com");
            helper.setTo(recipient);
            helper.setSubject("Testing Mail API With Attachment");
            helper.setText("Please find the attached document below");

            ClassPathResource classPathResource = new ClassPathResource("hello.jpg");
            helper.addAttachment(Objects.requireNonNull(classPathResource.getFilename()), classPathResource);

            mailSender.send(message);
        }
    }
}
