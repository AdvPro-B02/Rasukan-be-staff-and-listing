package advpro.b2.rasukanlsp.service;

import advpro.b2.rasukanlsp.model.Listing;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class FeaturedDecoratorServiceImpl implements FeaturedDecoratorService {

    private final ListingService listingService;
    public FeaturedDecoratorServiceImpl(@Qualifier("listingServiceImpl") ListingService listingService) {
        this.listingService = listingService;
    }
    @Override
    public Optional<Listing> getListingDetail(UUID id) {
        return listingService.getListingDetail(id);
    }

    @Override
    public void saveListing(Listing listing) {
        listingService.saveListing(listing);
    }

    @Override
    public List<Listing> getAllListings() {
        return listingService.getAllListings();
    }

    @Override
    public String markListingAsFeatured(UUID id, boolean status, LocalDate expirationDate) {
        Optional<Listing> optionalListing = getListingDetail(id);
        if (optionalListing.isPresent()) {
            Listing listing = optionalListing.get();
            listing.setFeaturedStatus(status);
            listing.setExpirationDate(status ? expirationDate : null);
            saveListing(listing);
            return "Listing with ID " + id + " has been marked as featured";
        } else {
            return "Listing with ID " + id + " not found";
        }
    }

    @Override
    public String removeFeaturedStatus(UUID id) {
        Optional<Listing> optionalListing = getListingDetail(id);
        if (optionalListing.isPresent()) {
            Listing listing = optionalListing.get();
            listing.setFeaturedStatus(false);
            listing.setExpirationDate(null);
            saveListing(listing);
            return "Featured status has been removed from listing with ID " + id;
        } else {
            return "Listing with ID " + id + " not found";
        }
    }

    @Override
    public List<Listing> getAllListingsSortedByFeatured() {
        List<Listing> allListings = getAllListings();

        allListings.forEach(listing -> {
            if (!listing.isFeaturedStatus() || (listing.getExpirationDate() != null && LocalDate.now().isAfter(listing.getExpirationDate()))) {
                listing.setFeaturedStatus(false);
                listing.setExpirationDate(null);
                saveListing(listing);
            }
        });

        List<Listing> sortedTrueListings = allListings.stream()
                .filter(Listing::isFeaturedStatus)
                .sorted(Comparator.comparing(Listing::getExpirationDate, Comparator.nullsLast(Comparator.naturalOrder())).reversed())
                .collect(Collectors.toList());

        List<Listing> falseListings = allListings.stream()
                .filter(listing -> !listing.isFeaturedStatus())
                .toList();

        sortedTrueListings.addAll(falseListings);

        return sortedTrueListings;
    }

    @Scheduled(fixedRate = 24 * 60 * 60 * 1000)
    @Override
    public void updateExpiredFeaturedStatus() {
        List<Listing> allListings = getAllListings();
        LocalDate currentDate = LocalDate.now();
        allListings.forEach(listing -> {
            if (listing.isFeaturedStatus() && listing.getExpirationDate() != null) {
                long days = ChronoUnit.DAYS.between(listing.getExpirationDate(), currentDate);
                if (days >= 7) {
                    listing.setFeaturedStatus(false);
                    listing.setExpirationDate(null);
                    saveListing(listing);
                }
            }
        });
    }

    @Override
    public List<Listing> getFeaturedListings() {
        List<Listing> allListings = getAllListings();
        allListings.forEach(listing -> {
            if (listing.isFeaturedStatus() && listing.getExpirationDate() != null && LocalDate.now().isAfter(listing.getExpirationDate())) {
                listing.setFeaturedStatus(false);
                listing.setExpirationDate(null);
                saveListing(listing);
            }
        });

        List<Listing> featuredListings = allListings.stream()
                .filter(Listing::isFeaturedStatus)
                .collect(Collectors.toList());

        featuredListings.sort(Comparator.comparing(Listing::getExpirationDate, Comparator.nullsLast(Comparator.naturalOrder())).reversed());

        return featuredListings;
    }
}
