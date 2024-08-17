package com.example.stripe;

import com.example.models.DetailOrderSale;
import com.example.models.OrderSale;
import com.example.models.ProductEntity;
import com.example.repositories.OrderSaleRepository;
import com.example.repositories.ProductRepository;
import com.stripe.model.checkout.Session;
import com.stripe.param.checkout.SessionCreateParams;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;

@Service
public class StripeService {

    private final String stripeApiKey = System.getenv("STRIPE_API_KEY");
    private final ProductRepository productRepository;
    private final OrderSaleRepository orderSaleRepository;

    public StripeService(ProductRepository productRepository, OrderSaleRepository orderSaleRepository) {
        this.productRepository = productRepository;
        this.orderSaleRepository = orderSaleRepository;

    }

    @PostConstruct
    public void init() {
        com.stripe.Stripe.apiKey = stripeApiKey;
        System.out.println("Stripe API Key initialized: " + com.stripe.Stripe.apiKey); // Debugging line
    }

    public CheckoutSessionResponse createCheckoutSession(
            List<Long> productIds, List<Long> quantities) throws Exception{
        List<SessionCreateParams.LineItem> lineItems = new ArrayList<>();
        List<DetailOrderSale> detailOrderSales = new ArrayList<>();
        Long total = 0L;

        for (int i=0; i<productIds.size(); i++){
            ProductEntity product = productRepository.findById(productIds.get(i))
                    .orElseThrow(() -> new Exception("Product not found"));

            SessionCreateParams.LineItem lineItem = SessionCreateParams.LineItem.builder()
                    .setQuantity(quantities.get(i))
                    .setPriceData(
                            SessionCreateParams.LineItem.PriceData.builder()
                                    .setCurrency("usd")
                                    .setUnitAmount(product.getPrice())
                                    .setProductData(
                                            SessionCreateParams.LineItem.PriceData.ProductData.builder()
                                                    .setName(product.getName())
                                                    .build())
                                    .build())
                    .build();
            lineItems.add(lineItem);


            DetailOrderSale detailOrderSale = new DetailOrderSale();
            detailOrderSale.setProductId(product.getId());
            detailOrderSale.setQuantity(quantities.get(i));
            detailOrderSale.setUnitPrice(product.getPrice());
            detailOrderSales.add(detailOrderSale);

            total += product.getPrice() * quantities.get(i);
        }

        SessionCreateParams params = SessionCreateParams.builder()
                .setMode(SessionCreateParams.Mode.PAYMENT)
                .setSuccessUrl("http://localhost:8080/success")
                .setCancelUrl("http://localhost:8080/cancel")
                .addAllLineItem(lineItems)  // Add the line items to the payment
                .build();

        Session session =Session.create(params);

        OrderSale orderSale = new OrderSale();
        orderSale.setTotal(total);
        orderSale.setStripeSessionId(session.getId());
        orderSale.setDetails(detailOrderSales);
        for (DetailOrderSale detail : detailOrderSales) {
            detail.setOrderSale(orderSale);
        }
        orderSaleRepository.save(orderSale);

        CheckoutSessionResponse response = new CheckoutSessionResponse();
        response.setId(session.getId());
        response.setUrl(session.getUrl());

        return response;
    }

    public OrderSale updateOrderSaleStatus(String sessionId, String status) {
        OrderSale orderSale = orderSaleRepository.findByStripeSessionId(sessionId);
        if (orderSale != null) {
            orderSale.setStatusPayment(status);
            return orderSaleRepository.save(orderSale);
        }
        return null;
    }
}
