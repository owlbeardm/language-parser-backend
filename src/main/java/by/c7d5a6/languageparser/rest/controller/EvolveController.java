package by.c7d5a6.languageparser.rest.controller;

import by.c7d5a6.languageparser.rest.model.Language;
import by.c7d5a6.languageparser.rest.model.WordTraceResult;
import by.c7d5a6.languageparser.service.EvolutionService;
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

    @Autowired
    public EvolveController(EvolutionService evolutionService) {
        this.evolutionService = evolutionService;
    }

    @Operation(summary = "Trace word by list of languages")
    @GetMapping("/trace/{word}")
    public List<WordTraceResult> trace(@PathVariable String word, @RequestParam(name = "languages") List<Language> languages) {
        return evolutionService.trace(word, languages);
    }


}
