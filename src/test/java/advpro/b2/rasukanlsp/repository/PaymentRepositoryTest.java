package advpro.b2.rasukanlsp.repository;

import advpro.b2.rasukanlsp.model.Payment;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class PaymentRepositoryTest {

    PaymentRepository paymentRepository;

    @BeforeEach
    void setUp() {
        paymentRepository = new PaymentRepository();
    }

    @Test
    void testSaveCreate() {
        Payment payment = new Payment(UUID.randomUUID(), "123d71a2-c421-4895-890b-1947e454d029", "order123", 100000L, "PENDING");

        Payment result = paymentRepository.save(payment);

        assertEquals(payment.getId(), result.getId());
    }

    @Test
    void testFindByIdIfIdFound() {
        Payment payment = new Payment(UUID.randomUUID(), "123d71a2-c421-4895-890b-1947e454d029", "order123", 100000L, "PENDING");
        paymentRepository.save(payment);

        Payment findResult = paymentRepository.findById(payment.getId().toString());

        assertNotNull(findResult);
        assertEquals(payment.getId().toString(), findResult.getId());
        assertEquals(payment.getUserId(), findResult.getUserId());
        assertEquals(payment.getOrderId(), findResult.getOrderId());
        assertEquals(payment.nominal(), findResult.nominal());
        assertEquals(payment.getPaymentStatus(), findResult.getPaymentStatus());
    }

    @Test
    void testFindByIdIfIdNotFound() {
        Payment payment = new Payment(UUID.randomUUID(), "123d71a2-c421-4895-890b-1947e454d029", "order123", 100000L, "PENDING");
        paymentRepository.save(payment);

        Payment findResult = paymentRepository.findById("invalid_id");

        assertNull(findResult);
    }

    @Test
    void testDeleteByIdIfIdFound() {
        Payment payment = new Payment(UUID.randomUUID(), "123d71a2-c421-4895-890b-1947e454d029", "order123", 100000L, "PENDING");
        paymentRepository.save(payment);

        paymentRepository.deleteById(payment.getId().toString());

        Payment findResult = paymentRepository.findById(payment.getId().toString());

        assertNull(findResult);
    }

    @Test
    void testDeleteByIdIfIdNotFound() {
        Payment payment = new Payment(UUID.randomUUID(), "123d71a2-c421-4895-890b-1947e454d029", "order123", 100000L, "PENDING");
        paymentRepository.save(payment);

        paymentRepository.deleteById("invalid_id");

        Payment findResult = paymentRepository.findById(payment.getId().toString());

        assertNotNull(findResult);
    }
}
