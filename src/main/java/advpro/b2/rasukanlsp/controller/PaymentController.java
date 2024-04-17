package advpro.b2.rasukanlsp.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/api/payment")
public class PaymentController {
    
    @PostMapping("/create")
    @ResponseBody
    public String createPayment() {
        return "Hi, this API currently is not functional. Thanks for the interest";
    }

    @GetMapping("/list")
    @ResponseBody
    public String listPayment() {
        return "<h1>Hi, this API currently is not functional. Thanks for the interest</h1>";
    }

    @GetMapping("/find")
    @ResponseBody
    public String findPaymentById(@RequestParam("id") String id) {
        return "<h1>Hi, this API currently is not functional. Thanks for the interest</h1>";
    }
}
