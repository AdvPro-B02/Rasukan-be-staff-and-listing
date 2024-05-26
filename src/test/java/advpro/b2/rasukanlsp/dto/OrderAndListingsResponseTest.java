package advpro.b2.rasukanlsp.dto;

import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

public class OrderAndListingsResponseTest {

    @Test
    public void testFullConstructor() {
        UUID orderId = UUID.randomUUID();
        UUID userId = UUID.randomUUID();
        int nominal = 1000;
        String orderStatus = "WAITING_PAYMENT";
        String paymentStatus = "PENDING";
        String notes = "Urgent delivery";
        int discount = 10;
        UUID seller = UUID.randomUUID();
        List<ListingDTO> listings = List.of(new ListingDTO(UUID.randomUUID(), "Item 1", 500, seller, 2, 1));

        OrderAndListingsResponse response = new OrderAndListingsResponse(orderId, userId, nominal, orderStatus, paymentStatus, notes, discount, seller, listings);

        assertNotNull(response);
        assertEquals(orderId, response.getOrderId());
        assertEquals(userId, response.getUserId());
        assertEquals(nominal, response.getNominal());
        assertEquals(orderStatus, response.getOrderStatus());
        assertEquals(paymentStatus, response.getPaymentStatus());
        assertEquals(notes, response.getNotes());
        assertEquals(discount, response.getDiscount());
        assertEquals(seller, response.getSeller());
        assertEquals(listings, response.getListings());
    }

    @Test
    public void testConstructorWithoutNotesAndDiscount() {
        UUID orderId = UUID.randomUUID();
        UUID userId = UUID.randomUUID();
        int nominal = 1000;
        String orderStatus = "WAITING_PAYMENT";
        String paymentStatus = "PENDING";
        UUID seller = UUID.randomUUID();
        List<ListingDTO> listings = List.of(new ListingDTO(UUID.randomUUID(), "Item 1", 500, seller, 2, 1));

        OrderAndListingsResponse response = new OrderAndListingsResponse(orderId, userId, nominal, orderStatus, paymentStatus, seller, listings);

        assertNotNull(response);
        assertEquals(orderId, response.getOrderId());
        assertEquals(userId, response.getUserId());
        assertEquals(nominal, response.getNominal());
        assertEquals(orderStatus, response.getOrderStatus());
        assertEquals(paymentStatus, response.getPaymentStatus());
        assertNull(response.getNotes());
        assertEquals(0, response.getDiscount());
        assertEquals(seller, response.getSeller());
        assertEquals(listings, response.getListings());
    }

    @Test
    public void testConstructorWithNotesWithoutDiscount() {
        UUID orderId = UUID.randomUUID();
        UUID userId = UUID.randomUUID();
        int nominal = 1000;
        String orderStatus = "Pending";
        String paymentStatus = "Unpaid";
        String notes = "Urgent delivery";
        UUID seller = UUID.randomUUID();
        List<ListingDTO> listings = List.of(new ListingDTO(UUID.randomUUID(), "Item 1", 500, seller, 2, 1));

        OrderAndListingsResponse response = new OrderAndListingsResponse(orderId, userId, nominal, orderStatus, paymentStatus, notes, seller, listings);

        assertNotNull(response);
        assertEquals(orderId, response.getOrderId());
        assertEquals(userId, response.getUserId());
        assertEquals(nominal, response.getNominal());
        assertEquals(orderStatus, response.getOrderStatus());
        assertEquals(paymentStatus, response.getPaymentStatus());
        assertEquals(notes, response.getNotes());
        assertEquals(0, response.getDiscount());
        assertEquals(seller, response.getSeller());
        assertEquals(listings, response.getListings());
    }

    @Test
    public void testConstructorWithDiscountWithoutNotes() {
        UUID orderId = UUID.randomUUID();
        UUID userId = UUID.randomUUID();
        int nominal = 1000;
        String orderStatus = "WAITING_PAYMENT";
        String paymentStatus = "PENDING";
        int discount = 10;
        UUID seller = UUID.randomUUID();
        List<ListingDTO> listings = List.of(new ListingDTO(UUID.randomUUID(), "Item 1", 500, seller, 2, 1));

        OrderAndListingsResponse response = new OrderAndListingsResponse(orderId, userId, nominal, orderStatus, paymentStatus, discount, seller, listings);

        assertNotNull(response);
        assertEquals(orderId, response.getOrderId());
        assertEquals(userId, response.getUserId());
        assertEquals(nominal, response.getNominal());
        assertEquals(orderStatus, response.getOrderStatus());
        assertEquals(paymentStatus, response.getPaymentStatus());
        assertNull(response.getNotes());
        assertEquals(discount, response.getDiscount());
        assertEquals(seller, response.getSeller());
        assertEquals(listings, response.getListings());
    }
}
