package advpro.b2.rasukanlsp.service;

import advpro.b2.rasukanlsp.model.FeaturedListing;
import advpro.b2.rasukanlsp.model.Listing;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
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
    void testFetchAllListings() {
        String url = "http://34.87.180.11/Buyer/listing/all";
        Listing listing1 = new Listing();
        listing1.setListingId(UUID.randomUUID());
        listing1.setName("Listing1");

        Listing listing2 = new Listing();
        listing2.setListingId(UUID.randomUUID());
        listing2.setName("Listing2");

        List<Listing> mockListings = Arrays.asList(listing1, listing2);
        ResponseEntity<List<Listing>> responseEntity = new ResponseEntity<>(mockListings, HttpStatus.OK);

        when(restTemplate.exchange(url, HttpMethod.GET, null, new ParameterizedTypeReference<List<Listing>>() {}))
                .thenReturn(responseEntity);

        List<Listing> listings = featuredDecoratorService.fetchAllListings();

        assertEquals(2, listings.size());
        assertEquals("Listing1", listings.get(0).getName());
        assertEquals("Listing2", listings.get(1).getName());
    }


    @Test
    void testGetFeaturedListings() {
        FeaturedListing featuredListing1 = new FeaturedListing(UUID.randomUUID(), "Listing1", true, LocalDate.now().plusDays(3));
        FeaturedListing featuredListing2 = new FeaturedListing(UUID.randomUUID(), "Listing2", true, LocalDate.now().plusDays(1));
        FeaturedListing nonFeaturedListing = new FeaturedListing(UUID.randomUUID(), "Listing3", false, null);

        List<FeaturedListing> mockListings = Arrays.asList(featuredListing1, featuredListing2, nonFeaturedListing);

        when(listingService.getAllListings()).thenReturn(mockListings);

        List<FeaturedListing> featuredListings = featuredDecoratorService.getFeaturedListings();

        assertEquals(2, featuredListings.size());
        assertEquals("Listing1", featuredListings.get(0).getName());
        assertEquals("Listing2", featuredListings.get(1).getName());
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

    @Test
    void testFetchAllListings_FailedRequest() {
        String listingAllApiUrl = "http://34.87.180.11/Buyer/listing/all";
        when(restTemplate.exchange(eq(listingAllApiUrl), eq(HttpMethod.GET), any(), eq(new ParameterizedTypeReference<List<Listing>>() {})))
                .thenThrow(new RuntimeException("Failed to fetch all listings from Supabase."));

        assertThrows(RuntimeException.class, () -> featuredDecoratorService.fetchAllListings());
    }

}
