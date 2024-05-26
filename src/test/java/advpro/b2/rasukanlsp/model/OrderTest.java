package advpro.b2.rasukanlsp.model;

import advpro.b2.rasukanlsp.model.builder.OrderBuilder;
import advpro.b2.rasukanlsp.enums.OrderStatus;
import advpro.b2.rasukanlsp.enums.PaymentStatus;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

public class OrderTest {

    private OrderBuilder orderBuilder;
    private UUID userId;
    private UUID seller;

    @BeforeEach
    void setUp() {
        userId = UUID.fromString("c4266d35-deb2-4dd3-b0c5-7ebb55b56996");
        seller = UUID.fromString("8e045a64-5756-4a33-b55e-53bed3e5cd15");
        int nominal = 100;
        orderBuilder = new OrderBuilder(userId, nominal, seller);
    }

    @Test
    void testCreateOrder_success() {
        String notes = "Lalala Aku sayang sekali doraemon";
        int nominal = 100;
        Integer discount = 10;

        orderBuilder.setNotes(notes);
        orderBuilder.setDiscount(discount);

        Order order = orderBuilder.build();

        assertEquals(userId, order.getUserId());
        assertEquals(nominal, order.getNominal());
        assertEquals("WAITING_PAYMENT", order.getOrderStatus());
        assertEquals("PENDING", order.getPaymentStatus());
        assertEquals(seller, order.getSeller());
        assertEquals(notes, order.getNotes());
        assertEquals(discount, order.getDiscount());
    }

    @Test
    void testConstructorWithOrderId() {
        UUID orderId = UUID.randomUUID();
        int nominal = 200;

        OrderBuilder orderBuilderWithId = new OrderBuilder(orderId, userId, nominal, seller);
        Order order = orderBuilderWithId.build();

        assertEquals(orderId, order.getOrderId());
        assertEquals(userId, order.getUserId());
        assertEquals(nominal, order.getNominal());
        assertNull(order.getOrderStatus());
        assertNull(order.getPaymentStatus());
        assertNull(order.getNotes());
        assertNull(order.getDiscount());
        assertEquals(seller, order.getSeller());
    }

    @Test
    void testSetOrderStatus_valid() {
        orderBuilder.setOrderStatus("PROCESSED");
        Order order = orderBuilder.build();
        assertEquals("PROCESSED", order.getOrderStatus());
    }

    @Test
    void testSetOrderStatus_invalid() {
        assertThrows(IllegalArgumentException.class, () -> orderBuilder.setOrderStatus("INVALID_STATUS"));
    }

    @Test
    void testSetPaymentStatus_valid() {
        orderBuilder.setPaymentStatus("REJECTED");
        Order order = orderBuilder.build();
        assertEquals("REJECTED", order.getPaymentStatus());
    }

    @Test
    void testSetPaymentStatus_invalid() {
        assertThrows(IllegalArgumentException.class, () -> orderBuilder.setPaymentStatus("INVALID_STATUS"));
    }

    @Test
    void testSetNotes() {
        String notes = "Special instructions";
        orderBuilder.setNotes(notes);
        Order order = orderBuilder.build();
        assertEquals(notes, order.getNotes());
    }

    @Test
    void testSetDiscount() {
        Integer discount = 15;
        orderBuilder.setDiscount(discount);
        Order order = orderBuilder.build();
        assertEquals(discount, order.getDiscount());
    }

    @Test
    void testGetterSetterCoverage() {
        Order order = new Order();

        UUID orderId = UUID.randomUUID();
        UUID userId = UUID.randomUUID();
        int nominal = 300;
        String orderStatus = "FINISHED";
        String paymentStatus = "ACCEPTED";
        String notes = "Tolong warna bajunya yang hitam";
        Integer discount = 5;
        UUID sellerId = UUID.randomUUID();

        order.setOrderId(orderId);
        order.setUserId(userId);
        order.setNominal(nominal);
        order.setOrderStatus(orderStatus);
        order.setPaymentStatus(paymentStatus);
        order.setNotes(notes);
        order.setDiscount(discount);
        order.setSeller(sellerId);

        assertEquals(orderId, order.getOrderId());
        assertEquals(userId, order.getUserId());
        assertEquals(nominal, order.getNominal());
        assertEquals(orderStatus, order.getOrderStatus());
        assertEquals(paymentStatus, order.getPaymentStatus());
        assertEquals(notes, order.getNotes());
        assertEquals(discount, order.getDiscount());
        assertEquals(sellerId, order.getSeller());
    }
}
