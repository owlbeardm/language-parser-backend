package by.c7d5a6.languageparser.service;

import by.c7d5a6.languageparser.entity.*;
import by.c7d5a6.languageparser.repository.DeclensionConnectionRepository;
import by.c7d5a6.languageparser.repository.DeclensionRepository;
import by.c7d5a6.languageparser.rest.model.*;
import by.c7d5a6.languageparser.rest.security.IsEditor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.webjars.NotFoundException;

import java.lang.invoke.MethodHandles;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class DeclensionService extends BaseService {

    private static final Logger logger = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

    private final POSService posService;
    private final LanguageService languageService;
    private final GrammaticalCategoryService grammaticalCategoryService;
    private final DeclensionRepository declensionRepository;
    private final DeclensionConnectionRepository declensionConnectionRepository;

    @Autowired
    public DeclensionService(POSService posService,
                             LanguageService languageService,
                             GrammaticalCategoryService grammaticalCategoryService,
                             DeclensionRepository declensionRepository,
                             DeclensionConnectionRepository declensionConnectionRepository) {
        this.posService = posService;
        this.languageService = languageService;
        this.grammaticalCategoryService = grammaticalCategoryService;
        this.declensionRepository = declensionRepository;
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

    public List<DeclensionFull> getFullDeclensions(Long languageId, Long posId) {
        ELanguage eLanguage = languageService.getLangById(languageId).orElseThrow(() -> new NotFoundException("Not found language" + languageId));
        EPOS epos = posService.getPosById(posId).orElseThrow(() -> new NotFoundException("Not found pos" + posId));
        List<List<GrammaticalCategoryValue>> matrix = calculateDeclensionMatrix(languageId, posId);
        List<DeclensionFull> result = declensionRepository.findByLanguage_IdAndPos_Id(languageId, posId).stream().map(ed -> {
            DeclensionFull d = mapper.map(ed, DeclensionFull.class);
            d.setValues(ed.getValues().stream().map(v -> mapper.map(v, GrammaticalCategoryValue.class)).collect(Collectors.toList()));
            d.setExist(true);
            d.setDeprecated(true);
            return d;
        }).collect(Collectors.toList());
        matrix.forEach((values) -> {
            boolean found = false;
            for (DeclensionFull declensionFull : result) {
                boolean isValuesEq = isValuesEqual(values, declensionFull.getValues());
                found = found || isValuesEq;
                declensionFull.setDeprecated(!(!declensionFull.isDeprecated() || isValuesEq));
            }
            if (!found) {
                DeclensionFull df = new DeclensionFull();
                df.setValues(values.stream().map(v -> mapper.map(v, GrammaticalCategoryValue.class)).toList());
                df.setExist(false);
                df.setLanguage(mapper.map(eLanguage, Language.class));
                df.setPos(mapper.map(epos, POS.class));
                result.add(df);
            }
        });
        return result;
    }

    private List<List<GrammaticalCategoryValue>> calculateDeclensionMatrix(Long languageId, Long posId) {
        List<DeclensionConnection> declensionConnections = getDeclensionConnections(languageId, posId);
        List<List<GrammaticalCategoryValue>> collect = declensionConnections.stream().map(dc -> {
            GrammaticalCategory grammaticalCategory = dc.getGrammaticalCategory();
            return grammaticalCategoryService.getCategoryValuesByCategoryAndLang(grammaticalCategory.getId(), languageId);
        }).collect(Collectors.toList());
        List<List<GrammaticalCategoryValue>> matrix = new ArrayList<>();
        matrix.add(new ArrayList<>());
        for (List<GrammaticalCategoryValue> values : collect) {
            List<List<GrammaticalCategoryValue>> newMatrix = new ArrayList<>();
            matrix.forEach((list) -> values.forEach((value) -> {
                List<GrammaticalCategoryValue> listToAdd = new ArrayList<>(list);
                listToAdd.add(value);
                newMatrix.add(listToAdd);
            }));
            matrix = newMatrix;
        }
        return matrix;
    }

    private boolean isValuesEqual(List<GrammaticalCategoryValue> matrixValues, List<GrammaticalCategoryValue> declensionValues) {
        if (matrixValues.size() != declensionValues.size()) {
            return false;
        }
        return matrixValues
                .stream()
                .map(mValue ->
                        declensionValues
                                .stream()
                                .anyMatch(dValue ->
                                        mValue.getId().equals(dValue.getId())))
                .reduce((a, b) -> a && b)
                .orElse(false);
    }

    @IsEditor
    public DeclensionFull saveDeclension(DeclensionFull declension) {
        final EDeclension eDeclension = new EDeclension();
        ELanguage eLanguage = languageService.getLangById(declension.getLanguage().getId()).orElseThrow(() -> new NotFoundException("Not found language" + declension.getLanguage().getId()));
        EPOS epos = posService.getPosById(declension.getPos().getId()).orElseThrow(() -> new NotFoundException("Not found pos" + declension.getPos().getId()));
        eDeclension.setLanguage(eLanguage);
        eDeclension.setPos(epos);
        declension.getValues().forEach((value) -> {
            EGrammaticalCategoryValue eGrammaticalCategoryValue = grammaticalCategoryService.getValueById(value.getId()).orElseThrow(() -> new NotFoundException("Not found value " + value.getId()));
            eDeclension.getValues().add(eGrammaticalCategoryValue);
        });

        final EDeclension savedDeclension = declensionRepository.saveAndFlush(eDeclension);
        DeclensionFull result = mapper.map(savedDeclension, DeclensionFull.class);
        result.setValues(savedDeclension.getValues().stream().map(v -> mapper.map(v, GrammaticalCategoryValue.class)).collect(Collectors.toList()));
        result.setExist(true);
        result.setDeprecated(true);
        List<List<GrammaticalCategoryValue>> matrix = calculateDeclensionMatrix(savedDeclension.getLanguage().getId(), savedDeclension.getPos().getId());
        matrix.forEach((values) -> {
            boolean isValuesEq = isValuesEqual(values, result.getValues());
            result.setDeprecated(!(!result.isDeprecated() || isValuesEq));
        });
        return result;
    }

    @IsEditor
    public void deleteDeclension(Long declensionId) {
        declensionRepository.deleteById(declensionId);
    }

}
