package com.example.server.config;

import java.io.FileInputStream;
import java.io.IOException;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.firestore.Firestore;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.cloud.FirestoreClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FirebaseConfig {

    @Bean
    public FirebaseApp firebaseConfig() throws IOException {
        if (!FirebaseApp.getApps().isEmpty())
            return FirebaseApp.getInstance();

        FileInputStream serviceAccount =
                new FileInputStream("src/main/resources/static/serviceAccountKey.json");

        FirebaseOptions options = new FirebaseOptions.Builder()
                .setCredentials(GoogleCredentials.fromStream(serviceAccount))
                .build();

        return FirebaseApp.initializeApp(options);
    }

    @Bean
    Firestore getFirestore(FirebaseApp firebaseApp) {
        Firestore fireDb = FirestoreClient.getFirestore();
        return fireDb;
    }
}
