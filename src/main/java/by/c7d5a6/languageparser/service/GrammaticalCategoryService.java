package by.c7d5a6.languageparser.service;

import by.c7d5a6.languageparser.entity.*;
import by.c7d5a6.languageparser.repository.*;
import by.c7d5a6.languageparser.rest.model.*;
import by.c7d5a6.languageparser.rest.security.IsEditor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.webjars.NotFoundException;

import java.lang.invoke.MethodHandles;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class GrammaticalCategoryService extends BaseService {

    private static final Logger logger = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

    private final WordService wordService;
    private final LanguageService languageService;
    private final GrammaticalCategoryRepository grammaticalCategoryRepository;
    private final GrammaticalCategoryValueRepository grammaticalCategoryValueRepository;
    private final GrammaticalCategoryConnectionRepository grammaticalCategoryConnectionRepository;
    private final GrammaticalValueWordRepository grammaticalValueWordRepository;
    private final GrammaticalCategoryValueConnectionRepository grammaticalCategoryValueConnectionRepository;
    private final GrammaticalValueEvolutionRepository grammaticalValueEvolutionRepository;

    @Autowired
    public GrammaticalCategoryService(WordService wordService,
                                      LanguageService languageService,
                                      GrammaticalCategoryRepository grammaticalCategoryRepository,
                                      GrammaticalCategoryValueRepository grammaticalCategoryValueRepository,
                                      GrammaticalCategoryConnectionRepository grammaticalCategoryConnectionRepository,
                                      GrammaticalValueWordRepository grammaticalValueWordRepository,
                                      GrammaticalCategoryValueConnectionRepository grammaticalCategoryValueConnectionRepository,
                                      GrammaticalValueEvolutionRepository grammaticalValueEvolutionRepository) {
        this.wordService = wordService;
        this.languageService = languageService;
        this.grammaticalCategoryRepository = grammaticalCategoryRepository;
        this.grammaticalCategoryValueRepository = grammaticalCategoryValueRepository;
        this.grammaticalCategoryConnectionRepository = grammaticalCategoryConnectionRepository;
        this.grammaticalValueWordRepository = grammaticalValueWordRepository;
        this.grammaticalCategoryValueConnectionRepository = grammaticalCategoryValueConnectionRepository;
        this.grammaticalValueEvolutionRepository = grammaticalValueEvolutionRepository;
    }

    public List<GrammaticalCategory> getAllCategories() {
        return grammaticalCategoryRepository.findAll().stream().map((egc) -> mapper.map(egc, GrammaticalCategory.class)).collect(Collectors.toList());
    }

    public List<GrammaticalCategoryValue> getCategoryValuesByCategory(Long categoryId) {
        return grammaticalCategoryValueRepository.findByCategory_Id(categoryId).stream().map((egcv) -> mapper.map(egcv, GrammaticalCategoryValue.class)).collect(Collectors.toList());
    }

    public List<GrammaticalCategoryValue> getCategoryValuesByCategoryAndLang(Long categoryId, Long langId) {
        return grammaticalCategoryValueRepository.findByCategoryAndLang(categoryId, langId).stream().map((egcv) -> mapper.map(egcv, GrammaticalCategoryValue.class)).collect(Collectors.toList());
    }

    @IsEditor
    public Long saveGrammaticalCategory(GrammaticalCategory grammaticalCategory) {
        EGrammaticalCategory ecategory;
        if (grammaticalCategory.getId() != null) {
            ecategory = grammaticalCategoryRepository.getById(grammaticalCategory.getId());
        } else {
            ecategory = new EGrammaticalCategory();
        }
        ecategory.setName(grammaticalCategory.getName());
        ecategory.setComment(grammaticalCategory.getComment());
        EGrammaticalCategory result = grammaticalCategoryRepository.save(ecategory);
        return result.getId();
    }

    @IsEditor
    public Long saveGrammaticalCategoryValue(GrammaticalCategoryValue grammaticalCategoryValue) {
        EGrammaticalCategoryValue ecategoryValue;
        if (grammaticalCategoryValue.getId() != null) {
            ecategoryValue = grammaticalCategoryValueRepository.getById(grammaticalCategoryValue.getId());
        } else {
            EGrammaticalCategory category = grammaticalCategoryRepository.getById(grammaticalCategoryValue.getCategory().getId());
            ecategoryValue = new EGrammaticalCategoryValue();
            ecategoryValue.setCategory(category);
        }
        ecategoryValue.setName(grammaticalCategoryValue.getName());
        EGrammaticalCategoryValue result = grammaticalCategoryValueRepository.save(ecategoryValue);
        return result.getId();
    }

    public List<GrammaticalCategoryConnection> getGrammaticalCategoryConnectionsForLang(Long categoryId, Long languageId) {
        List<EGrammaticalCategoryConnection> connections = grammaticalCategoryConnectionRepository.findByGrammaticalCategory_IdAndLanguage_Id(categoryId, languageId);
        return connections.stream().map((con) -> mapper.map(con, GrammaticalCategoryConnection.class)).collect(Collectors.toList());
    }

    @IsEditor
    public Long saveGrammaticalCategoryConnection(GrammaticalCategoryConnection grammaticalCategoryConnection) {
        EGrammaticalCategoryConnection connection = mapper.map(grammaticalCategoryConnection, EGrammaticalCategoryConnection.class);
        return grammaticalCategoryConnectionRepository.save(connection).getId();
    }

    @IsEditor
    public void deleteGrammaticalCategoryConnection(Long connectionId) {
        grammaticalCategoryConnectionRepository.deleteById(connectionId);
    }

    public List<GrammaticalValueWordConnection> getGrammaticalValuesByWord(Long wordId) {
        return grammaticalValueWordRepository.findByWord_Id(wordId).stream().map(vwc -> mapper.map(vwc, GrammaticalValueWordConnection.class)).collect(Collectors.toList());
    }

    @IsEditor
    public GrammaticalValueWordConnection replaceGrammaticalValuesByWord(GrammaticalValueWordConnection grammaticalValueWordConnection) {
        grammaticalValueWordRepository.deleteAllByWord_IdAndValue_Category_Id(grammaticalValueWordConnection.getWord().getId(), grammaticalValueWordConnection.getValue().getCategory().getId());
        EGrammaticalValueWordConnection connection = new EGrammaticalValueWordConnection();
        EGrammaticalCategoryValue eGrammaticalCategoryValue = grammaticalCategoryValueRepository.findById(grammaticalValueWordConnection.getValue().getId()).orElseThrow(() -> new IllegalArgumentException("no category value with id " + grammaticalValueWordConnection.getValue().getId()));
        EWord word = wordService.getWordById(grammaticalValueWordConnection.getWord().getId());
        connection.setWord(word);
        connection.setValue(eGrammaticalCategoryValue);
        EGrammaticalValueWordConnection result = grammaticalValueWordRepository.save(connection);
        return mapper.map(result, GrammaticalValueWordConnection.class);
    }

    @IsEditor
    public void removeGrammaticalValuesByWord(Long wordId, Long categoryId) {
        grammaticalValueWordRepository.deleteAllByWord_IdAndValue_Category_Id(wordId, categoryId);
    }

    public List<GrammaticalCategoryValueConnection> getGrammaticalValuesConnectionByLang(Long langId) {
        return grammaticalCategoryValueConnectionRepository.findByLanguage_Id(langId).stream().map(gcvc -> mapper.map(gcvc, GrammaticalCategoryValueConnection.class)).collect(Collectors.toList());
    }

    @IsEditor
    public Long saveGrammaticalValuesConnection(GrammaticalCategoryValueConnection grammaticalCategoryValueConnection) {
        EGrammaticalCategoryValue eGrammaticalCategoryValue = grammaticalCategoryValueRepository.findById(grammaticalCategoryValueConnection.getValue().getId()).orElseThrow(() -> new NotFoundException("Not found value" + grammaticalCategoryValueConnection.getValue().getId()));
        ELanguage eLanguage = languageService.getLangById(grammaticalCategoryValueConnection.getLanguage().getId()).orElseThrow(() -> new NotFoundException("Not found language" + grammaticalCategoryValueConnection.getLanguage().getId()));
        EGrammaticalCategoryValueConnection result = new EGrammaticalCategoryValueConnection();
        result.setLanguage(eLanguage);
        result.setValue(eGrammaticalCategoryValue);
        return grammaticalCategoryValueConnectionRepository.save(result).getId();
    }

    @IsEditor
    public void removeGrammaticalValuesConnectionById(Long grammaticalCategoryValueConnectionId) {
        grammaticalCategoryValueConnectionRepository.deleteById(grammaticalCategoryValueConnectionId);
    }

    public List<GrammaticalCategoryConnection> getGrammaticalCategoryConnectionsForLangAndPos(Long posId, Long languageId) {
        List<EGrammaticalCategoryConnection> connections = grammaticalCategoryConnectionRepository.findByPos_IdAndLanguage_Id(posId, languageId);
        return connections.stream().map((con) -> mapper.map(con, GrammaticalCategoryConnection.class)).collect(Collectors.toList());
    }

    public GrammaticalValueEvolution getGrammaticalValueEvolution(Long langFromId, Long langToId, Long posId, Long valueFromId) {
        Optional<EGrammaticalValueEvolution> byLanguageFrom_idAndLanguageTo_idAndPos_idAndValueFrom_id = grammaticalValueEvolutionRepository.findByLanguageFrom_IdAndLanguageTo_IdAndPos_IdAndValueFrom_Id(langFromId, langToId, posId, valueFromId);
        if (byLanguageFrom_idAndLanguageTo_idAndPos_idAndValueFrom_id.isEmpty()) {
            return null;
        }
        return mapper.map(byLanguageFrom_idAndLanguageTo_idAndPos_idAndValueFrom_id.get(), GrammaticalValueEvolution.class);
    }

    @IsEditor
    public Long saveGrammaticalValueEvolution(GrammaticalValueEvolution grammaticalValueEvolution) {
        EGrammaticalValueEvolution gve = mapper.map(grammaticalValueEvolution, EGrammaticalValueEvolution.class);
        return grammaticalValueEvolutionRepository.save(gve).getId();
    }

    @IsEditor
    public void removeGrammaticalValueEvolution(Long id) {
        grammaticalValueEvolutionRepository.deleteById(id);
    }
}
