package advpro.b2.rasukanlsp.controller;

import advpro.b2.rasukanlsp.model.Listing;
import advpro.b2.rasukanlsp.service.FeaturedDecoratorService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/listing")
public class ListingController {

    private final FeaturedDecoratorService listingService;

    public ListingController(FeaturedDecoratorService listingService) {
        this.listingService = listingService;
    }

    @GetMapping("/{id}")
    public String getListingDetail(@PathVariable UUID id) {
        listingService.updateExpiredFeaturedStatus();
        Optional<Listing> optionalListing = listingService.getListingDetail(id);
        if (optionalListing.isPresent()) {
            Listing listing = optionalListing.get();
            return "ID: " + listing.getId() + ", User ID: " + listing.getUserId() + ", Name: " + listing.getName() + ", Featured: " + listing.isFeaturedStatus();
        } else {
            return "Listing with ID " + id + " not found";
        }
    }

    @PostMapping("/{id}/featured")
    public ResponseEntity<String> markListingAsFeatured(@PathVariable UUID id, @RequestParam boolean status) {
        String result = listingService.markListingAsFeatured(id, status, LocalDate.now().plusDays(7));
        if (result != null) {
            return ResponseEntity.ok(result);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Listing with ID " + id + " not found");
        }
    }

    @DeleteMapping("/{id}/featured")
    public ResponseEntity<String> removeFeaturedStatus(@PathVariable UUID id) {
        String result = listingService.removeFeaturedStatus(id);
        if (result != null) {
            return ResponseEntity.ok(result);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Listing with ID " + id + " not found");
        }
    }
    @GetMapping("/sorted")
    public ResponseEntity<List<Listing>> getAllListingsSortedByFeatured() {
        List<Listing> sortedListings = listingService.getAllListingsSortedByFeatured();
        return ResponseEntity.ok(sortedListings);
    }

    @GetMapping("/featured")
    public ResponseEntity<List<Listing>> getFeaturedListings() {
        List<Listing> featuredListings = listingService.getFeaturedListings();
        return ResponseEntity.ok(featuredListings);
    }
}