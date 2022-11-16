package by.c7d5a6.languageparser.service;

import by.c7d5a6.languageparser.entity.EDeclensionConnection;
import by.c7d5a6.languageparser.entity.EGrammaticalCategory;
import by.c7d5a6.languageparser.entity.ELanguage;
import by.c7d5a6.languageparser.entity.EPOS;
import by.c7d5a6.languageparser.repository.DeclensionConnectionRepository;
import by.c7d5a6.languageparser.rest.model.DeclensionConnection;
import by.c7d5a6.languageparser.rest.model.GrammaticalCategory;
import by.c7d5a6.languageparser.rest.security.IsEditor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.webjars.NotFoundException;

import java.lang.invoke.MethodHandles;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class DeclensionService extends BaseService {

    private static final Logger logger = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

    private final POSService posService;
    private final LanguageService languageService;
    private final GrammaticalCategoryService grammaticalCategoryService;
    private final DeclensionConnectionRepository declensionConnectionRepository;

    @Autowired
    public DeclensionService(POSService posService,
                             LanguageService languageService,
                             GrammaticalCategoryService grammaticalCategoryService,
                             DeclensionConnectionRepository declensionConnectionRepository) {
        this.posService = posService;
        this.languageService = languageService;
        this.grammaticalCategoryService = grammaticalCategoryService;
        this.declensionConnectionRepository = declensionConnectionRepository;
    }

    public List<DeclensionConnection> getDeclensionConnections(Long langId, Long posId) {
        return declensionConnectionRepository.findByLanguage_IdAndPos_Id(langId, posId).stream().map((dc) -> mapper.map(dc, DeclensionConnection.class)).collect(Collectors.toList());
    }

    @IsEditor
    public Long saveDeclensionConnection(DeclensionConnection declensionConnection) {
        EGrammaticalCategory eGrammaticalCategory = grammaticalCategoryService.getGrammaticalCategoryById(declensionConnection.getGrammaticalCategory().getId()).orElseThrow(() -> new NotFoundException("Not found grammatical category" + declensionConnection.getGrammaticalCategory().getId()));
        ELanguage eLanguage = languageService.getLangById(declensionConnection.getLanguage().getId()).orElseThrow(() -> new NotFoundException("Not found language" + declensionConnection.getLanguage().getId()));
        EPOS epos = posService.getPosById(declensionConnection.getPos().getId()).orElseThrow(() -> new NotFoundException("Not found pos" + declensionConnection.getPos().getId()));
        EDeclensionConnection result = new EDeclensionConnection();
        result.setLanguage(eLanguage);
        result.setPos(epos);
        result.setGrammaticalCategory(eGrammaticalCategory);
        return declensionConnectionRepository.save(result).getId();
    }

    @IsEditor
    public void deleteDeclensionConnection(Long connectionId) {
        declensionConnectionRepository.deleteById(connectionId);
    }
}
