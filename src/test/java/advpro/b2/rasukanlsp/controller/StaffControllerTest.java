package advpro.b2.rasukanlsp.controller;

import advpro.b2.rasukanlsp.model.FeaturedListing;
import advpro.b2.rasukanlsp.service.FeaturedDecoratorService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;

public class StaffControllerTest {

    @Mock
    private FeaturedDecoratorService featuredService;

    @InjectMocks
    private StaffController listingController;

    private Map<UUID, FeaturedListing> listings;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);

        listings = new HashMap<>();
        UUID id = UUID.randomUUID();
        FeaturedListing listing = new FeaturedListing(id, "Product 1", false, null);
        listings.put(id, listing);
    }

    @Test
    public void testGetListingDetail_Success() {
        UUID dummyId = UUID.randomUUID();
        FeaturedListing dummyListing = new FeaturedListing(dummyId, "Product 1", false, null);
        when(featuredService.getListingDetail(dummyId)).thenReturn(Optional.of(dummyListing));

        ResponseEntity<?> responseEntity = listingController.getListingDetail(dummyId.toString());

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(dummyListing, responseEntity.getBody());
    }

    @Test
    public void testGetListingDetail_NotFound() {
        UUID id = UUID.randomUUID();
        when(featuredService.getListingDetail(any(UUID.class))).thenReturn(Optional.empty());

        ResponseEntity<?> responseEntity = listingController.getListingDetail(id.toString());

        assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());
        assertEquals("Listing with ID " + id + " not found", responseEntity.getBody());
    }

    @Test
    public void testGetListingDetail_InvalidUUID() {
        String invalidUUID = "invalid-uuid";

        ResponseEntity<?> responseEntity = listingController.getListingDetail(invalidUUID);

        assertEquals(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());
        assertEquals("Invalid UUID format: " + invalidUUID, responseEntity.getBody());
    }

    @Test
    public void testGetListingDetail_InternalServerError() {
        UUID dummyId = UUID.randomUUID();
        doThrow(new RuntimeException("Unexpected error")).when(featuredService).getListingDetail(dummyId);

        ResponseEntity<?> responseEntity = listingController.getListingDetail(dummyId.toString());

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, responseEntity.getStatusCode());
        assertEquals("Internal Server Error", responseEntity.getBody());
    }

    @Test
    public void testMarkListingAsFeatured_Success() {
        UUID id = listings.keySet().iterator().next();
        when(featuredService.markListingAsFeatured(eq(id)))
                .thenReturn("Listing with ID " + id + " has been marked as featured");

        ResponseEntity<String> responseEntity = listingController.markListingAsFeatured(id.toString());

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals("Listing with ID " + id + " has been marked as featured", responseEntity.getBody());
    }

    @Test
    public void testMarkListingAsFeatured_ListingNotFound() {
        UUID id = UUID.randomUUID();
        when(featuredService.markListingAsFeatured(any(UUID.class)))
                .thenReturn(null);

        ResponseEntity<String> responseEntity = listingController.markListingAsFeatured(id.toString());

        assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());
        assertEquals("Listing with ID " + id + " not found", responseEntity.getBody());
    }

    @Test
    public void testMarkListingAsFeatured_InvalidUUID() {
        String invalidUUID = "invalid-uuid";

        ResponseEntity<String> responseEntity = listingController.markListingAsFeatured(invalidUUID);

        assertEquals(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());
        assertEquals("Invalid UUID format: " + invalidUUID, responseEntity.getBody());
    }

    @Test
    public void testRemoveFeaturedStatus_Success() {
        UUID id = listings.keySet().iterator().next();
        FeaturedListing listing = listings.get(id);
        listing.setFeaturedStatus(true);
        when(featuredService.removeFeaturedStatus(id))
                .thenReturn("Featured status has been removed from listing with ID " + id);

        ResponseEntity<String> responseEntity = listingController.removeFeaturedStatus(id.toString());

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals("Featured status has been removed from listing with ID " + id, responseEntity.getBody());
    }

    @Test
    public void testRemoveFeaturedStatus_ListingNotFound() {
        UUID id = UUID.randomUUID();
        when(featuredService.removeFeaturedStatus(any(UUID.class)))
                .thenReturn(null);

        ResponseEntity<String> responseEntity = listingController.removeFeaturedStatus(id.toString());

        assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());
        assertEquals("Listing with ID " + id + " not found", responseEntity.getBody());
    }

    @Test
    public void testRemoveFeaturedStatus_InvalidUUID() {
        String invalidUUID = "invalid-uuid";

        ResponseEntity<String> responseEntity = listingController.removeFeaturedStatus(invalidUUID);

        assertEquals(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());
        assertEquals("Invalid UUID format: " + invalidUUID, responseEntity.getBody());
    }

    @Test
    public void testGetFeaturedListings_Success() {
        List<FeaturedListing> dummyListings = new ArrayList<>(listings.values());
        when(featuredService.getFeaturedListings()).thenReturn(dummyListings);

        ResponseEntity<List<FeaturedListing>> responseEntity = listingController.getFeaturedListings();

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(dummyListings, responseEntity.getBody());
    }
}
