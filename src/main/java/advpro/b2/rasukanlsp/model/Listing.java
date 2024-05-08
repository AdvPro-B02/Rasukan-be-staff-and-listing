package advpro.b2.rasukanlsp.model;

import lombok.Getter;
import lombok.Setter;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import java.time.LocalDate;
import java.util.UUID;

@Getter
@Setter
@Entity
public class Listing {

    @Id
    @GeneratedValue
    private UUID id;

    private String userId;
    private String name;
    private boolean featuredStatus;
    private LocalDate expirationDate;

    public Listing() {
        this.id = UUID.randomUUID();
    }

    public Listing(UUID id, String userId, String name, boolean featuredStatus, LocalDate expirationDate) {
        this.id = id;
        this.userId = userId;
        this.name = name;
        this.featuredStatus = featuredStatus;
        this.expirationDate = expirationDate;
    }
    @Override
    public String toString() {
        return "ID: " + id +
                "\nUser ID: " + userId +
                "\nName: " + name +
                "\nFeatured: " + featuredStatus +
                "\nExpiration Date: " + expirationDate;
    }
}