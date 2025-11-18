package com.exadel.notificationservice.kafka;



import com.exadel.notificationservice.dto.BookEvent;
import com.exadel.notificationservice.dto.BookStatus;
import com.exadel.notificationservice.service.EmailService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

class NotificationConsumerTest {

    private EmailService emailService;
    private NotificationConsumer consumer;

    @BeforeEach
    void setUp() {
        emailService = Mockito.mock(EmailService.class);
        consumer = new NotificationConsumer(emailService);
    }

    @Test
    void consume_shouldCallEmailService_whenBookIsAvailable() {
        BookEvent event = new BookEvent(1L, "Java Básico", BookStatus.AVAILABLE, "Matheus", "matheus@test.com");

        consumer.consume(event);

        Mockito.verify(emailService).sendBookAvailableEmail(
                "matheus@test.com",
                "Matheus",
                "Java Básico"
        );
    }

    @Test
    void consume_shouldNotCallEmailService_whenBookNotAvailable() {
        BookEvent event = new BookEvent(1L, "Java Básico", BookStatus.BORROWED, "Matheus", "matheus@test.com");

        consumer.consume(event);

        Mockito.verifyNoInteractions(emailService);
    }
}
