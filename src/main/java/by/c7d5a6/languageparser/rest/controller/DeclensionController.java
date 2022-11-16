package by.c7d5a6.languageparser.rest.controller;

import by.c7d5a6.languageparser.rest.model.DeclensionConnection;
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
}
