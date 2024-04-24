package advpro.b2.rasukanlsp.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import advpro.b2.rasukanlsp.repository.*;
import advpro.b2.rasukanlsp.model.Payment;
import advpro.b2.rasukanlsp.model.PaymentStatusChangeEvent;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.UUID;

@Service
public class PaymentServiceImpl implements PaymentService {

    @Autowired
    private MediatorService mediatorService;

    @Autowired
    private PaymentRepository paymentRepository;

    @Override
    public Payment addPayment(UUID id, String userId, String orderId, Long nominal, String paymentStatus) {
        if (paymentRepository.findById(id) == null){
            Payment payment = new Payment(id, userId, orderId, nominal, paymentStatus);
            paymentRepository.save(payment);
            return payment;
        }
        return null;
    }

    @Override
    public Payment setStatus(String paymentId, String status) {
        Payment payment = paymentRepository.findById(paymentId);
        if (status.equals("ACCEPTED")) {
            payment.setPaymentStatus("ACCEPTED");
        } else if (status.equals("REJECTED")) {
            payment.setPaymentStatus("REJECTED");
        } else {
            throw new IllegalArgumentException("Invalid status payment");
        }
        return payment;
    }

    @Override
    public void updatePaymentStatus(UUID paymentId, String newStatus) {
        Payment payment = paymentRepository.findById(paymentId);
        if (payment != null) {
            payment.setPaymentStatus(newStatus);
            paymentRepository.save(payment);

            PaymentStatusChangeEvent event = new PaymentStatusChangeEvent(paymentId, newStatus);
            mediatorService.mediateEvent(event);
        } else {
            throw new NoSuchElementException("Id not found");
        }
    }

    @Override
    public Payment getPayment(UUID paymentId) {
        return paymentRepository.findById(paymentId.toString());
    }

    @Override
    public List<Payment> getAllPayments() {
        return paymentRepository.findAll();
    }


    @Override
    public void deletePayment(UUID paymentId) {paymentRepository.deleteById(paymentId.toString());
    }
}
