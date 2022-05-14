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
import java.util.Optional;
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
        return all.stream().map((ELanguage lang) -> mapper.map(lang, Language.class)).collect(Collectors.toList());
    }

    public ELanguage getLangById(long fromLangId) {
        return languageRepository.findById(fromLangId).orElseThrow(() -> new IllegalArgumentException("Language " + fromLangId + " not found"));
    }

    public Language saveOrUpdateLanguage(Language language) {
        ELanguage lang;
        if (language.getId() == null) {
            lang = new ELanguage();
        } else {
            lang = languageRepository.findById(language.getId()).orElseThrow(() -> new IllegalArgumentException("Language with id " + language.getId() + " not found"));
        }
        lang.setComment(language.getComment());
        lang.setDisplayName(language.getDisplayName());
        lang.setNativeName(language.getNativeName());
        lang = languageRepository.save(lang);
        return mapper.map(lang, Language.class);
    }

    public void deleteLanguage(Long languageId) {
        ELanguage eLanguage = languageRepository.findById(languageId).orElseThrow(() -> new IllegalArgumentException("Language with id " + languageId + " not found"));
//        languageConnectionRepository.deleteAll(languageConnectionRepository.findByLangTo_Id(languageId));
//        languagePhonemeRepository.deleteAll(languagePhonemeRepository.findByLanguage_Id(languageId));
//        languagePOSRepository.deleteAll(languagePOSRepository.findByLanguage_Id(languageId));
        languageRepository.delete(eLanguage);
    }

    public boolean canDeleteLanguage(Long languageId) {
//        long lc = languageConnectionRepository.countByLangFrom_Id(languageId);
//        long w = wordsRepository.countByLanguage_Id(languageId);
//        return lc + w == 0;
        return false;
    }

    public Optional<ELanguage> getOLangById(Long id) {
        return languageRepository.findById(id);
    }
}
