package by.c7d5a6.languageparser.rest.controller;

import by.c7d5a6.languageparser.rest.model.*;
import by.c7d5a6.languageparser.rest.model.base.PageResult;
import by.c7d5a6.languageparser.rest.model.filter.WordListFilter;
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
@RequestMapping("/api/words")
@Tag(name = "Words", description = "Words related operations")
public class WordsController {

    private static final Logger logger = LoggerFactory.getLogger(LanguageController.class);

    private final WordService wordService;

    @Autowired
    public WordsController(WordService wordService) {
        this.wordService = wordService;
    }

    @Operation(summary = "Get all words from language")
    @GetMapping("/all/{from}")
    public List<Word> getAllWordsFromLang(@PathVariable Long from) {
        return wordService.getAllWordsFromLang(from);
    }

    @Operation(summary = "Get page of words with translations from language")
    @GetMapping("/page/{from}")
    public PageResult<WordWithTranslations> getAllWordsWithTranslationsFromLang(@PathVariable Long from) {
        return null;
//        return wordService.getAllWordsFromLang(from);
    }

    @Operation(summary = "Get all words")
    @GetMapping("/all")
    public PageResult<WordWithCategoryValues> getAllWords(@Valid WordListFilter filter) {
        return wordService.getAllWords(filter);
    }

    @Operation(summary = "Delete word by id")
    @DeleteMapping("/{id}")
    public void deleteWord(@PathVariable Long id) {
        wordService.deleteWord(id);
    }

    @Operation(summary = "Can delete word")
    @GetMapping("/{wordId}/candelete")
    public boolean canDeleteWord(@PathVariable Long wordId) {
        return wordService.canDeleteWord(wordId);
    }

    @Operation(summary = "Add new word")
    @PostMapping("/add")
    public Word addWord(@Valid @RequestBody Word word) {
        return wordService.saveWord(word);
    }

    @Operation(summary = "Add derived word")
    @PostMapping("/derive")
    public Word addDerivedWord(@Valid @RequestBody DerivedWordToAdd word) {
        return wordService.saveDerivedWord(word);
    }

    @Operation(summary = "Get detailed words by phonetics")
    @GetMapping("/{word}")
    public List<DetailedWord> getDetailedWordsByPhonetics(@PathVariable String word) {
        return wordService.getDetailedWordsByPhonetics(word);
    }

    @Operation(summary = "Clean IPA in words")
    @PostMapping("/clean")
    public void cleanIPAWords() {
        wordService.cleanIPAWords();
    }

//    @Operation(summary = "Get all words from language by text")
//    @GetMapping("/all/{from}/{text}")
//    public List<Word> getAllWords(@PathVariable("from") String fromId, @PathVariable("text") String text) {
//        logger.info("Get all words from {} by {}", fromId, text);
//        return wordService.getAllWords(fromId, text);
//    }
}
