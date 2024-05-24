package advpro.b2.rasukanlsp.service;

import advpro.b2.rasukanlsp.model.FeaturedListing;
import advpro.b2.rasukanlsp.model.Listing;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

public interface FeaturedDecoratorService extends ListingService {
    String markListingAsFeatured(UUID id);
    String removeFeaturedStatus(UUID id);
    void updateExpiredFeaturedStatus();

    List<FeaturedListing> getFeaturedListings();

    Listing fetchListingDetail(String listingId);
    List<Listing> fetchAllListings();

}
