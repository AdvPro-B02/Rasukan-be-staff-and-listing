package advpro.b2.rasukanlsp.repository;

import advpro.b2.rasukanlsp.model.Listing;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.*;

@Repository
public class ListingRepository {

    private final List<Listing> listings = new ArrayList<>();

    public ListingRepository() {
        LocalDate expirationDate = LocalDate.now().plusDays(7);
        // masih dummy data
        Listing listing1 = new Listing(UUID.randomUUID(), "user1", "Product 1",  false, expirationDate);
        Listing listing2 = new Listing(UUID.randomUUID(), "user2", "Product 2", true, expirationDate);
        Listing listing3 = new Listing(UUID.randomUUID(), "user3", "Product 3",  false, expirationDate);
        Listing listing4 = new Listing(UUID.randomUUID(), "user4", "Product 4", true, expirationDate);
        Listing listing5 = new Listing(UUID.randomUUID(), "user1", "Product 1",  true, expirationDate);

        listings.add(listing1);
        listings.add(listing2);
        listings.add(listing3);
        listings.add(listing4);
        listings.add(listing5);
    }

    public Optional<Listing> findById(UUID id) {
        return listings.stream()
                .filter(listing -> listing.getId().equals(id))
                .findFirst();
    }

    public Listing save(Listing listing) {
        Optional<Listing> existingListing = findById(listing.getId());
        if (existingListing.isPresent()) {
            // Update existing listing
            Listing existing = existingListing.get();
            existing.setUserId(listing.getUserId());
            existing.setName(listing.getName());
            existing.setFeaturedStatus(listing.isFeaturedStatus());
            existing.setExpirationDate(listing.getExpirationDate());
            if (!listing.isFeaturedStatus()) {
                existing.setExpirationDate(null);
            }
        } else {
            // Add new listing
            listings.add(listing);
        }
        return listing;
    }

    public List<Listing> findAll() {
        return new ArrayList<>(listings);
    }
}
