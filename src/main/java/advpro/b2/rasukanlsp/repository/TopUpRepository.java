package advpro.b2.rasukanlsp.repository;

import advpro.b2.rasukanlsp.model.TopUp;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface TopUpRepository extends JpaRepository<TopUp, UUID> {
    List<TopUp> findByUserId(UUID userId);

    @Modifying
    @Transactional
    @Query("delete from TopUp t where t.id = ?1")
    void deleteOneById(UUID id);

    @Modifying
    @Transactional
    @Query("delete from TopUp t where t.userId = ?1")
    void deleteAllByUserId(UUID userId);
}
