package com.soshal.controller;



import com.razorpay.Order;
import com.razorpay.RazorpayClient;
import org.json.JSONObject;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/payment")
public class PaymentController {

    private static final String RAZORPAY_KEY_ID = "your_razorpay_key";
    private static final String RAZORPAY_KEY_SECRET = "your_razorpay_secret";

    @PostMapping("/create-order")
    public ResponseEntity<String> createPaymentOrder(@RequestParam int amount) {
        try {
            RazorpayClient razorpay = new RazorpayClient(RAZORPAY_KEY_ID, RAZORPAY_KEY_SECRET);

            JSONObject orderRequest = new JSONObject();
            orderRequest.put("amount", amount * 100); // Razorpay expects amount in paise
            orderRequest.put("currency", "INR");
            orderRequest.put("payment_capture", 1);

            Order order = razorpay.orders.create(orderRequest);

            return ResponseEntity.ok(order.toString());
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body("Error creating payment order: " + e.getMessage());
        }
    }
}
