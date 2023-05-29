/*package com.example.medicaltec.funciones;

import jakarta.mail.Message;
import jakarta.mail.MessagingException;
import jakarta.mail.Session;
import jakarta.mail.Transport;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;
import java.util.Properties;


//para enviar correo al olvidar contraseña y registrarse
public class EnviarCorreo {

    public static void main(String correoDestino, String codigoUsuario)throws MessagingException {

        String correo = "pucpmonitoring2022@gmail.com"; //cambiar correo
        String contra = "cxzmeohuebkelqhc";  //cambiar contraseña

        // CONTRASEÑA 16 CARACETERES QUE SE TIENE QUE USAR ->
        Properties p = new Properties();
        p.put("mail.smtp.host","smtp.gmail.com");
        p.setProperty("mail.smtp.starttls.enable","true");
        p.put("mail.smtp.ssl.trust","smtp.gmail.com");
        p.setProperty("mail.smtp.port","587");
        p.setProperty("mail.smtp.user",correo);
        p.setProperty("mail.smtp.auth","true");

        Session s = Session.getDefaultInstance(p);
        MimeMessage mensaje = new MimeMessage(s);
        mensaje.setFrom(new InternetAddress(correo)); //FROM
        mensaje.addRecipient(Message.RecipientType.TO,new InternetAddress(correoDestino));  //TO
        mensaje.setSubject("Cambio de Contraseña - MONITORING PUCP"); //
        String link = "http://34.83.95.78:8080/MonitoringPUCP-1.0-SNAPSHOT/Login?accion=restablecer&code="+codigoUsuario;
        String msg = "Hola! \n" +
                "\n" +
                "Recibimos una solicitud para restablecer tu contraseña de Monitoring PUCP.\n" +
                "\n" +
                "Cree una nueva contraseña ingresando al siguiente link.\n" + link +
                "\n" +
                "\n" +
                "¿No solicitaste este cambio? Ignora este correo por favor. \n" +
                "\n" +
                "Monitoring PUCP";

        mensaje.setText(msg);
        //mensaje.setText("Este es un mensaje que se envia desde JAVA");

        Transport t = s.getTransport("smtp");
        t.connect(correo,contra);
        t.sendMessage(mensaje,mensaje.getAllRecipients());
        t.close();

        //JOptionPane.showMessageDialog(null,"Mensaje enviado");

    }

}*/
