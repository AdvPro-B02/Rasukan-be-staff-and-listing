package advpro.b2.rasukanlsp.controller;

import advpro.b2.rasukanlsp.model.FeaturedListing;
import advpro.b2.rasukanlsp.service.FeaturedDecoratorService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpClientErrorException;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/staff")
public class StaffController {

    private final FeaturedDecoratorService featuredService;

    public StaffController(FeaturedDecoratorService featuredService) {
        this.featuredService = featuredService;
    }

    @GetMapping("/listing/{id}")
    public ResponseEntity<?> getListingDetail(@PathVariable String id) {
        try {
            UUID listingId = UUID.fromString(id);
            FeaturedListing listing = featuredService.getListingDetail(listingId).orElse(null);
            return ResponseEntity.ok(listing);
        } catch (HttpClientErrorException.NotFound e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Listing with ID " + id + " not found");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Listing with ID " + id + " not found");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Internal Server Error");
        }
    }


    @PostMapping("/listing/{id}/featured")
    public ResponseEntity<String> markListingAsFeatured(@PathVariable String id, @RequestParam boolean status) {
        try {
            UUID listingId = UUID.fromString(id);
            String result = featuredService.markListingAsFeatured(listingId, status, LocalDate.now().plusDays(7));
            return ResponseEntity.ok(result);
        } catch (HttpClientErrorException.NotFound e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Listing with ID " + id + " not found");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Listing with ID " + id + " not found");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Internal Server Error");
        }
    }

    @DeleteMapping("/listing/{id}/featured")
    public ResponseEntity<String> removeFeaturedStatus(@PathVariable String id, boolean status, LocalDate expirationDate) {
        try {
            UUID listingId = UUID.fromString(id);
            String result = featuredService.removeFeaturedStatus(listingId,  status, expirationDate);
            return ResponseEntity.ok(result);
        } catch (HttpClientErrorException.NotFound e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Listing with ID " + id + " not found");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Listing with ID " + id + " not found");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Internal Server Error");
        }
    }


    @GetMapping("/listing/sorted")
    public ResponseEntity<List<FeaturedListing>> getAllListingsSortedByFeatured() {
        List<FeaturedListing> sortedListings = featuredService.getAllListingsSortedByFeatured();
        return ResponseEntity.ok(sortedListings);
    }

    @GetMapping("/listing/featured")
    public ResponseEntity<List<FeaturedListing>> getFeaturedListings() {
        List<FeaturedListing> featuredListings = featuredService.getFeaturedListings();
        return ResponseEntity.ok(featuredListings);
    }
}