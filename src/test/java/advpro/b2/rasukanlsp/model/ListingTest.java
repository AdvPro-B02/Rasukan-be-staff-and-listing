package advpro.b2.rasukanlsp.model;

import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

public class ListingTest {

    @Test
    public void testConstructorAndGetters() {
        UUID id = UUID.randomUUID();
        String userId = "user123";
        String name = "Test Listing";
        boolean featuredStatus = false;
        LocalDate expirationDate = null;

        Listing listing = new Listing(id, userId, name, featuredStatus, expirationDate);

        assertEquals(id, listing.getId());
        assertEquals(userId, listing.getUserId());
        assertEquals(name, listing.getName());
        assertEquals(featuredStatus, listing.isFeaturedStatus());
        assertEquals(expirationDate, listing.getExpirationDate());
    }

    @Test
    public void testSetters() {
        UUID id = UUID.randomUUID();
        Listing listing = new Listing();

        listing.setId(id);
        listing.setUserId("user123");
        listing.setName("Test Listing");
        listing.setFeaturedStatus(true);
        listing.setExpirationDate(LocalDate.now());

        assertEquals(id, listing.getId());
        assertEquals("user123", listing.getUserId());
        assertEquals("Test Listing", listing.getName());
        assertTrue(listing.isFeaturedStatus());
        assertNotNull(listing.getExpirationDate());
    }


    @Test
    public void testToStringMethod() {
        UUID id = UUID.randomUUID();
        String userId = "user123";
        String name = "Test Listing";
        boolean featuredStatus = false;
        LocalDate expirationDate = LocalDate.now().plusDays(7);
        Listing listing = new Listing(id, userId, name, featuredStatus, expirationDate);

        String toStringResult = listing.toString();

        assertTrue(toStringResult.contains("ID: " + id));
        assertTrue(toStringResult.contains("User ID: " + userId));
        assertTrue(toStringResult.contains("Name: " + name));
        assertTrue(toStringResult.contains("Featured: " + featuredStatus));
        assertTrue(toStringResult.contains("Expiration Date: " + expirationDate));
    }
}