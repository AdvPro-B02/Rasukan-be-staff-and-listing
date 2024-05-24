package advpro.b2.rasukanlsp.service;

import advpro.b2.rasukanlsp.model.FeaturedListing;
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

//    @Mock
//    private ListingService listingService;
//
//    @InjectMocks
//    private FeaturedDecoratorServiceImpl featuredDecoratorService;
//
//    @BeforeEach
//    void setUp() {
//        MockitoAnnotations.initMocks(this);
//    }
//
//    @Test
//    void testMarkListingAsFeatured() {
//        UUID id = UUID.randomUUID();
//        boolean status = true;
//
//        FeaturedListing listing = new FeaturedListing();
//        listing.setListingId(id);
//        listing.setName("Test Listing");
//
//        when(listingService.getListingDetail(id)).thenReturn(Optional.of(listing));
//
//        String message = featuredDecoratorService.markListingAsFeatured(id, status, LocalDate.now());
//
//        assertEquals("Listing with ID " + id.toString() + " has been marked as featured", message);
//    }
//
//    @Test
//    void testMarkListingAsFeatured_NotFound() {
//        UUID id = UUID.randomUUID();
//        boolean status = true;
//
//        when(listingService.getListingDetail(id)).thenReturn(Optional.empty());
//
//        String message = featuredDecoratorService.markListingAsFeatured(id, status, LocalDate.now());
//
//        assertEquals("Listing with ID " + id.toString() + " not found", message);
//    }
//
//    @Test
//    void testRemoveFeaturedStatus() {
//        UUID id = UUID.randomUUID();
//
//        FeaturedListing listing = new FeaturedListing();
//        listing.setListingId(id);
//        listing.setName("Test Listing");
//        listing.setFeaturedStatus(true);
//
//        when(listingService.getListingDetail(id)).thenReturn(Optional.of(listing));
//
//        String message = featuredDecoratorService.removeFeaturedStatus(id, false, null);
//
//        assertEquals("Featured status has been removed from listing with ID " + id.toString(), message);
//    }
//
//    @Test
//    void testRemoveFeaturedStatus_NotFound() {
//        UUID id = UUID.randomUUID();
//
//        when(listingService.getListingDetail(id)).thenReturn(Optional.empty());
//
//        String message = featuredDecoratorService.removeFeaturedStatus(id, false, null);
//
//        assertEquals("Listing with ID " + id.toString() + " not found", message);
//    }
//
//    @Test
//    void testUpdateExpiredFeaturedStatus() {
//        LocalDate currentDate = LocalDate.now();
//        LocalDate expiredDate = currentDate.minusDays(8);
//
//        FeaturedListing featuredListing = new FeaturedListing();
//        featuredListing.setListingId(UUID.randomUUID());
//        featuredListing.setFeaturedStatus(true);
//        featuredListing.setExpirationDate(expiredDate);
//
//        List<FeaturedListing> allListings = Collections.singletonList(featuredListing);
//
//        when(listingService.getAllListings()).thenReturn(allListings);
//
//        featuredDecoratorService.updateExpiredFeaturedStatus();
//
//        assertFalse(featuredListing.isFeaturedStatus());
//        assertNull(featuredListing.getExpirationDate());
//    }
//
//    @Test
//    void testGetAllListingsSortedByFeatured() {
//        LocalDate currentDate = LocalDate.now();
//
//        FeaturedListing featuredListing1 = new FeaturedListing(UUID.randomUUID(), "Listing 1", true, currentDate.plusDays(5));
//        FeaturedListing featuredListing2 = new FeaturedListing(UUID.randomUUID(), "Listing 3", true, currentDate.plusDays(3));
//
//        FeaturedListing nonFeaturedListing = new FeaturedListing(UUID.randomUUID(),  "Listing 2", true, currentDate.minusDays(8));
//
//        List<FeaturedListing> allListings = new ArrayList<>();
//        allListings.add(featuredListing1);
//        allListings.add(nonFeaturedListing);
//        allListings.add(featuredListing2);
//        when(listingService.getAllListings()).thenReturn(allListings);
//
//        List<FeaturedListing> sortedListings = featuredDecoratorService.getAllListingsSortedByFeatured();
//
//        assertEquals(3, sortedListings.size());
//        assertTrue(sortedListings.get(0).isFeaturedStatus());
//        assertTrue(sortedListings.get(1).isFeaturedStatus());
//        assertFalse(sortedListings.get(2).isFeaturedStatus());
//
//        assertEquals("Listing 1", sortedListings.get(0).getName());
//        assertEquals("Listing 3", sortedListings.get(1).getName());
//        assertEquals("Listing 2", sortedListings.get(2).getName());
//    }
}
