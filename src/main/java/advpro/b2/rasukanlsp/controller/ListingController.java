package advpro.b2.rasukanlsp.controller;

import advpro.b2.rasukanlsp.model.Listing;
import advpro.b2.rasukanlsp.service.FeaturedDecoratorService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/listing")
public class ListingController {

    private final FeaturedDecoratorService featuredService;

    public ListingController(FeaturedDecoratorService featuredService) {
        this.featuredService = featuredService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<Listing> getListingDetail(@PathVariable UUID id) {
        Listing listing = featuredService.getListingDetail(id).orElse(null);
        if (listing != null) {
            return ResponseEntity.ok(listing);
        } else {
            return ResponseEntity.notFound().build();
        }
    }


    @PostMapping("/{id}/featured")
    public ResponseEntity<String> markListingAsFeatured(@PathVariable UUID id, @RequestParam boolean status) {
        String result = featuredService.markListingAsFeatured(id, status, LocalDate.now().plusDays(7));
        if (result != null) {
            return ResponseEntity.ok(result);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Listing with ID " + id + " not found");
        }
    }

    @DeleteMapping("/{id}/featured")
    public ResponseEntity<String> removeFeaturedStatus(@PathVariable UUID id) {
        String result = featuredService.removeFeaturedStatus(id);
        if (result != null) {
            return ResponseEntity.ok(result);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Listing with ID " + id + " not found");
        }
    }

    @GetMapping("/sorted")
    public ResponseEntity<List<Listing>> getAllListingsSortedByFeatured() {
        List<Listing> sortedListings = featuredService.getAllListingsSortedByFeatured();
        return ResponseEntity.ok(sortedListings);
    }

    @GetMapping("/featured")
    public ResponseEntity<List<Listing>> getFeaturedListings() {
        List<Listing> featuredListings = featuredService.getFeaturedListings();
        return ResponseEntity.ok(featuredListings);
    }
}