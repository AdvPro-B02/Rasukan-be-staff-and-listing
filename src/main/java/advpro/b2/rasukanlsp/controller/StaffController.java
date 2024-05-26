package advpro.b2.rasukanlsp.controller;

import advpro.b2.rasukanlsp.model.FeaturedListing;
import advpro.b2.rasukanlsp.service.FeaturedDecoratorService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/staff")
public class StaffController {

    private static final String LISTING_ID_MSG = "Listing with ID ";
    private static final String NOT_FOUND_MSG = " not found";
    private static final String INTERNAL_SERVER_ERROR_MSG = "Internal Server Error";

    private final FeaturedDecoratorService featuredService;

    public StaffController(FeaturedDecoratorService featuredService) {
        this.featuredService = featuredService;
    }

    @GetMapping("/listing/{id}")
    public ResponseEntity<?> getListingDetail(@PathVariable String id) {
        try {
            UUID listingId = UUID.fromString(id);
            FeaturedListing listing = featuredService.getListingDetail(listingId).orElse(null);
            if (listing == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(LISTING_ID_MSG + id + NOT_FOUND_MSG);
            } else {
                return ResponseEntity.ok(listing);
            }
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid UUID format: " + id);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(INTERNAL_SERVER_ERROR_MSG);
        }
    }

    @PostMapping("/listing/{id}/featured")
    public ResponseEntity<String> markListingAsFeatured(@PathVariable String id) {
        try {
            UUID listingId = UUID.fromString(id);
            String result = featuredService.markListingAsFeatured(listingId);
            if (result == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(LISTING_ID_MSG + id + NOT_FOUND_MSG);
            } else {
                return ResponseEntity.ok(result);
            }
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid UUID format: " + id);
        }
    }

    @DeleteMapping("/listing/{id}/featured")
    public ResponseEntity<String> removeFeaturedStatus(@PathVariable String id) {
        try {
            UUID listingId = UUID.fromString(id);
            String result = featuredService.removeFeaturedStatus(listingId);
            if (result == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(LISTING_ID_MSG + id + NOT_FOUND_MSG);
            } else {
                return ResponseEntity.ok(result);
            }
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid UUID format: " + id);
        }
    }

    @GetMapping("/listing/featured")
    public ResponseEntity<List<FeaturedListing>> getFeaturedListings() {
        List<FeaturedListing> featuredListings = featuredService.getFeaturedListings();
        return ResponseEntity.ok(featuredListings);
    }
}
