package advpro.b2.rasukanlsp.service;

import advpro.b2.rasukanlsp.model.FeaturedListing;
import advpro.b2.rasukanlsp.model.Listing;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDate;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class FeaturedDecoratorServiceImplTest {

    @Mock
    private ListingService listingService;

    @Mock
    private RestTemplate restTemplate;

    @InjectMocks
    private FeaturedDecoratorServiceImpl featuredDecoratorService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testMarkListingAsFeatured() {
        UUID id = UUID.randomUUID();

        Listing mockListing = new Listing(id, "Test Listing", 10, 100, UUID.randomUUID(), 5);

        when(restTemplate.getForEntity(anyString(), eq(Listing.class)))
                .thenReturn(new ResponseEntity<>(mockListing, HttpStatus.OK));

        String message = featuredDecoratorService.markListingAsFeatured(id);

        assertEquals("Listing with ID " + id.toString() + " has been marked as featured", message);
    }

    @Test
    void testMarkListingAsFeatured_NotFound() {
        UUID id = UUID.randomUUID();

        // Stub the RestTemplate to return NOT_FOUND response
        when(restTemplate.getForEntity(anyString(), eq(Listing.class)))
                .thenReturn(new ResponseEntity<>(HttpStatus.NOT_FOUND));

        String message = featuredDecoratorService.markListingAsFeatured(id);

        assertEquals("Listing with ID " + id.toString() + " not found", message);
    }

    @Test
    void testRemoveFeaturedStatus() {
        UUID id = UUID.randomUUID();

        FeaturedListing listing = new FeaturedListing();
        listing.setListingId(id);
        listing.setName("Test Listing");
        listing.setFeaturedStatus(true);

        when(listingService.getListingDetail(id)).thenReturn(Optional.of(listing));

        String message = featuredDecoratorService.removeFeaturedStatus(id);

        assertEquals("Featured status has been removed from listing with ID " + id.toString(), message);
    }

    @Test
    void testRemoveFeaturedStatus_NotFound() {
        UUID id = UUID.randomUUID();

        when(listingService.getListingDetail(id)).thenReturn(Optional.empty());

        String message = featuredDecoratorService.removeFeaturedStatus(id);

        assertEquals("Listing with ID " + id.toString() + " not found", message);
    }

    @Test
    void testUpdateExpiredFeaturedStatus() {
        LocalDate currentDate = LocalDate.now();
        LocalDate expiredDate = currentDate.minusDays(8);

        FeaturedListing featuredListing = new FeaturedListing();
        featuredListing.setListingId(UUID.randomUUID());
        featuredListing.setFeaturedStatus(true);
        featuredListing.setExpirationDate(expiredDate);

        List<FeaturedListing> allListings = Collections.singletonList(featuredListing);

        when(listingService.getAllListings()).thenReturn(allListings);

        featuredDecoratorService.updateExpiredFeaturedStatus();

        assertFalse(featuredListing.isFeaturedStatus());
        assertNull(featuredListing.getExpirationDate());
    }
}
