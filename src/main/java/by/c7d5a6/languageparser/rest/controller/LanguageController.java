package by.c7d5a6.languageparser.rest.controller;

import by.c7d5a6.languageparser.rest.model.Language;
import by.c7d5a6.languageparser.rest.security.IsVerifiedUser;
import by.c7d5a6.languageparser.service.LanguageService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/language")
@Tag(name = "Languages", description = "Language related operations")
public class LanguageController {

    private static final Logger logger = LoggerFactory.getLogger(LanguageController.class);

    private final LanguageService languageService;

    @Autowired
    public LanguageController(LanguageService languageService) {
        this.languageService = languageService;
    }

    @IsVerifiedUser
    @Operation(summary = "Get all languages")
    @GetMapping("/all")
    public List<Language> getAllLanguages() {
        logger.info("Getting all languages");
        return languageService.getAllLanguages();
    }

    @Operation(summary = "Save language")
    @PostMapping("/")
    public Language saveLanguage(@RequestBody Language language) {
        logger.info("Saving language");
        return languageService.saveOrUpdateLanguage(language);
    }

}
