package by.c7d5a6.languageparser.service;

import by.c7d5a6.languageparser.rest.model.Language;
import by.c7d5a6.languageparser.rest.model.WordTraceResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.lang.invoke.MethodHandles;
import java.util.List;

@Service
public class EvolutionService extends BaseService {

    private static final Logger logger = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

    public List<WordTraceResult> trace(String word, List<Language> languages) {
        throw new UnsupportedOperationException("Not implemented yet");
    }
}
