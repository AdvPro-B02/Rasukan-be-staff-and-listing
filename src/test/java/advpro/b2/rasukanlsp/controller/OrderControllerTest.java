package advpro.b2.rasukanlsp.controller;

import advpro.b2.rasukanlsp.dto.ListingDTO;
import advpro.b2.rasukanlsp.dto.OrderAndListingsRequest;
import advpro.b2.rasukanlsp.dto.OrderAndListingsResponse;
import advpro.b2.rasukanlsp.model.Order;
import advpro.b2.rasukanlsp.service.ListingToOrderService;
import advpro.b2.rasukanlsp.service.OrderService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class OrderControllerTest {

    @InjectMocks
    private OrderController orderController;

    @Mock
    private OrderService orderService;

    @Mock
    private ListingToOrderService listingToOrderService;

    @Test
    void testGetOrderAndListings_Success() {
        UUID orderId = UUID.randomUUID();
        Order order = new Order();
        order.setOrderId(orderId);
        order.setUserId(UUID.randomUUID());
        order.setNominal(1000);
        order.setOrderStatus("WAITING_PAYMENT");
        order.setPaymentStatus("PENDING");
        order.setNotes("Test notes");
        order.setDiscount(50);

        List<ListingDTO> listingDTOList = new ArrayList<>();
        ListingDTO listingDTO = new ListingDTO(UUID.randomUUID(), "Test Listing", 500, UUID.randomUUID(), 2);
        listingDTOList.add(listingDTO);

        when(orderService.getOrderById(orderId)).thenReturn(Optional.of(order));
        when(listingToOrderService.getListingsByOrderId(orderId)).thenReturn(listingDTOList);

        ResponseEntity<?> responseEntity = orderController.getOrderAndListings(orderId);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertTrue(responseEntity.getBody() instanceof OrderAndListingsResponse);
    }

    @Test
    void testGetOrderAndListings_NotFound() {
        when(orderService.getOrderById(any())).thenReturn(Optional.empty());

        ResponseEntity<?> responseEntity = orderController.getOrderAndListings(UUID.randomUUID());

        assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());
    }

    @Test
    void testDeleteOrder_Success() {
        UUID orderId = UUID.randomUUID();

        ResponseEntity<String> responseEntity = orderController.deleteOrder(orderId);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals("Order telah berhasil dihapus.", responseEntity.getBody());
    }

    @Test
    void testDeleteOrder_Failure() {
        UUID orderId = UUID.randomUUID();

        doThrow(RuntimeException.class).when(orderService).deleteOrder(orderId);

        ResponseEntity<String> responseEntity = orderController.deleteOrder(orderId);

        assertEquals(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());
    }

    @Test
    void testUpdateOrderStatus_Success() {
        UUID orderId = UUID.randomUUID();

        ResponseEntity<String> responseEntity = orderController.updateOrderStatus(orderId, "FINISHED");

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals("Status order telah berhasil diupdate", responseEntity.getBody());
    }

    @Test
    void testUpdateOrderStatus_Failure() {
        UUID orderId = UUID.randomUUID();

        doThrow(RuntimeException.class).when(orderService).updateOrderStatus(orderId, "FINISHED");

        ResponseEntity<String> responseEntity = orderController.updateOrderStatus(orderId, "FINISHED");

        assertEquals(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());
    }

    @Test
    void testUpdatePaymentStatus_Success() {
        UUID orderId = UUID.randomUUID();

        ResponseEntity<String> responseEntity = orderController.updatePaymentStatus(orderId, "ACCEPTED");

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals("Status payment telah berhasil diupdate", responseEntity.getBody());
    }

    @Test
    void testUpdatePaymentStatus_Failure() {
        UUID orderId = UUID.randomUUID();

        doThrow(RuntimeException.class).when(orderService).updatePaymentStatus(orderId, "ACCEPTED");

        ResponseEntity<String> responseEntity = orderController.updatePaymentStatus(orderId, "ACCEPTED");

        assertEquals(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());
    }

}
