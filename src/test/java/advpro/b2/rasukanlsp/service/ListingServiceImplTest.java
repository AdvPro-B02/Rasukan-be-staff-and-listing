package advpro.b2.rasukanlsp.service;

import advpro.b2.rasukanlsp.model.Listing;
import advpro.b2.rasukanlsp.repository.ListingRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
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
        UUID id = UUID.randomUUID();
        Listing listing = new Listing();
        listing.setId(id);
        listing.setUserId("user123");
        listing.setName("Test Listing");
        listing.setFeaturedStatus(true);

        when(listingRepository.findById(any(UUID.class))).thenReturn(Optional.of(listing));

        Optional<Listing> result = listingService.getListingDetail(UUID.fromString(id.toString()));

        assertEquals(Optional.of(listing), result);
    }
}
