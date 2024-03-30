package com.appointmed.appointmed.config;

import com.appointmed.appointmed.config.secrets.SMTPSecrets;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;

import java.util.Properties;

@Configuration
public class SmtpClientConfig {

    @Value("${custom-env.smtp.host}")
    private final String HOST;

    @Value("${custom-env.smtp.port}")
    private final int PORT;

    @Value("${custom-env.smtp.username}")
    private final String USERNAME;

    private final SMTPSecrets smtpSecrets;

    public SmtpClientConfig(SMTPSecrets smtpSecrets, @Value("${custom-env.smtp.port}") int port,
                            @Value("${custom-env.smtp.username}") String username, @Value("${custom-env.smtp.host}") String host) {
        this.HOST = host;
        this.PORT = port;
        this.smtpSecrets = smtpSecrets;
        this.USERNAME = username;
    }

    @Bean
    public JavaMailSender createJavaMailSender() {
        JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
        mailSender.setHost(HOST);
        mailSender.setPort(PORT);

        mailSender.setUsername(USERNAME);
        mailSender.setPassword(smtpSecrets.getPassword());

        Properties props = mailSender.getJavaMailProperties();
        props.put("mail.transport.protocol", "smtp");
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.debug", "true");

        return mailSender;
    }
}
