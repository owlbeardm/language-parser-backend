package by.c7d5a6.languageparser.rest.controller;

import by.c7d5a6.languageparser.rest.model.GrammaticalCategory;
import by.c7d5a6.languageparser.rest.model.GrammaticalCategoryValue;
import by.c7d5a6.languageparser.rest.model.LanguagePOS;
import by.c7d5a6.languageparser.rest.model.POS;
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
}
