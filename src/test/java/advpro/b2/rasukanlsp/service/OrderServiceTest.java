package advpro.b2.rasukanlsp.service;

import advpro.b2.rasukanlsp.dto.ListingDTO;
import advpro.b2.rasukanlsp.model.Listing;
import advpro.b2.rasukanlsp.model.Order;
import advpro.b2.rasukanlsp.model.builder.OrderBuilder;
import advpro.b2.rasukanlsp.model.builder.ListingToOrderBuilder;
import advpro.b2.rasukanlsp.repository.OrderRepository;
import advpro.b2.rasukanlsp.service.ListingToOrderService;
import advpro.b2.rasukanlsp.service.OrderServiceImpl;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.*;
import org.springframework.web.client.RestTemplate;

import java.util.*;
import java.util.concurrent.CompletableFuture;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class OrderServiceTest {

    @InjectMocks
    private OrderServiceImpl orderService;

    @Mock(lenient = true)
    private OrderRepository orderRepository;

    @Mock(lenient = true)
    private ListingToOrderService listingToOrderService;

    @Mock(lenient = true)
    private RestTemplate restTemplate;

    private UUID userId;
    private UUID sellerId;
    private UUID listingId;
    private Order mockOrder;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        // Initialize shared variables
        userId = UUID.fromString("e3b012da-23a3-46c3-9307-23387f350d85");
        sellerId = UUID.fromString("02e4de08-6b04-46f1-86b9-03106bd5ef98");
        listingId = UUID.fromString("16b81013-7f96-4eab-8689-7d6ebf3bbc76");

        // Mock Listing
        Listing listing = new Listing();
        listing.setListingId(listingId);
        listing.setPrice(6969);
        listing.setStock(69404);

        when(restTemplate.getForEntity(eq("http://34.87.180.11/Buyer/listing/get/" + listingId), eq(Listing.class)))
                .thenReturn(new ResponseEntity<>(listing, HttpStatus.OK));

        when(restTemplate.exchange(eq("http://34.87.180.11/Buyer/listing/update/" + listingId), eq(HttpMethod.POST), any(HttpEntity.class), eq(Listing.class)))
                .thenReturn(new ResponseEntity<>(listing, HttpStatus.OK));

        // Mock User Balance
        Map<String, String> balanceResponse = new HashMap<>();
        balanceResponse.put("balance", "348870");
        when(restTemplate.getForEntity(eq("http://35.197.147.171/api/users/" + userId + "/balance"), eq(Map.class)))
                .thenReturn(new ResponseEntity<>(balanceResponse, HttpStatus.OK));

        Map<String, String> sellerBalanceResponse = new HashMap<>();
        sellerBalanceResponse.put("balance", "9000000");
        when(restTemplate.getForEntity(eq("http://35.197.147.171/api/users/" + sellerId + "/balance"), eq(Map.class)))
                .thenReturn(new ResponseEntity<>(sellerBalanceResponse, HttpStatus.OK));

        when(restTemplate.exchange(eq("http://35.197.147.171/api/users/" + userId + "/balance?balance=348870"), eq(HttpMethod.PUT), any(HttpEntity.class), eq(Map.class)))
                .thenReturn(new ResponseEntity<>(new HashMap<>(), HttpStatus.OK));

        when(restTemplate.exchange(eq("http://35.197.147.171/api/users/" + sellerId + "/balance?balance=9000000"), eq(HttpMethod.PUT), any(HttpEntity.class), eq(Map.class)))
                .thenReturn(new ResponseEntity<>(new HashMap<>(), HttpStatus.OK));

        // Mock Order
        mockOrder = new OrderBuilder(UUID.randomUUID(), userId, 13938, sellerId).build();
//                .setNotes("Tolong yang banyakKK")
        mockOrder.setNotes("Tolong yang banyakKK");
        mockOrder.setDiscount(10);

        when(orderRepository.save(any(Order.class))).thenReturn(mockOrder);
        when(orderRepository.findById(any(UUID.class))).thenReturn(Optional.of(mockOrder));
    }

