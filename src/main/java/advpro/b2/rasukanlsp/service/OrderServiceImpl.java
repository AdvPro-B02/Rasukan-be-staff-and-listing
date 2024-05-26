package advpro.b2.rasukanlsp.service;

import advpro.b2.rasukanlsp.dto.ListingDTO;
import advpro.b2.rasukanlsp.model.Listing;
import advpro.b2.rasukanlsp.model.ListingToOrder;
import advpro.b2.rasukanlsp.model.Order;

import advpro.b2.rasukanlsp.model.builder.OrderBuilder;
import advpro.b2.rasukanlsp.model.builder.ListingToOrderBuilder;

import advpro.b2.rasukanlsp.repository.OrderRepository;

import org.aspectj.weaver.ast.Or;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.AsyncResult;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Future;

import java.util.*;

@Service
public class OrderServiceImpl implements OrderService {
    private final String LISTING_API_BASE_URL = "http://34.87.180.11/";
    private final String USER_API_BASE_URL = "http://35.197.147.171/";
    private RestTemplate restTemplate;
    private ListingToOrderService listingToOrderService;
    private static final Logger logger = LoggerFactory.getLogger(OrderService.class);

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    public OrderServiceImpl(ListingToOrderService listingToOrderService, RestTemplate restTemplate) {
        this.listingToOrderService = listingToOrderService;
        this.restTemplate = restTemplate;
    }

    public OrderServiceImpl() {}

    @Override
    public Order createOrder(OrderBuilder orderBuilder, Map<UUID, Integer> listingQuantityMap) {
        logger.info("hii");
        Order order = orderBuilder.build();
        orderRepository.save(order);
        int totalPrice = 0;

        for (Map.Entry<UUID, Integer> listings : listingQuantityMap.entrySet()) {
            UUID listingNowId = listings.getKey();
            Integer quantity = listings.getValue();
            ListingToOrderBuilder listingToOrderBuilder = new ListingToOrderBuilder(listingNowId, order, quantity);
            listingToOrderService.createListingToOrder(listingToOrderBuilder);

            // Mengurangi stock listing
            Listing listing = fetchListingDetail(listingNowId.toString());
            int stockNow = listing.getStock() - quantity;
            listing.setStock(stockNow);
            logger.info("Updated stock for listing ID: {}", listingNowId);
            updateStockListing(listing);

            totalPrice += quantity * listing.getPrice();
        }
        // Mengurangi saldo user
        String userId = order.getUserId().toString();
        int currentBalance = fetchUserBalance(userId);

        if (order.getDiscount() != null) {
            int discount = totalPrice * order.getDiscount()/100;
            totalPrice -= discount;
        }

        order.setNominal(totalPrice);

        updateUserBalance(userId, currentBalance - totalPrice);
        return order;
    }

    @Override
    public Listing fetchListingDetail(String listingId) {
        logger.info("masuk fetchlistingdetail");
        String url = LISTING_API_BASE_URL + "Buyer/listing/get/" + listingId;
        logger.info("{}", url);
        ResponseEntity<Listing> response = restTemplate.getForEntity(url, Listing.class);
        logger.info("{}", response);
        if (response.getStatusCode() == HttpStatus.OK) {
            logger.info("berhasil get");
            return response.getBody();
        } else {
            logger.info("tidak berhasil get");
            throw new RuntimeException("Gagal fetch detail listing dari microservice buysell.");
        }
    }

    @Override
    @Async
    public CompletableFuture<Void> updateStockListing(Listing listing) {
        logger.info("Updating stock listing");
        HttpHeaders headers = new HttpHeaders();
        HttpEntity<Listing> requestEntity = new HttpEntity<>(listing);

        String updateUrl = LISTING_API_BASE_URL + "Buyer/listing/update/" + listing.getListingId();
        ResponseEntity<Listing> updateResponse = restTemplate.exchange(updateUrl, HttpMethod.POST, requestEntity, Listing.class);
        if (updateResponse.getStatusCode() != HttpStatus.OK) {
            throw new RuntimeException("Failed to update listing stock in the listing service.");
        }
        return CompletableFuture.completedFuture(null);
    }

    @Override
    @Async
    public int fetchUserBalance(String userId) {
        logger.info("masuk fetchuserbaalance");
        String url = USER_API_BASE_URL + "api/users/" + userId + "/balance";
        ResponseEntity<Map> response = restTemplate.getForEntity(url, Map.class);
        if (response.getStatusCode() == HttpStatus.OK) {
            Map<String, String> body = response.getBody();
            return Integer.parseInt(body.get("balance"));
        } else {
            throw new RuntimeException("Failed to fetch user balance from the user service.");
        }
    }

    @Override
    @Async
    public CompletableFuture<Void> updateUserBalance(String userId, int amount) {
        logger.info("Updating user balance");
        logger.info(userId);
        String url = USER_API_BASE_URL + "api/users/" + userId + "/balance?balance=" + amount;
        HttpHeaders headers = new HttpHeaders();
        HttpEntity<String> requestEntity = new HttpEntity<>(headers);
        ResponseEntity<Map> response = restTemplate.exchange(url, HttpMethod.PUT, requestEntity, Map.class);
        logger.info("{}", response);
        logger.info("{}", url);
        if (response.getStatusCode() != HttpStatus.OK) {
            throw new RuntimeException("Failed to update user balance in the user service.");
        }
        return CompletableFuture.completedFuture(null);
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
        logger.info("deleteorder: masuk method");
        listingToOrderService.deleteListingToOrderByOrderId(orderId);
        logger.info("deleteorder: masuk method");
        orderRepository.deleteById(orderId);
    }

