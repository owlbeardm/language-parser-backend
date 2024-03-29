package by.c7d5a6.languageparser.service;

import by.c7d5a6.languageparser.entity.*;
import by.c7d5a6.languageparser.entity.base.BaseEntity;
import by.c7d5a6.languageparser.enums.SoundChangePurpose;
import by.c7d5a6.languageparser.enums.SoundChangeType;
import by.c7d5a6.languageparser.repository.DeclensionRepository;
import by.c7d5a6.languageparser.repository.DeclensionRuleRepository;
import by.c7d5a6.languageparser.repository.SoundChangeRepository;
import by.c7d5a6.languageparser.repository.WordsRepository;
import by.c7d5a6.languageparser.rest.model.Language;
import by.c7d5a6.languageparser.rest.model.SoundChange;
import by.c7d5a6.languageparser.rest.security.IsEditor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.lang.invoke.MethodHandles;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

@Service
public class SoundChangesService extends BaseService {

    private static final Logger logger = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

    private final SoundChangeRepository soundChangeRepository;
    private final WordsRepository wordsRepository;
    private final DeclensionRepository declensionRepository;
    private final DeclensionRuleRepository declensionRuleRepository;
    private final LanguageService languageService;
    private final DeclensionRuleService declensionRuleService;
    private final IPAService ipaService;

    @Autowired
    public SoundChangesService(SoundChangeRepository soundChangeRepository, WordsRepository wordsRepository, DeclensionRepository declensionRepository, DeclensionRuleRepository declensionRuleRepository, LanguageService languageService, DeclensionRuleService declensionRuleService, IPAService ipaService) {
        this.soundChangeRepository = soundChangeRepository;
        this.wordsRepository = wordsRepository;
        this.declensionRepository = declensionRepository;
        this.declensionRuleRepository = declensionRuleRepository;
        this.languageService = languageService;
        this.declensionRuleService = declensionRuleService;
        this.ipaService = ipaService;
    }

    public List<ESoundChange> getSoundChangesFromLines(String lines) {
        return Arrays.stream(lines.split(System.lineSeparator())).map(this::getSoundChangesFromLine).collect(Collectors.toList());
    }

    public ESoundChange getSoundChangesFromLine(String line) {
        ESoundChange soundChange = new ESoundChange();
        String trimed = line.trim();
        trimed = IPAService.cleanIPA(trimed);
        trimed = replaceArrows(trimed);
        if (!trimed.contains("→") && !trimed.contains("»") && !trimed.contains("«")) {
            throw new IllegalArgumentException("Sound change doesn't contain \"to\" symbol: " + line);
        }
        if ((trimed.contains("/") || trimed.contains("!")) && !trimed.matches(".*[/!] .*_.*")) {
            throw new IllegalArgumentException("Sound change contains \"/\" or \"!\" symbol but doesn't contain \"_\" in environment section: " + line);
        }
        String splitBy;
        if (trimed.contains("→")) {
            soundChange.setType(SoundChangeType.REPLACE_ALL);
            splitBy = "→";
        } else if (trimed.contains("»")) {
            soundChange.setType(SoundChangeType.REPLACE_FIRST);
            splitBy = "»";
        } else {
            soundChange.setType(SoundChangeType.REPLACE_LAST);
            splitBy = "«";
        }
        String[] splitFrom = trimed.split(splitBy);
        soundChange.setSoundFrom(splitFrom[0].trim());
        if (trimed.contains("/")) {
            String[] splitTo = splitFrom[1].split("/", 2);
            soundChange.setSoundTo(splitTo[0].trim());
            String[] splitEnvironment = splitTo[1].split("_", 2);
            soundChange.setEnvironmentBefore(splitEnvironment[0].trim());
            soundChange.setEnvironmentAfter(splitEnvironment[1].trim());
        } else {
            soundChange.setSoundTo(splitFrom[1].trim());
        }
        return soundChange;
    }

    public List<SoundChange> getSoundChanges(long fromLangId, long toLangId, SoundChangePurpose soundChangePurpose) {
        if (soundChangePurpose == SoundChangePurpose.SOUND_CHANGE) {
            return getSoundChangesByLangs(fromLangId, toLangId, soundChangePurpose);
        } else {
            return getSoundChangesByLang(fromLangId, soundChangePurpose);
        }
    }

