package advpro.b2.rasukanlsp.service;

import advpro.b2.rasukanlsp.model.FeaturedListing;
import advpro.b2.rasukanlsp.model.Listing;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class FeaturedDecoratorServiceImpl implements FeaturedDecoratorService {
    private final String listingAllApiUrl = "http://34.87.180.11/Buyer/listing/all";
    private final String listingApiBaseUrl = "http://34.87.180.11/Buyer/listing/get/";
    private final String LISTING_ID_MSG = "Listing with ID ";
    private final RestTemplate restTemplate;
    private final ListingService listingService;

    public FeaturedDecoratorServiceImpl(@Qualifier("listingServiceImpl") ListingService listingService, RestTemplate restTemplate) {
        this.listingService = listingService;
        this.restTemplate = restTemplate;
    }

    @Override
    public Optional<FeaturedListing> getListingDetail(UUID listingId) {
        return listingService.getListingDetail(listingId);
    }

    @Override
    public void saveListing(FeaturedListing listing) {
        listingService.saveListing(listing);
    }

    @Override
    public List<FeaturedListing> getAllListings() {
        return listingService.getAllListings();
    }

    @Override
    public void removeListingById(UUID listingId) {
        listingService.removeListingById(listingId);
    }

    private static class ListingDetailFetchException extends RuntimeException {
        public ListingDetailFetchException(String message) {
            super(message);
        }
    }

    @Override
    public Listing fetchListingDetail(String listingId) {
        String url = listingApiBaseUrl + listingId;
        ResponseEntity<Listing> response = restTemplate.getForEntity(url, Listing.class);
        if (response.getStatusCode() == HttpStatus.OK) {
            return response.getBody();
        } else {
            throw new ListingDetailFetchException("Failed to fetch listing detail from Supabase.");
        }
    }
    @Override
    public List<Listing> fetchAllListings() {
        String url = listingAllApiUrl;
        ResponseEntity<List<Listing>> response = restTemplate.exchange(
                url,
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<List<Listing>>() {});

        if (response.getStatusCode() == HttpStatus.OK) {
            return response.getBody();
        } else {
            throw new RuntimeException("Failed to fetch all listings from Supabase.");
        }
    }

    @Override
    public String markListingAsFeatured(UUID listingId) {
        try {
            Listing supabaseListing = fetchListingDetail(listingId.toString());

            FeaturedListing featuredListing = new FeaturedListing(
                    supabaseListing.getListingId(),
                    supabaseListing.getName(),
                    true,
                    LocalDate.now().plusDays(7)
            );
            saveListing(featuredListing);
            return LISTING_ID_MSG + listingId + " has been marked as featured";
        } catch (RuntimeException e) {
            return LISTING_ID_MSG + listingId + " not found";
        }
    }

    @Override
    public String removeFeaturedStatus(UUID listingId) {
        Optional<FeaturedListing> optionalFeaturedListing = getListingDetail(listingId);

        if (optionalFeaturedListing.isPresent()) {
            removeListingById(listingId);
            return "Featured status has been removed from listing with ID " + listingId;
        } else {
            return LISTING_ID_MSG + listingId + " not found";
        }
    }

    @Scheduled(fixedRate = 24 * 60 * 60 * 1000)
    @Override
    public void updateExpiredFeaturedStatus() {
        List<FeaturedListing> allListings = getAllListings();
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
    public List<FeaturedListing> getFeaturedListings() {
        List<FeaturedListing> allListings = getAllListings();

        List<FeaturedListing> featuredListings = allListings.stream()
                .filter(FeaturedListing::isFeaturedStatus)
                .collect(Collectors.toList());

        featuredListings.sort(Comparator.comparing(FeaturedListing::getExpirationDate, Comparator.nullsLast(Comparator.naturalOrder())).reversed());

        return featuredListings;
    }
}
