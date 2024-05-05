package advpro.b2.rasukanlsp.controller;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.extension.ExtendWith;

@ExtendWith(MockitoExtension.class)
class TimeManagerControllerTest {

    @InjectMocks
    private TimeManagerController timeManagerController;

    private static final int DAYS_TO_EXPIRE = 7;

    @Test
    void testSetExpirationDate() {
        LocalDateTime expirationDate = LocalDateTime.now().plusDays(DAYS_TO_EXPIRE);
        ResponseEntity<String> responseEntity = timeManagerController.setExpirationDate(expirationDate);
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
    }

    @Test
    void testCheckExpiration_NotExpired() {
        ResponseEntity<String> responseEntity = timeManagerController.checkExpiration();
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals("check expiration date: false", responseEntity.getBody());
    }

    @Test
    void testCheckExpiration_Expired() {
        ResponseEntity<String> responseEntitySet = timeManagerController.setExpirationDate(LocalDateTime.now().minusDays(DAYS_TO_EXPIRE));
        assertEquals(HttpStatus.OK, responseEntitySet.getStatusCode());

        ResponseEntity<String> responseEntity = timeManagerController.checkExpiration();
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals("check expiration date: true", responseEntity.getBody());
    }
}