package advpro.b2.rasukanlsp.repository;

import advpro.b2.rasukanlsp.model.ListingToOrder;
import advpro.b2.rasukanlsp.model.Order;
import advpro.b2.rasukanlsp.model.builder.ListingToOrderBuilder;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@Transactional
public class ListingToOrderRepositoryTest {

//    @Autowired
//    ListingToOrderRepository listingToOrderRepository;
//
//    private ListingToOrderBuilder builder;
//
//    @BeforeEach
//    void setUp() {
//        builder = new ListingToOrderBuilder(UUID.randomUUID(), new Order(), 5);
////        builder.setListingToOrderId(UUID.randomUUID());
////        builder.setQuantity(5);
//    }
//
//    @Test
//    void testFindByOrder_success() {
//        Order order = new Order();
//        order.setOrderId(UUID.randomUUID());
//
//        ListingToOrder listingToOrder1 = builder.build();
//        ListingToOrder listingToOrder2 = builder.build();
//
//        listingToOrder1.setOrder(order);
//        listingToOrder2.setOrder(order);
//
//        listingToOrderRepository.saveAll(List.of(listingToOrder1, listingToOrder2));
//
//        List<ListingToOrder> foundListingToOrders = listingToOrderRepository.findByOrder(order);
//
//        assertEquals(2, foundListingToOrders.size());
//        assertTrue(foundListingToOrders.contains(listingToOrder1));
//        assertTrue(foundListingToOrders.contains(listingToOrder2));
//    }
}
