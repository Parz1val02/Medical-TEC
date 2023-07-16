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
                "\t\t\t\t<img alt=\"x\" src=\"https://ci5.googleusercontent.com/proxy/oI279oTiQTODAnBqRikWRyNPGjb0B1iLcs97jNPgd0No737wt_8onUBCC_84k567Qc7teZlAfd9PyktdJI2nadOAd7jkXGtgUMzU8JAKUKdeZq97PIZGJTQ3=s0-d-e1-ft#https://s3.amazonaws.com/files.pucp.edu.pe/dci/brochure/img/default.gif\" width=\"650\" class=\"CToWUd\" data-bit=\"iit\">\n" +
                "\t\t\t</td>\n" +
                "\t\t</tr>\n" +
                "\t\t<tr>\n" +
                "\t\t\t<td valign=\"top\" bgcolor=\"#FFFFFF\" style=\"background:#fff;line-height:0\">\n" +
                "\t\t\t\t<img src=\"https://ci3.googleusercontent.com/proxy/LZnN5YsgxaTyjxFVLO4LsQI5WLttqYI5wYnieniyIqU-pJWlZMf82_FGdfqu9untPyo6iudVRDUwU1MiQuk3X8vimmt2E4cHopbtI-DDXqxssXzQU9y0Uan1wS4W=s0-d-e1-ft#https://files.pucp.education/dci/comunicados/2015/09/22195052/sombrita.jpg\" alt=\"\" width=\"650\" height=\"26\" class=\"CToWUd\" data-bit=\"iit\">\n" +
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

        String body = "<!DOCTYPE html>\n" +
                "<html lang=\"en\" xmlns=\"http://www.w3.org/1999/xhtml\" xmlns:o=\"urn:schemas-microsoft-com:office:office\">\n" +
                "<head>\n" +
                "  <meta charset=\"utf-8\">\n" +
                "  <meta name=\"viewport\" content=\"width=device-width,initial-scale=1\">\n" +
                "  <meta name=\"x-apple-disable-message-reformatting\">\n" +
                "  <title></title>\n" +
                "  <!--[if mso]>\n" +
                "  <style>\n" +
                "    table {border-collapse:collapse;border-spacing:0;border:none;margin:0;}\n" +
                "    div, td {padding:0;}\n" +
                "    div {margin:0 !important;}\n" +
                "  </style>\n" +
                "  <noscript>\n" +
                "    <xml>\n" +
                "      <o:OfficeDocumentSettings>\n" +
                "        <o:PixelsPerInch>96</o:PixelsPerInch>\n" +
                "      </o:OfficeDocumentSettings>\n" +
                "    </xml>\n" +
                "  </noscript>\n" +
                "  <![endif]-->\n" +
                "  <style>\n" +
                "    table, td, div, h1, p {\n" +
                "      font-family: Arial, sans-serif;\n" +
                "    }\n" +
                "    @media screen and (max-width: 530px) {\n" +
                "      .unsub {\n" +
                "        display: block;\n" +
                "        padding: 8px;\n" +
                "        margin-top: 14px;\n" +
                "        border-radius: 6px;\n" +
                "        background-color: #555555;\n" +
                "        text-decoration: none !important;\n" +
                "        font-weight: bold;\n" +
                "      }\n" +
                "      .col-lge {\n" +
                "        max-width: 100% !important;\n" +
                "      }\n" +
                "    }\n" +
                "    @media screen and (min-width: 531px) {\n" +
                "      .col-sml {\n" +
                "        max-width: 27% !important;\n" +
                "      }\n" +
                "      .col-lge {\n" +
                "        max-width: 73% !important;\n" +
                "      }\n" +
                "    }\n" +
                "  </style>\n" +
                "</head>\n" +
                "<body style=\"margin:0;padding:0;word-spacing:normal;background-color:#939297;\">\n" +
                "  <div role=\"article\" aria-roledescription=\"email\" lang=\"en\" style=\"text-size-adjust:100%;-webkit-text-size-adjust:100%;-ms-text-size-adjust:100%;background-color:#939297;\">\n" +
                "    <table role=\"presentation\" style=\"width:100%;border:none;border-spacing:0;\">\n" +
                "      <tr>\n" +
                "        <td align=\"center\" style=\"padding:0;\">\n" +
                "          <!--[if mso]>\n" +
                "          <table role=\"presentation\" align=\"center\" style=\"width:600px;\">\n" +
                "          <tr>\n" +
                "          <td>\n" +
                "          <![endif]-->\n" +
                "          <table role=\"presentation\" style=\"width:94%;max-width:600px;border:none;border-spacing:0;text-align:left;font-family:Arial,sans-serif;font-size:16px;line-height:22px;color:#363636;\">\n" +
                "            <tr>\n" +
                "              <td style=\"padding:40px 30px 30px 30px;text-align:center;font-size:24px;font-weight:bold;\">\n" +
                "                <a href=\"http://www.example.com/\" style=\"text-decoration:none;\"><img src=\"https://assets.codepen.io/210284/logo.png\" width=\"165\" alt=\"Logo\" style=\"width:165px;max-width:80%;height:auto;border:none;text-decoration:none;color:#ffffff;\"></a>\n" +
                "              </td>\n" +
                "            </tr>\n" +
                "            <tr>\n" +
                "              <td style=\"padding:30px;background-color:#ffffff;\">\n" +
                "                <h1 style=\"margin-top:0;margin-bottom:16px;font-size:26px;line-height:32px;font-weight:bold;letter-spacing:-0.02em;\">Bienvenido a la plataforma de Medical-TEC!</h1>\n" +
                "                <p style=\"margin:0;\"> TEXTO DE BIENVENIDA <a href=\"http://www.example.com/\" style=\"color:#e50d70;text-decoration:underline;\"></a></p>\n" +
                " <p style=\"margin:0;\"> Su contraseña predeterminada es " + contrasena + " </p> " +
                "              </td>\n" +
                "            </tr>\n" +
                "            <tr>\n" +
                "              <td style=\"padding:0;font-size:24px;line-height:28px;font-weight:bold;\">\n" +
                "                <a href=\"http://www.example.com/\" style=\"text-decoration:none;\"><img src=\"https://assets.codepen.io/210284/1200x800-2.png\" width=\"600\" alt=\"\" style=\"width:100%;height:auto;display:block;border:none;text-decoration:none;color:#363636;\"></a>\n" +
                "              </td>\n" +
                "            </tr>\n" +
                "            <tr>\n" +
                "              <td style=\"padding:35px 30px 11px 30px;font-size:0;background-color:#ffffff;border-bottom:1px solid #f0f0f5;border-color:rgba(201,201,207,.35);\">\n" +
                "                <!--[if mso]>\n" +
                "                <table role=\"presentation\" width=\"100%\">\n" +
                "                <tr>\n" +
                "                <td style=\"width:145px;\" align=\"left\" valign=\"top\">\n" +
                "                <![endif]-->\n" +
                "                <div class=\"col-sml\" style=\"display:inline-block;width:100%;max-width:145px;vertical-align:top;text-align:left;font-family:Arial,sans-serif;font-size:14px;color:#363636;\">\n" +
                "                  <img src=\"https://assets.codepen.io/210284/icon.png\" width=\"115\" alt=\"\" style=\"width:115px;max-width:80%;margin-bottom:20px;\">\n" +
                "                </div>\n" +
                "                <!--[if mso]>\n" +
                "                </td>\n" +
                "                <td style=\"width:395px;padding-bottom:20px;\" valign=\"top\">\n" +
                "                <![endif]-->\n" +
                "                <div class=\"col-lge\" style=\"display:inline-block;width:100%;max-width:395px;vertical-align:top;padding-bottom:20px;font-family:Arial,sans-serif;font-size:16px;line-height:22px;color:#363636;\">\n" +
                "                  <p style=\"margin-top:0;margin-bottom:12px;\">Nullam mollis sapien vel cursus fermentum. Integer porttitor augue id ligula facilisis pharetra. In eu ex et elit ultricies ornare nec ac ex. Mauris sapien massa, placerat non venenatis et, tincidunt eget leo.</p>\n" +
                "                  <p style=\"margin-top:0;margin-bottom:18px;\">Nam non ante risus. Vestibulum vitae eleifend nisl, quis vehicula justo. Integer viverra efficitur pharetra. Nullam eget erat nibh.</p>\n" +
                "                  <p style=\"margin:0;\"><a href=\"https://example.com/\" style=\"background: #ff3884; text-decoration: none; padding: 10px 25px; color: #ffffff; border-radius: 4px; display:inline-block; mso-padding-alt:0;text-underline-color:#ff3884\"><!--[if mso]><i style=\"letter-spacing: 25px;mso-font-width:-100%;mso-text-raise:20pt\">&nbsp;</i><![endif]--><span style=\"mso-text-raise:10pt;font-weight:bold;\">Claim yours now</span><!--[if mso]><i style=\"letter-spacing: 25px;mso-font-width:-100%\">&nbsp;</i><![endif]--></a></p>\n" +
                "                </div>\n" +
                "                <!--[if mso]>\n" +
                "                </td>\n" +
                "                </tr>\n" +
                "                </table>\n" +
                "                <![endif]-->\n" +
                "              </td>\n" +
                "            </tr>\n" +
                "            <tr>\n" +
                "              <td style=\"padding:30px;font-size:24px;line-height:28px;font-weight:bold;background-color:#ffffff;border-bottom:1px solid #f0f0f5;border-color:rgba(201,201,207,.35);\">\n" +
                "                <a href=\"http://www.example.com/\" style=\"text-decoration:none;\"><img src=\"https://assets.codepen.io/210284/1200x800-1.png\" width=\"540\" alt=\"\" style=\"width:100%;height:auto;border:none;text-decoration:none;color:#363636;\"></a>\n" +
                "              </td>\n" +
                "            </tr>\n" +
                "            <tr>\n" +
                "              <td style=\"padding:30px;background-color:#ffffff;\">\n" +
                "                <p style=\"margin:0;\">Duis sit amet accumsan nibh, varius tincidunt lectus. Quisque commodo, nulla ac feugiat cursus, arcu orci condimentum tellus, vel placerat libero sapien et libero. Suspendisse auctor vel orci nec finibus.</p>\n" +
                "              </td>\n" +
                "            </tr>\n" +
                "            <tr>\n" +
                "              <td style=\"padding:30px;text-align:center;font-size:12px;background-color:#404040;color:#cccccc;\">\n" +
                "                <p style=\"margin:0 0 8px 0;\"><a href=\"http://www.facebook.com/\" style=\"text-decoration:none;\"><img src=\"https://assets.codepen.io/210284/facebook_1.png\" width=\"40\" height=\"40\" alt=\"f\" style=\"display:inline-block;color:#cccccc;\"></a> <a href=\"http://www.twitter.com/\" style=\"text-decoration:none;\"><img src=\"https://assets.codepen.io/210284/twitter_1.png\" width=\"40\" height=\"40\" alt=\"t\" style=\"display:inline-block;color:#cccccc;\"></a></p>\n" +
                "                <p style=\"margin:0;font-size:14px;line-height:20px;\">&reg; Someone, Somewhere 2021<br><a class=\"unsub\" href=\"http://www.example.com/\" style=\"color:#cccccc;text-decoration:underline;\">Unsubscribe instantly</a></p>\n" +
                "              </td>\n" +
                "            </tr>\n" +
                "          </table>\n" +
                "          <!--[if mso]>\n" +
                "          </td>\n" +
                "          </tr>\n" +
                "          </table>\n" +
                "          <![endif]-->\n" +
                "        </td>\n" +
                "      </tr>\n" +
                "    </table>\n" +
                "  </div>\n" +
                "</body>\n" +
                "</html>";

        helper.setText(prueba, true);

        mailSender.send(message);

    }


}