//    @Test
//    void testCreateOrder() {
//        System.out.println(userId);
//        Map<UUID, Integer> listingQuantityMap = new HashMap<>();
//        listingQuantityMap.put(listingId, 2);
//
//        OrderBuilder orderBuilder = new OrderBuilder(UUID.randomUUID(), userId, 13938, sellerId);
//        Order result = orderService.createOrder(orderBuilder, listingQuantityMap);
//
//        assertNotNull(result);
//        assertEquals(13938, result.getNominal());
//        verify(orderRepository).save(any(Order.class));
//    }

//    @Test
//    void testUpdateOrderStatus_ProcessedToFinished() {
//        // Arrange
//        UUID orderId = UUID.randomUUID();
//        Order mockOrder = new Order();
//        mockOrder.setOrderId(orderId);
//        mockOrder.setOrderStatus("PROCESSED");
//        mockOrder.setPaymentStatus("COMPLETED");
//        mockOrder.setNominal(100); // Set nominal to any value
//
//        when(orderRepository.findById(orderId)).thenReturn(Optional.of(mockOrder));
//
//        // Mocking RestTemplate response
//        ResponseEntity<Map> mockResponseEntity = ResponseEntity.status(HttpStatus.OK).build();
//        when(restTemplate.getForEntity(anyString(), eq(Map.class))).thenReturn(mockResponseEntity);
//
//        // Act
//        orderService.updateOrderStatus(orderId, "FINISHED");
//
//        // Assert
//        verify(orderRepository, times(1)).findById(orderId);
//        verify(orderRepository, times(1)).save(any(Order.class));
//    }

    @Test
    void testUpdateOrderStatus_InvalidOrder() {
        // Arrange
        UUID orderId = UUID.randomUUID();

        when(orderRepository.findById(orderId)).thenReturn(Optional.empty());

        // Act & Assert
        NoSuchElementException exception = org.junit.jupiter.api.Assertions.assertThrows(
                NoSuchElementException.class,
                () -> orderService.updateOrderStatus(orderId, "FINISHED")
        );

        // Assert
        verify(orderRepository, times(1)).findById(orderId);
        verify(orderRepository, never()).save(any(Order.class));
    }

    @Test
    void testUpdatePaymentStatus_PendingToAccepted() {
        // Arrange
        UUID orderId = UUID.randomUUID();
        Order mockOrder = new Order();
        mockOrder.setOrderId(orderId);
        mockOrder.setPaymentStatus("PENDING");

        when(orderRepository.findById(orderId)).thenReturn(Optional.of(mockOrder));

        // Act
        orderService.updatePaymentStatus(orderId, "ACCEPTED");

        // Assert
        verify(orderRepository, times(1)).findById(orderId);
        verify(orderRepository, times(1)).save(any(Order.class));
    }

    @Test
    void testUpdatePaymentStatus_InvalidPaymentStatus() {
        // Arrange
        UUID orderId = UUID.randomUUID();
        Order mockOrder = new Order();
        mockOrder.setOrderId(orderId);
        mockOrder.setPaymentStatus("COMPLETED");

        when(orderRepository.findById(orderId)).thenReturn(Optional.of(mockOrder));

        // Act & Assert
        IllegalStateException exception = org.junit.jupiter.api.Assertions.assertThrows(
                IllegalStateException.class,
                () -> orderService.updatePaymentStatus(orderId, "REJECTED")
        );

        // Assert
        verify(orderRepository, times(1)).findById(orderId);
        verify(orderRepository, never()).save(any(Order.class));
    }

    @Test
    void testDeleteOrder() {
        UUID orderId = mockOrder.getOrderId();

        orderService.deleteOrder(orderId);

        verify(listingToOrderService).deleteListingToOrderByOrderId(orderId);
        verify(orderRepository).deleteById(orderId);
    }

    @Test
    void testFetchListingDetail() {
        Listing result = orderService.fetchListingDetail(listingId.toString());

        assertNotNull(result);
        assertEquals(6969, result.getPrice());
        verify(restTemplate).getForEntity(eq("http://34.87.180.11/Buyer/listing/get/" + listingId), eq(Listing.class));
    }

    @Test
    void testUpdateStockListing() {
        Listing listing = new Listing();
        listing.setListingId(listingId);
        listing.setStock(69404);
        listing.setPrice(6969);

        CompletableFuture<Void> result = orderService.updateStockListing(listing);

        assertNotNull(result);
        verify(restTemplate).exchange(eq("http://34.87.180.11/Buyer/listing/update/" + listingId), eq(HttpMethod.POST), any(HttpEntity.class), eq(Listing.class));
    }

    @Test
    void testFetchUserBalance() {
        int balance = orderService.fetchUserBalance(userId.toString());

        assertEquals(348870, balance);
        verify(restTemplate).getForEntity(eq("http://35.197.147.171/api/users/" + userId + "/balance"), eq(Map.class));
    }

    @Test
    void testUpdateUserBalance() {
        CompletableFuture<Void> result = orderService.updateUserBalance(userId.toString(), 348870);

        assertNotNull(result);
        verify(restTemplate).exchange(eq("http://35.197.147.171/api/users/" + userId + "/balance?balance=348870"), eq(HttpMethod.PUT), any(HttpEntity.class), eq(Map.class));
    }

    @Test
    void testGetAllOrders() {
        List<Order> orders = Collections.singletonList(mockOrder);
        when(orderRepository.findAll()).thenReturn(orders);

        List<Order> result = orderService.getAllOrders();

        assertEquals(orders, result);
        verify(orderRepository).findAll();
    }

    @Test
    void testGetOrderById() {
        UUID orderId = mockOrder.getOrderId();

        Optional<Order> result = orderService.getOrderById(orderId);

        assertTrue(result.isPresent());
        assertEquals(mockOrder, result.get());
        verify(orderRepository).findById(orderId);
    }

