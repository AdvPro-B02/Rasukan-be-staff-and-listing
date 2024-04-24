package advpro.b2.rasukanlsp.service;

import advpro.b2.rasukanlsp.model.Listing;

import java.util.Optional;


public interface ListingService {
    Optional<Listing> getListingDetail(String id);
}