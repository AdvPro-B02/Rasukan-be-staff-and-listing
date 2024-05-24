package advpro.b2.rasukanlsp.dto;
import java.sql.Date;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.UUID;

@Getter
@Setter
public class ListingDTO {
    private UUID listingId;
    private String name;
    private int price;
    private UUID seller;
    private int quantity;
    private int orderCounter;
    private LocalDate expiredDate;
    private boolean featuredListing;

    public ListingDTO(UUID listingId, String name, int price, UUID seller, int quantity, int orderCounter) {
        this.listingId = listingId;
        this.name = name;
        this.price = price;
        this.seller = seller;
        this.quantity = quantity;
        this.orderCounter = orderCounter;
    }
}