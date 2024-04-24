package advpro.b2.rasukanlsp.controller;

import advpro.b2.rasukanlsp.model.Listing;
import advpro.b2.rasukanlsp.service.FeaturedDecoratorService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/listing")
public class ListingController {

    private final FeaturedDecoratorService listingService;

    public ListingController(FeaturedDecoratorService listingService) {
        this.listingService = listingService;
    }

    @GetMapping("/{id}")
    public String getListingDetail(@PathVariable String id) {
        listingService.updateExpiredFeaturedStatus();
        Optional<Listing> optionalListing = listingService.getListingDetail(id);
        if (optionalListing.isPresent()) {
            Listing listing = optionalListing.get();
            return "ID: " + listing.getId() + "\n" +
                    "User ID: " + listing.getUserId() + "\n" +
                    "Name: " + listing.getName() + "\n" +
                    "Description: " + listing.getDescription() + "\n" +
                    "Featured: " + listing.isFeaturedStatus();
        } else {
            return "Listing with ID " + id + " not found";
        }
    }

    @PostMapping("/{id}/featured")
    public ResponseEntity<String> markListingAsFeatured(@PathVariable String id, @RequestParam boolean status) {
        LocalDate expirationDate = LocalDate.now().plusDays(7); // set expiration date to 7 days from now
        String result = listingService.markListingAsFeatured(id, status, expirationDate);
        if (result != null) {
            return ResponseEntity.ok(result);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Listing with ID " + id + " not found");
        }
    }

    @DeleteMapping("/{id}/featured")
    public ResponseEntity<String> removeFeaturedStatus(@PathVariable String id) {
        String result = listingService.removeFeaturedStatus(id);
        if (result != null) {
            return ResponseEntity.ok(result);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Listing with ID " + id + " not found");
        }
    }
    @GetMapping
    public ResponseEntity<List<Listing>> getAllListingsSortedByFeatured() {
        List<Listing> sortedListings = listingService.getAllListingsSortedByFeatured();
        return ResponseEntity.ok(sortedListings);
    }
}