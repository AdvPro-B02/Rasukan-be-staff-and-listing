package advpro.b2.rasukanlsp.repository;

import advpro.b2.rasukanlsp.model.FeaturedListing;
import advpro.b2.rasukanlsp.service.ListingServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class FeaturedListingRepositoryTest {

    @Mock
    private FeaturedListingRepository featuredListingRepository;

    @InjectMocks
    private ListingServiceImpl listingService;

    @Test
    public void testFindById() {
        UUID id = UUID.randomUUID();
        FeaturedListing listing = new FeaturedListing(id, "Test Listing", false, null);
        when(featuredListingRepository.findById(id)).thenReturn(Optional.of(listing));

        Optional<FeaturedListing> result = listingService.getListingDetail(id);

        assertEquals(listing, result.get());
    }

    @Test
    public void testFindAll() {
        List<FeaturedListing> listings = new ArrayList<>();
        listings.add(new FeaturedListing(UUID.randomUUID(), "Listing 1", false, null));
        listings.add(new FeaturedListing(UUID.randomUUID(), "Listing 2", true, LocalDate.now().plusDays(5)));
        when(featuredListingRepository.findAll()).thenReturn(listings);

        List<FeaturedListing> result = listingService.getAllListings();

        assertEquals(listings.size(), result.size());
        assertEquals(listings.get(0), result.get(0));
        assertEquals(listings.get(1), result.get(1));
    }

    @Test
    public void testSaveListing() {
        UUID id = UUID.randomUUID();
        FeaturedListing listing = new FeaturedListing(id, "Test Listing", false, null);

        listingService.saveListing(listing);

        verify(featuredListingRepository, times(1)).save(listing);
    }
}


