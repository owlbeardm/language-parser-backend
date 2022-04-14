package by.c7d5a6.languageparser.rest.controller;

import by.c7d5a6.languageparser.rest.model.*;
import by.c7d5a6.languageparser.rest.model.base.PageResult;
import by.c7d5a6.languageparser.rest.model.filter.TranslationListFilter;
import by.c7d5a6.languageparser.rest.model.filter.WordListFilter;
import by.c7d5a6.languageparser.rest.model.filter.WordWithEvolutionsListFilter;
import by.c7d5a6.languageparser.service.TranslationService;
import by.c7d5a6.languageparser.service.WordService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/translation")
@Tag(name = "Translation", description = "Translation related operations")
public class TranslationController {

    private static final Logger logger = LoggerFactory.getLogger(LanguageController.class);

    private final TranslationService translationService;

    @Autowired
    public TranslationController(TranslationService translationService) {
        this.translationService = translationService;
    }

    @Operation(summary = "Get page of words with translations from language")
    @GetMapping("/page")
    public PageResult<WordWithTranslations> getAllWordsWithTranslationsFromLang(@Valid TranslationListFilter filter) {
        return translationService.getAllWords(filter);
    }

    @Operation(summary = "Get translations for word")
    @GetMapping("/tranlationsfor/{id}")
    public List<Translation> getTranslationsForWord(@PathVariable Long id) {
        return translationService.getTranslationsForWord(id);
    }

    @Operation(summary = "Delete translation by id")
    @DeleteMapping("{id}")
    public void deleteTranslation(@PathVariable Long id){
        translationService.deleteTranslation(id);
    }

    @Operation(summary = "Add translation")
    @PostMapping("/")
    public Long addTranslation(@RequestBody Translation translation){
        return translationService.addTranlation(translation);
    }


}
