package advpro.b2.rasukanlsp.service;

import advpro.b2.rasukanlsp.model.Listing;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface ListingService {
    Optional<Listing> getListingDetail(UUID id);
    void saveListing(Listing listing);
    List<Listing> getAllListings();
}
