package com.exadel.notificationservice.kafka;

import com.exadel.notificationservice.dto.BookEvent;
import com.exadel.notificationservice.dto.BookStatus;
import com.exadel.notificationservice.service.EmailService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class NotificationConsumer {

    private final EmailService emailService;

    @KafkaListener(topics = "book-events", groupId = "notification-group")
    public void consume(BookEvent event) {
        log.info("📨 Received BookEvent: {}", event);

        if (event.getStatus().equals(BookStatus.AVAILABLE)) {
            log.info("📢 Notification: O livro '{}' está disponível novamente!", event.getTitle());

            // 👉 Enviar e-mail para o usuário
            try {
                emailService.sendBookAvailableEmail(
                        event.getUserEmail(),
                        event.getUserName(),
                        event.getTitle()
                );
                log.info("✅ Email enviado para {}", event.getUserEmail());
            } catch (Exception e) {
                log.error("❌ Erro ao enviar e-mail: {}", e.getMessage(), e);
            }
        }
    }
}
