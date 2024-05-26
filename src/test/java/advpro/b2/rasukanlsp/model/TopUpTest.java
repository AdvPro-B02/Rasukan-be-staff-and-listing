package advpro.b2.rasukanlsp.model;

import advpro.b2.rasukanlsp.enums.TopUpStatus;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

public class TopUpTest {
    
    @BeforeEach
    void setUp() {}

    @Test
    void testCreateTopUpDefaultStatusPending() {
        UUID id = UUID.randomUUID();
        UUID user = UUID.randomUUID();
        int amount = 100000;
        TopUp topUp = new TopUp(id, user, amount);

        assertEquals(id, topUp.getId());
        assertEquals(user, topUp.getUserId());
        assertEquals(amount, topUp.getAmount());
        assertEquals(TopUpStatus.PENDING, topUp.getStatus());
    }

    @Test
    void testSetStatus_success() {
        UUID id = UUID.randomUUID();
        UUID user = UUID.randomUUID();
        int amount = 100000;
        TopUp topUp = new TopUp(id, user, amount);

        assertEquals(TopUpStatus.PENDING, topUp.getStatus());

        topUp.setStatus(TopUpStatus.ACCEPTED);
        assertEquals(TopUpStatus.ACCEPTED, topUp.getStatus());

        topUp.setStatus(TopUpStatus.REJECTED);
        assertEquals(TopUpStatus.REJECTED, topUp.getStatus());
    }
}
