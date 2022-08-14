package by.c7d5a6.languageparser.configuration;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.auth.FirebaseAuth;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;

import java.io.IOException;

@Configuration
public class FirebaseAuthConfig {

//    @Value("classpath:data/data.json")
//    Resource serviceAccount;

    @Autowired
    ResourceLoader resourceLoader;

    @Bean
    FirebaseAuth firebaseAuth() throws IOException {

        Resource data = data();
        if (data == null) {
            throw new IOException("No firebase auth");
        }
        FirebaseOptions options = FirebaseOptions.builder()
                .setCredentials(GoogleCredentials.fromStream(data.getInputStream()))
                .build();

        FirebaseApp firebaseApp = FirebaseApp.initializeApp(options);

        return FirebaseAuth.getInstance(firebaseApp);
    }

    public Resource data() {
        return resourceLoader.getResource(
                "classpath:data/data.json");
    }

}
