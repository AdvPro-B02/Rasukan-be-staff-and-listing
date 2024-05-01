package advpro.b2.rasukanlsp.service;

import advpro.b2.rasukanlsp.model.Listing;
import advpro.b2.rasukanlsp.repository.ListingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class FeaturedDecoratorServiceImpl implements FeaturedDecoratorService {

    @Autowired
    private ListingRepository listingRepository;

    @Override
    public Optional<Listing> getListingDetail(UUID id) {
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

    @Override
    public String markListingAsFeatured(UUID id, boolean status, LocalDate expirationDate) {
        Optional<Listing> optionalListing = listingRepository.findById(id);
        if (optionalListing.isPresent()) {
            Listing listing = optionalListing.get();
            listing.setFeaturedStatus(status);
            listing.setExpirationDate(expirationDate);
            listingRepository.save(listing);
            return "Listing with ID " + id + " has been marked as featured";
        } else {
            return "Listing with ID " + id + " not found";
        }
    }

    @Override
    public String removeFeaturedStatus(UUID id) {
        Optional<Listing> optionalListing = listingRepository.findById(id);
        if (optionalListing.isPresent()) {
            Listing listing = optionalListing.get();
            listing.setFeaturedStatus(false);
            listing.setExpirationDate(null);
            listingRepository.save(listing);
            return "Featured status has been removed from listing with ID " + id;
        } else {
            return "Listing with ID " + id + " not found";
        }
    }
    public List<Listing> getAllListingsSortedByFeatured() {
        List<Listing> allListings = listingRepository.findAll();
        List<Listing> sortedListings = allListings.stream()
                .sorted(Comparator.comparing(Listing::isFeaturedStatus).reversed())
                .collect(Collectors.toList());
        return sortedListings;
    }
    @Override
    public void updateExpiredFeaturedStatus() {
        List<Listing> allListings = listingRepository.findAll();
        LocalDate currentDate = LocalDate.now();
        allListings.forEach(listing -> {
            if (listing.isFeaturedStatus() && listing.getExpirationDate() != null && currentDate.isAfter(listing.getExpirationDate())) {
                listing.setFeaturedStatus(false);
                listing.setExpirationDate(null);
                listingRepository.save(listing);
            }
        });
    }

    @Override
    public List<Listing> getFeaturedListings() {
        List<Listing> allListings = listingRepository.findAll();

        List<Listing> featuredListings = allListings.stream()
                .filter(Listing::isFeaturedStatus)
                .sorted(Comparator.comparing(Listing::getExpirationDate).reversed())
                .collect(Collectors.toList());

        return featuredListings;
    }
}
