package advpro.b2.rasukanlsp.service;

import advpro.b2.rasukanlsp.model.Listing;
import advpro.b2.rasukanlsp.model.Order;
import advpro.b2.rasukanlsp.model.builder.OrderBuilder;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;

public interface OrderService {
    Order createOrder(OrderBuilder orderBuilder, Map<UUID, Integer> listings);
    Listing fetchListingDetail(String listingId);
    CompletableFuture<Void> updateStockListing(Listing listing);
    int fetchUserBalance(String userId);
    CompletableFuture<Void> updateUserBalance(String userId, int amount);
    List<Order> getAllOrders();
    Optional<Order> getOrderById(UUID orderId);
    void deleteOrder(UUID orderId);
    Order updateOrderStatus(UUID orderId, String newOrderStatus);
    Order updatePaymentStatus(UUID orderId, String newPaymentStatus);
    List<Order> getOrdersBySeller(UUID seller);
    List<Order> getOrdersByBuyer(UUID userId);
}