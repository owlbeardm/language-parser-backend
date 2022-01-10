package by.c7d5a6.languageparser.service;

import by.c7d5a6.languageparser.entity.ELanguage;
import by.c7d5a6.languageparser.repository.LanguageRepository;
import by.c7d5a6.languageparser.rest.model.Language;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.lang.invoke.MethodHandles;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class LanguageService extends BaseService {

    private static final Logger logger = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

    private final LanguageRepository languageRepository;

    @Autowired
    public LanguageService(LanguageRepository languageRepository) {
        this.languageRepository = languageRepository;
    }

    public List<Language> getAllLanguages() {
        List<ELanguage> all = languageRepository.findAll();
        return all.stream().map(this::convertToRestModel).collect(Collectors.toList());
    }

    private Language convertToRestModel(ELanguage eLang) {
        Language language = new Language();
        language.setId(eLang.getId());
        language.setVersion(eLang.getVersion());
        language.setDisplayName(eLang.getDisplayName());
        language.setNativeName(eLang.getNativeName());
        language.setComment(eLang.getComment());
        return language;
    }

    public List<List<Language>> getAllPaths(Language from, Language to) {
        throw new UnsupportedOperationException("Not implemented yet");
    }

    public List<Language> getAllLanguagesFrom(Language from) {
        throw new UnsupportedOperationException("Not implemented yet");
    }
}
