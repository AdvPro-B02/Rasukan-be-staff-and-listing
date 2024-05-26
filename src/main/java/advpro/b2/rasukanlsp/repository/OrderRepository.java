package advpro.b2.rasukanlsp.repository;

import advpro.b2.rasukanlsp.model.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;
import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<Order, UUID> {
    List<Order> findBySeller(UUID seller);
    List<Order> findByUserId(UUID userId);
}