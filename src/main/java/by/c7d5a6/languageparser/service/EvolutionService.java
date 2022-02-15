package by.c7d5a6.languageparser.service;

import by.c7d5a6.languageparser.entity.ESoundChange;
import by.c7d5a6.languageparser.repository.SoundChangeRepository;
import by.c7d5a6.languageparser.rest.model.Language;
import by.c7d5a6.languageparser.rest.model.WordTraceResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.lang.invoke.MethodHandles;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class EvolutionService extends BaseService {

    private static final Logger logger = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

    private final SoundChangeRepository soundChangeRepository;

    @Autowired
    public EvolutionService(SoundChangeRepository soundChangeRepository) {
        this.soundChangeRepository = soundChangeRepository;
    }

    public List<WordTraceResult> trace(String word, List<Language> languages) {
        if (languages.size() < 2) {
            throw new IllegalArgumentException("At least 2 languages are required");
        }
        List<WordTraceResult> result = new ArrayList<>(languages.size());
        String evolvedWord = word;
        result.add(new WordTraceResult(languages.get(0), evolvedWord));
        for (int i = 1; i < languages.size(); i++) {
            evolvedWord = evolveWord(evolvedWord, languages.get(i - 1), languages.get(i));
            result.add(new WordTraceResult(languages.get(i), evolvedWord));
        }
        return result;
    }

    public String evolveWord(String word, Language languageFrom, Language languageTo) {
        return evolveWord(word, getSoundChanges(languageFrom, languageTo));
    }

    private List<ESoundChange> getSoundChanges(Language languageFrom, Language languageTo) {
        return soundChangeRepository.findByLangFrom_IdAndLangTo_Id(languageFrom.getId(), languageTo.getId());
    }

    public String evolveWord(String word, List<ESoundChange> soundChanges) {
        String result = word;
        for (ESoundChange soundChange : soundChanges) {
            Pattern pattern = Pattern.compile(soundChange.getSoundFrom());
            Matcher matcher = pattern.matcher(result);
            result = matcher.replaceAll(soundChange.getSoundTo());
        }
        return result;
    }
}
