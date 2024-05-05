package advpro.b2.rasukanlsp.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
public class ListingDTO {
    private UUID listingId;
    private String name;
    private int price;
    private UUID seller;
    private int quantity;

    public ListingDTO(UUID listingId, String name, int price, UUID seller, int quantity) {
        this.listingId = listingId;
        this.name = name;
        this.price = price;
        this.seller = seller;
        this.quantity = quantity;
    }
}