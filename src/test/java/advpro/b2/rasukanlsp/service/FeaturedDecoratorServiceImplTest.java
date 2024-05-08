package advpro.b2.rasukanlsp.service;

import advpro.b2.rasukanlsp.model.Listing;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDate;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class FeaturedDecoratorServiceImplTest {

    @Mock
    private ListingService listingService;

    @InjectMocks
    private FeaturedDecoratorServiceImpl featuredDecoratorService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void testMarkListingAsFeatured() {
        UUID id = UUID.randomUUID();
        boolean status = true;

        Listing listing = new Listing();
        listing.setId(id);
        listing.setUserId("user123");
        listing.setName("Test Listing");

        when(listingService.getListingDetail(id)).thenReturn(Optional.of(listing));

        String message = featuredDecoratorService.markListingAsFeatured(id, status, LocalDate.now());

        assertEquals("Listing with ID " + id.toString() + " has been marked as featured", message);
        assertTrue(listing.isFeaturedStatus());
        assertNotNull(listing.getExpirationDate());
    }

    @Test
    void testMarkListingAsFeatured_NotFound() {
        UUID id = UUID.randomUUID();
        boolean status = true;

        when(listingService.getListingDetail(id)).thenReturn(Optional.empty());

        String message = featuredDecoratorService.markListingAsFeatured(id, status, LocalDate.now());

        assertEquals("Listing with ID " + id.toString() + " not found", message);
    }

    @Test
    void testRemoveFeaturedStatus() {
        UUID id = UUID.randomUUID();

        Listing listing = new Listing();
        listing.setId(id);
        listing.setUserId("user123");
        listing.setName("Test Listing");
        listing.setFeaturedStatus(true);

        when(listingService.getListingDetail(id)).thenReturn(Optional.of(listing));

        String message = featuredDecoratorService.removeFeaturedStatus(id);

        assertEquals("Featured status has been removed from listing with ID " + id.toString(), message);
        assertFalse(listing.isFeaturedStatus());
        assertNull(listing.getExpirationDate());
    }

    @Test
    void testRemoveFeaturedStatus_NotFound() {
        UUID id = UUID.randomUUID();

        when(listingService.getListingDetail(id)).thenReturn(Optional.empty());

        String message = featuredDecoratorService.removeFeaturedStatus(id);

        assertEquals("Listing with ID " + id.toString() + " not found", message);
    }

    @Test
    void testUpdateExpiredFeaturedStatus_Expired() {
        LocalDate currentDate = LocalDate.of(2024, 5, 7);
        LocalDate expiredDate = currentDate.minusDays(8);

        Listing featuredListing1 = new Listing(UUID.randomUUID(), "user1", "Listing 1", true, expiredDate);

        List<Listing> allListings = Arrays.asList(featuredListing1);

        when(listingService.getAllListings()).thenReturn(allListings);

        featuredDecoratorService.updateExpiredFeaturedStatus();

        assertFalse(featuredListing1.isFeaturedStatus());
        assertNull(featuredListing1.getExpirationDate());
    }

    @Test
    void testUpdateExpiredFeaturedStatus_NotExpired() {
        LocalDate currentDate = LocalDate.of(2024, 5, 7);
        LocalDate futureDate = currentDate.plusDays(2);

        Listing featuredListing2 = new Listing(UUID.randomUUID(), "user2", "Listing 2", true, futureDate);

        List<Listing> allListings = Arrays.asList(featuredListing2);

        when(listingService.getAllListings()).thenReturn(allListings);

        featuredDecoratorService.updateExpiredFeaturedStatus();

        assertTrue(featuredListing2.isFeaturedStatus());
        assertEquals(futureDate, featuredListing2.getExpirationDate());
    }

    @Test
    void testGetAllListingsSortedByFeatured() {
        LocalDate currentDate = LocalDate.now();

        Listing featuredListing1 = new Listing(UUID.randomUUID(), "user1", "Listing 1", true, currentDate.plusDays(5));
        Listing featuredListing2 = new Listing(UUID.randomUUID(), "user3", "Listing 3", true, currentDate.plusDays(3));

        Listing nonFeaturedListing = new Listing(UUID.randomUUID(), "user2", "Listing 2", true, currentDate.minusDays(8));

        List<Listing> allListings = new ArrayList<>();
        allListings.add(featuredListing1);
        allListings.add(nonFeaturedListing);
        allListings.add(featuredListing2);
        when(listingService.getAllListings()).thenReturn(allListings);

        List<Listing> sortedListings = featuredDecoratorService.getAllListingsSortedByFeatured();

        assertEquals(3, sortedListings.size());
        assertTrue(sortedListings.get(0).isFeaturedStatus());
        assertTrue(sortedListings.get(1).isFeaturedStatus());
        assertFalse(sortedListings.get(2).isFeaturedStatus());

        assertEquals("Listing 1", sortedListings.get(0).getName());
        assertEquals("Listing 3", sortedListings.get(1).getName());

        assertEquals("Listing 2", sortedListings.get(2).getName());
    }
}
