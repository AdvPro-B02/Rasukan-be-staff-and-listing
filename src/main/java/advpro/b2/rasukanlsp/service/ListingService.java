package advpro.b2.rasukanlsp.service;

import advpro.b2.rasukanlsp.model.Listing;

import java.util.Optional;
import java.util.UUID;


public interface ListingService {
    Optional<Listing> getListingDetail(UUID id);
}