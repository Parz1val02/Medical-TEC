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

    public void sendEmailUserCreation(String toEmail,String subject,String contrasena) throws MessagingException {

        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");

        helper.setFrom("telesystemclinic@gmail.com");
        helper.setTo(toEmail);
        helper.setSubject(subject);

        String prueba = "<table width=\"650\" border=\"0\" align=\"center\" cellpadding=\"0\" cellspacing=\"0\" style=\"border:1px solid #d8d8d8;border-collapse:collapse\">\n" +
                "\t\t<tbody><tr>\n" +
                "          <td valign=\"top\" bgcolor=\"#FFFFFF\" style=\"background:#fff;line-height:0\">\n" +
                "\t\t\t\t<img alt=\"x\" src=\"https://res.cloudinary.com/dtnko1xwm/image/upload/v1689559827/medical-logo-cloud_ketqww.jpg\" width=\"650\" class=\"CToWUd\" data-bit=\"iit\">\n" +
                "\t\t\t</td>\n" +
                "\t\t</tr>\n" +
                "\t\t<tr>\n" +
                "\t\t\t<td valign=\"top\" bgcolor=\"#FFFFFF\" style=\"background:#fff;line-height:0\">\n" +
                "\t\t\t</td>\n" +
                "\t\t</tr>\n" +
                "      \t\n" +
                "\t\t<tr>\n" +
                "\t\t\t<td valign=\"top\" bgcolor=\"#fff\" style=\"background:#fff;padding-right:30px;padding-left:30px;padding-bottom:25px;font-size:15px;font-family:Arial;color:#000;line-height:22px\">\n" +
                "\t\t\t\t<p><span style=\"font-weight:400\"><strong>¡Bienvenido/a a Medical-Tec!</strong></span></p>\n" +
                "<p>Nos complace enormemente darte la bienvenida a nuestra plataforma web de servicios médicos. Queremos agradecerte por elegirnos como tu aliado de salud y confiar en nuestros servicios para el cuidado de tu bienestar.</p>\n" +
                "<p><strong>Tu cuenta ha sido creada exitosamente</strong>, y estamos emocionados de que te unas a nuestra comunidad médica. A continuación, te proporcionamos los detalles de tu cuenta: </p>\n" +
                "<p><strong>Contraseña: </strong>" + contrasena + "</p>\n" +
                "<p>Con Medical-Tec, tendrás acceso a una amplia gama de servicios médicos y especialidades. Podrás agendar citas con diferentes médicos y especialistas, así como administrar tu historial médico de manera cómoda y segura.</p>\n" +

                "<table border=\"0\" align=\"center\" cellpadding=\"0\" cellspacing=\"0\" style='background-image: linear-gradient(-45deg, #014ba7, #0183d0)'>\n" +
                "\t\t\t\t\t\t\t<tbody><tr>\n" +
                "\t\t\t\t\t\t\t\t<td colspan=\"3\" style=\"font-size:0;height:15px;line-height:1\">&nbsp;</td>\n" +
                "\t\t\t\t\t\t\t</tr>\n" +
                "\t\t\t\t\t\t\t<tr>\n" +
                "\t\t\t\t\t\t\t\t<td style=\"font-size:0;line-height:1\" width=\"50\">&nbsp;</td>\n" +
                "\t\t\t\t\t\t\t\t<td align=\"center\" style=\"text-align:center;font-family:Arial,sans-serif;line-height:1.1\">\n" +
                "\t\t\t\t\t\t\t\t\t<a href=\"\" style=\"color:#fff;text-decoration:none;font-weight:bold;display:inline-block;line-height:inherit\" target=\"_blank\" data-saferedirecturl=\"\">Ir a la pagina de inicio de sesión</a>\n" +
                "\t\t\t\t\t\t\t\t</td>\n" +
                "\t\t\t\t\t\t\t\t<td style=\"font-size:0;line-height:1\" width=\"50\">&nbsp;</td>\n" +
                "\t\t\t\t\t\t\t</tr>\n" +
                "\t\t\t\t\t\t\t<tr>\n" +
                "\t\t\t\t\t\t\t\t<td colspan=\"3\" style=\"font-size:0;height:15px;line-height:1\">&nbsp;</td>\n" +
                "\t\t\t\t\t\t\t</tr>\n" +
                "\t\t\t\t\t\t</tbody></table>\n" +
                "\t\t\t\t\t\t<table border=\"0\" align=\"center\" cellpadding=\"0\" cellspacing=\"0\">\n" +
                "\t\t\t\t\t\t\t<tbody><tr>\n" +
                "\t\t\t\t\t\t\t\t<td style=\"font-size:0;height:20px;line-height:1\">&nbsp;</td>\n" +
                "\t\t\t\t\t\t\t</tr>\n" +
                "\t\t\t\t\t\t</tbody></table>\n" +
                "\t\t\t\t\t\t<p><span style=\"font-weight:400\">Muchas gracias por tu preferencia.</span></p>\n" +
                "<p><strong>Clínica Medical-Tec</strong></p>\n" +
                "\n" +
                "\t        </td>\n" +
                "\t\t</tr>\n" +
                "\t\t<tr>\n" +
                "\t\t\t<td bgcolor=\"\" style='background-image: linear-gradient(-45deg, #014ba7, #0183d0)' styleheight=\"25\"> </td>\n" +
                "\t\t</tr>\n" +
                "\t</tbody></table>";


        helper.setText(prueba, true);

        mailSender.send(message);

    }


}
