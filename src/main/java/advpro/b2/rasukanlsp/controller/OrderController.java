package advpro.b2.rasukanlsp.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@Controller
@RequestMapping("/staff/")
class StaffController {
    @GetMapping("/payment")
    ResponseEntity<String> listPayment() {
        String responseBody = "Hello World!, this is halaman untuk melihat daftar payment seluruh user.";
        return new ResponseEntity<>(responseBody, HttpStatus.OK);
    }

    @GetMapping("/payment/{paymentId}")
    ResponseEntity<String> paymentDetail(@PathVariable String paymentId) {
        String responseBody = "Hello World!, this is halaman untuk melihat payment ber id-" + paymentId;
        return new ResponseEntity<>(responseBody, HttpStatus.OK);
    }

    @GetMapping("/editPayment/{paymentId}")
    ResponseEntity<String> paymentEdit(@PathVariable String paymentId) {
        String responseBody = "Hello World!, this is halaman untuk mengubah status payment ber id-" + paymentId;
        return new ResponseEntity<>(responseBody, HttpStatus.OK);
    }

    @GetMapping("/topup")
    ResponseEntity<String> listTopup() {
        String responseBody = "Hello World!, this is halaman untuk melihat daftar topup seluruh user.";
        return new ResponseEntity<>(responseBody, HttpStatus.OK);
    }

    @GetMapping("/topup{topupId}")
    ResponseEntity<String> topupDetail(@PathVariable String topupId) {
        String responseBody = "Hello World!, this is halaman untuk melihat topup ber id-" + topupId;
        return new ResponseEntity<>(responseBody, HttpStatus.OK);
    }

    @GetMapping("/editTopup/{topupId}")
    ResponseEntity<String> topupEdit(@PathVariable String topupId) {
        String responseBody = "Hello World!, this is halaman untuk mengubah status topup ber id-" + topupId;
        return new ResponseEntity<>(responseBody, HttpStatus.OK);
    }
}