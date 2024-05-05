package advpro.b2.rasukanlsp.controller;

import advpro.b2.rasukanlsp.model.Listing;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/listing")
public class ListingController {

//    private Listing dummyListing = new Listing("1", "user1", "Product 1", "Description of Product 1", false, null);
//
//    @GetMapping("/{id}")
//    public ResponseEntity<Optional<Listing>> getListingDetail(@PathVariable String id) {
//        Optional<Listing> listingDetail = Optional.of(dummyListing);
//        return ResponseEntity.ok(listingDetail);
//    }
//
//    @PostMapping("/{id}/featured")
//    public ResponseEntity<String> markListingAsFeatured(@PathVariable String id, @RequestParam boolean status) {
//        dummyListing.setFeaturedStatus(status);
//        return ResponseEntity.ok("Listing with ID " + id + " has been marked as featured");
//    }
//
//    @DeleteMapping("/{id}/featured")
//    public ResponseEntity<String> removeFeaturedStatus(@PathVariable String id) {
//        dummyListing.setFeaturedStatus(false);
//        return ResponseEntity.ok("Featured status has been removed from listing with ID " + id);
//    }
}