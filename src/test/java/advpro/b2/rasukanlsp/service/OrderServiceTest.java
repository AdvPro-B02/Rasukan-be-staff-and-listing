package advpro.b2.rasukanlsp.service;

import advpro.b2.rasukanlsp.model.Order;
import advpro.b2.rasukanlsp.model.builder.OrderBuilder;
import advpro.b2.rasukanlsp.model.builder.ListingToOrderBuilder;
import advpro.b2.rasukanlsp.repository.OrderRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.server.ResponseStatusException;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class OrderServiceTest {
//
//    @InjectMocks
//    private OrderServiceImpl orderService;
//
//    @Mock
//    private ListingToOrderService listingToOrderService;
//
//    @Mock
//    private OrderRepository orderRepository;
//
//    private Order testOrder;
//
//    @BeforeEach
//    void setUp() {
//        UUID orderId = UUID.randomUUID();
//        UUID userId = UUID.randomUUID();
//        int nominal = 1000;
//        OrderBuilder testOrderBuilder = new OrderBuilder(orderId, userId, nominal);
//        testOrder = testOrderBuilder.build();
//    }
//
//    @Test
//    void testCreateOrder() {
//        UUID orderId = UUID.randomUUID();
//        UUID userId = UUID.randomUUID();
//        int nominal = 1000;
//        OrderBuilder orderBuilder = new OrderBuilder(orderId, userId, nominal);
//        Map<UUID, Integer> listingQuantityMap = new HashMap<>();
//        listingQuantityMap.put(UUID.randomUUID(), 2);
//        listingQuantityMap.put(UUID.randomUUID(), 3);
//
//        Order savedOrder = orderBuilder.build();
//        when(orderRepository.save(any(Order.class))).thenReturn(savedOrder);
//
//        orderService.createOrder(orderBuilder, listingQuantityMap);
//
//        verify(orderRepository, times(1)).save(any(Order.class));
//        verify(listingToOrderService, times(2)).createListingToOrder(any(ListingToOrderBuilder.class));
//    }
//
//    @Test
//    void testGetAllOrders() {
//        List<Order> orders = Arrays.asList(new Order(), new Order());
//        when(orderRepository.findAll()).thenReturn(orders);
//
//        List<Order> result = orderService.getAllOrders();
//
//        assertEquals(orders, result);
//    }
//
//    @Test
//    void testGetOrderByIdSuccess() {
//        UUID orderId = testOrder.getOrderId();
////        Order order = orderBuilder.build();
//        when(orderRepository.findById(orderId)).thenReturn(Optional.of(testOrder));
//
//        Optional<Order> result = orderService.getOrderById(orderId);
//
//        assertTrue(result.isPresent());
//        assertEquals(testOrder, result.get());
//    }
//
//    @Test
//    void testGetOrderByIdNotFound() {
//        UUID orderId = UUID.randomUUID();
//        when(orderRepository.findById(orderId)).thenReturn(Optional.empty());
//
//        Optional<Order> result = orderService.getOrderById(orderId);
//
//        assertFalse(result.isPresent());
//    }
//
//    @Test
//    void testDeleteOrder() {
//        UUID orderId = UUID.randomUUID();
//
//        assertDoesNotThrow(() -> orderService.deleteOrder(orderId));
//
//        verify(listingToOrderService, times(1)).deleteListingToOrderByOrderId(orderId);
//        verify(orderRepository, times(1)).deleteById(orderId);
//    }
//
//    @Test
//    void testUpdateOrderStatus() {
//        UUID orderId = testOrder.getOrderId();
////        Order order = new Order(orderId, UUID.randomUUID(), 1000);
//        when(orderRepository.findById(orderId)).thenReturn(Optional.of(testOrder));
//
//        assertDoesNotThrow(() -> orderService.updateOrderStatus(orderId, "FINISHED"));
//    }
//
//    @Test
//    void testUpdateOrderStatusInvalidStatus() {
//        UUID orderId = testOrder.getOrderId();
////        Order order = new Order(orderId, UUID.randomUUID(), 1000);
//        when(orderRepository.findById(orderId)).thenReturn(Optional.of(testOrder));
//
//        assertThrows(NullPointerException.class, () -> orderService.updateOrderStatus(orderId, "INVALID_STATUS"));
//    }
//
//    @Test
//    void testUpdateOrderStatusPaymentPending() {
//        UUID orderId = testOrder.getOrderId();
////        Order order = new Order(orderId, UUID.randomUUID(), 1000);
//        testOrder.setPaymentStatus("PENDING");
//        when(orderRepository.findById(orderId)).thenReturn(Optional.of(testOrder));
//
//        assertThrows(IllegalStateException.class, () -> orderService.updateOrderStatus(orderId, "FINISHED"));
//    }
//
//    @Test
//    void testUpdatePaymentStatus() {
//        UUID orderId = testOrder.getOrderId();
////        Order order = new Order(orderId, UUID.randomUUID(), 1000);
//        testOrder.setPaymentStatus("PENDING");
//        when(orderRepository.findById(orderId)).thenReturn(Optional.of(testOrder));
//
//        assertDoesNotThrow(() -> orderService.updatePaymentStatus(orderId, "ACCEPTED"));
//    }
//
//    @Test
//    void testUpdatePaymentStatusInvalidStatus() {
//        UUID orderId = testOrder.getOrderId();
////        Order order = new Order(orderId, UUID.randomUUID(), 1000);
//        testOrder.setPaymentStatus("PENDING");
//        when(orderRepository.findById(orderId)).thenReturn(Optional.of(testOrder));
//
//        assertThrows(IllegalStateException.class, () -> orderService.updatePaymentStatus(orderId, "INVALID_STATUS"));
//    }
//
//    @Test
//    void testUpdatePaymentStatusPaymentNotPending() {
//        UUID orderId = testOrder.getOrderId();
////        Order order = new Order(orderId, UUID.randomUUID(), 1000);
//        testOrder.setPaymentStatus("ACCEPTED");
//        when(orderRepository.findById(orderId)).thenReturn(Optional.of(testOrder));
//
//        assertThrows(IllegalStateException.class, () -> orderService.updatePaymentStatus(orderId, "ACCEPTED"));
//    }
}
