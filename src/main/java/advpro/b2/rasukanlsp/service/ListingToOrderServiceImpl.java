package advpro.b2.rasukanlsp.service;

import advpro.b2.rasukanlsp.dto.ListingDTO;
import advpro.b2.rasukanlsp.model.builder.ListingToOrderBuilder;
import advpro.b2.rasukanlsp.repository.ListingToOrderRepository;
import advpro.b2.rasukanlsp.repository.OrderRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.ClientHttpRequestExecution;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.stereotype.Service;
import advpro.b2.rasukanlsp.model.Listing;
import advpro.b2.rasukanlsp.model.Order;
import advpro.b2.rasukanlsp.model.ListingToOrder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.*;

import org.springframework.web.client.RestTemplate;

@Service
public class ListingToOrderServiceImpl implements ListingToOrderService {
    private final String LISTING_API_BASE_URL = "http://34.87.180.11/Buyer/listing/get/";
    private RestTemplate restTemplate;
    private static final Logger logger = LoggerFactory.getLogger(OrderService.class);

    public ListingToOrderServiceImpl(OrderRepository orderRepository, ListingToOrderRepository listingToOrderRepository, RestTemplate restTemplate) {
        this.orderRepository = orderRepository;
        this.listingToOrderRepository = listingToOrderRepository;
        this.restTemplate = restTemplate;
    }

    @Autowired
    OrderRepository orderRepository;

    @Autowired
    ListingToOrderRepository listingToOrderRepository;

    @Override
    public ListingToOrder createListingToOrder(ListingToOrderBuilder listingToOrderBuilder) {
        return listingToOrderRepository.save(listingToOrderBuilder.build());
    }

    @Override
    public Listing fetchListingDetail(String listingId) {
        String url = LISTING_API_BASE_URL + listingId;
        logger.info("{}", url);

        HttpHeaders headers = new HttpHeaders();
        HttpEntity<String> entity = new HttpEntity<>(headers);

        ResponseEntity<Listing> response = restTemplate.getForEntity(url, Listing.class);
        if (response.getStatusCode() == HttpStatus.OK) {
            return response.getBody();
        } else {
            throw new RuntimeException("Gagal fetch detail listing dari microservice buysell.");
        }
    }

    @Override
    public List<ListingDTO> getListingsByOrderId(UUID orderId) {
        Optional<Order> order = orderRepository.findById(orderId);
        logger.info("getListingsByOrderId: masuk method");
        List<ListingDTO> listOfListings = new ArrayList<>();
        if (order.isPresent()) {
            logger.info("getListingsByOrderId: ordernya ada");
            List<ListingToOrder> listOfListingToOrder = listingToOrderRepository.findByOrder(order.get());
            for (ListingToOrder listingToOrder : listOfListingToOrder) {

                String listingIdNow = listingToOrder.getListingId().toString();
                logger.info("getListingsByOrderId: listingid: {}", listingIdNow);
                Listing listing = fetchListingDetail(listingIdNow);
                logger.info("getListingsByOrderId: listinG: {}", listing);
                ListingDTO listingDTO = new ListingDTO(listing.getListingId(), listing.getName(), listing.getPrice(), listing.getSeller(), listingToOrder.getQuantity(), listing.getOrderCounter());
                listOfListings.add(listingDTO);
                logger.info("list of listings: {}", listOfListings);
            }
        } else {
            throw new NoSuchElementException("Order dengan id " + orderId + " tidak ditemukan.");
        }
        return listOfListings;
    }

    @Override
    public List<ListingToOrder> getAllListingToOrders() {
        return listingToOrderRepository.findAll();
    }

    @Override
    public void deleteListingToOrderByOrderId(UUID orderId) {
        Optional<Order> order = orderRepository.findById(orderId);
        if (order.isPresent()) {
            List<ListingToOrder> listOfListingToOrder = listingToOrderRepository.findByOrder(order.get());
            for (ListingToOrder listingToOrder : listOfListingToOrder) {
                UUID listingToOrderId = listingToOrder.getListingInOrderId();
                listingToOrderRepository.deleteById(listingToOrderId);
            }
        }
    }
}
