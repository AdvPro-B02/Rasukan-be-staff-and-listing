package advpro.b2.rasukanlsp.service;

import advpro.b2.rasukanlsp.model.FeaturedListing;
import advpro.b2.rasukanlsp.repository.FeaturedListingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;
import java.util.List;


@Service
public class ListingServiceImpl implements ListingService {

    @Autowired
    private FeaturedListingRepository featuredListingRepository;

    @Override
    public Optional<FeaturedListing> getListingDetail(UUID id) {
        return featuredListingRepository.findById(id);
    }

    @Override
    public void saveListing(FeaturedListing listing) {
        featuredListingRepository.save(listing);
    }

    @Override
    public List<FeaturedListing> getAllListings() {
        return featuredListingRepository.findAll();
    }

    @Override
    public void removeListingById(UUID listingId) {
        featuredListingRepository.deleteById(listingId);
    }
}