package com.jayarama.testsmtpconnection.smtp;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.util.Date;
import java.util.Properties;

@Slf4j
@SpringBootApplication
public class SmtpUtil implements CommandLineRunner {

    @Override
    public void run(String... args) throws Exception {
        final JavaMailSenderImpl mailer = getMailSender();
        mailer.testConnection();
    }

    public static void main(String[] args) throws Exception {
        SpringApplication.run(SmtpUtil.class, args);
    }

    private static JavaMailSenderImpl getMailSender() {
        final JavaMailSenderImpl mailer = new JavaMailSenderImpl();
        mailer.setDefaultEncoding("UTF-8");

        Properties mailerProperties = mailer.getJavaMailProperties();

        // Set default properties
        mailerProperties.put("mail.debug", true);
        mailerProperties.put("mail.transport.protocol", "smtp");
        mailerProperties.put("mail.mime.charset", "UTF-8");
        mailerProperties.put("mail.smtp.auth", true);
        mailerProperties.put("mail.smtp.port", 587);
        mailerProperties.put("mail.smtp.connectiontimeout", 10 * 1000);
        mailerProperties.put("mail.smtp.timeout", 10 * 1000);
        mailerProperties.put("mail.smtp.writetimeout", 10 * 1000);

        mailer.setHost("HostName");
        mailer.setPort(587);

        mailerProperties.put("mail.smtp.ssl.enable", false);
        mailerProperties.put("mail.smtp.starttls.enable", true);
        mailerProperties.put("mail.smtp.starttls.required", true);

//        mailerProperties.put("mail.smtp.ssl.protocol", "TLS");
//        mailerProperties.put("mail.smtp.ssl.enabled-protocols", "TLSv1.2");

        mailer.setUsername("UserName");

        mailer.setPassword("Password");

        return mailer;
    }

    private static void send(final JavaMailSenderImpl mailer) throws MessagingException {
        final MimeMessage message = mailer.createMimeMessage();
        final MimeMessageHelper messageHelper = new MimeMessageHelper(message);
        messageHelper.setValidateAddresses(true);
        messageHelper.setSentDate(new Date());
        messageHelper.setFrom("from email");
        messageHelper.setTo(new String[]{"to email"});
        messageHelper.setSubject("test smtp connection");
        messageHelper.setText("test smtp connection", true);
        mailer.send(message);
    }
}
