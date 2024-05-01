package advpro.b2.rasukanlsp.service;

import advpro.b2.rasukanlsp.model.Listing;
import advpro.b2.rasukanlsp.repository.ListingRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDate;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;

public class FeaturedDecoratorServiceImplTest {

    @Mock
    private ListingRepository listingRepository;

    @InjectMocks
    private FeaturedDecoratorServiceImpl featuredDecoratorService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void testGetListingDetail() {
        UUID id = UUID.randomUUID();
        Listing listing = new Listing();
        listing.setId(id);
        listing.setUserId("user123");
        listing.setName("Test Listing");
        listing.setFeaturedStatus(true);

        when(listingRepository.findById(any(UUID.class))).thenReturn(Optional.of(listing));

        Optional<Listing> result = featuredDecoratorService.getListingDetail(id);

        assertEquals(Optional.of(listing), result);
    }

    @Test
    void testMarkListingAsFeatured() {
        UUID id = UUID.randomUUID();
        boolean status = true;

        when(listingRepository.findById(eq(id))).thenReturn(Optional.of(new Listing()));

        String message = featuredDecoratorService.markListingAsFeatured(id, status, LocalDate.now());

        assertEquals("Listing with ID " + id.toString() + " has been marked as featured", message);
    }

    @Test
    void testRemoveFeaturedStatus() {
        UUID id = UUID.randomUUID();
        Listing listing = new Listing();
        listing.setId(id);
        listing.setUserId("user123");
        listing.setName("Test Listing");
        listing.setFeaturedStatus(true);

        when(listingRepository.findById(eq(id))).thenReturn(Optional.of(listing));

        String message = featuredDecoratorService.removeFeaturedStatus(id);

        assertEquals("Featured status has been removed from listing with ID " + id.toString(), message);
    }
}
