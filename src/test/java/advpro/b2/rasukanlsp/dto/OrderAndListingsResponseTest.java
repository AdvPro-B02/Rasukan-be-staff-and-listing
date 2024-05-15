package advpro.b2.rasukanlsp.dto;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class OrderAndListingsResponseTest {
//
//    @Test
//    void testCreateOrderAndListingsResponse() {
//        UUID orderId = UUID.randomUUID();
//        UUID userId = UUID.randomUUID();
//        int nominal = 100;
//        String orderStatus = "PENDING";
//        String paymentStatus = "UNPAID";
//        String notes = "Some notes";
//        int discount = 10;
//
//        List<ListingDTO> listings = new ArrayList<>();
//        ListingDTO listing1 = new ListingDTO(UUID.randomUUID(), "Item 1", 50, UUID.randomUUID(), 2);
//        ListingDTO listing2 = new ListingDTO(UUID.randomUUID(), "Item 2", 75, UUID.randomUUID(), 3);
//        listings.add(listing1);
//        listings.add(listing2);
//
//        OrderAndListingsResponse response = new OrderAndListingsResponse(orderId, userId, nominal, orderStatus, paymentStatus, notes, discount, listings);
//
//        assertEquals(orderId, response.getOrderId());
//        assertEquals(userId, response.getUserId());
//        assertEquals(nominal, response.getNominal());
//        assertEquals(orderStatus, response.getOrderStatus());
//        assertEquals(paymentStatus, response.getPaymentStatus());
//        assertEquals(notes, response.getNotes());
//        assertEquals(discount, response.getDiscount());
//        assertNotNull(response.getListings());
//        assertEquals(2, response.getListings().size());
//        assertEquals("Item 1", response.getListings().get(0).getName());
//        assertEquals(75, response.getListings().get(1).getPrice());
//    }
}
