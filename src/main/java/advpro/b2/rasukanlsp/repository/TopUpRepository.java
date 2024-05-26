package advpro.b2.rasukanlsp.repository;

import advpro.b2.rasukanlsp.model.TopUp;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface TopUpRepository extends JpaRepository<TopUp, UUID> {
    List<TopUp> findByUserId(UUID userId);
}
