package advpro.b2.rasukanlsp.repository;

import advpro.b2.rasukanlsp.model.FeaturedListing;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface FeaturedListingRepository extends JpaRepository<FeaturedListing, UUID> {

}
