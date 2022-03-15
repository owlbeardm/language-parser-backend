package by.c7d5a6.languageparser.service;

import by.c7d5a6.languageparser.entity.ELanguage;
import by.c7d5a6.languageparser.entity.ELanguagePOS;
import by.c7d5a6.languageparser.entity.EPOS;
import by.c7d5a6.languageparser.repository.LanguagePOSRepository;
import by.c7d5a6.languageparser.repository.LanguageRepository;
import by.c7d5a6.languageparser.repository.POSRepository;
import by.c7d5a6.languageparser.rest.model.LanguagePOS;
import by.c7d5a6.languageparser.rest.model.POS;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.lang.invoke.MethodHandles;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class POSService extends BaseService {

    private static final Logger logger = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

    private final LanguageRepository languageRepository;
    private final POSRepository posRepository;
    private final LanguagePOSRepository languagePOSRepository;

    @Autowired
    public POSService(LanguageRepository languageRepository, POSRepository posRepository, LanguagePOSRepository languagePOSRepository) {
        this.languageRepository = languageRepository;
        this.posRepository = posRepository;
        this.languagePOSRepository = languagePOSRepository;
    }

    public List<POS> getAllPOS() {
        return posRepository.findAll().stream().map(this::convertToPOS).collect(Collectors.toList());
    }

    private POS convertToPOS(EPOS epos) {
        return mapper.map(epos, POS.class);
    }

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
        return languagePOSRepository.findByLanguage_Id(languageId).stream().map((elp) -> new LanguagePOS(elp.getId(), elp.getLanguage().getId(), elp.getPos().getId())).collect(Collectors.toList());
    }

    public Long saveLanguagePOS(LanguagePOS languagePOS) {
        ELanguage elanguage = this.languageRepository.findById(languagePOS.getLanguageId()).orElseThrow(() -> new IllegalArgumentException("Language " + languagePOS.getLanguageId() + " not found"));
        EPOS epos = this.posRepository.findById(languagePOS.getPosId()).orElseThrow(() -> new IllegalArgumentException("POS " + languagePOS.getPosId() + " not found"));
        ELanguagePOS eLanguagePOS = new ELanguagePOS(elanguage, epos);
        return this.languagePOSRepository.save(eLanguagePOS).getId();
    }

    public void deleteLanguagePOS(Long id) {
        this.languagePOSRepository.deleteById(id);
    }
}
