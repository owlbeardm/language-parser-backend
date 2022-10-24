package by.c7d5a6.languageparser.rest.controller;

import by.c7d5a6.languageparser.entity.EGrammaticalValueEvolution;
import by.c7d5a6.languageparser.enums.SoundChangePurpose;
import by.c7d5a6.languageparser.rest.model.*;
import by.c7d5a6.languageparser.rest.model.base.PageResult;
import by.c7d5a6.languageparser.rest.model.filter.WordBorrowedListFilter;
import by.c7d5a6.languageparser.rest.model.filter.WordWithEvolutionsListFilter;
import by.c7d5a6.languageparser.rest.security.IsEditor;
import by.c7d5a6.languageparser.service.EvolutionService;
import by.c7d5a6.languageparser.service.GrammaticalCategoryService;
import by.c7d5a6.languageparser.service.LanguageService;
import by.c7d5a6.languageparser.service.SoundChangesService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/evolve")
@Tag(name = "Languages evolution", description = "Languages evolution related operations")
public class EvolveController {

    private static final Logger logger = LoggerFactory.getLogger(LanguageController.class);

    private final EvolutionService evolutionService;
    private final LanguageService languageService;
    private final SoundChangesService soundChangesService;
    private final GrammaticalCategoryService grammaticalCategoryService;

    @Autowired
    public EvolveController(EvolutionService evolutionService, LanguageService languageService, SoundChangesService soundChangesService, GrammaticalCategoryService grammaticalCategoryService) {
        this.evolutionService = evolutionService;
        this.languageService = languageService;
        this.soundChangesService = soundChangesService;
        this.grammaticalCategoryService = grammaticalCategoryService;
    }

    @Operation(summary = "Get connections")
    @GetMapping("/connection/all")
    public List<LanguageConnection> getConnections() {
        logger.info("Get connections");
        return languageService.getConnections();
    }

    @Operation(summary = "Get connection between two languages")
    @GetMapping("/connection/{fromLangId}/{toLangId}")
    public LanguageConnection getConnectionByLangs(@PathVariable long fromLangId, @PathVariable long toLangId) {
        logger.info("Get connection between {} and {}", fromLangId, toLangId);
        return languageService.getConnection(fromLangId, toLangId);
    }

    @Operation(summary = "Get connections from language")
    @GetMapping("/connection/from/{fromLangId}")
    public List<LanguageConnection> getConnectionFromLang(@PathVariable long fromLangId) {
        logger.info("Get connections from {}", fromLangId);
        return languageService.getConnectionsFrom(fromLangId);
    }

    @Operation(summary = "Get connections from language")
    @GetMapping("/connection/to/{toLangId}")
    public List<LanguageConnection> getConnectionToLang(@PathVariable long toLangId) {
        logger.info("Get connections to {}", toLangId);
        return languageService.getConnectionsTo(toLangId);
    }

    @Operation(summary = "Update connection between two languages")
    @PostMapping(value = "/connection/{fromLangId}/{toLangId}")
    public void updateConnectionByLangs(@PathVariable long fromLangId, @PathVariable long toLangId, @RequestBody LanguageConnectionTypeModel connectionType) {
        logger.info("Update connection between {} and {}", fromLangId, toLangId);
        languageService.updateConnection(fromLangId, toLangId, connectionType.getLanguageConnectionType());
    }

