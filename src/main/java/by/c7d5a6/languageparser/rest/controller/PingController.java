package by.c7d5a6.languageparser.rest.controller;

import by.c7d5a6.languageparser.enums.FirebaseCustomClaims;
import by.c7d5a6.languageparser.rest.security.IsAdmin;
import by.c7d5a6.languageparser.service.FirebaseService;
import com.google.firebase.auth.FirebaseAuthException;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/ping")
@Tag(name = "Ping")
public class PingController {

    private static final Logger logger = LoggerFactory.getLogger(PingController.class);

    private final FirebaseService firebaseService;

    @Autowired
    public PingController(FirebaseService firebaseService) {
        this.firebaseService = firebaseService;
    }

    @Operation(summary = "Ping")
    @GetMapping
    public String ping() {
        logger.info("PingController.ping()");
        return "pong";
    }

    @PostMapping(path = "/user-claims/{uid}")
    @IsAdmin
    public void setUserClaims(
            @PathVariable String uid,
            @RequestBody List<FirebaseCustomClaims> requestedClaims
    ) throws FirebaseAuthException {
        firebaseService.setUserClaims(uid, requestedClaims);
    }
}
