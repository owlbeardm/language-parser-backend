package by.c7d5a6.languageparser.rest.controller;

import by.c7d5a6.languageparser.rest.model.DeclensionConnection;
import by.c7d5a6.languageparser.rest.model.DeclensionFull;
import by.c7d5a6.languageparser.rest.model.DeclensionRule;
import by.c7d5a6.languageparser.rest.model.DeclinedWord;
import by.c7d5a6.languageparser.service.DeclensionService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/declension")
@Tag(name = "Declension", description = "Declension related operations")
public class DeclensionController {

    private static final Logger logger = LoggerFactory.getLogger(DeclensionController.class);

    private final DeclensionService declensionService;

    @Autowired
    public DeclensionController(DeclensionService declensionService) {
        this.declensionService = declensionService;
    }

    @Operation(summary = "Get declensions connected with lang and pos")
    @GetMapping("/connections/{languageId}/{posId}")
    public List<DeclensionConnection> getDeclensionConnectionWithLangAndPos(@PathVariable Long languageId, @PathVariable Long posId) {
        logger.info("Get declensions connected with lang {} and pos {}", languageId, posId);
        return declensionService.getDeclensionConnections(languageId, posId);
    }

    @Operation(summary = "Save declensions connected")
    @PostMapping("/connections")
    public Long saveDeclensionConnection(@RequestBody DeclensionConnection declensionConnection) {
        logger.info("Save declensions connected");
        return declensionService.saveDeclensionConnection(declensionConnection);
    }

    @Operation(summary = "Delete declensions connected")
    @DeleteMapping("/connections/{connectionId}")
    public void deleteDeclensionConnection(@PathVariable Long connectionId) {
        logger.info("Delete declensions connected");
        declensionService.deleteDeclensionConnection(connectionId);
    }

    @Operation(summary = "Get declensions by lang and pos")
    @GetMapping("/declensions/{languageId}/{posId}")
    public List<DeclensionFull> getDeclensionsByLangAndPos(@PathVariable Long languageId, @PathVariable Long posId) {
        logger.info("Get declensions by lang {} and pos {}", languageId, posId);
        return declensionService.getFullDeclensions(languageId, posId);
    }

    @Operation(summary = "Save declension")
    @PostMapping("/declension")
    public DeclensionFull saveDeclension(@RequestBody DeclensionFull newDeclension) {
        logger.info("Save declensions connected");
        return declensionService.saveDeclension(newDeclension);
    }

    @Operation(summary = "Delete declension")
    @DeleteMapping("/declension/{declensionId}")
    public void deleteDeclension(@PathVariable Long declensionId) {
        logger.info("Delete declension {}", declensionId);
        declensionService.deleteDeclension(declensionId);
    }

    @Operation(summary = "Get declension rules")
    @GetMapping("/rules/{declensionId}")
    public List<DeclensionRule> getDeclensionRules(@PathVariable Long declensionId) {
        logger.info("Get declension rules {}", declensionId);
        return declensionService.getDeclensionRules(declensionId);
    }

    @Operation(summary = "Is main declension")
    @GetMapping("/ismain/{declensionId}")
    public boolean isMainDelcension(@PathVariable Long declensionId) {
        logger.info("Is main declension {}", declensionId);
        return declensionService.isMainDelcension(declensionId);
    }

    @Operation(summary = "Set as main declension")
    @PostMapping("/setmain/{declensionId}")
    public void setAsMainDeclension(@PathVariable Long declensionId) {
        logger.info("Set as main declension {}", declensionId);
        declensionService.setAsMainDeclension(declensionId);
    }

    @Operation(summary = "Remove from main declension")
    @PostMapping("/removemain/{declensionId}")
    public void removeFromMainDeclension(@PathVariable Long declensionId) {
        logger.info("Remove from main declension {}", declensionId);
        declensionService.removeFromMainDeclension(declensionId);
    }

    @Operation(summary = "Save declension rule")
    @PostMapping("/rule")
    public DeclensionRule saveDeclensionRule(@RequestBody DeclensionRule declensionRule) {
        logger.info("Save declension rule");
        return declensionService.saveDeclensionRule(declensionRule);
    }

    @Operation(summary = "Delete declension rule")
    @DeleteMapping("/rule/{declensionRuleId}")
    public void deleteDeclensionRule(@PathVariable Long declensionRuleId) {
        logger.info("Delete declension rule {}", declensionRuleId);
        declensionService.deleteDeclensionRule(declensionRuleId);
    }

    @Operation(summary = "Get declension rules")
    @GetMapping("/word/{wordId}")
    public DeclinedWord getDeclineWord(@PathVariable Long wordId) {
        logger.info("Decline word {}", wordId);
        return declensionService.getDeclineWord(wordId);
    }
}
