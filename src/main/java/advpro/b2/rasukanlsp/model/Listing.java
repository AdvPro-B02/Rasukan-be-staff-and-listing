package advpro.b2.rasukanlsp.model;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.sql.Date;
import java.time.LocalDate;
import java.util.UUID;


@Getter @Setter
public class Listing {
    private UUID listingId;
    private String name;
    private int price;
    private int stock;
    private UUID seller;
    private int orderCounter;

    public Listing(UUID listingId, String name, int stock, int price, UUID seller, int orderCounter){
        this.listingId = listingId;
        this.name = name;
        this.price = price;
        this.stock = stock;
        this.seller = seller;
        this.orderCounter = orderCounter;
    }

    public Listing() {}

    @Override
    public String toString() {
        ObjectMapper om = new ObjectMapper();
        try {
            return om.writeValueAsString(this);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }
}