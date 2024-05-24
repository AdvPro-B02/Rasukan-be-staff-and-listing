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

import java.time.LocalDate;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;

public class StaffControllerTest {
//
//    @Mock
//    private FeaturedDecoratorService featuredService;
//
//    @InjectMocks
//    private StaffController listingController;
//
//    private Map<UUID, FeaturedListing> listings;
//
//    @BeforeEach
//    void setUp() {
//        MockitoAnnotations.initMocks(this);
//
//        // Dummy data
//        listings = new HashMap<>();
//        UUID id = UUID.randomUUID();
//        FeaturedListing listing = new FeaturedListing(id, "Product 1", false, null);
//        listings.put(id, listing);
//    }
//
//    @Test
//    public void testGetListingDetail_Success() {
//        UUID dummyId = UUID.randomUUID();
//        FeaturedListing dummyListing = new FeaturedListing(dummyId,  "Product 1", false, null);
//        when(featuredService.getListingDetail(dummyId)).thenReturn(Optional.of(dummyListing));
//
//        ResponseEntity<?> responseEntity = listingController.getListingDetail(dummyId.toString());
//
//        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
//        assertEquals(dummyListing, responseEntity.getBody());
//    }
//
//    @Test
//    public void testGetListingDetail_NotFound() {
//        UUID id = UUID.randomUUID();
//        when(featuredService.getListingDetail(any(UUID.class))).thenReturn(Optional.empty());
//
//        ResponseEntity<?> responseEntity = listingController.getListingDetail(id.toString());
//
//        assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());
//        assertEquals("Listing with ID " + id + " not found", responseEntity.getBody());
//    }
//
//    @Test
//    public void testMarkListingAsFeatured_Success() {
//        UUID id = listings.keySet().iterator().next();
//        when(featuredService.markListingAsFeatured(eq(id)))
//                .thenReturn("Listing with ID " + id + " has been marked as featured");
//
//        ResponseEntity<String> responseEntity = listingController.markListingAsFeatured(id.toString());
//
//        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
//        assertEquals("Listing with ID " + id + " has been marked as featured", responseEntity.getBody());
//    }
//
//    @Test
//    public void testMarkListingAsFeatured_ListingNotFound() {
//        UUID id = UUID.randomUUID();
//        when(featuredService.markListingAsFeatured(any(UUID.class)))
//                .thenReturn(null);
//
//        ResponseEntity<String> responseEntity = listingController.markListingAsFeatured(id.toString());
//
//        assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());
//        assertEquals("Listing with ID " + id + " not found", responseEntity.getBody());
//    }
//
//    @Test
//    public void testRemoveFeaturedStatus_Success() {
//        UUID id = listings.keySet().iterator().next();
//        FeaturedListing listing = listings.get(id);
//        listing.setFeaturedStatus(true);
//        when(featuredService.removeFeaturedStatus(id))
//                .thenReturn("Featured status has been removed from listing with ID " + id);
//
//        ResponseEntity<String> responseEntity = listingController.removeFeaturedStatus(id.toString());
//
//        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
//        assertEquals("Featured status has been removed from listing with ID " + id, responseEntity.getBody());
//    }
//
//    @Test
//    public void testRemoveFeaturedStatus_ListingNotFound() {
//        UUID id = UUID.randomUUID();
//        when(featuredService.removeFeaturedStatus(any(UUID.class)))
//                .thenReturn(null);
//
//        ResponseEntity<String> responseEntity = listingController.removeFeaturedStatus(id.toString());
//
//        assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());
//        assertEquals("Listing with ID " + id + " not found", responseEntity.getBody());
//    }

}
