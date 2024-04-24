package advpro.b2.rasukanlsp.service;

import advpro.b2.rasukanlsp.model.Payment;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public interface PaymentService {
    public Payment addPayment(UUID id, String userId, String orderId, Long nominal, String paymentStatus);
    public Payment setStatus(String paymentId, String status);
    public Payment getPayment(String paymentId);

    void updatePaymentStatus(UUID paymentId, String newStatus);

    Payment getPaymentById(UUID paymentId);

    public List<Payment> getAllPayments();
    public void deletePayment(String paymentId);

    void deletePayment(UUID paymentId);
}
