package com.sm.society_management.controllers;

import com.razorpay.*;
import com.sm.society_management.models.Bill;
import com.sm.society_management.repositories.BillRepository;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/payment")
@CrossOrigin("*")
public class PaymentController {
    @Autowired
    BillRepository billRepository;

    @Value("${razorpay.key_id}")
    private String keyId;

    @Value("${razorpay.key_secret}")
    private String keySecret;

    @PostMapping("/create-order/{billId}/{amount}")
    public String createOrder(@PathVariable int billId,
                              @PathVariable double amount) throws Exception {

        RazorpayClient client = new RazorpayClient(keyId, keySecret);

        JSONObject orderRequest = new JSONObject();
        orderRequest.put("amount", (int)(amount * 100)); // Razorpay uses paise
        orderRequest.put("currency", "INR");
        orderRequest.put("receipt", "bill_" + billId);

        Order order = client.orders.create(orderRequest);

        return order.toString();
    }
    @PostMapping("/verify/{billId}")
    public String verifyPayment(@PathVariable int billId) {
        System.out.println(">>> VERIFY PAYMENT CALLED for billId = " + billId);

        // assume success
        Bill bill = billRepository.findById(billId).orElse(null);
        if(bill != null){
            bill.setStatus("PAID");
            billRepository.save(bill);
            System.out.println(">>> BILL MARKED PAID");
        }
        return "Payment verified";
    }
}