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

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
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
        String id = "123";
        Listing listing = new Listing();
        listing.setId(id);
        listing.setUserId("user123");
        listing.setName("Test Listing");
        listing.setDescription("Test Description");
        listing.setFeaturedStatus(true);

        when(listingRepository.findById(anyString())).thenReturn(Optional.of(listing));

        Optional<Listing> result = featuredDecoratorService.getListingDetail(id);

        assertEquals(Optional.of(listing), result);
    }

    @Test
    void testMarkListingAsFeatured() {
        String id = "123";
        boolean status = true;

        when(listingRepository.findById(anyString())).thenReturn(Optional.of(new Listing()));

        String message = featuredDecoratorService.markListingAsFeatured(id, status, LocalDate.now());

        assertEquals("Listing with ID 123 has been marked as featured", message);
    }

    @Test
    void testRemoveFeaturedStatus() {
        String id = "123";
        Listing listing = new Listing();
        listing.setId(id);
        listing.setUserId("user123");
        listing.setName("Test Listing");
        listing.setDescription("Test Description");
        listing.setFeaturedStatus(true);

        when(listingRepository.findById(anyString())).thenReturn(Optional.of(listing));

        String message = featuredDecoratorService.removeFeaturedStatus(id);

        assertEquals("Featured status has been removed from listing with ID 123", message);
    }
}