    public List<SoundChange> getSoundChangesByLang(long fromLangId, SoundChangePurpose soundChangePurpose) {
        List<ESoundChange> byLangFrom_idAndLangTo_idOrderByPriority = soundChangeRepository.findByLangFrom_IdAndSoundChangePurposeOrderByPriority(fromLangId, soundChangePurpose);
        return byLangFrom_idAndLangTo_idOrderByPriority.stream().map(this::convertToSoundChange).collect(Collectors.toList());
    }

    public List<ESoundChange> getESoundChangesByLang(long fromLangId, SoundChangePurpose soundChangePurpose) {
        List<ESoundChange> byLangFrom_idAndLangTo_idOrderByPriority = soundChangeRepository.findByLangFrom_IdAndSoundChangePurposeOrderByPriority(fromLangId, soundChangePurpose);
        return byLangFrom_idAndLangTo_idOrderByPriority;
    }

    public List<SoundChange> getSoundChangesByLangs(long fromLangId, long toLangId, SoundChangePurpose soundChangePurpose) {
        List<ESoundChange> byLangFrom_idAndLangTo_idOrderByPriority = soundChangeRepository.findByLangFrom_IdAndLangTo_IdAndAndSoundChangePurposeOrderByPriority(fromLangId, toLangId, soundChangePurpose);
        return byLangFrom_idAndLangTo_idOrderByPriority.stream().map(this::convertToSoundChange).collect(Collectors.toList());
    }

    public String getSoundChangesRawLinesByLangs(long fromLangId, Long toLangId, SoundChangePurpose soundChangePurpose) {
        List<ESoundChange> soundChanges;
        if (soundChangePurpose == SoundChangePurpose.SOUND_CHANGE) {
            soundChanges = soundChangeRepository.findByLangFrom_IdAndLangTo_IdAndAndSoundChangePurposeOrderByPriority(fromLangId, toLangId, soundChangePurpose);
        } else {
            soundChanges = soundChangeRepository.findByLangFrom_IdAndSoundChangePurposeOrderByPriority(fromLangId, soundChangePurpose);
        }
        return soundChanges.stream().map(this::soundChangeToRawLine).collect(Collectors.joining(System.lineSeparator()));
    }

    @IsEditor
    public void saveSoundChangesRawLinesByLangs(Long fromLangId, Long toLangId, Long declensionRuleId, String rawLines, SoundChangePurpose soundChangePurpose) {
        ELanguage langFrom = fromLangId == null ? null : languageService.getLangById(fromLangId).orElseThrow(() -> new IllegalArgumentException("Language from with id " + fromLangId + " doesn't exist"));
        ELanguage langTo = toLangId == null ? null : languageService.getLangById(toLangId).orElseThrow(() -> new IllegalArgumentException("Language to with id " + fromLangId + " doesn't exist"));
        EDeclensionRule declensionRule = declensionRuleId == null ? null : declensionRuleRepository.findById(declensionRuleId).orElseThrow(() -> new IllegalArgumentException("Declenstion rule to with id " + fromLangId + " doesn't exist"));
        List<ESoundChange> soundChanges = getSoundChangesFromLines(rawLines);
        soundChangeRepository.deleteByLangFrom_IdAndLangTo_IdAndDeclensionRule_IdAndSoundChangePurpose(fromLangId, toLangId, declensionRuleId, soundChangePurpose);
        for (long i = 0; i < soundChanges.size(); i++) {
            ESoundChange soundChange = soundChanges.get((int) i);
            soundChange.setPriority(i);
            soundChange.setLangFrom(langFrom);
            soundChange.setLangTo(langTo);
            soundChange.setDeclensionRule(declensionRule);
            soundChange.setSoundChangePurpose(soundChangePurpose);
            soundChangeRepository.save(soundChange);
        }
    }

