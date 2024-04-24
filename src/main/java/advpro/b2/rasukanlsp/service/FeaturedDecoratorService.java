package advpro.b2.rasukanlsp.service;

import advpro.b2.rasukanlsp.model.Listing;

import java.time.LocalDate;
import java.util.List;

public interface FeaturedDecoratorService extends ListingService {
    String markListingAsFeatured(String id, boolean status, LocalDate expirationDate);
    String removeFeaturedStatus(String id);
    List<Listing> getAllListingsSortedByFeatured();
    void updateExpiredFeaturedStatus();

}
