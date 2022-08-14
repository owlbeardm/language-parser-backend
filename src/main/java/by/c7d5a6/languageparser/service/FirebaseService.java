package by.c7d5a6.languageparser.service;

import by.c7d5a6.languageparser.enums.FirebaseCustomClaims;
import by.c7d5a6.languageparser.rest.security.IsAdmin;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class FirebaseService {


    private final FirebaseAuth firebaseAuth;

    @Autowired
    public FirebaseService(FirebaseAuth firebaseAuth) {
        this.firebaseAuth = firebaseAuth;
    }

    @IsAdmin
    public void setUserClaims(String uid, List<FirebaseCustomClaims> requestedPermissions) throws FirebaseAuthException {
        List<String> permissions = requestedPermissions
                .stream()
                .map(Enum::toString)
                .collect(Collectors.toList());

        Map<String, Object> claims = Map.of("custom_claims", permissions);

        firebaseAuth.setCustomUserClaims(uid, claims);
    }
}