    @Override
    public Order updateOrderStatus(UUID orderId, String newOrderStatus) {
        Optional<Order> optionalOrder = orderRepository.findById(orderId);
        if (optionalOrder.isPresent()) {
            Order order = optionalOrder.get();
            String statusOrderBefore = order.getOrderStatus();
            String statusPaymentNow = order.getPaymentStatus();
            int nominal = order.getNominal();
            if (statusPaymentNow.equals("PENDING")) {
                throw new IllegalStateException("Status order belum dapat diubah. Mohon tunggu hingga tahap verifikasi payment selesai.");
            } else if (statusOrderBefore.equals("PROCESSED") && (newOrderStatus.equals("FINISHED"))) {
                OrderBuilder orderBuilder = new OrderBuilder(orderId, order.getUserId(), order.getNominal(), order.getSeller());
                orderBuilder.setOrderStatus(newOrderStatus);
                orderBuilder.setPaymentStatus(statusPaymentNow);
                if(order.getDiscount() != null) {
                    orderBuilder.setDiscount(order.getDiscount());
                    nominal -= (nominal * order.getDiscount()/100);
                }
                if(order.getNotes() != null) {
                    orderBuilder.setNotes(order.getNotes());
                }
                String sellerId = "";
                List<ListingDTO> listingDTOS = listingToOrderService.getListingsByOrderId(order.getOrderId());
                for (ListingDTO listingDTO : listingDTOS) {
                    sellerId = listingDTO.getSeller().toString();
                }

                int sellerBalance = fetchUserBalance(sellerId);
                updateUserBalance(sellerId, sellerBalance + nominal);
                return orderRepository.save(orderBuilder.build());

            } else if (statusOrderBefore.equals("PROCESSED") && (newOrderStatus.equals("CANCELLED"))) {
                OrderBuilder orderBuilder = new OrderBuilder(orderId, order.getUserId(), order.getNominal(), order.getSeller());
                orderBuilder.setOrderStatus(newOrderStatus);
                orderBuilder.setPaymentStatus(statusPaymentNow);
                if (order.getDiscount() != null) {
                    orderBuilder.setDiscount(order.getDiscount());
                    nominal -= (nominal * order.getDiscount()/100);
                }
                if (order.getNotes() != null) {
                    orderBuilder.setNotes(order.getNotes());
                }

                List<ListingDTO> listingDTOS = listingToOrderService.getListingsByOrderId(orderId);
                for (ListingDTO listingDTO : listingDTOS) {
                    // Mengurangi stock listing
                    Listing listing = fetchListingDetail(listingDTO.getListingId().toString());
                    int stockNow = listing.getStock() + listingDTO.getQuantity();
                    listing.setStock(stockNow);
                    updateStockListing(listing);
                }
                int userBalance = fetchUserBalance(order.getUserId().toString());
                updateUserBalance(order.getUserId().toString(), userBalance + nominal);
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
                    OrderBuilder orderBuilder = new OrderBuilder(orderId, order.getUserId(), order.getNominal(), order.getSeller());
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
                    OrderBuilder orderBuilder = new OrderBuilder(orderId, order.getUserId(), order.getNominal(), order.getSeller());
                    orderBuilder.setOrderStatus("FAILED");
                    orderBuilder.setPaymentStatus(newPaymentStatus);
                    int totalPrice = order.getNominal();

                    if(order.getDiscount() != null) {
                        orderBuilder.setDiscount(order.getDiscount());
                        totalPrice -= order.getNominal()* order.getDiscount()/100;
                    }
                    if(order.getNotes() != null) {
                        orderBuilder.setNotes(order.getNotes());
                    }
                    int balanceUser = fetchUserBalance(order.getUserId().toString());

                    List<ListingDTO> listingDTOS = listingToOrderService.getListingsByOrderId(orderId);
                    for (ListingDTO listingDTO : listingDTOS) {
                        // Mengurangi stock listing
                        Listing listing = fetchListingDetail(listingDTO.getListingId().toString());
                        int stockNow = listing.getStock() + listingDTO.getQuantity();
                        listing.setStock(stockNow);
                        updateStockListing(listing);
                    }

                    updateUserBalance(order.getUserId().toString(), balanceUser + totalPrice);
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

    @Override
    public List<Order> getOrdersBySeller(UUID seller) {
        List<Order> listOfOrders = orderRepository.findBySeller(seller);
        if (listOfOrders != null) {
            return listOfOrders;
        } else {
            return null;
        }
    }

    @Override
    public List<Order> getOrdersByBuyer(UUID userId) {
        List<Order> listOfOrders = orderRepository.findByUserId(userId);
        if (listOfOrders != null) {
            return listOfOrders;
        } else {
            return null;
        }
    }
}
