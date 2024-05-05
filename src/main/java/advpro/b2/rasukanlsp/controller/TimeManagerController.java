package advpro.b2.rasukanlsp.controller;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

@RestController
@RequestMapping("/time-manager")
public class TimeManagerController {
    private LocalDateTime expirationTime = LocalDateTime.of(2024, 6, 30, 23, 59, 59); //dummy

    @PostMapping("/set-expiration-date")
    public ResponseEntity<String> setExpirationDate(@RequestParam LocalDateTime expirationDate) {
        System.out.println("Expiration date set to: " + expirationDate); //dummy
        this.setExpirationTime(expirationDate);
        return ResponseEntity.status(HttpStatus.OK).body("Expiration date successfully set to: " + expirationDate);
    }

    @GetMapping("/check-expiration")
    public ResponseEntity<String> checkExpiration() {
        LocalDateTime currentTime = LocalDateTime.now();
        boolean isExpired = currentTime.isAfter(expirationTime);
        String message = "check expiration date: " + isExpired;
        return ResponseEntity.ok(message);
    }
    private void setExpirationTime(LocalDateTime expirationTime) {
        this.expirationTime = expirationTime;
    }
}