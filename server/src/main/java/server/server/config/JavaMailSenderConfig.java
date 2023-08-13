package server.server.config;


import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;

import java.util.Properties;

@Configuration
public class JavaMailSenderConfig {

    @Value("${spring.mail.username}")
    String userName;
    @Value("${spring.mail.password}")
    String password;
    @Value("${spring.mail.host}")
    String host;
    @Value("${spring.mail.port}")
    Integer port;

    @Bean
    JavaMailSender javaMailSender(){
        JavaMailSenderImpl javaMailSenderImpl = new JavaMailSenderImpl();
        javaMailSenderImpl.setHost(host);
        javaMailSenderImpl.setPort(port);
        javaMailSenderImpl.setUsername(userName);
        javaMailSenderImpl.setPassword(password);

        Properties properties = javaMailSenderImpl.getJavaMailProperties();
        properties.put("mail.smtp.starttls.enable","true");
        return javaMailSenderImpl;
    }
}
