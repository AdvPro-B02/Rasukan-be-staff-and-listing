package advpro.b2.rasukanlsp.model;

import advpro.b2.rasukanlsp.model.Listing;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class ListingTest {

    @Test
    public void testConstructorAndGetters() {
        UUID id = UUID.randomUUID();
        String name = "Test Listing";
        int price = 100;
        int stock = 10;
        UUID seller = UUID.randomUUID();
        int orderCounter = 5;

        Listing listing = new Listing(id, name, stock, price, seller, orderCounter);

        assertEquals(id, listing.getListingId());
        assertEquals(name, listing.getName());
        assertEquals(price, listing.getPrice());
        assertEquals(stock, listing.getStock());
        assertEquals(seller, listing.getSeller());
        assertEquals(orderCounter, listing.getOrderCounter());
    }

    @Test
    public void testSetters() {
        UUID id = UUID.randomUUID();
        Listing listing = new Listing();

        listing.setListingId(id);
        listing.setName("Test Listing");
        listing.setPrice(100);
        listing.setStock(10);
        listing.setSeller(UUID.randomUUID());
        listing.setOrderCounter(5);

        assertEquals(id, listing.getListingId());
        assertEquals("Test Listing", listing.getName());
        assertEquals(100, listing.getPrice());
        assertEquals(10, listing.getStock());
        assertNotNull(listing.getSeller());
        assertEquals(5, listing.getOrderCounter());
    }


    @Test
    public void testToStringMethod() {
        UUID id = UUID.randomUUID();
        String name = "Test Listing";
        int price = 100;
        int stock = 10;
        UUID seller = UUID.randomUUID();
        int orderCounter = 5;

        Listing listing = new Listing(id, name, stock, price, seller, orderCounter);

        String toStringResult = listing.toString();

        assertTrue(toStringResult.contains("listingId"));
        assertTrue(toStringResult.contains("name"));
        assertTrue(toStringResult.contains("price"));
        assertTrue(toStringResult.contains("stock"));
        assertTrue(toStringResult.contains("seller"));
        assertTrue(toStringResult.contains("orderCounter"));
    }
}