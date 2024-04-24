package advpro.b2.rasukanlsp.service;

import advpro.b2.rasukanlsp.model.Payment;
import advpro.b2.rasukanlsp.repository.PaymentRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.UUID;
import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

class PaymentServiceImplTest {

    PaymentServiceImpl paymentService;
    PaymentRepository paymentRepository;
    MediatorService mediatorService;

    @BeforeEach
    void setUp() {
        paymentRepository = mock(PaymentRepository.class);
        mediatorService = mock(MediatorService.class);
        paymentService = new PaymentServiceImpl();
//        paymentService.setMediatorService(mediatorService);
//        paymentService.setPaymentRepository(paymentRepository);
    }

    @Test
    void testAddPayment() {
        UUID paymentId = UUID.randomUUID();
        UUID userId = UUID.randomUUID().t;
        UUID orderId = UUID.randomUUID();
        Long nominal = 1000L;
        String paymentStatus = "PENDING";

        when(paymentRepository.findById(paymentId.toString())).thenReturn(null);

        Payment result = paymentService.addPayment(paymentId, userId.toString(), orderId.toString(), nominal, paymentStatus);

        assertNotNull(result);
        assertEquals(paymentId, UUID.fromString(result.getId()));
        assertEquals(userId, UUID.fromString(result.getUserId()));
        assertEquals(orderId, result.getOrderId());
        assertEquals(nominal, Long.valueOf(result.nominal()));
        assertEquals(paymentStatus, result.getPaymentStatus());

        verify(paymentRepository, times(1)).save(any(Payment.class));
    }

    @Test
    void testAddPaymentWithExistingId() {
        UUID paymentId = UUID.randomUUID();
        UUID userId = UUID.randomUUID();
        UUID orderId = UUID.randomUUID();
        Long nominal = 1000L;
        String paymentStatus = "PENDING";

        when(paymentRepository.findById(paymentId.toString())).thenReturn(new Payment());

        Payment result = paymentService.addPayment(paymentId, userId.toString(), orderId.toString(), nominal, paymentStatus);

        assertNull(result);
        verify(paymentRepository, never()).save(any(Payment.class));
    }

    @Test
    void testSetStatusAccepted() {
        UUID paymentId = UUID.randomUUID();
        Payment payment = new Payment(paymentId, UUID.randomUUID(), UUID.randomUUID(), 1000L, "PENDING");

        when(paymentRepository.findById(paymentId.toString())).thenReturn(payment);

        Payment result = paymentService.setStatus(paymentId.toString(), "ACCEPTED");

        assertNotNull(result);
        assertEquals("ACCEPTED", result.getPaymentStatus());
        verify(paymentRepository, times(1)).save(payment);
    }

    @Test
    void testSetStatusRejected() {
        UUID paymentId = UUID.randomUUID();
        Payment payment = new Payment(paymentId, UUID.randomUUID(), UUID.randomUUID(), 1000L, "PENDING");

        when(paymentRepository.findById(paymentId.toString())).thenReturn(payment);

        Payment result = paymentService.setStatus(paymentId.toString(), "REJECTED");

        assertNotNull(result);
        assertEquals("REJECTED", result.getPaymentStatus());
        verify(paymentRepository, times(1)).save(payment);
    }

    @Test
    void testSetStatusInvalid() {
        UUID paymentId = UUID.randomUUID();
        Payment payment = new Payment(paymentId, UUID.randomUUID(), UUID.randomUUID(), 1000L, "PENDING");

        when(paymentRepository.findById(paymentId.toString())).thenReturn(payment);

        assertThrows(IllegalArgumentException.class, () -> {
            paymentService.setStatus(paymentId.toString(), "INVALID_STATUS");
        });

        verify(paymentRepository, never()).save(any());
    }
}