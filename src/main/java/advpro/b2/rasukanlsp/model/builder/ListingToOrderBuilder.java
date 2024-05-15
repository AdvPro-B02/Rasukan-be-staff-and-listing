package advpro.b2.rasukanlsp.model.builder;

import advpro.b2.rasukanlsp.model.ListingToOrder;
import advpro.b2.rasukanlsp.model.Order;
import advpro.b2.rasukanlsp.model.Listing;

import java.util.UUID;

public class ListingToOrderBuilder {

    private UUID listingToOrderId;

    private UUID listingId;

    private Order order;

    private int quantity;

    public ListingToOrderBuilder(UUID listingId, Order order, int quantity) {
        this.listingId = listingId;
        this.order = order;
        this.quantity = quantity;
    }

    public void setListingToOrderId(UUID listingToOrderId) {
        this.listingToOrderId = listingToOrderId;
    }

    public void setListing(UUID listingId) {
        this.listingId = listingId;
    }

    public void setOrder(Order order) {
        this.order = order;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public ListingToOrder build() {
        return new ListingToOrder(listingToOrderId, listingId, order, quantity);
    }
}
