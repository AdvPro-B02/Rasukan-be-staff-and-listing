package advpro.b2.rasukanlsp.repository;

import advpro.b2.rasukanlsp.model.Listing;
import org.springframework.stereotype.Repository;

import java.util.*;

@Repository
public class ListingRepository {

    private final Map<String, Listing> listings = new HashMap<>();

    public ListingRepository() {
        // masih dummy data
        Listing listing1 = new Listing("1", "user1", "Product 1", "Description of Product 1", false, null);
        Listing listing2 = new Listing("2", "user2", "Product 2", "Description of Product 2", true, null);
        Listing listing3 = new Listing("3", "user3", "Product 3", "Description of Product 3", false, null);
        Listing listing4 = new Listing("4", "user4", "Product 4", "Description of Product 4", true, null);

        listings.put(listing1.getId(), listing1);
        listings.put(listing2.getId(), listing2);
        listings.put(listing3.getId(), listing3);
        listings.put(listing4.getId(), listing4);
    }

    public Optional<Listing> findById(String id) {
        return Optional.ofNullable(listings.get(id));
    }

    public Listing save(Listing listing) {
        listings.put(listing.getId(), listing);
        return listing;
    }

    public List<Listing> findAll() {
        return new ArrayList<>(listings.values());
    }
}
