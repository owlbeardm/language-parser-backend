package by.c7d5a6.languageparser.rest.controller;

import by.c7d5a6.languageparser.rest.model.*;
import by.c7d5a6.languageparser.service.GrammaticalCategoryService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/category")
@Tag(name = "Category", description = "Grammatical category related operations")
public class CategoryController {

    private static final Logger logger = LoggerFactory.getLogger(CategoryController.class);

    private final GrammaticalCategoryService grammaticalCategoryService;

    @Autowired
    public CategoryController(GrammaticalCategoryService grammaticalCategoryService) {
        this.grammaticalCategoryService = grammaticalCategoryService;
    }

    @Operation(summary = "Get all grammatical categories")
    @GetMapping
    public List<GrammaticalCategory> getAllCategories() {
        logger.info("Getting all grammatical categories");
        return grammaticalCategoryService.getAllCategories();
    }

    @Operation(summary = "Get grammatical categories values of category")
    @GetMapping("/{categoryId}/values")
    public List<GrammaticalCategoryValue> getCategoryValuesByCategory(@PathVariable Long categoryId) {
        logger.info("Get grammatical categories values of category {}", categoryId);
        return grammaticalCategoryService.getCategoryValuesByCategory(categoryId);
    }

    @Operation(summary = "Get grammatical categories values of category and lang")
    @GetMapping("/{categoryId}/{langId}/values")
    public List<GrammaticalCategoryValue> getCategoryValuesByCategoryAndLang(@PathVariable Long categoryId, @PathVariable Long langId) {
        logger.info("Get grammatical categories values of category {} and lang {}", categoryId, langId);
        return grammaticalCategoryService.getCategoryValuesByCategoryAndLang(categoryId, langId);
    }

    @Operation(summary = "Save grammatical category")
    @PostMapping
    public Long saveGrammaticalCategory(@RequestBody GrammaticalCategory grammaticalCategory) {
        logger.info("Save grammatical category");
        return grammaticalCategoryService.saveGrammaticalCategory(grammaticalCategory);
    }

    @Operation(summary = "Save grammatical category value")
    @PostMapping("/value")
    public Long saveGrammaticalCategoryValue(@RequestBody GrammaticalCategoryValue grammaticalCategoryValue) {
        logger.info("Save grammatical category value");
        return grammaticalCategoryService.saveGrammaticalCategoryValue(grammaticalCategoryValue);
    }

    @Operation(summary = "Get grammatical category connections")
    @GetMapping("/{categoryId}/{languageId}/connections")
    public List<GrammaticalCategoryConnection> getGrammaticalCategoryConnectionsForLang(@PathVariable Long categoryId, @PathVariable Long languageId) {
        logger.info("Get grammatical category connections for category {} and language {}", categoryId, languageId);
        return grammaticalCategoryService.getGrammaticalCategoryConnectionsForLang(categoryId, languageId);
    }

    @Operation(summary = "Save grammatical category connection")
    @PostMapping("/connection")
    public Long saveGrammaticalCategoryConnection(@RequestBody GrammaticalCategoryConnection grammaticalCategoryConnection) {
        logger.info("Save grammatical category connection");
        return grammaticalCategoryService.saveGrammaticalCategoryConnection(grammaticalCategoryConnection);
    }

    @Operation(summary = "Delete grammatical category connection")
    @DeleteMapping("/connection/{connectionId}")
    public void deleteGrammaticalCategoryConnection(@PathVariable Long connectionId) {
        logger.info("Delete grammatical category connection");
        grammaticalCategoryService.deleteGrammaticalCategoryConnection(connectionId);
    }

    @Operation(summary = "Get grammatical value word connections")
    @GetMapping("/{wordId}/valuebyword")
    public List<GrammaticalValueWordConnection> getGrammaticalValuesByWord(@PathVariable Long wordId) {
        logger.info("Get grammatical value word connections {}", wordId);
        return grammaticalCategoryService.getGrammaticalValuesByWord(wordId);
    }

    @Operation(summary = "Get grammatical values connection by lang")
    @GetMapping("/{langId}/valuelangconnection")
    public List<GrammaticalCategoryValueConnection> getGrammaticalValuesConnectionByLang(@PathVariable Long langId) {
        logger.info("Get grammatical values connection by lang {}", langId);
        return grammaticalCategoryService.getGrammaticalValuesConnectionByLang(langId);
    }

    @Operation(summary = "Replace grammatical values connection")
    @PostMapping("/valuelangconnection")
    public Long saveGrammaticalCategoryValueConnection(@RequestBody GrammaticalCategoryValueConnection gcvc) {
        logger.info("Replace grammatical value word connections");
        return grammaticalCategoryService.saveGrammaticalValuesConnection(gcvc);
    }

    @Operation(summary = "Remove grammatical values connection")
    @DeleteMapping("/valuelangconnection/{gcvcId}")
    public void removeGrammaticalCategoryValueConnection(@PathVariable Long gcvcId) {
        logger.info("Remove grammatical values connection {}", gcvcId);
        grammaticalCategoryService.removeGrammaticalValuesConnectionById(gcvcId);
    }

    @Operation(summary = "Replace grammatical value word connections")
    @PostMapping("/valuebyword/replace")
    public GrammaticalValueWordConnection replaceGrammaticalValuesByWord(@RequestBody GrammaticalValueWordConnection grammaticalValueWordConnection) {
        logger.info("Replace grammatical value word connections");
        return grammaticalCategoryService.replaceGrammaticalValuesByWord(grammaticalValueWordConnection);
    }

    @Operation(summary = "Remove grammatical value word connections")
    @DeleteMapping("/valuebyword/{wordId}/{categoryId}")
    public void removeGrammaticalValuesByWord(@PathVariable Long wordId, @PathVariable Long categoryId) {
        logger.info("Remove grammatical value word {} connections for category {}", wordId, categoryId);
        grammaticalCategoryService.removeGrammaticalValuesByWord(wordId, categoryId);
    }
}
