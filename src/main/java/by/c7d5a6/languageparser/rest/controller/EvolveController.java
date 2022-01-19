package by.c7d5a6.languageparser.rest.controller;

import by.c7d5a6.languageparser.rest.model.Language;
import by.c7d5a6.languageparser.rest.model.WordTraceResult;
import by.c7d5a6.languageparser.service.EvolutionService;
import by.c7d5a6.languageparser.service.LanguageService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/evolve")
@Tag(name = "Languages evolution", description = "Languages evolution related operations")
public class EvolveController {

    private static final Logger logger = LoggerFactory.getLogger(LanguageController.class);

    private final EvolutionService evolutionService;
    private final LanguageService languageService;

    @Autowired
    public EvolveController(EvolutionService evolutionService, LanguageService languageService) {
        this.evolutionService = evolutionService;
        this.languageService = languageService;
    }

    @Operation(summary = "Get all languages to which path from the given language is possible")
    @GetMapping("/allfrom/{fromId}")
    public List<Language> getAllLanguagesFrom(@PathVariable long fromId) {
        logger.info("Getting all languages from {}", fromId);
        return languageService.getAllLanguagesFrom(fromId);
    }

    @Operation(summary = "Get all routes from language to other given language")
    @GetMapping("/routes/{fromId}/{toId}")
    public List<List<Language>> getAllRoutes(@PathVariable long fromId, @PathVariable long toId) {
        logger.info("Getting all paths from {} to {}", fromId, toId);
        return languageService.getAllRoutes(fromId, toId);
    }

    @Operation(summary = "Trace word changes by list of languages")
    @PostMapping("/trace/{word}")
    public List<WordTraceResult> trace(@PathVariable String word, @RequestBody List<Language> languages) {
        logger.info("Trace {} by list of languages", word);
        return evolutionService.trace(word, languages);
    }

}
