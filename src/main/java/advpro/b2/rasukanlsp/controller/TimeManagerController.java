package advpro.b2.rasukanlsp.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

@RestController
@RequestMapping("/time-manager")
public class TimeManagerController {

    @PostMapping("/set-expiration-date")
    public ResponseEntity<Void> setExpirationDate(@RequestParam LocalDateTime expirationDate) {
        System.out.println("Expiration date set to: " + expirationDate); //dummy
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @GetMapping("/check-expiration")
    public ResponseEntity<Boolean> checkExpiration(@RequestParam LocalDateTime expirationTime) {
        boolean isExpired = LocalDateTime.now().isAfter(expirationTime); //dummy
        return ResponseEntity.ok(isExpired);
    }
}