    public String getSoundChangeRaw(long id) {
        return soundChangeRepository.findById(id).map(this::soundChangeToRawLine).orElseThrow(() -> new IllegalArgumentException("Sound change with id " + id + " doesn't exist"));
    }

    @IsEditor
    public void updateSoundChange(long id, String rawLine) {
        soundChangeRepository.findById(id).map((sc) -> {
            ESoundChange soundChangesFromLine = this.getSoundChangesFromLine(rawLine);
            sc.setSoundFrom(soundChangesFromLine.getSoundFrom());
            sc.setSoundTo(soundChangesFromLine.getSoundTo());
            sc.setEnvironmentBefore(soundChangesFromLine.getEnvironmentBefore());
            sc.setEnvironmentAfter(soundChangesFromLine.getEnvironmentAfter());
            sc.setType(soundChangesFromLine.getType());
            return this.soundChangeRepository.save(sc);
        }).orElseThrow(() -> new IllegalArgumentException("Sound change with id " + id + " doesn't exist"));
    }

    @IsEditor
    public void deleteSoundChange(long id) {
        soundChangeRepository.delete(soundChangeRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Sound change with id " + id + " doesn't exist")));
    }

    public SoundChange getSoundChange(long id) {
        return soundChangeRepository.findById(id).map(this::convertToSoundChange).orElseThrow(() -> new IllegalArgumentException("Sound change with id " + id + " doesn't exist"));
    }

    private String replaceArrows(String trimed) {
        return trimed
                .replaceFirst("->", "→")
                .replaceFirst("=", "→")
                .replaceFirst(">>", "»")
                .replaceFirst(">", "→")
                .replaceFirst("<<", "«");
    }

    private String soundChangeToRawLine(ESoundChange soundChange) {
        return soundChange.getSoundFrom()
                + " " + soundChangeTypeToRaw(soundChange.getType())
                + " " + soundChange.getSoundTo()
                + (
                ((soundChange.getEnvironmentBefore() != null && !soundChange.getEnvironmentBefore().isEmpty())
                        || (soundChange.getEnvironmentAfter() != null && !soundChange.getEnvironmentAfter().isEmpty()))
                        ? (" / " + soundChange.getEnvironmentBefore() + "_" + soundChange.getEnvironmentAfter())
                        : "");
    }

    private String soundChangeTypeToRaw(SoundChangeType type) {
        return switch (type) {
            case REPLACE_ALL -> ">";
            case REPLACE_FIRST -> ">>";
            case REPLACE_LAST -> "<<";
        };
    }

    private SoundChange convertToSoundChange(ESoundChange soundChange) {
        return mapper.map(soundChange, SoundChange.class);
    }

    public String evolveWord(String word, Language languageFrom, Language languageTo) {
        return evolveWord(word, getSoundChanges(languageFrom, languageTo));
    }

    private List<ESoundChange> getSoundChanges(ELanguage languageFrom, ELanguage languageTo) {
        return getSoundChanges(languageFrom.getId(), languageTo.getId());
    }

    public List<ESoundChange> getSoundChanges(Language languageFrom, Language languageTo) {
        return getSoundChanges(languageFrom.getId(), languageTo.getId());
    }

    private List<ESoundChange> getSoundChanges(Long languageFromId, Long languageToId) {
        return soundChangeRepository.findByLangFrom_IdAndLangTo_IdAndAndSoundChangePurposeOrderByPriority(languageFromId, languageToId, SoundChangePurpose.SOUND_CHANGE);
    }

    public Map<Long, Map<Long, String>> evolveWords(List<WordWithIdAndLanguage> words, List<ELanguageConnection> languageConnections) {
        return languageConnections
                .stream()
                .collect(Collectors.toMap(BaseEntity::getId, languageConnection -> evolveWords(words, languageConnection)));
    }

    private Map<Long, String> evolveWords(List<WordWithIdAndLanguage> words, ELanguageConnection languageConnection) {
        return getSounChangedWords(words, languageConnection.getLangFrom(), languageConnection.getLangTo());
    }

    public Map<Long, String> getSounChangedWords(List<WordWithIdAndLanguage> words, ELanguage langFrom, ELanguage langTo) {
        List<ESoundChange> soundChanges = getSoundChanges(langFrom, langTo);
        final Map<Long, String> result = new HashMap<>();
        words
                .stream()
                .filter(word -> word.getLanguage().getId().equals(langFrom.getId()))
                .forEach(word -> {
                    result.put(word.getId(), evolveWord(word, soundChanges));
                });
        return result;
    }

    public String evolveWord(WordWithIdAndLanguage word, List<ESoundChange> soundChanges) {
        String result = word.getWord();
        result = getMainDeclensionWord(word.getId()).orElse(result);
        return evolveWord(result, soundChanges);
    }

    public String evolveWord(String word, List<ESoundChange> soundChanges) {
        String result = word;
        for (ESoundChange soundChange : soundChanges) {
            result = evolveWordBySingleSoundChange(result, soundChange);
        }
        return result;
    }

    public Optional<String> getMainDeclensionWord(Long wordId) {
        Optional<EWord> oword = wordsRepository
                .findById(wordId);
        return oword
                .flatMap((w) -> declensionRepository
                        .findByLanguage_IdAndPos_Id(w.getLanguage().getId(), w.getPartOfSpeech().getId())
                        .stream()
                        .filter(EDeclension::isMainDeclension)
                        .findFirst())
                .map((declension) -> {
                    List<EDeclensionRule> rules = declensionRuleRepository.findByDeclension_Id(declension.getId());
                    for (EDeclensionRule rule : rules) {
                        if (declensionRuleService.isDeclensionRuleApply(rule, oword.get())) {
                            return changeWordByRule(oword.get().getWord(), rule);
                        }
                    }
                    return null;
                });
    }

    private String evolveWordBySingleSoundChange(String result, ESoundChange soundChange) {
        String regexp = getRegexpFromSoundChange(soundChange);
        Pattern pattern = Pattern.compile(regexp);
        Matcher matcher = pattern.matcher(result);
        final String soundTo = soundChange.getSoundTo().replaceAll("ø", "");
        switch (soundChange.getType()) {
            case REPLACE_ALL -> result = matcher.replaceAll(soundTo);
            case REPLACE_FIRST -> result = matcher.replaceFirst(soundTo);
            case REPLACE_LAST -> throw new UnsupportedOperationException("Not implemented yet");
        }
        return result.trim();
    }

    private String getRegexpFromSoundChange(ESoundChange soundChange) {
        String regexpBefore = (soundChange.getEnvironmentBefore() != null && !soundChange.getEnvironmentBefore().isEmpty()) ? "(?<=" + soundChange.getEnvironmentBefore() + ")" : "";
        String regexpAfter = (soundChange.getEnvironmentAfter() != null && !soundChange.getEnvironmentAfter().isEmpty()) ? "(?=" + soundChange.getEnvironmentAfter() + ")" : "";
        String regexp = regexpBefore + soundChange.getSoundFrom() + regexpAfter;
        return regexp;
    }

    public List<SoundChange> getSoundChangesByRule(long ruleId, SoundChangePurpose soundChangePurpose) {
        List<ESoundChange> byLangFrom_idAndLangTo_idOrderByPriority = soundChangeRepository.findByDeclensionRule_IdAndSoundChangePurposeOrderByPriority(ruleId, soundChangePurpose);
        return byLangFrom_idAndLangTo_idOrderByPriority.stream().map(this::convertToSoundChange).collect(Collectors.toList());
    }

    public String getSoundChangesRawLinesByRule(long declensionId, SoundChangePurpose soundChangePurpose) {
        List<ESoundChange> soundChanges = soundChangeRepository.findByDeclensionRule_IdAndSoundChangePurposeOrderByPriority(declensionId, soundChangePurpose);
        return soundChanges.stream().map(this::soundChangeToRawLine).collect(Collectors.joining(System.lineSeparator()));
    }

    public String changeWordByRule(String word, EDeclensionRule rule) {
        List<ESoundChange> changes = soundChangeRepository.findByDeclensionRule_IdAndSoundChangePurposeOrderByPriority(rule.getId(), SoundChangePurpose.DECLENSION);
        return evolveWord(word, changes);
    }
}
