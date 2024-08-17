package com.example.configurations;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

@Configuration
public class FirebaseConfig {

    @Bean
    public FirebaseApp initializeFirebase() {
        try {
            FirebaseOptions options = new FirebaseOptions.Builder()
                    .setCredentials(GoogleCredentials.fromStream(getFirebaseConfig()))
                    .setStorageBucket("ecommerce-ds-b10f0.appspot.com")
                    .build();
            return FirebaseApp.initializeApp(options);
        } catch (Exception e) {
            throw new RuntimeException("Failed to initialize Firebase", e);
        }
    }

    private InputStream getFirebaseConfig() {
        String firebaseConfig = String.format(
                """
                        {
                          "type": "%s",
                          "project_id": "%s",
                          "private_key_id": "%s",
                          "private_key": "%s",
                          "client_email": "%s",
                          "client_id": "%s",
                          "auth_uri": "%s",
                          "token_uri": "%s",
                          "auth_provider_x509_cert_url": "%s",
                          "client_x509_cert_url": "%s"
                        }""",
                System.getenv("TYPE"),
                System.getenv("PROJECT_ID"),
                System.getenv("PRIVATE_KEY_ID"),
                System.getenv("PRIVATE_KEY").replace("\\n", "\n"),
                System.getenv("CLIENT_EMAIL"),
                System.getenv("CLIENT_ID"),
                System.getenv("AUTH_URI"),
                System.getenv("TOKEN_URI"),
                System.getenv("AUTH_PROVIDER_X509_CERT_URL"),
                System.getenv("CLIENT_X509_CERT_URL")
        );
        return new ByteArrayInputStream(firebaseConfig.getBytes());
    }
}
