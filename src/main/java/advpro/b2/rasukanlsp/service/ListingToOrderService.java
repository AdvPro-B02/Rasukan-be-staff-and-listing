package advpro.b2.rasukanlsp.service;

import advpro.b2.rasukanlsp.dto.ListingDTO;
import advpro.b2.rasukanlsp.model.Listing;
import advpro.b2.rasukanlsp.model.ListingToOrder;
import advpro.b2.rasukanlsp.model.builder.ListingToOrderBuilder;

import java.util.List;
import java.util.UUID;

public interface ListingToOrderService {
    Listing fetchListingDetail(String listingId);
    ListingToOrder createListingToOrder(ListingToOrderBuilder listingToOrderBuilder);
    List<ListingDTO> getListingsByOrderId(UUID orderId);
    List<ListingToOrder> getAllListingToOrders();
    void deleteListingToOrderByOrderId(UUID orderId);
}
