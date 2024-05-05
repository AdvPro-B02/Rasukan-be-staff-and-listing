package advpro.b2.rasukanlsp.service;

import advpro.b2.rasukanlsp.dto.ListingDTO;
import advpro.b2.rasukanlsp.model.Listing;
import advpro.b2.rasukanlsp.model.ListingToOrder;
import advpro.b2.rasukanlsp.model.Order;
import advpro.b2.rasukanlsp.model.builder.ListingToOrderBuilder;
import advpro.b2.rasukanlsp.repository.ListingToOrderRepository;
import advpro.b2.rasukanlsp.repository.OrderRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ListingToOrderServiceTest {

    @InjectMocks
    private ListingToOrderServiceImpl listingToOrderService;

    @Mock
    private OrderRepository orderRepository;

    @Mock
    private ListingToOrderRepository listingToOrderRepository;

    @Mock
    private RestTemplate restTemplate;

    @Test
    void testCreateListingToOrder() {
        ListingToOrderBuilder listingToOrderBuilder = new ListingToOrderBuilder(UUID.randomUUID(), new Order(), 5);
        ListingToOrder expectedListingToOrder = listingToOrderBuilder.build();

        when(listingToOrderRepository.save(any())).thenReturn(expectedListingToOrder);

        ListingToOrder createdListingToOrder = listingToOrderService.createListingToOrder(listingToOrderBuilder);

        assertEquals(expectedListingToOrder, createdListingToOrder);
        verify(listingToOrderRepository, times(1)).save(any());
    }

    @Test
    void testFetchListingDetail() {
        String listingId = "2d87d53a-4789-4d29-a7c5-f2f93db916b7";
        String url = "http://34.87.180.11/Buyer/listings/get/" + listingId;
        Listing expectedListing = new Listing(UUID.fromString("2d87d53a-4789-4d29-a7c5-f2f93db916b7"), "Dress Ungu", 50, 400000, UUID.fromString("87b3bc40-3223-472b-bef7-d28407836603"));
        ResponseEntity<Listing> responseEntity = new ResponseEntity<>(expectedListing, HttpStatus.OK);

        when(restTemplate.getForEntity(url, Listing.class)).thenReturn(responseEntity);

        Listing fetchedListing = listingToOrderService.fetchListingDetail(listingId);

        assertEquals(expectedListing, fetchedListing);
        verify(restTemplate, times(1)).getForEntity(url, Listing.class);
    }

    @Test
    void testGetListingsByOrderId() {
        UUID orderId = UUID.randomUUID();
        Order order = new Order();
        order.setOrderId(orderId);
        Listing listing = new Listing(UUID.fromString("2d87d53a-4789-4d29-a7c5-f2f93db916b7"), "Dress Ungu", 50, 400000, UUID.fromString("87b3bc40-3223-472b-bef7-d28407836603"));
        ListingToOrder listingToOrder = new ListingToOrder(UUID.randomUUID(), listing.getListingId(), order, 3);
        List<ListingToOrder> listingToOrderList = Collections.singletonList(listingToOrder);
        when(orderRepository.findById(orderId)).thenReturn(Optional.of(order));
        when(listingToOrderRepository.findByOrder(order)).thenReturn(listingToOrderList);
        when(restTemplate.getForEntity(anyString(), eq(Listing.class)))
                .thenReturn(new ResponseEntity<>(listing, HttpStatus.OK));

        List<ListingDTO> result = listingToOrderService.getListingsByOrderId(orderId);

        assertNotNull(result);
        assertEquals(1, result.size());
        ListingDTO resultDTO = result.get(0);
        assertEquals(listing.getListingId(), resultDTO.getListingId());
        assertEquals(listing.getName(), resultDTO.getName());
        assertEquals(listing.getPrice(), resultDTO.getPrice());
        assertEquals(listing.getSeller(), resultDTO.getSeller());
        assertEquals(listingToOrder.getQuantity(), resultDTO.getQuantity());
    }

    @Test
    void testGetAllListingToOrders() {
        List<ListingToOrder> expectedListingToOrders = Arrays.asList(
                new ListingToOrder(), new ListingToOrder());

        when(listingToOrderRepository.findAll()).thenReturn(expectedListingToOrders);

        List<ListingToOrder> allListingToOrders = listingToOrderService.getAllListingToOrders();

        assertEquals(expectedListingToOrders, allListingToOrders);
        verify(listingToOrderRepository, times(1)).findAll();
    }

    @Test
    void testDeleteListingToOrderByOrderId() {
        UUID orderId = UUID.randomUUID();
        Order order = new Order();
        order.setOrderId(orderId);
        ListingToOrder listingToOrder = new ListingToOrder(UUID.randomUUID(), UUID.randomUUID(), order, 3);
        List<ListingToOrder> listingToOrderList = Collections.singletonList(listingToOrder);
        when(orderRepository.findById(orderId)).thenReturn(Optional.of(order));
        when(listingToOrderRepository.findByOrder(order)).thenReturn(listingToOrderList);

        listingToOrderService.deleteListingToOrderByOrderId(orderId);

        verify(listingToOrderRepository, times(1)).deleteById(listingToOrder.getListingInOrderId());
    }
}
