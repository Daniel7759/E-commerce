package com.example.controllers;

import com.example.models.OrderSale;
import com.example.stripe.CheckoutSessionResponse;
import com.example.stripe.StripeService;
import com.stripe.exception.SignatureVerificationException;
import com.stripe.model.Event;
import com.stripe.model.checkout.Session;
import com.stripe.net.Webhook;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/checkout")
public class CheckoutController {

    private final String webhookSecret = System.getenv("STRIPE_WEBHOOK_SECRET");
    private final StripeService stripeService;

    public CheckoutController(StripeService stripeService) {
        this.stripeService = stripeService;
    }


    @PostMapping("/create-session")
    public ResponseEntity<?> createCheckoutSession(@RequestParam List<Long> productIds, @RequestParam List<Long> quantitys) {
        try {
            CheckoutSessionResponse session = stripeService.createCheckoutSession(productIds, quantitys);
            return ResponseEntity.ok(session);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    @PostMapping("/webhook")
    public ResponseEntity<String> webhook(@RequestBody String payload, @RequestHeader("Stripe-Signature") String sigHeader) {
        if (webhookSecret == null || webhookSecret.isEmpty()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Webhook secret is not set.");
        }

        Event event;

        try {
            event = Webhook.constructEvent(
                    payload, sigHeader, webhookSecret
            );
        } catch (SignatureVerificationException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid signature.");
        }

        // Aquí manejas los diferentes tipos de eventos
        if ("checkout.session.completed".equals(event.getType())) {
            // Procesa el evento
            Session session = (Session) event.getDataObjectDeserializer().getObject().orElse(null);
            if (session != null) {
                OrderSale orderSale = stripeService.updateOrderSaleStatus(session.getId(), "COMPLETED");
                if (orderSale == null) {
                    return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("OrderSale not found.");
                }
            }
        }

        return ResponseEntity.ok("Received");
    }
}
