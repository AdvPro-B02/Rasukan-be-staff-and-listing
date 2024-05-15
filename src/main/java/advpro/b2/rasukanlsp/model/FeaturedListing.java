package advpro.b2.rasukanlsp.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.UUID;

@Entity
@Getter
@Setter
public class FeaturedListing {

    @Id
    private UUID listingId;
    private String name;
    private boolean featuredStatus;
    private LocalDate expirationDate;

    public FeaturedListing() {
    }

    public FeaturedListing(UUID listingId, String name, boolean featuredStatus, LocalDate expirationDate) {
        this.listingId = listingId;
        this.name = name;
        this.featuredStatus = featuredStatus;
        this.expirationDate = expirationDate;
    }
    @Override
    public String toString() {
        return "ID: " + listingId +
                "\nName: " + name +
                "\nFeatured: " + featuredStatus +
                "\nExpiration Date: " + expirationDate;
    }
}