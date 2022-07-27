package by.c7d5a6.languageparser.service;

import by.c7d5a6.languageparser.entity.ESoundChange;
import by.c7d5a6.languageparser.entity.enums.SoundChangePurpose;
import by.c7d5a6.languageparser.rest.model.Word;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.lang.invoke.MethodHandles;
import java.util.List;

@Service
public class WordWrittenService extends BaseService {

    private static final Logger logger = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

    private final EvolutionService evolutionService;
    private final SoundChangesService soundChangesService;

    @Autowired
    public WordWrittenService(EvolutionService evolutionService, SoundChangesService soundChangesService) {
        this.evolutionService = evolutionService;
        this.soundChangesService = soundChangesService;
    }

    public String getWrittenForm(Word word) {
        List<ESoundChange> soundChangesByLang = soundChangesService.getESoundChangesByLang(word.getLanguage().getId(), SoundChangePurpose.WRITING_SYSTEM);
        return evolutionService.evolveWord(word.getWord(), soundChangesByLang);
    }
}
