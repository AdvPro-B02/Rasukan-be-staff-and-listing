package advpro.b2.rasukanlsp.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import java.util.ArrayList;
import java.util.List;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class StaffControllerTest {

    @InjectMocks
    private StaffController staffController;

    @BeforeEach
    void setUp() {
    }

    @Test
    void testListPayment() {
        ResponseEntity<String> response = staffController.listPayment();
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Hello World!, this is halaman untuk melihat daftar payment seluruh user.", response.getBody());
    }

    @Test
    void testPaymentDetail() {
        String paymentId = "718bd872-bb52-42ec-9c39-2c5ec3ed5a97";
        ResponseEntity<String> response = staffController.paymentDetail(paymentId);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Hello World!, this is halaman untuk melihat payment ber id-" + paymentId, response.getBody());
    }

    @Test
    void testPaymentEdit() {
        String paymentId = "718bd872-bb52-42ec-9c39-2c5ec3ed5a97";
        ResponseEntity<String> response = staffController.paymentEdit(paymentId);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Hello World!, this is halaman untuk mengubah status payment ber id-" + paymentId, response.getBody());
    }

    @Test
    void testListTopup() {
        ResponseEntity<String> response = staffController.listTopup();
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Hello World!, this is halaman untuk melihat daftar topup seluruh user.", response.getBody());
    }

    @Test
    void testTopupDetail() {
        String topupId = "718bd872-bb52-42ec-9c39-2c5ec3ed5a97";
        ResponseEntity<String> response = staffController.topupDetail(topupId);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Hello World!, this is halaman untuk melihat topup ber id-" + topupId, response.getBody());
    }

    @Test
    void testTopupEdit() {
        String topupId = "718bd872-bb52-42ec-9c39-2c5ec3ed5a97";
        ResponseEntity<String> response = staffController.topupEdit(topupId);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Hello World!, this is halaman untuk mengubah status topup ber id-" + topupId, response.getBody());
    }
}
