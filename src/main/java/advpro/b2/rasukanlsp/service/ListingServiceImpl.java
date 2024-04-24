package advpro.b2.rasukanlsp.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Optional;

import advpro.b2.rasukanlsp.model.Listing;
import advpro.b2.rasukanlsp.repository.ListingRepository;

@Service
public class ListingServiceImpl implements ListingService {

    @Autowired
    private ListingRepository listingRepository;

    @Override
    public Optional<Listing> getListingDetail(String id) {
        Optional<Listing> optionalListing = listingRepository.findById(id);
        if (optionalListing.isPresent()) {
            Listing listing = optionalListing.get();
            if (listing.isFeaturedStatus() && listing.getExpirationDate() != null && LocalDate.now().isAfter(listing.getExpirationDate())) {
                listing.setFeaturedStatus(false);
                listing.setExpirationDate(null);
                listingRepository.save(listing);
            }
            return Optional.of(listing);
        } else {
            return Optional.empty();
        }
    }
}