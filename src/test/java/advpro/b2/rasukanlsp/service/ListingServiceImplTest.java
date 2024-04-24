package advpro.b2.rasukanlsp.service;

import advpro.b2.rasukanlsp.model.Listing;
import advpro.b2.rasukanlsp.repository.ListingRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

public class ListingServiceImplTest {

    @Mock
    private ListingRepository listingRepository;

    @InjectMocks
    private ListingServiceImpl listingService;

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

        Optional<Listing> result = listingService.getListingDetail(id);

        assertEquals(Optional.of(listing), result);
    }
}
