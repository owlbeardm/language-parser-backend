package by.c7d5a6.languageparser.service;

import by.c7d5a6.languageparser.rest.model.Language;
import by.c7d5a6.languageparser.rest.model.WordTraceResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.lang.invoke.MethodHandles;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class EvolutionService extends BaseService {

    private static final Logger logger = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

    public List<WordTraceResult> trace(String word, List<Language> languages) {
        // TODO: implement
        return languages.stream().map(language -> {return new WordTraceResult(language, word);}).collect(Collectors.toList());
    }
}
