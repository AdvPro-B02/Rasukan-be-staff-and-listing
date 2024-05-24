package advpro.b2.rasukanlsp.service;

import advpro.b2.rasukanlsp.model.FeaturedListing;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface ListingService {
    Optional<FeaturedListing> getListingDetail(UUID id);
    void saveListing(FeaturedListing listing);
    List<FeaturedListing> getAllListings();


}
