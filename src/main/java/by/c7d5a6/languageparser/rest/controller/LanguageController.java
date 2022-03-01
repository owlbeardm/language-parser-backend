package by.c7d5a6.languageparser.rest.controller;

import by.c7d5a6.languageparser.rest.model.Language;
import by.c7d5a6.languageparser.rest.model.LanguagePhoneme;
import by.c7d5a6.languageparser.rest.model.ListOfLanguagePhonemes;
import by.c7d5a6.languageparser.rest.model.POS;
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

    @Operation(summary = "Delete language by id")
    @DeleteMapping("/{languageId}")
    public void deleteLanguage(@PathVariable Long languageId) {
        logger.info("Deleting language by id");
        languageService.deleteLanguage(languageId);
    }

    @Operation(summary = "Can delete language")
    @GetMapping("/{languageId}/candelete")
    public boolean canDeleteLanguage(@PathVariable Long languageId) {
        logger.info("Can delete language");
        return languageService.canDeleteLanguage(languageId);
    }

    @Operation(summary = "Get all parts of speech")
    @GetMapping("/pos")
    public List<POS> getAllPartsOfSpeech() {
        logger.info("Getting all parts of speech");
        return languageService.getAllPartsOfSpeech();
    }

    @Operation(summary = "Get all parts of speech by language")
    @GetMapping("/pos/{languageId}")
    public List<POS> getAllPartsOfSpeechByLanguage(@PathVariable Long languageId) {
        logger.info("Getting all parts of speech by language");
        return languageService.getAllPartsOfSpeechByLanguage(languageId);
    }

    @Operation(summary = "Get language phonemes by id")
    @GetMapping("/phoneme/{languageId}")
    public ListOfLanguagePhonemes getLanguagePhonemes(@PathVariable Long languageId) {
        logger.info("Getting language phonemes by id");
        return languageService.getLanguagePhonemes(languageId);
    }

    @Operation(summary = "Save language phoneme")
    @PostMapping("/phoneme/{languageId}")
    public LanguagePhoneme saveLanguagePhoneme(@PathVariable Long languageId, @RequestBody String phoneme) {
        logger.info("Saving language phoneme");
        return languageService.saveLanguagePhoneme(languageId, phoneme);
    }

    @Operation(summary = "Delete language phoneme")
    @DeleteMapping("/phoneme/{phonemeId}")
    public void deleteLanguagePhoneme(@PathVariable Long phonemeId) {
        logger.info("Deleting language phoneme");
        languageService.deleteLanguagePhoneme(phonemeId);
    }
}
