package advpro.b2.rasukanlsp.model;

import advpro.b2.rasukanlsp.model.builder.OrderBuilder;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class OrderTest {

//    private OrderBuilder orderBuilder;
//    private UUID userId;
//
//    @BeforeEach
//    void setUp() {
//        UUID userId = UUID.fromString("c4266d35-deb2-4dd3-b0c5-7ebb55b56996");
//        int nominal = 100;
//        orderBuilder = new OrderBuilder(userId, nominal);
//    }
//
//    @Test
//    void testCreateOrder_success() {
//        String notes = "Lalala Aku sayang sekali doraemon";
//        int nominal = 100;
//        Integer discount = 10;
//
//        orderBuilder.setNotes(notes);
//        orderBuilder.setDiscount(discount);
//
//        Order order = orderBuilder.build();
//
//        assertEquals(UUID.fromString("c4266d35-deb2-4dd3-b0c5-7ebb55b56996"), order.getUserId());
//        assertEquals(nominal, order.getNominal());
//        assertEquals("WAITING_PAYMENT", order.getOrderStatus());
//        assertEquals("PENDING", order.getPaymentStatus());
//        assertEquals(notes, order.getNotes());
//        assertEquals(discount, order.getDiscount());
//    }
}
