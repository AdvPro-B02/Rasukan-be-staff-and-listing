package advpro.b2.rasukanlsp.repository;

import advpro.b2.rasukanlsp.model.ListingToOrder;
import advpro.b2.rasukanlsp.model.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;
import java.util.List;

@Repository
public interface ListingToOrderRepository extends JpaRepository<ListingToOrder, UUID> {
    List<ListingToOrder> findByOrder(Order order);
}