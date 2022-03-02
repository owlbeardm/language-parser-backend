package by.c7d5a6.languageparser.rest.controller;

import by.c7d5a6.languageparser.rest.model.Language;
import by.c7d5a6.languageparser.rest.model.LanguagePhoneme;
import by.c7d5a6.languageparser.rest.model.ListOfLanguagePhonemes;
import by.c7d5a6.languageparser.rest.model.POS;
import by.c7d5a6.languageparser.rest.security.IsVerifiedUser;
import by.c7d5a6.languageparser.service.LanguageService;
import by.c7d5a6.languageparser.service.POSService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/pos")
@Tag(name = "POS", description = "Parts of speech related operations")
public class POSController {

    private static final Logger logger = LoggerFactory.getLogger(POSController.class);

    private final POSService posService;

    @Autowired
    public POSController(POSService posService) {
        this.posService = posService;
    }

    @Operation(summary = "Get all parts of speech")
    @GetMapping
    public List<POS> getAllPOS() {
        logger.info("Getting all parts of speech");
        return posService.getAllPOS();
    }

}
