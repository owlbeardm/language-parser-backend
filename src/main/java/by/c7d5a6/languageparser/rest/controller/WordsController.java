package by.c7d5a6.languageparser.rest.controller;

import by.c7d5a6.languageparser.rest.model.Word;
import by.c7d5a6.languageparser.service.WordService;
import io.swagger.v3.oas.annotations.Operation;
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
@RequestMapping("/api/words")
@Tag(name = "Words", description = "Words related operations")
public class WordsController {

    private static final Logger logger = LoggerFactory.getLogger(LanguageController.class);

    private final WordService wordService;

    @Autowired
    public WordsController(WordService wordService) {
        this.wordService = wordService;
    }

    @Operation(summary = "Get all words from language by text")
    @GetMapping("/all/{from}/{text}")
    public List<Word> getAllWords(@PathVariable("from") String from, @PathVariable("text") String text) {
        return wordService.getAllWords(from, text);
    }

}
