package advpro.b2.rasukanlsp.model;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;

class FeaturedListingTest {

    @Test
    void testConstructorAndGetters() {
        UUID id = UUID.randomUUID();
        String name = "Featured Listing Test";
        boolean featuredStatus = true;
        LocalDate expirationDate = LocalDate.of(2024, 5, 20);

        FeaturedListing listing = new FeaturedListing(id, name, featuredStatus, expirationDate);

        assertEquals(id, listing.getListingId(), "Listing ID should match");
        assertEquals(name, listing.getName(), "Listing name should match");
        assertEquals(featuredStatus, listing.isFeaturedStatus(), "Featured status should match");
        assertEquals(expirationDate, listing.getExpirationDate(), "Expiration date should match");
    }

    @Test
    void testSetters() {
        FeaturedListing listing = new FeaturedListing();

        UUID id = UUID.randomUUID();
        String name = "New Featured Listing";
        boolean featuredStatus = true;
        LocalDate expirationDate = LocalDate.of(2024, 5, 25);

        listing.setListingId(id);
        listing.setName(name);
        listing.setFeaturedStatus(featuredStatus);
        listing.setExpirationDate(expirationDate);

        assertEquals(id, listing.getListingId(), "Listing ID should match after modification");
        assertEquals(name, listing.getName(), "Listing name should match after modification");
        assertEquals(featuredStatus, listing.isFeaturedStatus(), "Featured status should match after modification");
        assertEquals(expirationDate, listing.getExpirationDate(), "Expiration date should match after modification");
    }

    @Test
    void testToStringMethod() {
        UUID id = UUID.randomUUID();
        String name = "Featured Listing Test";
        boolean featuredStatus = true;
        LocalDate expirationDate = LocalDate.of(2024, 5, 20);

        FeaturedListing listing = new FeaturedListing(id, name, featuredStatus, expirationDate);

        String expectedOutput = "ID: " + id +
                "\nName: " + name +
                "\nFeatured: " + featuredStatus +
                "\nExpiration Date: " + expirationDate;
        assertEquals(expectedOutput, listing.toString(), "Output of toString() method should match");
    }
}
