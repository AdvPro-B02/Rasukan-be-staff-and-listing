package advpro.b2.rasukanlsp.service;

import advpro.b2.rasukanlsp.model.FeaturedListing;
import advpro.b2.rasukanlsp.repository.FeaturedListingRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.ArgumentMatchers.eq;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ListingServiceImplTest {

    @Mock
    private FeaturedListingRepository featuredListingRepository;

    @InjectMocks
    private ListingServiceImpl listingService;

    @Test
    void testGetListingDetail_HappyPath() {
        UUID id = UUID.randomUUID();
        FeaturedListing listing = new FeaturedListing();
        listing.setListingId(id);
        listing.setName("Test Listing");
        listing.setFeaturedStatus(true);

        when(featuredListingRepository.findById(any(UUID.class))).thenReturn(Optional.of(listing));

        Optional<FeaturedListing> result = listingService.getListingDetail(id);

        assertTrue(result.isPresent());
        assertEquals(listing, result.get());
    }

    @Test
    void testGetListingDetail_ListingNotFound() {
        UUID id = UUID.randomUUID();

        when(featuredListingRepository.findById(any(UUID.class))).thenReturn(Optional.empty());

        Optional<FeaturedListing> result = listingService.getListingDetail(id);

        assertFalse(result.isPresent());
    }

    @Test
    void testSaveListing() {
        FeaturedListing listing = new FeaturedListing();
        listing.setListingId(UUID.randomUUID());
        listing.setName("Test Listing");
        listing.setFeaturedStatus(true);

        when(featuredListingRepository.save(any(FeaturedListing.class))).thenReturn(listing);

        listingService.saveListing(listing);

        verify(featuredListingRepository, times(1)).save(eq(listing));
    }

    @Test
    void testGetAllListings_HappyPath() {
        List<FeaturedListing> listings = new ArrayList<>();
        listings.add(new FeaturedListing(UUID.randomUUID(),  "Listing 1", true, null));
        listings.add(new FeaturedListing(UUID.randomUUID(), "Listing 2", true, null));
        listings.add(new FeaturedListing(UUID.randomUUID(),  "Listing 3", true, null));

        when(featuredListingRepository.findAll()).thenReturn(listings);

        List<FeaturedListing> result = listingService.getAllListings();

        assertEquals(3, result.size());
        assertTrue(result.containsAll(listings));
    }

    @Test
    void testGetAllListings_EmptyList() {
        when(featuredListingRepository.findAll()).thenReturn(new ArrayList<>());

        List<FeaturedListing> result = listingService.getAllListings();

        assertTrue(result.isEmpty());
    }

    @Test
    void testRemoveListingById() {
        UUID listingId = UUID.randomUUID();

        listingService.removeListingById(listingId);

        verify(featuredListingRepository, times(1)).deleteById(listingId);
    }

}
