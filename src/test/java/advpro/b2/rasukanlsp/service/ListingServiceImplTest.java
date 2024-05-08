package advpro.b2.rasukanlsp.service;

import advpro.b2.rasukanlsp.model.Listing;
import advpro.b2.rasukanlsp.repository.ListingRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

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
    void testGetListingDetail_HappyPath() {
        UUID id = UUID.randomUUID();
        Listing listing = new Listing();
        listing.setId(id);
        listing.setUserId("user123");
        listing.setName("Test Listing");
        listing.setFeaturedStatus(true);

        when(listingRepository.findById(any(UUID.class))).thenReturn(Optional.of(listing));

        Optional<Listing> result = listingService.getListingDetail(id);

        assertTrue(result.isPresent());
        assertEquals(listing, result.get());
    }

    @Test
    void testGetListingDetail_ListingNotFound() {
        UUID id = UUID.randomUUID();

        when(listingRepository.findById(any(UUID.class))).thenReturn(Optional.empty());

        Optional<Listing> result = listingService.getListingDetail(id);

        assertFalse(result.isPresent());
    }

    @Test
    void testSaveListing() {
        Listing listing = new Listing();
        listing.setId(UUID.randomUUID());
        listing.setUserId("user123");
        listing.setName("Test Listing");
        listing.setFeaturedStatus(true);

        when(listingRepository.save(any(Listing.class))).thenReturn(listing);

        listingService.saveListing(listing);
    }

    @Test
    void testGetAllListings_HappyPath() {
        List<Listing> listings = new ArrayList<>();
        listings.add(new Listing(UUID.randomUUID(), "user1", "Listing 1", true, LocalDate.now()));
        listings.add(new Listing(UUID.randomUUID(), "user2", "Listing 2", true, LocalDate.now()));
        listings.add(new Listing(UUID.randomUUID(), "user3", "Listing 3", true, LocalDate.now()));

        when(listingRepository.findAll()).thenReturn(listings);

        List<Listing> result = listingService.getAllListings();

        assertEquals(3, result.size());
        assertTrue(result.containsAll(listings));
    }

    @Test
    void testGetAllListings_EmptyList() {
        when(listingRepository.findAll()).thenReturn(new ArrayList<>());

        List<Listing> result = listingService.getAllListings();

        assertTrue(result.isEmpty());
    }
}