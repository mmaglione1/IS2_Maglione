package com.colegio.services;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

/**
 * ============================================================================
 * SERVICIO DE MENSAJERÍA (JavaMailSender / Mailtrap SMTP)
 * ============================================================================
 * Capa: Servicio / Notificaciones.
 * Envía el correo de bienvenida al registrarse el docente.
 */
@Service
public class EmailService {

    @Autowired
    private JavaMailSender mailSender;

    /**
     * Envía un correo HTML de bienvenida al correo personal del docente.
     *
     * @param destinatario Correo personal registrado.
     * @param nombreDocente Nombre y apellido del profesor.
     */
    @Async
    public void enviarCorreoBienvenida(String destinatario, String nombreDocente) {
        try {
            MimeMessage mensaje = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(mensaje, true, "UTF-8");

            helper.setFrom("notificaciones@colegio.edu.ar");
            helper.setTo(destinatario);
            helper.setSubject("¡Bienvenido/a al Sistema de Gestión Escolar!");

            String contenidoHtml = """
                <div style='font-family: Arial, sans-serif; max-width: 600px; margin: 0 auto; border: 1px solid #e0e0e0; border-radius: 8px; overflow: hidden;'>
                    <div style='background: #0d6efd; color: white; padding: 20px; text-align: center;'>
                        <h2>Sistema de Gestión Escolar</h2>
                    </div>
                    <div style='padding: 24px; color: #333;'>
                        <p>Estimado/a <strong>%s</strong>,</p>
                        <p>Su registro docente en la plataforma institucional se ha completado exitosamente.</p>
                        <p>Sus credenciales de ingreso son:</p>
                        <ul>
                            <li><strong>Usuario:</strong> %s</li>
                            <li><strong>Contraseña:</strong> (La que estableció durante su registro)</li>
                        </ul>
                        <p>Recuerde que ante cualquier eventualidad puede modificar su contraseña desde su perfil una vez iniciada la sesión.</p>
                        <div style='margin-top: 30px; padding-top: 15px; border-top: 1px solid #eee; font-size: 12px; color: #777;'>
                            Este mensaje ha sido generado automáticamente por el entorno escolar.
                        </div>
                    </div>
                </div>
            """.formatted(nombreDocente, destinatario);

            helper.setText(contenidoHtml, true);
            mailSender.send(mensaje);
            System.out.println(">> [Mailtrap] Correo de bienvenida enviado con éxito a: " + destinatario);

        } catch (MessagingException e) {
            System.err.println(">> Error al despachar correo a través de Mailtrap: " + e.getMessage());
        }
    }
}