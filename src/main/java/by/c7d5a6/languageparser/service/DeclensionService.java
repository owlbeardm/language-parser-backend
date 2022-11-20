package by.c7d5a6.languageparser.service;

import by.c7d5a6.languageparser.entity.*;
import by.c7d5a6.languageparser.repository.DeclensionConnectionRepository;
import by.c7d5a6.languageparser.repository.DeclensionRepository;
import by.c7d5a6.languageparser.repository.DeclensionRuleRepository;
import by.c7d5a6.languageparser.rest.model.*;
import by.c7d5a6.languageparser.rest.security.IsEditor;
import com.google.common.base.Strings;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.webjars.NotFoundException;

import java.lang.invoke.MethodHandles;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

@Service
public class DeclensionService extends BaseService {

    private static final Logger logger = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

    private final POSService posService;
    private final WordService wordService;
    private final LanguageService languageService;
    private final SoundChangesService soundChangesService;
    private final GrammaticalCategoryService grammaticalCategoryService;
    private final DeclensionRepository declensionRepository;
    private final DeclensionRuleRepository declensionRuleRepository;
    private final DeclensionConnectionRepository declensionConnectionRepository;

    @Autowired
    public DeclensionService(POSService posService,
                             WordService wordService,
                             LanguageService languageService,
                             SoundChangesService soundChangesService,
                             GrammaticalCategoryService grammaticalCategoryService,
                             DeclensionRepository declensionRepository,
                             DeclensionRuleRepository declensionRuleRepository,
                             DeclensionConnectionRepository declensionConnectionRepository) {
        this.posService = posService;
        this.wordService = wordService;
        this.languageService = languageService;
        this.soundChangesService = soundChangesService;
        this.grammaticalCategoryService = grammaticalCategoryService;
        this.declensionRepository = declensionRepository;
        this.declensionRuleRepository = declensionRuleRepository;
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

    public List<DeclensionRule> getDeclensionRules(Long declensionId) {
        return declensionRuleRepository.findByDeclension_Id(declensionId).stream().map(this::convertDeclensionRule).collect(Collectors.toList());
    }

    private DeclensionRule convertDeclensionRule(EDeclensionRule edr) {
        DeclensionRule result = mapper.map(edr, DeclensionRule.class);
        result.getDeclension().setValues(edr.getDeclension().getValues().stream().map(v -> mapper.map(v, GrammaticalCategoryValue.class)).collect(Collectors.toList()));
        result.setValues(edr.getValues().stream().map(v -> mapper.map(v, GrammaticalCategoryValue.class)).collect(Collectors.toList()));
        return result;
    }

    @IsEditor
    public DeclensionRule saveDeclensionRule(DeclensionRule declensionRule) {
        EDeclensionRule eDeclensionRule;
        if (declensionRule.getId() != null) {
            eDeclensionRule = declensionRuleRepository.findById(declensionRule.getId()).orElseThrow(() -> new NotFoundException("No declension rule"));
        } else {
            eDeclensionRule = new EDeclensionRule();
            EDeclension eDeclension = declensionRepository.findById(declensionRule.getDeclension().getId()).orElseThrow(() -> new NotFoundException("No declension"));
            eDeclensionRule.setDeclension(eDeclension);
        }
        eDeclensionRule.setName(declensionRule.getName());
        eDeclensionRule.setEnabled(declensionRule.getEnabled());
        eDeclensionRule.setWordPattern(declensionRule.getWordPattern());
        eDeclensionRule.setValues(new HashSet<>());
        if (declensionRule.getValues() != null)
            for (GrammaticalCategoryValue value : declensionRule.getValues()) {
                EGrammaticalCategoryValue eGrammaticalCategoryValue = grammaticalCategoryService.getValueById(value.getId()).orElseThrow(() -> new NotFoundException("Not found value " + value.getId()));
                eDeclensionRule.getValues().add(eGrammaticalCategoryValue);
            }
        EDeclensionRule save = declensionRuleRepository.save(eDeclensionRule);
        return convertDeclensionRule(save);
    }

    @IsEditor
    public void deleteDeclensionRule(Long declensionRuleId) {
        declensionRuleRepository.deleteById(declensionRuleId);
    }

    public DeclinedWord getDeclineWord(Long wordId) {
        final DeclinedWord result = new DeclinedWord();
        result.setDeclensionList(new ArrayList<>());
        EWord word = this.wordService.getWordById(wordId);
        List<DeclensionFull> fullDeclensions = getFullDeclensions(word.getLanguage().getId(), word.getPartOfSpeech().getId());
        fullDeclensions.forEach(declensionFull -> {
            if (declensionFull.isDeprecated() || !declensionFull.isExist()) {
                return;
            }
            EDeclension declension = declensionRepository.getById(declensionFull.getId());
            List<EDeclensionRule> rules = declensionRuleRepository.findByDeclension_Id(declension.getId());
            List<String> declinedWords = new ArrayList<>();
            rules.forEach((rule) -> {
                if (isRuleApply(rule, word)) {
                    String changedByRule = this.soundChangesService.changeWordByRule(word.getWord(), rule);
                    declinedWords.add(changedByRule);
                }
            });
            if (!declinedWords.isEmpty()) {
                WordDeclension wd = new WordDeclension();
                wd.setDeclension(mapper.map(declension, Declension.class));
                wd.setWordDeclensions(declinedWords);
                result.getDeclensionList().add(wd);
            }
        });
        return result;
    }

    private boolean isRuleApply(EDeclensionRule rule, EWord word) {
        if (!Strings.isNullOrEmpty(rule.getWordPattern())) {
            Pattern pattern = Pattern.compile(rule.getWordPattern());
            Matcher matcher = pattern.matcher(word.getWord());
            if (!matcher.matches())
                return false;
        }
        if (!rule.getValues().isEmpty()) {
            List<EGrammaticalCategoryValue> wordValues = grammaticalCategoryService
                    .findGrammaticalValuesByWord(word.getId())
                    .stream()
                    .map(EGrammaticalValueWordConnection::getValue)
                    .collect(Collectors.toList());
            for (EGrammaticalCategoryValue value : rule.getValues()) {
                if (wordValues.stream().noneMatch((wordValue) -> wordValue.getId().equals(value.getId())))
                    return false;
            }
        }
        return true;
    }
}
