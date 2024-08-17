package com.example.stripe;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CheckoutSessionResponse {

    private String id;
    private String url;
}
