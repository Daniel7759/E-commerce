package com.example.configurations;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;

import java.io.ByteArrayInputStream;
import java.io.FileInputStream;
import java.io.InputStream;

@Configuration
public class FirebaseConfig {

    /*@Value("${firebase.config.path}")
    private String firebaseConfigPath;*/

    @Bean
    public FirebaseApp initializeFirebase() {
        try {
            FirebaseOptions options = new FirebaseOptions.Builder()
                    .setCredentials(GoogleCredentials.fromStream(getFirebaseConfig()))
                    .setStorageBucket("ecommerce-ds-b10f0.appspot.com")
                    .build();
            return FirebaseApp.initializeApp(options);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Failed to initialize Firebase", e);
        }
    }

    private InputStream getFirebaseConfig() {
        String firebaseConfig = String.format(
                "{\n" +
                        "  \"type\": \"%s\",\n" +
                        "  \"project_id\": \"%s\",\n" +
                        "  \"private_key_id\": \"%s\",\n" +
                        "  \"private_key\": \"%s\",\n" +
                        "  \"client_email\": \"%s\",\n" +
                        "  \"client_id\": \"%s\",\n" +
                        "  \"auth_uri\": \"%s\",\n" +
                        "  \"token_uri\": \"%s\",\n" +
                        "  \"auth_provider_x509_cert_url\": \"%s\",\n" +
                        "  \"client_x509_cert_url\": \"%s\"\n" +
                        "}",
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
