package advpro.b2.rasukanlsp.dto;

import advpro.b2.rasukanlsp.model.Order;
import advpro.b2.rasukanlsp.model.Listing;

import java.util.List;
import java.util.Map;
import java.util.UUID;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class OrderAndListingsResponse {
    private UUID orderId;
    private UUID userId;
    private int nominal;
    private String orderStatus;
    private String paymentStatus;
    private String notes;
    private int discount;
    private List<ListingDTO> listings;

    public OrderAndListingsResponse(UUID orderId, UUID userId, int nominal, String orderStatus, String paymentStatus, String notes, int discount, List<ListingDTO> listings) {
        this.orderId = orderId;
        this.userId = userId;
        this.nominal = nominal;
        this.orderStatus = orderStatus;
        this.paymentStatus = paymentStatus;
        this.notes = notes;
        this.discount = discount;
        this.listings = listings;
    }
}
