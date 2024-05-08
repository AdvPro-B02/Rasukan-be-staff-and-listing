package advpro.b2.rasukanlsp.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;
import java.util.List;

import advpro.b2.rasukanlsp.model.Listing;
import advpro.b2.rasukanlsp.repository.ListingRepository;

@Service
public class ListingServiceImpl implements ListingService {

    @Autowired
    private ListingRepository listingRepository;

    @Override
    public Optional<Listing> getListingDetail(UUID id) {
        return listingRepository.findById(id);
    }

    @Override
    public void saveListing(Listing listing) {
        listingRepository.save(listing);
    }

    @Override
    public List<Listing> getAllListings() {
        return listingRepository.findAll();
    }
}