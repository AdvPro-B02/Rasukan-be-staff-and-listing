package advpro.b2.rasukanlsp.model;

import org.junit.jupiter.api.Test;
import java.util.UUID;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class PaymentStatusChangeEventTest {

    @Test
    void testPaymentStatusChangeEvent_success() {
        UUID paymentId = UUID.randomUUID();
        String newStatus = "COMPLETED";

        PaymentStatusChangeEvent event = new PaymentStatusChangeEvent(paymentId, newStatus);

        assertEquals(paymentId, event.getPaymentId());
        assertEquals(newStatus, event.getNewStatus());
    }
}

