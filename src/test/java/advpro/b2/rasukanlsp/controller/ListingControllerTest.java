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

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;

public class ListingControllerTest {

    @Mock
    private FeaturedDecoratorService listingService;

    @InjectMocks
    private ListingController listingController;

    private Map<String, Listing> listings;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);

        listings = new HashMap<>();
        Listing listing = new Listing("1", "user1", "Product 1", "Description of Product 1", false, null);
        listings.put(listing.getId(), listing);
    }

    @Test
    public void testGetListingDetail_Success() {
        when(listingService.getListingDetail("1")).thenReturn(Optional.of(listings.get("1")));

        String response = listingController.getListingDetail("1");

        assertNotNull(response);
        assertTrue(response.contains("ID: 1"));
        assertTrue(response.contains("User ID: user1"));
        assertTrue(response.contains("Name: Product 1"));
        assertTrue(response.contains("Description: Description of Product 1"));
        assertTrue(response.contains("Featured: false"));
    }


    @Test
    public void testGetListingDetail_NotFound() {
        when(listingService.getListingDetail(anyString())).thenReturn(Optional.empty());

        String response = listingController.getListingDetail("999");

        assertEquals("Listing with ID 999 not found", response);
    }


    @Test
    public void testMarkListingAsFeatured_Success() {
        when(listingService.markListingAsFeatured(eq("1"), eq(true), any(LocalDate.class)))
                .thenReturn("Listing with ID 1 has been marked as featured");

        ResponseEntity<String> responseEntity = listingController.markListingAsFeatured("1", true);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals("Listing with ID 1 has been marked as featured", responseEntity.getBody());
    }

    @Test
    public void testMarkListingAsFeatured_ListingNotFound() {
        when(listingService.markListingAsFeatured(anyString(), anyBoolean(), any(LocalDate.class)))
                .thenReturn(null);

        ResponseEntity<String> responseEntity = listingController.markListingAsFeatured("999", true);

        assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());
        assertEquals("Listing with ID 999 not found", responseEntity.getBody());
    }

    @Test
    public void testRemoveFeaturedStatus_Success() {
        Listing listing = new Listing("1", "user1", "Product 1", "Description of Product 1", true, null);
        listings.put(listing.getId(), listing);
        when(listingService.removeFeaturedStatus("1"))
                .thenReturn("Featured status has been removed from listing with ID 1");

        ResponseEntity<String> responseEntity = listingController.removeFeaturedStatus("1");

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals("Featured status has been removed from listing with ID 1", responseEntity.getBody());
    }

    @Test
    public void testRemoveFeaturedStatus_ListingNotFeatured() {
        Listing listing = new Listing("2", "user2", "Product 2", "Description of Product 2", false, null);
        listings.put(listing.getId(), listing);
        when(listingService.removeFeaturedStatus("2"))
                .thenReturn("Listing with ID 2 is not a featured listing");

        ResponseEntity<String> responseEntity = listingController.removeFeaturedStatus("2");

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals("Listing with ID 2 is not a featured listing", responseEntity.getBody());
    }


    @Test
    public void testRemoveFeaturedStatus_ListingNotFound() {
        when(listingService.removeFeaturedStatus("999"))
                .thenReturn(null);

        ResponseEntity<String> responseEntity = listingController.removeFeaturedStatus("999");

        assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());
        assertEquals("Listing with ID 999 not found", responseEntity.getBody());
    }
}
