package advpro.b2.rasukanlsp.service;

import advpro.b2.rasukanlsp.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Map;
import java.util.UUID;

@Service
public class MediatorService implements Mediator {
    @Autowired
    private RestTemplate restTemplate;

    public void mediateEvent(Event event) {
        if(event instanceof PaymentStatusChangeEvent) {
            handlePaymentStatusChangedEvent((PaymentStatusChangeEvent) event);
        } else if (event instanceof TopupStatusChangeEvent) {
            handleTopupStatusChangedEvent((TopupStatusChangeEvent) event);
        }
    }

    private void handlePaymentStatusChangedEvent(PaymentStatusChangeEvent event) {
        UUID userId = event.getPaymentId();
        User user = restTemplate.getForObject("http://auth-service/users/{userId}", User.class, userId);

        List<Map<UUID, String>> payments = user.getUserPayments();
        UUID paymentId = event.getPaymentId();
        String newStatus = event.getNewStatus();

        for (Map<UUID, String> payment : payments) {
            if (payment.containsKey(paymentId)) {
                payment.put(paymentId, newStatus);
                break;
            }
        }

        restTemplate.put("http://auth-service/users/{userId}", user, userId);

        System.out.println("User ber-ID " + userId + " telah berhasil mendapat notifikasi berubahnya status payment ber-ID " + paymentId + ": " + newStatus);
    }

    private void handleTopupStatusChangedEvent(TopupStatusChangeEvent event) {

    }
}
