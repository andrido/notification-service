package com.exadel.notificationservice.service;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EmailService {

    private final JavaMailSender mailSender;

    public void sendBookAvailableEmail(String to, String clientName, String bookTitle) {
        try {
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");

            helper.setTo(to);
            helper.setSubject("📚 Livro disponível novamente!");
            helper.setFrom("andrido.dev@gmail.com");

            String html = """
                <div style="font-family: Arial, sans-serif; padding: 20px; color: #333;">
                    <h2>Olá, %s!</h2>
                    <p>O livro <b>"%s"</b> que você reservou está disponível para empréstimo.</p>
                    <p>Aproveite e garanta o seu antes que acabe! 😄</p>
                    <br>
                    <p>— Equipe da Biblioteca</p>
                </div>
                """.formatted(clientName, bookTitle);

            helper.setText(html, true);

            mailSender.send(message);
        } catch (MessagingException e) {
            throw new RuntimeException("Erro ao enviar e-mail", e);
        }
    }
}