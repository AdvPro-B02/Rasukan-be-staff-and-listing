package advpro.b2.rasukanlsp.service;

import advpro.b2.rasukanlsp.model.Order;

import advpro.b2.rasukanlsp.model.builder.OrderBuilder;
import advpro.b2.rasukanlsp.model.builder.ListingToOrderBuilder;

import advpro.b2.rasukanlsp.repository.OrderRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.*;

@Service
public class OrderServiceImpl implements OrderService {
    private final String AUTH_API_BASE_URL = "http://34.87.180.11/Buyer/listing/get/";
    private RestTemplate restTemplate;
    private ListingToOrderService listingToOrderService;

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    public OrderServiceImpl(ListingToOrderService listingToOrderService) {
        this.listingToOrderService = listingToOrderService;
        this.restTemplate = restTemplate;
    }

    public OrderServiceImpl() {}

    @Override
    public Order createOrder(OrderBuilder orderBuilder, Map<UUID, Integer> listingQuantityMap) {
        Order order = orderBuilder.build();
        orderRepository.save(order);

        for (Map.Entry<UUID, Integer> listings : listingQuantityMap.entrySet()) {
            UUID listingNowId = listings.getKey();
            Integer quantity = listings.getValue();
            ListingToOrderBuilder listingToOrderBuilder = new ListingToOrderBuilder(listingNowId, order, quantity);
            listingToOrderService.createListingToOrder(listingToOrderBuilder);
        }

        return order;
    }

    @Override
    public List<Order> getAllOrders() {
        return orderRepository.findAll();
    }

    @Override
    public Optional<Order> getOrderById(UUID orderId) {
        return orderRepository.findById(orderId);
    }

    @Override
    public void deleteOrder(UUID orderId) {
        listingToOrderService.deleteListingToOrderByOrderId(orderId);
        orderRepository.deleteById(orderId);
    }

    @Override
    public Order updateOrderStatus(UUID orderId, String newOrderStatus) {
        Optional<Order> optionalOrder = orderRepository.findById(orderId);
        if (optionalOrder.isPresent()) {
            Order order = optionalOrder.get();
            String statusOrderBefore = order.getOrderStatus();
            String statusPaymentNow = order.getPaymentStatus();
            if (statusPaymentNow.equals("PENDING")) {
                throw new IllegalStateException("Status order belum dapat diubah. Mohon tunggu hingga tahap verifikasi payment selesai.");
            } else if (statusOrderBefore.equals("PROCESSED") && (newOrderStatus.equals("FINISHED") || newOrderStatus.equals("CANCELLED"))) {
                OrderBuilder orderBuilder = new OrderBuilder(orderId, order.getUserId(), order.getNominal());
                orderBuilder.setOrderStatus(newOrderStatus);
                orderBuilder.setPaymentStatus(statusPaymentNow);
                if(order.getDiscount() != null) {
                    orderBuilder.setDiscount(order.getDiscount());
                }
                if(order.getNotes() != null) {
                    orderBuilder.setNotes(order.getNotes());
                }
                return orderRepository.save(orderBuilder.build());
            } else {
                throw new IllegalStateException("Status order tidak valid atau sudah tidak dapat diubah.");
            }
        } else {
            throw new NoSuchElementException("Order dengan id " + orderId + " tidak ditemukan.");
        }
    }

    @Override
    public Order updatePaymentStatus(UUID orderId, String newPaymentStatus) {
        Optional<Order> optionalOrder = orderRepository.findById(orderId);
        if (optionalOrder.isPresent()) {
            Order order = optionalOrder.get();
            String statusPaymentBefore = order.getPaymentStatus();
            if (statusPaymentBefore.equals("PENDING")) {
                if (newPaymentStatus.equals("ACCEPTED")) {
                    OrderBuilder orderBuilder = new OrderBuilder(orderId, order.getUserId(), order.getNominal());
                    orderBuilder.setOrderStatus("PROCESSED");
                    orderBuilder.setPaymentStatus(newPaymentStatus);
                    if(order.getDiscount() != null) {
                        orderBuilder.setDiscount(order.getDiscount());
                    }
                    if(order.getNotes() != null) {
                        orderBuilder.setNotes(order.getNotes());
                    }
                    return orderRepository.save(orderBuilder.build());
                } else if (newPaymentStatus.equals("REJECTED")) {
                    OrderBuilder orderBuilder = new OrderBuilder(orderId, order.getUserId(), order.getNominal());
                    orderBuilder.setOrderStatus("FAILED");
                    orderBuilder.setPaymentStatus(newPaymentStatus);
                    if(order.getDiscount() != null) {
                        orderBuilder.setDiscount(order.getDiscount());
                    }
                    if(order.getNotes() != null) {
                        orderBuilder.setNotes(order.getNotes());
                    }
                    return orderRepository.save(orderBuilder.build());
                } else {
                    throw new IllegalStateException("Status baru payment tidak valid.");
                }
            } else {
                throw new IllegalStateException("Status payment sudah tidak dapat diubah.");
            }
        } else {
            throw new NoSuchElementException("Order dengan id " + orderId + " tidak ditemukan.");
        }
    }

}
