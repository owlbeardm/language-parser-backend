package by.c7d5a6.languageparser.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.lang.invoke.MethodHandles;

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


}
