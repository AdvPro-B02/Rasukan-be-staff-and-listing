package advpro.b2.rasukanlsp.controller;

import advpro.b2.rasukanlsp.dto.ListingDTO;
import advpro.b2.rasukanlsp.dto.OrderAndListingsRequest;
import advpro.b2.rasukanlsp.dto.OrderAndListingsResponse;
import advpro.b2.rasukanlsp.model.Order;
import advpro.b2.rasukanlsp.model.builder.OrderBuilder;
import advpro.b2.rasukanlsp.service.ListingToOrderService;
import advpro.b2.rasukanlsp.service.OrderService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

public class OrderControllerTest {

    @Mock
    private OrderService orderService;

    @Mock
    private ListingToOrderService listingToOrderService;

    @InjectMocks
    private OrderController orderController;

    private UUID orderId;
    private UUID userId;
    private UUID sellerId;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
        orderId = UUID.fromString("c4266d35-deb2-4dd3-b0c5-7ebb55b56996");
        userId = UUID.fromString("c4266d35-deb2-4dd3-b0c5-7ebb55b56996");
        sellerId = UUID.fromString("8e045a64-5756-4a33-b55e-53bed3e5cd15");
    }

    @Test
    void testCheckout_success() {
        OrderAndListingsRequest request = new OrderAndListingsRequest();
        request.setUserId(userId);
        request.setNominal(100);
        request.setNotes("Notes");
        request.setDiscount(10);
        request.setSeller(sellerId);
        request.setListingQuantityMap(new HashMap<>());

        ResponseEntity<String> response = orderController.checkout(request);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Checkout telah berhasil.", response.getBody());
        verify(orderService, times(1)).createOrder(any(OrderBuilder.class), any(Map.class));
    }

    @Test
    void testCheckout_failure() {
        OrderAndListingsRequest request = new OrderAndListingsRequest();
        request.setUserId(userId);
        request.setNominal(100);
        request.setNotes("Notes");
        request.setDiscount(10);
        request.setSeller(sellerId);
        request.setListingQuantityMap(new HashMap<>());

        doThrow(new RuntimeException()).when(orderService).createOrder(any(OrderBuilder.class), any(Map.class));

        ResponseEntity<String> response = orderController.checkout(request);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    }

    @Test
    void testGetAllOrderAndListings() {
        Order order = new Order(orderId, userId, 100, "WAITING_PAYMENT", "PENDING", "Notes", 10, sellerId);
        when(orderService.getAllOrders()).thenReturn(Collections.singletonList(order));
        when(orderService.getOrderById(orderId)).thenReturn(Optional.of(order));

        List<ListingDTO> listings = Collections.singletonList(new ListingDTO(UUID.randomUUID(), "Listing 1", 10, sellerId, 100, 5));
        when(listingToOrderService.getListingsByOrderId(orderId)).thenReturn(listings);

        ResponseEntity<?> response = orderController.getAllOrderAndListings();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        List<OrderAndListingsResponse> responseBody = (List<OrderAndListingsResponse>) response.getBody();
        assertNotNull(responseBody);
        assertEquals(1, responseBody.size());
    }

    @Test
    void testGetOrderAndListings_success() {
        Order order = new Order(orderId, userId, 100, "WAITING_PAYMENT", "PENDING", "Notes", 10, sellerId);
        when(orderService.getOrderById(orderId)).thenReturn(Optional.of(order));

        List<ListingDTO> listings = Collections.singletonList(new ListingDTO(UUID.randomUUID(), "Listing 1", 10, sellerId, 100, 5));
        when(listingToOrderService.getListingsByOrderId(orderId)).thenReturn(listings);

        ResponseEntity<?> response = orderController.getOrderAndListings(orderId);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        OrderAndListingsResponse responseBody = (OrderAndListingsResponse) response.getBody();
        assertNotNull(responseBody);
        assertEquals(orderId, responseBody.getOrderId());
    }

    @Test
    void testGetOrderAndListings_notFound() {
        when(orderService.getOrderById(orderId)).thenReturn(Optional.empty());

        ResponseEntity<?> response = orderController.getOrderAndListings(orderId);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    void testGetOrderAndListings_withDiscount() {
        Order order = new Order(orderId, userId, 100, "WAITING_PAYMENT", "PENDING", null, 10, sellerId);
        when(orderService.getOrderById(orderId)).thenReturn(Optional.of(order));

        List<ListingDTO> listings = Collections.singletonList(new ListingDTO(UUID.randomUUID(), "Listing 1", 10, sellerId, 100, 5));
        when(listingToOrderService.getListingsByOrderId(orderId)).thenReturn(listings);

        ResponseEntity<?> response = orderController.getOrderAndListings(orderId);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        OrderAndListingsResponse responseBody = (OrderAndListingsResponse) response.getBody();
        assertNotNull(responseBody);
        assertEquals(orderId, responseBody.getOrderId());
        assertEquals(10, responseBody.getDiscount());
    }

    @Test
    void testGetOrderAndListings_withNotes() {
        Order order = new Order(orderId, userId, 100, "WAITING_PAYMENT", "PENDING", "Notes", null, sellerId);
        when(orderService.getOrderById(orderId)).thenReturn(Optional.of(order));

        List<ListingDTO> listings = Collections.singletonList(new ListingDTO(UUID.randomUUID(), "Listing 1", 10, sellerId, 100, 5));
        when(listingToOrderService.getListingsByOrderId(orderId)).thenReturn(listings);

        ResponseEntity<?> response = orderController.getOrderAndListings(orderId);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        OrderAndListingsResponse responseBody = (OrderAndListingsResponse) response.getBody();
        assertNotNull(responseBody);
        assertEquals(orderId, responseBody.getOrderId());
        assertEquals("Notes", responseBody.getNotes());
    }

    @Test
    void testGetOrderAndListings_withoutDiscountAndNotes() {
        Order order = new Order(orderId, userId, 100, "WAITING_PAYMENT", "PENDING", null, null, sellerId);
        when(orderService.getOrderById(orderId)).thenReturn(Optional.of(order));

        List<ListingDTO> listings = Collections.singletonList(new ListingDTO(UUID.randomUUID(), "Listing 1", 10, sellerId, 100, 5));
        when(listingToOrderService.getListingsByOrderId(orderId)).thenReturn(listings);

        ResponseEntity<?> response = orderController.getOrderAndListings(orderId);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        OrderAndListingsResponse responseBody = (OrderAndListingsResponse) response.getBody();
        assertNotNull(responseBody);
        assertEquals(orderId, responseBody.getOrderId());
        assertEquals(sellerId, responseBody.getSeller());
    }

    @Test
    void testDeleteOrder_success() {
        ResponseEntity<String> response = orderController.deleteOrder(orderId);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Order telah berhasil dihapus.", response.getBody());
        verify(orderService, times(1)).deleteOrder(orderId);
    }

    @Test
    void testDeleteOrder_failure() {
        doThrow(new RuntimeException()).when(orderService).deleteOrder(orderId);

        ResponseEntity<String> response = orderController.deleteOrder(orderId);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    }

    @Test
    void testUpdateOrderStatus_success() {
        String newOrderStatus = "SHIPPED";

        ResponseEntity<String> response = orderController.updateOrderStatus(orderId, newOrderStatus);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Status order telah berhasil diupdate", response.getBody());

        verify(orderService, times(1)).updateOrderStatus(orderId, newOrderStatus);
    }

    @Test
    void testUpdateOrderStatus_failure() {
        String newOrderStatus = "INVALID_STATUS";
        doThrow(new RuntimeException()).when(orderService).updateOrderStatus(orderId, newOrderStatus);

        ResponseEntity<String> response = orderController.updateOrderStatus(orderId, newOrderStatus);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    }

    @Test
    void testUpdatePaymentStatus_success() {
        String newPaymentStatus = "PAID";

        ResponseEntity<String> response = orderController.updatePaymentStatus(orderId, newPaymentStatus);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Status payment telah berhasil diupdate", response.getBody());

        verify(orderService, times(1)).updatePaymentStatus(orderId, newPaymentStatus);
    }

    @Test
    void testUpdatePaymentStatus_failure() {
        String newPaymentStatus = "INVALID_STATUS";
        doThrow(new RuntimeException()).when(orderService).updatePaymentStatus(orderId, newPaymentStatus);

        ResponseEntity<String> response = orderController.updatePaymentStatus(orderId, newPaymentStatus);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    }

    @Test
    void testGetOrderAndListingBySeller_success() {
        Order order = new Order(orderId, userId, 100, "WAITING_PAYMENT", "PENDING", "Notes", 10, sellerId);
        when(orderService.getOrdersBySeller(sellerId)).thenReturn(Collections.singletonList(order));
        when(orderService.getOrderById(orderId)).thenReturn(Optional.of(order));

        List<ListingDTO> listings = Collections.singletonList(new ListingDTO(UUID.randomUUID(), "Listing 1", 10, sellerId, 100, 5));
        when(listingToOrderService.getListingsByOrderId(orderId)).thenReturn(listings);

        ResponseEntity<?> response = orderController.geOrderAndListingBySeller(sellerId);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        List<OrderAndListingsResponse> responseBody = (List<OrderAndListingsResponse>) response.getBody();
        assertNotNull(responseBody);
        assertEquals(1, responseBody.size());
    }

    @Test
    void testGetOrderAndListingBySeller_noOrders() {
        when(orderService.getOrdersBySeller(sellerId)).thenReturn(Collections.emptyList());

        ResponseEntity<?> response = orderController.geOrderAndListingBySeller(sellerId);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Seller tidak punya order.", response.getBody());
    }

    @Test
    void testGetOrderAndListingByBuyer_success() {
        Order order = new Order(orderId, userId, 100, "WAITING_PAYMENT", "PENDING", "Notes", 10, sellerId);
        when(orderService.getOrdersByBuyer(userId)).thenReturn(Collections.singletonList(order));
        when(orderService.getOrderById(orderId)).thenReturn(Optional.of(order));

        List<ListingDTO> listings = Collections.singletonList(new ListingDTO(UUID.randomUUID(), "Listing 1", 10, sellerId, 100, 5));
        when(listingToOrderService.getListingsByOrderId(orderId)).thenReturn(listings);

        ResponseEntity<?> response = orderController.geOrderAndListingByBuyer(userId);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        List<OrderAndListingsResponse> responseBody = (List<OrderAndListingsResponse>) response.getBody();
        assertNotNull(responseBody);
        assertEquals(1, responseBody.size());
    }

    @Test
    void testGetOrderAndListingByBuyer_noOrders() {
        when(orderService.getOrdersByBuyer(userId)).thenReturn(Collections.emptyList());

        ResponseEntity<?> response = orderController.geOrderAndListingByBuyer(userId);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Buyer tidak punya order.", response.getBody());
    }

    @Test
    void testGetOrderAndListingWaitingPayment_success() {
        Order order = new Order(orderId, userId, 100, "WAITING_PAYMENT", "PENDING", "Notes", 10, sellerId);
        when(orderService.getAllOrders()).thenReturn(Collections.singletonList(order));
        when(orderService.getOrderById(orderId)).thenReturn(Optional.of(order));

        List<ListingDTO> listings = Collections.singletonList(new ListingDTO(UUID.randomUUID(), "Listing 1", 10, sellerId, 100, 5));
        when(listingToOrderService.getListingsByOrderId(orderId)).thenReturn(listings);

        ResponseEntity<?> response = orderController.geOrderAndListingWaitingPayment();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        List<OrderAndListingsResponse> responseBody = (List<OrderAndListingsResponse>) response.getBody();
        assertNotNull(responseBody);
        assertEquals(1, responseBody.size());
    }

    @Test
    void testGetOrderAndListingNonWaitingPayment_success() {
        Order order = new Order(orderId, userId, 100, "FINISHED", "ACCEPTED", "Notes", 10, sellerId);
        when(orderService.getAllOrders()).thenReturn(Collections.singletonList(order));
        when(orderService.getOrderById(orderId)).thenReturn(Optional.of(order));

        List<ListingDTO> listings = Collections.singletonList(new ListingDTO(UUID.randomUUID(), "Listing 1", 10, sellerId, 100, 5));
        when(listingToOrderService.getListingsByOrderId(orderId)).thenReturn(listings);

        ResponseEntity<?> response = orderController.geOrderAndListingNonWaitingPayment();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        List<OrderAndListingsResponse> responseBody = (List<OrderAndListingsResponse>) response.getBody();
        assertNotNull(responseBody);
        assertEquals(1, responseBody.size());
    }
}