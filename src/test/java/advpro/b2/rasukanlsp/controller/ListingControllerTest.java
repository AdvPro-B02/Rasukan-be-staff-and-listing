package advpro.b2.rasukanlsp.controller;

import advpro.b2.rasukanlsp.model.Listing;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import java.util.Optional;

import org.junit.jupiter.api.extension.ExtendWith;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class ListingControllerTest {

    @InjectMocks
    private ListingController listingController;

    @Test
    void testGetListingDetail() {
        String listingId = "1";
        Listing dummyListing = new Listing(listingId, "user1", "Product 1", "Description of Product 1", false, null);

        ResponseEntity<Optional<Listing>> response = listingController.getListingDetail(listingId);

        assertEquals(HttpStatus.OK, response.getStatusCode());

        Listing actualListing = response.getBody().orElse(null);
        assertNotNull(actualListing);
        assertEquals(dummyListing.getId(), actualListing.getId());
        assertEquals(dummyListing.getUserId(), actualListing.getUserId());
        assertEquals(dummyListing.getName(), actualListing.getName());
        assertEquals(dummyListing.getDescription(), actualListing.getDescription());
        assertEquals(dummyListing.isFeaturedStatus(), actualListing.isFeaturedStatus());
        assertEquals(dummyListing.getExpirationDate(), actualListing.getExpirationDate());
    }

    @Test
    void testMarkListingAsFeatured() {
        String listingId = "1";
        boolean status = true;

        ResponseEntity<Void> response = listingController.markListingAsFeatured(listingId, status);

        assertEquals(HttpStatus.OK, response.getStatusCode());

        Listing updatedListing = listingController.getListingDetail(listingId).getBody().orElse(null);
        assertNotNull(updatedListing);
        assertEquals(status, updatedListing.isFeaturedStatus());
    }

    @Test
    void testRemoveFeaturedStatus() {
        String listingId = "1";

        ResponseEntity<Void> response = listingController.removeFeaturedStatus(listingId);

        assertEquals(HttpStatus.OK, response.getStatusCode());

        Listing updatedListing = listingController.getListingDetail(listingId).getBody().orElse(null);
        assertNotNull(updatedListing);
        assertFalse(updatedListing.isFeaturedStatus());
    }
}
