package by.c7d5a6.languageparser.rest.controller;

import by.c7d5a6.languageparser.rest.model.LanguagePOS;
import by.c7d5a6.languageparser.rest.model.POS;
import by.c7d5a6.languageparser.service.POSService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/pos")
@Tag(name = "POS", description = "Parts of speech related operations")
public class POSController {

    private static final Logger logger = LoggerFactory.getLogger(POSController.class);

    private final POSService posService;

    @Autowired
    public POSController(POSService posService) {
        this.posService = posService;
    }

    @Operation(summary = "Get all parts of speech")
    @GetMapping
    public List<POS> getAllPOS() {
        logger.info("Getting all parts of speech");
        return posService.getAllPOS();
    }

    @Operation(summary = "Add new part of speech")
    @PostMapping
    public Long savePOS(@RequestBody POS pos) {
        logger.info("Adding new part of speech");
        return posService.savePOS(pos);
    }

    @Operation(summary = "Get pos by language")
    @GetMapping("/languagepos/{languageId}")
    public List<LanguagePOS> getPOSByLanguage(@PathVariable Long languageId) {
        logger.info("Getting parts of speech to language connections by language");
        return posService.getLanguagePOSByLanguage(languageId);
    }

    @Operation(summary = "Add pos to language")
    @PostMapping("/languagepos")
    public Long saveLanguagePOS(@RequestBody LanguagePOS languagePOS) {
        logger.info("Adding new language to pos connection");
        return posService.saveLanguagePOS(languagePOS);
    }

    @Operation(summary = "Delete pos from language")
    @DeleteMapping("/languagepos/{id}")
    public void deleteLanguagePOS(@PathVariable Long id) {
        logger.info("Deleting language to pos connection");
        posService.deleteLanguagePOS(id);
    }

}
