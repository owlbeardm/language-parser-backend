package by.c7d5a6.languageparser.service;

import by.c7d5a6.languageparser.entity.EDeclensionRule;
import by.c7d5a6.languageparser.entity.EGrammaticalCategoryValue;
import by.c7d5a6.languageparser.entity.EGrammaticalValueWordConnection;
import by.c7d5a6.languageparser.entity.EWord;
import by.c7d5a6.languageparser.repository.GrammaticalValueWordRepository;
import com.google.common.base.Strings;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.lang.invoke.MethodHandles;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

@Service
public class DeclensionRuleService extends BaseService {

    private static final Logger logger = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

    private final GrammaticalValueWordRepository grammaticalValueWordRepository;

    @Autowired
    public DeclensionRuleService(GrammaticalValueWordRepository grammaticalValueWordRepository) {
        this.grammaticalValueWordRepository = grammaticalValueWordRepository;
    }

    public List<EGrammaticalValueWordConnection> findGrammaticalValuesByWord(Long wordId) {
        return grammaticalValueWordRepository.findByWord_Id(wordId);
    }

    public boolean isDeclensionRuleApply(EDeclensionRule rule, EWord word) {
        if (!rule.getEnabled())
            return false;
        if (!Strings.isNullOrEmpty(rule.getWordPattern())) {
            Pattern pattern = Pattern.compile(rule.getWordPattern());
            Matcher matcher = pattern.matcher(word.getWord());
            if (!matcher.matches())
                return false;
        }
        if (!rule.getValues().isEmpty()) {
            List<EGrammaticalCategoryValue> wordValues = findGrammaticalValuesByWord(word.getId())
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
