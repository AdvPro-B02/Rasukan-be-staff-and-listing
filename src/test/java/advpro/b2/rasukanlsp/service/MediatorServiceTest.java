package advpro.b2.rasukanlsp.service;

import advpro.b2.rasukanlsp.model.PaymentStatusChangeEvent;
import advpro.b2.rasukanlsp.model.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.web.client.RestTemplate;

import java.util.*;

import static org.mockito.Mockito.*;

class MediatorServiceTest {

    MediatorService mediatorService;
    RestTemplate restTemplate;

    @BeforeEach
    void setUp() {
        restTemplate = mock(RestTemplate.class);
        mediatorService = new MediatorService();
        mediatorService.setRestTemplate(restTemplate);
    }

    @Test
    void testHandlePaymentStatusChangedEvent() {
        UUID userId = UUID.randomUUID();
        UUID paymentId = UUID.randomUUID();
        String newStatus = "ACCEPTED";

        User user = new User();
        user.setUserId(userId);
        Map<UUID, String> paymentMap = new HashMap<>();
        paymentMap.put(paymentId, "PENDING");
        user.setUserPayments(List.of(paymentMap));

        when(restTemplate.getForObject(anyString(), eq(User.class), any(UUID.class))).thenReturn(user);

        PaymentStatusChangeEvent event = new PaymentStatusChangeEvent(paymentId, newStatus);
        mediatorService.mediateEvent(event);

        verify(restTemplate, times(1)).getForObject(anyString(), eq(User.class), any(UUID.class));
        verify(restTemplate, times(1)).put(anyString(), eq(user), any(UUID.class));
    }
}
