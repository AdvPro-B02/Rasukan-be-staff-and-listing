package advpro.b2.rasukanlsp.controller;

import advpro.b2.rasukanlsp.model.Listing;
import advpro.b2.rasukanlsp.service.FeaturedDecoratorService;
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
    private FeaturedDecoratorService listingService;

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
        UUID id = listings.keySet().iterator().next();
        when(listingService.getListingDetail(id)).thenReturn(Optional.of(listings.get(id)));

        String response = listingController.getListingDetail(UUID.fromString(id.toString()));

        assertNotNull(response);
        assertTrue(response.contains("ID: " + id.toString()));
        assertTrue(response.contains("User ID: user1"));
        assertTrue(response.contains("Name: Product 1"));
        assertTrue(response.contains("Featured: false"));
    }

    @Test
    public void testGetListingDetail_NotFound() {
        UUID id = UUID.randomUUID();
        when(listingService.getListingDetail(any(UUID.class))).thenReturn(Optional.empty());

        String response = listingController.getListingDetail(UUID.fromString(id.toString()));

        assertEquals("Listing with ID " + id.toString() + " not found", response);
    }


    @Test
    public void testMarkListingAsFeatured_Success() {
        UUID id = listings.keySet().iterator().next();
        when(listingService.markListingAsFeatured(eq(id), eq(true), any(LocalDate.class)))
                .thenReturn("Listing with ID " + id.toString() + " has been marked as featured");

        ResponseEntity<String> responseEntity = listingController.markListingAsFeatured(UUID.fromString(id.toString()), true);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals("Listing with ID " + id.toString() + " has been marked as featured", responseEntity.getBody());
    }

    @Test
    public void testMarkListingAsFeatured_ListingNotFound() {
        when(listingService.markListingAsFeatured(any(UUID.class), anyBoolean(), any(LocalDate.class)))
                .thenReturn(null);

        UUID id = UUID.randomUUID();
        ResponseEntity<String> responseEntity = listingController.markListingAsFeatured(UUID.fromString(id.toString()), true);

        assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());
        assertEquals("Listing with ID " + id.toString() + " not found", responseEntity.getBody());
    }

    @Test
    public void testRemoveFeaturedStatus_Success() {
        UUID id = listings.keySet().iterator().next();
        Listing listing = new Listing(id, "user1", "Product 1", true, null);
        listings.put(id, listing);
        when(listingService.removeFeaturedStatus(id))
                .thenReturn("Featured status has been removed from listing with ID " + id.toString());

        ResponseEntity<String> responseEntity = listingController.removeFeaturedStatus(UUID.fromString(id.toString()));

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals("Featured status has been removed from listing with ID " + id.toString(), responseEntity.getBody());
    }

    @Test
    public void testRemoveFeaturedStatus_ListingNotFeatured() {
        UUID id = UUID.randomUUID();
        Listing listing = new Listing(id, "user2", "Product 2", false, null);
        listings.put(id, listing);
        when(listingService.removeFeaturedStatus(id))
                .thenReturn("Listing with ID " + id.toString() + " is not a featured listing");

        ResponseEntity<String> responseEntity = listingController.removeFeaturedStatus(UUID.fromString(id.toString()));

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals("Listing with ID " + id.toString() + " is not a featured listing", responseEntity.getBody());
    }

    @Test
    public void testRemoveFeaturedStatus_ListingNotFound() {
        when(listingService.removeFeaturedStatus(any(UUID.class)))
                .thenReturn(null);

        UUID id = UUID.randomUUID();
        ResponseEntity<String> responseEntity = listingController.removeFeaturedStatus(UUID.fromString(id.toString()));

        assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());
        assertEquals("Listing with ID " + id.toString() + " not found", responseEntity.getBody());
    }

}