//    @Test
//    void testUpdateOrderStatus() {
//        UUID orderId = mockOrder.getOrderId();
//        mockOrder.setOrderStatus("PROCESSED");
//        mockOrder.setPaymentStatus("ACCEPTED");
//
//        when(orderRepository.findById(orderId)).thenReturn(Optional.of(mockOrder));
//        when(orderRepository.save(any(Order.class))).thenReturn(mockOrder);
//
//        Order result = orderService.updateOrderStatus(orderId, "FINISHED");
//
//        assertNotNull(result);
//        assertEquals("FINISHED", result.getOrderStatus());
//        verify(orderRepository).findById(orderId);
//        verify(orderRepository).save(any(Order.class));
//    }

//    @Test
//    void testUpdatePaymentStatus() {
//        UUID orderId = mockOrder.getOrderId();
//        mockOrder.setPaymentStatus("PENDING");
//
//        when(orderRepository.findById(orderId)).thenReturn(Optional.of(mockOrder));
//        when(orderRepository.save(any(Order.class))).thenReturn(mockOrder);
//
//        Order result = orderService.updatePaymentStatus(orderId, "ACCEPTED");
//
//        assertNotNull(result);
//        assertEquals("ACCEPTED", result.getPaymentStatus());
//        verify(orderRepository).findById(orderId);
//        verify(orderRepository).save(any(Order.class));
//    }

    @Test
    void testGetOrdersBySeller() {
        UUID seller = mockOrder.getSeller();
        List<Order> orders = Collections.singletonList(mockOrder);

        when(orderRepository.findBySeller(seller)).thenReturn(orders);

        List<Order> result = orderService.getOrdersBySeller(seller);

        assertNotNull(result);
        assertFalse(result.isEmpty());
        assertEquals(orders, result);
        verify(orderRepository).findBySeller(seller);
    }

    @Test
    void testGetOrdersByBuyer() {
        UUID buyer = mockOrder.getUserId();
        List<Order> orders = Collections.singletonList(mockOrder);

        when(orderRepository.findByUserId(buyer)).thenReturn(orders);

        List<Order> result = orderService.getOrdersByBuyer(buyer);

        assertNotNull(result);
        assertFalse(result.isEmpty());
        assertEquals(orders, result);
        verify(orderRepository).findByUserId(buyer);
    }
}