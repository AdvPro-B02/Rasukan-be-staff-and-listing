package advpro.b2.rasukanlsp.controller;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(PaymentController.class)
class PaymentControllerTest {
    
    @Autowired
    private MockMvc mvc;

    @BeforeAll
    static void setUp() {}

    @Test
    void testCreatePayment_success() throws Exception {
        mvc.perform(post("/api/payment/create"))
            .andExpect(status().isOk())
            .andExpect(content().string(
                "Hi, this API currently is not functional. Thanks for the interest"
            ));
    }

    @Test
    void testListPayment_success() throws Exception {
        mvc.perform(get("/api/payment/list"))
            .andExpect(status().isOk())
            .andExpect(content().string(
                "<h1>Hi, this API currently is not functional. Thanks for the interest</h1>"
            ));
    }
    
    @Test
    void testFindPaymentById_success() throws Exception {
        mvc.perform(get("/api/payment/find?id=1"))
            .andExpect(status().isOk())
            .andExpect(content().string(
                "<h1>Hi, this API currently is not functional. Thanks for the interest</h1>"
            ));
    }
}
