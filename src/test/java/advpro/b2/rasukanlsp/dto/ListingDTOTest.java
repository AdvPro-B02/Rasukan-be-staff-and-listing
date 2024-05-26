package advpro.b2.rasukanlsp.dto;

import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

public class ListingDTOTest {

    @Test
    public void testListingDTOCreation() {
        UUID listingId = UUID.randomUUID();
        String name = "Test Listing";
        int price = 100;
        UUID seller = UUID.randomUUID();
        int quantity = 10;
        int orderCounter = 5;

        ListingDTO listing = new ListingDTO(listingId, name, price, seller, quantity, orderCounter);

        assertNotNull(listing);
        assertEquals(listingId, listing.getListingId());
        assertEquals(name, listing.getName());
        assertEquals(price, listing.getPrice());
        assertEquals(seller, listing.getSeller());
        assertEquals(quantity, listing.getQuantity());
        assertEquals(orderCounter, listing.getOrderCounter());
    }

    @Test
    public void testSettersAndGetters() {
        ListingDTO listing = new ListingDTO(
                UUID.randomUUID(),
                "Initial Name",
                200,
                UUID.randomUUID(),
                20,
                10
        );

        UUID newListingId = UUID.randomUUID();
        String newName = "Updated Name";
        int newPrice = 300;
        UUID newSeller = UUID.randomUUID();
        int newQuantity = 30;
        int newOrderCounter = 15;

        listing.setListingId(newListingId);
        listing.setName(newName);
        listing.setPrice(newPrice);
        listing.setSeller(newSeller);
        listing.setQuantity(newQuantity);
        listing.setOrderCounter(newOrderCounter);

        assertEquals(newListingId, listing.getListingId());
        assertEquals(newName, listing.getName());
        assertEquals(newPrice, listing.getPrice());
        assertEquals(newSeller, listing.getSeller());
        assertEquals(newQuantity, listing.getQuantity());
        assertEquals(newOrderCounter, listing.getOrderCounter());
    }
}
