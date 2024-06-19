package com.example.configurations;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;

import java.io.FileInputStream;
import java.io.InputStream;

@Configuration
public class FirebaseConfig {

    @Value("${firebase.config.path}")
    private String firebaseConfigPath;

    @Bean
    public FirebaseApp initializeFirebase() {
        try {
            InputStream serviceAccount = new ClassPathResource(firebaseConfigPath).getInputStream();
            FirebaseOptions options = new FirebaseOptions.Builder()
                    .setCredentials(GoogleCredentials.fromStream(serviceAccount))
                    .setStorageBucket("ecommerce-ds-b10f0.appspot.com")
                    .build();
            return FirebaseApp.initializeApp(options);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Failed to initialize Firebase", e);
        }
    }
}
