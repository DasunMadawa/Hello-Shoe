package lk.ijse.helloshoe.service.impl;

import lk.ijse.helloshoe.dto.EmailDTO;
import lk.ijse.helloshoe.service.EmailService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;

@Slf4j
@RequiredArgsConstructor
@Component
public class EmailServiceImpl implements EmailService {
    private final JavaMailSender emailSender;

    @Override
    public void sendSimpleEmail(EmailDTO email) {
        try {
            SimpleMailMessage message = new SimpleMailMessage();
            message.setFrom("helloshoespvtltd@gmail.com");
            message.setTo(email.getTo());
            message.setSubject(email.getSubject());
            message.setText(email.getBody());
            emailSender.send(message);
            log.info("Email sent successfully");
        } catch (
                Exception e) {
            log.error("Error occurred while sending email");
            throw e;
        }


    }
}
