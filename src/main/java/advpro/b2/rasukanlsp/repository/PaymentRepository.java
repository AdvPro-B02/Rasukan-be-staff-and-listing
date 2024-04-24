package advpro.b2.rasukanlsp.repository;

import advpro.b2.rasukanlsp.model.*;

import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class PaymentRepository {
    private List<Payment> paymentData = new ArrayList<>();
    public Payment save(Payment payment) {
        int i = 0;
        for (Payment savedPayment : paymentData) {
            if (savedPayment.getId().equals((payment.getId()))) {
                paymentData.remove(i);
                paymentData.add(i, payment);
                return payment;
            }
            i += 1;
        }
        paymentData.add(payment);
        return payment;
    }
    public Payment findById(String id) {
        for (Payment savedPayment : paymentData) {
            if (savedPayment.getId().equals(id)) {
                return savedPayment;
            }
        }
        return null;
    }
    public List<Payment> findAll() {
        return paymentData;
    }
    public void deleteById(String id) {
        paymentData.removeIf(savedPayment -> savedPayment.getId().equals(id));
    }
}
