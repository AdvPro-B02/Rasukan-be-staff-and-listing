package advpro.b2.rasukanlsp.service;

import advpro.b2.rasukanlsp.model.Listing;
import advpro.b2.rasukanlsp.model.Order;
import advpro.b2.rasukanlsp.model.builder.OrderBuilder;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

public interface OrderService {
    Order createOrder(OrderBuilder orderBuilder, Map<UUID, Integer> listings);
    List<Order> getAllOrders();
    Optional<Order> getOrderById(UUID orderId);
    void deleteOrder(UUID orderId);
    Order updateOrderStatus(UUID orderId, String newOrderStatus);
    Order updatePaymentStatus(UUID orderId, String newPaymentStatus);
}