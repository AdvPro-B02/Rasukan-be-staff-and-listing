package advpro.b2.rasukanlsp.repository;

import advpro.b2.rasukanlsp.model.Listing;
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
public class ListingRepositoryTest {

    @Mock
    private ListingRepository listingRepository;

    @InjectMocks
    private ListingServiceImpl listingService;

    @Test
    public void testFindById() {
        UUID id = UUID.randomUUID();
        Listing listing = new Listing(id, "user123", "Test Listing", false, null);
        when(listingRepository.findById(id)).thenReturn(Optional.of(listing));

        Optional<Listing> result = listingService.getListingDetail(id);

        assertEquals(listing, result.get());
    }

    @Test
    public void testFindAll() {
        List<Listing> listings = new ArrayList<>();
        listings.add(new Listing(UUID.randomUUID(), "user123", "Listing 1", false, null));
        listings.add(new Listing(UUID.randomUUID(), "user456", "Listing 2", true, LocalDate.now().plusDays(5)));
        when(listingRepository.findAll()).thenReturn(listings);

        List<Listing> result = listingService.getAllListings();

        assertEquals(listings.size(), result.size());
        assertEquals(listings.get(0), result.get(0));
        assertEquals(listings.get(1), result.get(1));
    }

    @Test
    public void testSaveListing() {
        UUID id = UUID.randomUUID();
        Listing listing = new Listing(id, "user123", "Test Listing", false, null);

        listingService.saveListing(listing);

        verify(listingRepository, times(1)).save(listing);
    }
}

