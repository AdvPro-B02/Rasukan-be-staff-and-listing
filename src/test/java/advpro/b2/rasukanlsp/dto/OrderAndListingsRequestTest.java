package advpro.b2.rasukanlsp.dto;

import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Map;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

public class OrderAndListingsRequestTest {

    @Test
    public void testOrderAndListingsRequestCreation() {
        UUID userId = UUID.randomUUID();
        int nominal = 1000;
        String notes = "Urgent delivery";
        Integer discount = 15;
        UUID seller = UUID.randomUUID();
        Map<UUID, Integer> listingQuantityMap = Map.of(UUID.randomUUID(), 2);

        OrderAndListingsRequest request = new OrderAndListingsRequest();
        request.setUserId(userId);
        request.setNominal(nominal);
        request.setNotes(notes);
        request.setDiscount(discount);
        request.setSeller(seller);
        request.setListingQuantityMap(listingQuantityMap);

        assertNotNull(request);
        assertEquals(userId, request.getUserId());
        assertEquals(nominal, request.getNominal());
        assertEquals(notes, request.getNotes());
        assertEquals(discount, request.getDiscount());
        assertEquals(seller, request.getSeller());
        assertEquals(listingQuantityMap, request.getListingQuantityMap());
    }

    @Test
    public void testOrderAndListingsRequestSettersAndGetters() {
        OrderAndListingsRequest request = new OrderAndListingsRequest();

        UUID userId = UUID.randomUUID();
        int nominal = 1000;
        String notes = "Urgent delivery";
        Integer discount = 15;
        UUID seller = UUID.randomUUID();
        Map<UUID, Integer> listingQuantityMap = Map.of(UUID.randomUUID(), 2);

        request.setUserId(userId);
        request.setNominal(nominal);
        request.setNotes(notes);
        request.setDiscount(discount);
        request.setSeller(seller);
        request.setListingQuantityMap(listingQuantityMap);

        assertEquals(userId, request.getUserId());
        assertEquals(nominal, request.getNominal());
        assertEquals(notes, request.getNotes());
        assertEquals(discount, request.getDiscount());
        assertEquals(seller, request.getSeller());
        assertEquals(listingQuantityMap, request.getListingQuantityMap());
    }

    @Test
    public void testSettersAndGetters() {
        OrderAndListingsResponse response = new OrderAndListingsResponse(
                UUID.randomUUID(),
                UUID.randomUUID(),
                1000,
                "Pending",
                "Unpaid",
                "Notes",
                10,
                UUID.randomUUID(),
                List.of(new ListingDTO(UUID.randomUUID(), "Item 1", 500, UUID.randomUUID(), 2, 1))
        );

        UUID newOrderId = UUID.randomUUID();
        UUID newUserId = UUID.randomUUID();
        int newNominal = 2000;
        String newOrderStatus = "WAITING_PAYMENT";
        String newPaymentStatus = "PENDING";
        String newNotes = "New notes";
        int newDiscount = 20;
        UUID newSeller = UUID.randomUUID();
        List<ListingDTO> newListings = List.of(new ListingDTO(UUID.randomUUID(), "Item 2", 600, newSeller, 3, 2));

        response.setOrderId(newOrderId);
        response.setUserId(newUserId);
        response.setNominal(newNominal);
        response.setOrderStatus(newOrderStatus);
        response.setPaymentStatus(newPaymentStatus);
        response.setNotes(newNotes);
        response.setDiscount(newDiscount);
        response.setSeller(newSeller);
        response.setListings(newListings);

        assertEquals(newOrderId, response.getOrderId());
        assertEquals(newUserId, response.getUserId());
        assertEquals(newNominal, response.getNominal());
        assertEquals(newOrderStatus, response.getOrderStatus());
        assertEquals(newPaymentStatus, response.getPaymentStatus());
        assertEquals(newNotes, response.getNotes());
        assertEquals(newDiscount, response.getDiscount());
        assertEquals(newSeller, response.getSeller());
        assertEquals(newListings, response.getListings());
    }

    @Test
    public void testOrderAndListingsRequestDefaultValues() {
        OrderAndListingsRequest request = new OrderAndListingsRequest();

        assertNull(request.getUserId());
        assertEquals(0, request.getNominal());
        assertNull(request.getNotes());
        assertNull(request.getDiscount());
        assertNull(request.getSeller());
        assertNull(request.getListingQuantityMap());
    }
}
