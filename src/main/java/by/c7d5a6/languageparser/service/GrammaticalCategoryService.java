package by.c7d5a6.languageparser.service;

import by.c7d5a6.languageparser.entity.EGrammaticalCategory;
import by.c7d5a6.languageparser.entity.EGrammaticalCategoryConnection;
import by.c7d5a6.languageparser.entity.EGrammaticalCategoryValue;
import by.c7d5a6.languageparser.repository.GrammaticalCategoryConnectionRepository;
import by.c7d5a6.languageparser.repository.GrammaticalCategoryRepository;
import by.c7d5a6.languageparser.repository.GrammaticalCategoryValueRepository;
import by.c7d5a6.languageparser.rest.model.GrammaticalCategory;
import by.c7d5a6.languageparser.rest.model.GrammaticalCategoryConnection;
import by.c7d5a6.languageparser.rest.model.GrammaticalCategoryValue;
import by.c7d5a6.languageparser.rest.security.IsEditor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.lang.invoke.MethodHandles;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class GrammaticalCategoryService extends BaseService {

    private static final Logger logger = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

    private final GrammaticalCategoryRepository grammaticalCategoryRepository;
    private final GrammaticalCategoryValueRepository grammaticalCategoryValueRepository;
    private final GrammaticalCategoryConnectionRepository grammaticalCategoryConnectionRepository;

    @Autowired
    public GrammaticalCategoryService(GrammaticalCategoryRepository grammaticalCategoryRepository, GrammaticalCategoryValueRepository grammaticalCategoryValueRepository, GrammaticalCategoryConnectionRepository grammaticalCategoryConnectionRepository) {
        this.grammaticalCategoryRepository = grammaticalCategoryRepository;
        this.grammaticalCategoryValueRepository = grammaticalCategoryValueRepository;
        this.grammaticalCategoryConnectionRepository = grammaticalCategoryConnectionRepository;
    }

    public List<GrammaticalCategory> getAllCategories() {
        return grammaticalCategoryRepository.findAll().stream().map((egc) -> mapper.map(egc, GrammaticalCategory.class)).collect(Collectors.toList());
    }

    public List<GrammaticalCategoryValue> getCategoryValuesByCategory(Long categoryId) {
        return grammaticalCategoryValueRepository.findByGrammaticalCategory_Id(categoryId).stream().map((egcv) -> mapper.map(egcv, GrammaticalCategoryValue.class)).collect(Collectors.toList());
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
            ecategoryValue.setGrammaticalCategory(category);
        }
        ecategoryValue.setName(grammaticalCategoryValue.getName());
        EGrammaticalCategoryValue result = grammaticalCategoryValueRepository.save(ecategoryValue);
        return result.getId();
    }

    public List<GrammaticalCategoryConnection> getGrammaticalCategoryConnectionsForLang(Long categoryId, Long languageId) {
        List<EGrammaticalCategoryConnection> connections = grammaticalCategoryConnectionRepository.findByGrammaticalCategory_IdAndLanguage_Id(categoryId, languageId);
        return connections.stream().map((con) -> mapper.map(con, GrammaticalCategoryConnection.class)).collect(Collectors.toList());
    }

    public Long saveGrammaticalCategoryConnection(GrammaticalCategoryConnection grammaticalCategoryConnection) {
        EGrammaticalCategoryConnection connection = mapper.map(grammaticalCategoryConnection, EGrammaticalCategoryConnection.class);
        return grammaticalCategoryConnectionRepository.save(connection).getId();
    }

    public void deleteGrammaticalCategoryConnection(Long connectionId) {
        grammaticalCategoryConnectionRepository.deleteById(connectionId);
    }
}