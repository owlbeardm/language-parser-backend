package by.c7d5a6.languageparser.service;

import by.c7d5a6.languageparser.entity.EGrammaticalCategory;
import by.c7d5a6.languageparser.entity.ELanguage;
import by.c7d5a6.languageparser.entity.ELanguagePOS;
import by.c7d5a6.languageparser.entity.EPOS;
import by.c7d5a6.languageparser.repository.LanguagePOSRepository;
import by.c7d5a6.languageparser.repository.LanguageRepository;
import by.c7d5a6.languageparser.repository.POSRepository;
import by.c7d5a6.languageparser.repository.WordsRepository;
import by.c7d5a6.languageparser.rest.model.LanguagePOS;
import by.c7d5a6.languageparser.rest.model.POS;
import by.c7d5a6.languageparser.rest.security.IsEditor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.lang.invoke.MethodHandles;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class POSService extends BaseService {

    private static final Logger logger = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

    private final LanguageRepository languageRepository;
    private final POSRepository posRepository;
    private final LanguagePOSRepository languagePOSRepository;
    private final WordsRepository wordsRepository;

    @Autowired
    public POSService(LanguageRepository languageRepository, POSRepository posRepository, LanguagePOSRepository languagePOSRepository, WordsRepository wordsRepository) {
        this.languageRepository = languageRepository;
        this.posRepository = posRepository;
        this.languagePOSRepository = languagePOSRepository;
        this.wordsRepository = wordsRepository;
    }

    public Optional<EPOS> getPosById(long id) {
        return posRepository.findById(id);
    }

    @IsEditor
    public Long savePOS(POS pos) {
        if (pos.getId() != null) {
            posRepository.findById(pos.getId()).ifPresent(epos -> {
                epos.setName(pos.getName());
                epos.setAbbreviation(pos.getAbbreviation());
                posRepository.save(epos);
            });
            return pos.getId();
        }
        return posRepository.save(mapper.map(pos, EPOS.class)).getId();
    }

    public List<LanguagePOS> getLanguagePOSByLanguage(Long languageId) {
        final List<LanguagePOS> connected = languagePOSRepository.findByLanguage_Id(languageId).stream().map((elp) -> new LanguagePOS(elp.getId(), elp.getLanguage().getId(), elp.getPos().getId(), false, true)).collect(Collectors.toList());
        final List<LanguagePOS> used = wordsRepository.findPOSInWordsByLanguage(languageId).stream().map((pos) -> new LanguagePOS(null, languageId, pos.getId(), true, false)).collect(Collectors.toList());
        connected.forEach((lp) -> {
            if (used.stream().anyMatch((ulp) -> ulp.getPosId().equals(lp.getPosId()))) lp.setUsedInLanguage(true);
        });
        return Stream.concat(
                used.stream().filter((ulp) -> connected.stream().noneMatch((clp) -> clp.getPosId().equals(ulp.getPosId()))),
                connected.stream()).collect(Collectors.toList());
    }

    public List<POS> getAllPartsOfSpeech() {
        return this.posRepository.findAll().stream().map(this::convertToRestModel).collect(Collectors.toList());
    }

    public List<POS> getAllPartsOfSpeechByLanguage(Long languageId) {
        return this.posRepository.findByLanguage_Id(languageId).stream().map(this::convertToRestModel).collect(Collectors.toList());
    }

    @IsEditor
    public Long saveLanguagePOS(LanguagePOS languagePOS) {
        ELanguage elanguage = this.languageRepository.findById(languagePOS.getLanguageId()).orElseThrow(() -> new IllegalArgumentException("Language " + languagePOS.getLanguageId() + " not found"));
        EPOS epos = this.posRepository.findById(languagePOS.getPosId()).orElseThrow(() -> new IllegalArgumentException("POS " + languagePOS.getPosId() + " not found"));
        ELanguagePOS eLanguagePOS = new ELanguagePOS(elanguage, epos);
        return this.languagePOSRepository.save(eLanguagePOS).getId();
    }

    @IsEditor
    public void deleteLanguagePOS(Long id) {
        this.languagePOSRepository.deleteById(id);
    }

    private POS convertToRestModel(EPOS epos) {
        return mapper.map(epos, POS.class);
    }

    public Optional<EPOS> getPOSById(Long id) {
        return posRepository.findById(id);
    }
}
