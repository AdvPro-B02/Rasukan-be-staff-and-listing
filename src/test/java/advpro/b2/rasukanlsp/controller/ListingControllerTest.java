package advpro.b2.rasukanlsp.controller;

import advpro.b2.rasukanlsp.model.Listing;
import advpro.b2.rasukanlsp.service.FeaturedDecoratorService;
import advpro.b2.rasukanlsp.service.ListingService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;

public class ListingControllerTest {

    @Mock
    private FeaturedDecoratorService featuredService;

    @Mock
    private ListingService listingService;

    @InjectMocks
    private ListingController listingController;

    private Map<UUID, Listing> listings;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);

        listings = new HashMap<>();
        UUID id = UUID.randomUUID();
        Listing listing = new Listing(id, "user1", "Product 1", false, null);
        listings.put(id, listing);
    }

    @Test
    public void testGetListingDetail_Success() {
        UUID dummyId = UUID.randomUUID();;
        Listing dummyListing = new Listing(dummyId, "user1", "Product 1", false, null);
        when(featuredService.getListingDetail(dummyId)).thenReturn(Optional.of(dummyListing));

        ResponseEntity<Listing> responseEntity = listingController.getListingDetail(dummyId);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(dummyListing, responseEntity.getBody());
    }


    @Test
    public void testGetListingDetail_NotFound() {
        UUID id = UUID.randomUUID();
        when(listingService.getListingDetail(any(UUID.class))).thenReturn(Optional.empty());

        ResponseEntity<Listing> responseEntity = listingController.getListingDetail(id);

        assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());
        assertNull(responseEntity.getBody());
    }

    @Test
    public void testMarkListingAsFeatured_Success() {
        UUID id = listings.keySet().iterator().next();
        when(featuredService.markListingAsFeatured(eq(id), eq(true), any(LocalDate.class)))
                .thenReturn("Listing with ID " + id + " has been marked as featured");

        ResponseEntity<String> responseEntity = listingController.markListingAsFeatured(id, true);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals("Listing with ID " + id + " has been marked as featured", responseEntity.getBody());
    }

    @Test
    public void testMarkListingAsFeatured_ListingNotFound() {
        UUID id = UUID.randomUUID();
        when(featuredService.markListingAsFeatured(any(UUID.class), anyBoolean(), any(LocalDate.class)))
                .thenReturn(null);

        ResponseEntity<String> responseEntity = listingController.markListingAsFeatured(id, true);

        assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());
        assertEquals("Listing with ID " + id + " not found", responseEntity.getBody());
    }

    @Test
    public void testRemoveFeaturedStatus_Success() {
        UUID id = listings.keySet().iterator().next();
        Listing listing = listings.get(id);
        listing.setFeaturedStatus(true);
        when(featuredService.removeFeaturedStatus(id))
                .thenReturn("Featured status has been removed from listing with ID " + id);

        ResponseEntity<String> responseEntity = listingController.removeFeaturedStatus(id);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals("Featured status has been removed from listing with ID " + id, responseEntity.getBody());
    }

    @Test
    public void testRemoveFeaturedStatus_ListingNotFound() {
        UUID id = UUID.randomUUID();
        when(featuredService.removeFeaturedStatus(any(UUID.class)))
                .thenReturn(null);

        ResponseEntity<String> responseEntity = listingController.removeFeaturedStatus(id);

        assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());
        assertEquals("Listing with ID " + id + " not found", responseEntity.getBody());
    }
}