package advpro.b2.rasukanlsp.controller;

import advpro.b2.rasukanlsp.dto.ListingDTO;
import advpro.b2.rasukanlsp.dto.OrderAndListingsResponse;
import advpro.b2.rasukanlsp.dto.OrderAndListingsRequest;
import advpro.b2.rasukanlsp.model.builder.OrderBuilder;
import advpro.b2.rasukanlsp.model.Listing;
import advpro.b2.rasukanlsp.model.Order;
import advpro.b2.rasukanlsp.service.ListingToOrderService;
import advpro.b2.rasukanlsp.service.OrderService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/order")
class OrderController {

    @Autowired
    OrderService orderService;

    @Autowired
    ListingToOrderService listingToOrderService;

    @PostMapping("/checkout")
    public ResponseEntity<String> checkout(@RequestBody OrderAndListingsRequest orderAndListingsRequest) {
        try {
            OrderBuilder orderBuilder = new OrderBuilder(orderAndListingsRequest.getUserId(), orderAndListingsRequest.getNominal());
            orderBuilder.setNotes(orderAndListingsRequest.getNotes());
            orderBuilder.setDiscount(orderAndListingsRequest.getDiscount());
            orderService.createOrder(orderBuilder, orderAndListingsRequest.getListingQuantityMap());
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>("Checkout telah berhasil.", HttpStatus.OK);
    }

    @GetMapping("/")
    public ResponseEntity<?> getAllOrderAndListings() {
        List<Order> listOfOrders = orderService.getAllOrders();
        List<OrderAndListingsResponse> orderAndListingsResponses = new ArrayList<>();
        for (Order order : listOfOrders) {
            ResponseEntity<?> responseEntity = getOrderAndListings(order.getOrderId());
            if (responseEntity.getStatusCode() == HttpStatus.OK) {
                OrderAndListingsResponse orderAndListingsResponse = (OrderAndListingsResponse) responseEntity.getBody();
                orderAndListingsResponses.add(orderAndListingsResponse);
            } else {
                continue;
            }
        }
        return ResponseEntity.ok(orderAndListingsResponses);
    }

    @GetMapping("/{orderId}")
    public ResponseEntity<?> getOrderAndListings(@PathVariable UUID orderId) {
        Optional<Order> orderOptional = orderService.getOrderById(orderId);
        if (orderOptional.isPresent()) {
            List<ListingDTO> listings = listingToOrderService.getListingsByOrderId(orderId);
            Order order = orderOptional.get();
            return ResponseEntity.ok(new OrderAndListingsResponse(order.getOrderId(), order.getUserId(), order.getNominal(), order.getOrderStatus(), order.getPaymentStatus(), order.getNotes(), order.getDiscount(), listings));
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{orderId}")
    public ResponseEntity<String> deleteOrder(@PathVariable UUID orderId) {
        try {
            orderService.deleteOrder(orderId);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>("Order telah berhasil dihapus.", HttpStatus.OK);
    }

    @PutMapping("/{orderId}/update-order-status")
    public ResponseEntity<String> updateOrderStatus(@PathVariable UUID orderId, @RequestParam String newOrderStatus) {
        try {
            orderService.updateOrderStatus(orderId, newOrderStatus);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>("Status order telah berhasil diupdate", HttpStatus.OK);
    }

    @PutMapping("/{orderId}/update-payment-status")
    public ResponseEntity<String> updatePaymentStatus(@PathVariable UUID orderId, @RequestParam String newPaymentStatus) {
        try {
            orderService.updatePaymentStatus(orderId, newPaymentStatus);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>("Status payment telah berhasil diupdate", HttpStatus.OK);
    }
}