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
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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

    @Operation(summary = "Get all languages to which path from the given language is possible")
    @GetMapping("/allfrom/{fromId}")
    public List<Language> getAllLanguagesFrom(@PathVariable long fromId) {
        logger.info("Getting all languages from {}", fromId);
        return languageService.getAllLanguagesFrom(fromId);
    }

    @Operation(summary = "Get all paths from language to other given language")
    @GetMapping("/paths/{fromId}/{toId}")
    public List<List<Language>> getAllPaths(@PathVariable long fromId, @PathVariable long toId) {
        logger.info("Getting all paths from {} to {}", fromId, toId);
        return languageService.getAllPaths(fromId, toId);
    }


}