    @Operation(summary = "Delete connection between two languages")
    @DeleteMapping("/connection/{fromLangId}/{toLangId}")
    public void deleteConnectionByLangs(@PathVariable long fromLangId, @PathVariable long toLangId) {
        logger.info("Delete connection between {} and {}", fromLangId, toLangId);
        languageService.deleteConnection(fromLangId, toLangId);
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

    @Operation(summary = "Get sound change")
    @GetMapping("/sc/{id}")
    public SoundChange getSoundChange(@PathVariable long id) {
        logger.info("Get sound change {}", id);
        return soundChangesService.getSoundChange(id);
    }

    @Operation(summary = "Delete sound change")
    @DeleteMapping("/sc/{id}")
    public void deleteSoundChange(@PathVariable long id) {
        logger.info("Delete sound change {}", id);
        soundChangesService.deleteSoundChange(id);
    }

    @Operation(summary = "Get sound change raw")
    @GetMapping(value = "/sc/raw/{id}", produces = "text/plain")
    public String getSoundChangeRaw(@PathVariable long id) {
        logger.info("Get sound change raw {}", id);
        return soundChangesService.getSoundChangeRaw(id);
    }

    @Operation(summary = "Update sound change from text form")
    @PostMapping("/sc/raw/{id}")
    public void updateSoundChange(@PathVariable long id, @RequestBody String rawLine) {
        logger.info("Update sound change {}", id);
        soundChangesService.updateSoundChange(id, rawLine);
    }

    @Operation(summary = "Get all sound changes")
    @GetMapping("/sc/lang/{soundChangePurpose}/{fromLangId}/{toLangId}")
    public List<SoundChange> getSoundChangesByLangs(@PathVariable SoundChangePurpose soundChangePurpose, @PathVariable long fromLangId, @PathVariable long toLangId) {
        logger.info("Get all sound changes from {} to {}, {}", fromLangId, toLangId, soundChangePurpose);
        return soundChangesService.getSoundChangesByLangs(fromLangId, toLangId, soundChangePurpose);
    }

    @Operation(summary = "Get all sound changes")
    @GetMapping("/sc/lang/{soundChangePurpose}/{fromLangId}")
    public List<SoundChange> getSoundChangesByLang(@PathVariable SoundChangePurpose soundChangePurpose, @PathVariable long fromLangId) {
        logger.info("Get all sound changes from {}, {}", fromLangId, soundChangePurpose);
        return soundChangesService.getSoundChangesByLang(fromLangId, soundChangePurpose);
    }

    @Operation(summary = "Save all sound changes from text form")
    @PostMapping("/sc/raw/lang/{soundChangePurpose}/{fromLangId}/{toLangId}")
    public void saveSoundChangesRawLinesByLangs(@PathVariable SoundChangePurpose soundChangePurpose, @PathVariable long fromLangId, @PathVariable long toLangId, @RequestBody String rawLines) {
        logger.info("Save all sound changes from text form from {}, {}", fromLangId, toLangId, soundChangePurpose);
        soundChangesService.saveSoundChangesRawLinesByLangs(fromLangId, toLangId, rawLines, soundChangePurpose);
    }

    @Operation(summary = "Save all sound changes from text form")
    @PostMapping("/sc/raw/lang/{soundChangePurpose}/{fromLangId}")
    public void saveSoundChangesRawLinesByLang(@PathVariable SoundChangePurpose soundChangePurpose, @PathVariable long fromLangId, @RequestBody String rawLines) {
        logger.info("Save all sound changes from text form from {}, {}", fromLangId, soundChangePurpose);
        soundChangesService.saveSoundChangesRawLinesByLangs(fromLangId, null, rawLines, soundChangePurpose);
    }

    @Operation(summary = "Get all sound changes in text form")
    @GetMapping(value = "/sc/raw/lang/{soundChangePurpose}/{fromLangId}/{toLangId}", produces = "text/plain")
    public String getSoundChangesRawLinesByLangs(@PathVariable SoundChangePurpose soundChangePurpose, @PathVariable long fromLangId, @PathVariable long toLangId) {
        logger.info("Get all sound changes in text form from {} to {}, {}", fromLangId, toLangId, soundChangePurpose);
        return soundChangesService.getSoundChangesRawLinesByLangs(fromLangId, toLangId, soundChangePurpose);
    }

    @Operation(summary = "Get all sound changes in text form")
    @GetMapping(value = "/sc/raw/lang/{soundChangePurpose}/{fromLangId}", produces = "text/plain")
    public String getSoundChangesRawLinesByLang(@PathVariable SoundChangePurpose soundChangePurpose, @PathVariable long fromLangId) {
        logger.info("Get all sound changes in text form from {}, {}", fromLangId, soundChangePurpose);
        return soundChangesService.getSoundChangesRawLinesByLangs(fromLangId, null, soundChangePurpose);
    }

    @Operation(summary = "Get all words with evolutions")
    @GetMapping("/words")
    public PageResult<WordWithEvolution> getAllWordsWithEvolutions(@Valid WordWithEvolutionsListFilter filter) {
        logger.info("Get all words with evolutions");
        return evolutionService.getAllWordsWithEvolutions(filter);
    }

    @Operation(summary = "Get all words")
    @GetMapping("/words/borrowed")
    public PageResult<WordWithBorrowed> getAllWords(@Valid WordBorrowedListFilter filter) {
        return evolutionService.getAllBorrowedWords(filter);
    }

    @Operation(summary = "Evolve word")
    @PostMapping("/words/evolve")
    @IsEditor
    public WordWithEvolution addEvolvedWord(@RequestBody WordToEvolve wordToEvolve) {
        return evolutionService.addEvolvedWord(wordToEvolve.getWord(), wordToEvolve.getLanguageConnection()).stream().findFirst().orElseThrow(() -> new RuntimeException("No result"));
    }

    @Operation(summary = "Evolve word")
    @PostMapping("/words/borrow")
    @IsEditor
    public WordWithBorrowed addBorrowedWord(@RequestBody WordToBorrow wordToBorrow) {
        return evolutionService.addBorrowedWord(wordToBorrow);
    }

    @Operation(summary = "Evolve all words")
    @PostMapping("/words/evolve/all")
    public List<WordWithEvolution> addEvolvedWord(@RequestBody List<WordToEvolve> wordToEvolve) {
        return evolutionService.addEvolvedWords(wordToEvolve);
    }

    @Operation(summary = "Get language connection graph")
    @GetMapping("/graph")
    public String getLanguageGraph() {
        return evolutionService.getGraph();
    }

    @Operation(summary = "Get grammatical value evolution")
    @GetMapping("/grammaticalvalue/{langFromId}/{langToId}/{posId}/{valueFromId}")
    public GrammaticalValueEvolution getGrammaticalValueEvolution(@PathVariable Long langFromId, @PathVariable Long langToId, @PathVariable Long posId, @PathVariable Long valueFromId) {
        logger.info("Get grammatical value evolution lang from {}, lang to {}, pos {}, value from {}", langFromId, langToId, posId, valueFromId);
        return grammaticalCategoryService.getGrammaticalValueEvolution(langFromId, langToId, posId, valueFromId);
    }

    @Operation(summary = "Save grammatical value evolution")
    @PostMapping("/grammaticalvalue")
    public Long saveGrammaticalValueEvolution(@RequestBody GrammaticalValueEvolution grammaticalValueEvolution) {
        logger.info("Save grammatical value evolution");
        return grammaticalCategoryService.saveGrammaticalValueEvolution(grammaticalValueEvolution);
    }

    @Operation(summary = "Delete grammatical value evolution")
    @DeleteMapping("/grammaticalvalue/{id}")
    public void removeGrammaticalValueEvolution(@PathVariable Long id) {
        logger.info("Save grammatical value evolution");
        grammaticalCategoryService.removeGrammaticalValueEvolution(id);
    }

}
