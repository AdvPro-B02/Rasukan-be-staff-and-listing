package advpro.b2.rasukanlsp.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.time.LocalDate;

@Getter
@Setter
@Entity
public class Listing {
    @Id
    private String id;
    private String userId;
    private String name;
    private String description;
    private boolean featuredStatus;
    private LocalDate expirationDate;

    public Listing() {
    }

    public Listing(String id, String userId, String name, String description, boolean featuredStatus, LocalDate expirationDate) {
        this.id = id;
        this.userId = userId;
        this.name = name;
        this.description = description;
        this.featuredStatus = featuredStatus;
        this.expirationDate = expirationDate;
    }
}
