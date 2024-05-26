package advpro.b2.rasukanlsp.service;

import advpro.b2.rasukanlsp.dto.ListingDTO;
import advpro.b2.rasukanlsp.model.Order;
import advpro.b2.rasukanlsp.model.Listing;
import advpro.b2.rasukanlsp.model.ListingToOrder;
import advpro.b2.rasukanlsp.model.builder.ListingToOrderBuilder;
import advpro.b2.rasukanlsp.repository.ListingToOrderRepository;
import advpro.b2.rasukanlsp.repository.OrderRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.client.MockRestServiceServer;
import org.springframework.web.client.RestTemplate;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.method;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.requestTo;
import static org.springframework.test.web.client.response.MockRestResponseCreators.withStatus;

class ListingToOrderServiceTest {

    @Mock
    private OrderRepository orderRepository;

    @Mock
    private ListingToOrderRepository listingToOrderRepository;

    @Mock
    private RestTemplate restTemplate;

    @LocalServerPort
    private int port;

    private final String LISTING_API_BASE_URL = "http://34.87.180.11/Buyer/listing/get/";

    @InjectMocks
    private ListingToOrderServiceImpl listingToOrderService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void testCreateListingToOrder() {
        UUID listingToOrderId = UUID.randomUUID();
        UUID listingId = UUID.randomUUID();
        Order order = new Order();
        int quantity = 5;

        ListingToOrderBuilder listingToOrderBuilder = new ListingToOrderBuilder(listingId, order, quantity);
        ListingToOrder listingToOrder = new ListingToOrder(listingToOrderId, listingId, order, quantity);

        when(listingToOrderRepository.save(any())).thenReturn(listingToOrder);

        ListingToOrder createdListingToOrder = listingToOrderService.createListingToOrder(listingToOrderBuilder);

        verify(listingToOrderRepository, times(1)).save(any());

        assertNotNull(createdListingToOrder);
        assertEquals(listingToOrderId, createdListingToOrder.getListingInOrderId());
        assertEquals(listingId, createdListingToOrder.getListingId());
        assertEquals(order, createdListingToOrder.getOrder());
        assertEquals(quantity, createdListingToOrder.getQuantity());
    }

//    @Test
//    void testFetchListingDetail_Success() {
//        RestTemplate restTemplate = new RestTemplate();
//        String listingId = "4ce8e1a4-88f5-4114-9ae1-1c13f54977f2";
//        String url = LISTING_API_BASE_URL + listingId;
//
//        ResponseEntity<Listing> response = restTemplate.getForEntity(url, Listing.class);
//
//        assertNotNull(response);
//        assertEquals(HttpStatus.OK, response.getStatusCode());
//        assertNotNull(response.getBody());
//        assertEquals(listingId, response.getBody().getListingId().toString());
//    }

    @Test
    void testFetchListingDetail_Failure() {
        String listingId = "invalid_listing_id";

        when(restTemplate.getForEntity(anyString(), eq(Listing.class))).thenThrow(new RuntimeException("Gagal fetch detail listing dari microservice buysell."));

        assertThrows(RuntimeException.class, () -> listingToOrderService.fetchListingDetail(listingId));
    }

//    @Test
//    void testFetchListingDetail_HTTPError() {
//        // Mock data
//        String listingId = "4ce8e1a4-88f5-4114-9ae1-1c13f54977f2";
//        String url = "http://34.87.180.11/Buyer/listing/get/" + listingId;
//
//        // Create a RestTemplate with MockRestServiceServer
//        RestTemplate restTemplate = new RestTemplate();
//        MockRestServiceServer mockServer = MockRestServiceServer.createServer(restTemplate);
//
//        // Expect HTTP GET request to the specified URL
//        mockServer.expect(requestTo(url))
//                .andExpect(method(HttpMethod.GET))
//                .andRespond(withStatus(HttpStatus.NOT_FOUND));
//
//        // Test fetchListingDetail method
//        assertThrows(RuntimeException.class, () -> listingToOrderService.fetchListingDetail(listingId));
//
//        // Verify that the HTTP request was sent as expected
//        mockServer.verify();
//    }

    @Test
    void testGetListingsByOrderId() {
        UUID orderId = UUID.randomUUID();
        Order order = new Order(orderId, UUID.randomUUID(), 100, "WAITING_PAYMENT", "PENDING", "Notes", 10, UUID.randomUUID());
        Listing listing = new Listing(UUID.randomUUID(), "listing", 100, 1000, UUID.randomUUID(), 0);
        ListingToOrder listingToOrder = new ListingToOrder(UUID.randomUUID(), listing.getListingId(), order, 5);
        List<ListingToOrder> listingToOrderList = Collections.singletonList(listingToOrder);
        ListingDTO expectedListingDTO = new ListingDTO(listing.getListingId(), listing.getName(), listing.getPrice(), listing.getSeller(), listingToOrder.getQuantity(), listing.getOrderCounter());

        when(orderRepository.findById(orderId)).thenReturn(Optional.of(order));
        when(listingToOrderRepository.findByOrder(order)).thenReturn(listingToOrderList);
        when(restTemplate.getForEntity(anyString(), eq(Listing.class))).thenReturn(new ResponseEntity<>(listing, HttpStatus.OK));

        List<ListingDTO> actualListings = listingToOrderService.getListingsByOrderId(orderId);

        assertNotNull(actualListings);
        assertEquals(1, actualListings.size());
        assertEquals(expectedListingDTO.getListingId(), actualListings.get(0).getListingId());
        assertEquals(expectedListingDTO.getName(), actualListings.get(0).getName());
        assertEquals(expectedListingDTO.getPrice(), actualListings.get(0).getPrice());
        assertEquals(expectedListingDTO.getSeller(), actualListings.get(0).getSeller());
        assertEquals(expectedListingDTO.getQuantity(), actualListings.get(0).getQuantity());
        assertEquals(expectedListingDTO.getOrderCounter(), actualListings.get(0).getOrderCounter());
    }

    @Test
    void testGetListingsByOrderId_OrderNotFound() {
        UUID orderId = UUID.randomUUID();

        when(orderRepository.findById(orderId)).thenReturn(Optional.empty());

        assertThrows(NoSuchElementException.class, () -> listingToOrderService.getListingsByOrderId(orderId));
    }

    @Test
    void testGetAllListingToOrders() {
        List<ListingToOrder> expectedList = Collections.emptyList();

        when(listingToOrderRepository.findAll()).thenReturn(expectedList);

        List<ListingToOrder> actualList = listingToOrderService.getAllListingToOrders();

        assertNotNull(actualList);
        assertEquals(expectedList.size(), actualList.size());
    }

    @Test
    void testDeleteListingToOrderByOrderId() {
        UUID orderId = UUID.randomUUID();
        Order order = new Order(orderId, UUID.randomUUID(), 100, "WAITING_PAYMENT", "PENDING", "Notes", 10, UUID.randomUUID());
        List<ListingToOrder> listingToOrderList = Arrays.asList(
                new ListingToOrder(UUID.randomUUID(), UUID.randomUUID(), order, 5),
                new ListingToOrder(UUID.randomUUID(), UUID.randomUUID(), order, 3)
        );

        when(orderRepository.findById(orderId)).thenReturn(Optional.of(order));
        when(listingToOrderRepository.findByOrder(order)).thenReturn(listingToOrderList);

        assertDoesNotThrow(() -> listingToOrderService.deleteListingToOrderByOrderId(orderId));

        verify(listingToOrderRepository, times(listingToOrderList.size())).deleteById(any());
    }
}
