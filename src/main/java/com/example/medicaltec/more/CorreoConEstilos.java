package com.example.medicaltec.more;



import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

@Service
public class CorreoConEstilos {

    @Autowired
    private JavaMailSender mailSender;

    public void sendEmailEstilos(String toEmail,String subject,String body) throws MessagingException {

        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");

        helper.setFrom("telesystemclinic@gmail.com");
        helper.setTo(toEmail);
        helper.setSubject(subject);
        helper.setText(body, true);

        mailSender.send(message);

    }

}
