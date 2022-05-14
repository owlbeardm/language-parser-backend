package by.c7d5a6.languageparser.service;

import by.c7d5a6.languageparser.entity.ELanguage;
import by.c7d5a6.languageparser.entity.ELanguagePhoneme;
import by.c7d5a6.languageparser.entity.EWord;
import by.c7d5a6.languageparser.repository.LanguagePhonemeRepository;
import by.c7d5a6.languageparser.repository.LanguageRepository;
import by.c7d5a6.languageparser.rest.model.LanguagePhoneme;
import by.c7d5a6.languageparser.rest.model.LanguageSoundClusters;
import by.c7d5a6.languageparser.rest.model.ListOfLanguagePhonemes;
import by.c7d5a6.languageparser.rest.model.Word;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.lang.invoke.MethodHandles;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class LanguagePhonemeService extends BaseService {

    private static final Logger logger = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

    private final IPAService ipaService;
    private final WordService wordService;
    private final LanguagePhonemeRepository languagePhonemeRepository;
    private final LanguageRepository languageRepository;

    @Autowired
    public LanguagePhonemeService(IPAService ipaService, WordService wordService, LanguagePhonemeRepository languagePhonemeRepository, LanguageRepository languageRepository) {
        this.ipaService = ipaService;
        this.wordService = wordService;
        this.languagePhonemeRepository = languagePhonemeRepository;
        this.languageRepository = languageRepository;
    }

    public ListOfLanguagePhonemes getLanguagePhonemes(Long languageId) {
        ELanguage eLanguage = languageRepository.findById(languageId).orElseThrow(() -> new IllegalArgumentException("Language with id " + languageId + " not found"));
        ListOfLanguagePhonemes resultList = new ListOfLanguagePhonemes();
        resultList.setLangId(languageId);
        String languagePhonemes = ipaService.cleanIPA(getExistingLanguagePhonemes(eLanguage));
        List<ELanguagePhoneme> elp = this.languagePhonemeRepository.findByLanguage_Id(languageId);
        String[] allSoundsWithVariants = ipaService.getAllSoundsWithVariants();
        List<String> allSoundsWithVariantsAndLanguagePhonemes = elp.stream().map(ELanguagePhoneme::getPhoneme).collect(Collectors.toList());
        allSoundsWithVariantsAndLanguagePhonemes.addAll(Arrays.asList(allSoundsWithVariants));

        List<String> sounds = allSoundsWithVariantsAndLanguagePhonemes
                .stream()
                .sorted((o1, o2) -> o2.length() - o1.length())
                .collect(Collectors.toList());
        List<String> usedMainPhonemes = new ArrayList<>();
        for (String sound : sounds) {
            if (languagePhonemes.contains(sound)) {
                usedMainPhonemes.add(sound);
                languagePhonemes = languagePhonemes.replaceAll(sound, "");
            }
        }
        resultList.setUsedMainPhonemes(usedMainPhonemes);
        List<String> restSounds = Arrays
                .stream(languagePhonemes.split(""))
                .filter(s -> !s.isEmpty() && !s.trim().isEmpty())
                .sorted((o1, o2) -> o2.length() - o1.length())
                .distinct()
                .collect(Collectors.toList());
        resultList.setRestUsedPhonemes(restSounds);

        List<LanguagePhoneme> lpused = elp
                .stream()
                .filter((lp) -> Arrays.stream(allSoundsWithVariants).anyMatch(lp.getPhoneme()::equals))
                .map((ELanguagePhoneme lp) -> mapper.map(lp, LanguagePhoneme.class))
                .collect(Collectors.toList());
        resultList.setSelectedMainPhonemes(lpused);
        List<LanguagePhoneme> lprest = elp
                .stream()
                .filter((lp) -> Arrays.stream(allSoundsWithVariants).noneMatch(lp.getPhoneme()::equals))
                .map((ELanguagePhoneme lp) -> mapper.map(lp, LanguagePhoneme.class))
                .collect(Collectors.toList());
        resultList.setSelectedRestPhonemes(lprest);

        return resultList;
    }

    public LanguagePhoneme saveLanguagePhoneme(Long languageId, String phoneme) {
        ELanguage eLanguage = languageRepository.findById(languageId).orElseThrow(() -> new IllegalArgumentException("Language with id " + languageId + " not found"));
        ELanguagePhoneme eLanguagePhoneme = new ELanguagePhoneme();
        eLanguagePhoneme.setPhoneme(phoneme);
        eLanguagePhoneme.setLanguage(eLanguage);
        eLanguagePhoneme = languagePhonemeRepository.save(eLanguagePhoneme);
        return mapper.map(eLanguagePhoneme, LanguagePhoneme.class);
    }

    public void deleteLanguagePhoneme(Long phonemeId) {
        ELanguagePhoneme eLanguagePhoneme = languagePhonemeRepository.findById(phonemeId).orElseThrow(() -> new IllegalArgumentException("Phoneme with id " + phonemeId + " not found"));
        languagePhonemeRepository.delete(eLanguagePhoneme);
    }

    private String getExistingLanguagePhonemes(ELanguage eLanguage) {
        return this.wordService
                .getAllWordsFromLang(eLanguage)
                .stream()
                .map(EWord::getWord)
                .reduce((a, b) -> a + " " + b)
                .orElse("");
    }

    public LanguageSoundClusters getLanguageSoundClusters(Long languageId) {
        final Set<String> constClustersStart = new HashSet<>();
        final Set<String> constClustersEnd = new HashSet<>();
        final Set<String> constClusters = new HashSet<>();
        final Set<String> vowelClustersStart = new HashSet<>();
        final Set<String> vowelClustersEnd = new HashSet<>();
        final Set<String> vowelClusters = new HashSet<>();
        String constRegexp = "(" + String.join("|", ipaService.getAllConstanantVariants()) + ")";
        logger.info(constRegexp);
        String vowelRegexp = "(" + String.join("|", ipaService.getAllVowelVariants()) + ")";
        logger.info(vowelRegexp);
        this.wordService.getAllWordsFromLangId(languageId).stream()
                .map(Word::getWord)
                .forEach(w -> {
                    w = ipaService.cleanIPA(w);
                    String[] vowelClustersArr = w.split(constRegexp);
                    String[] constClustersArr = w.split(vowelRegexp);
                    getClusters(constClustersStart, constClustersEnd, constClusters, w, constClustersArr);
                    getClusters(vowelClustersStart, vowelClustersEnd, vowelClusters, w, vowelClustersArr);
                });
        LanguageSoundClusters languageSoundClusters = new LanguageSoundClusters();
        languageSoundClusters.setConstClusters(constClusters.stream().sorted().toList());
        languageSoundClusters.setConstClustersStart(constClustersStart.stream().sorted().toList());
        languageSoundClusters.setConstClustersEnd(constClustersEnd.stream().sorted().toList());
        languageSoundClusters.setVowelClusters(vowelClusters.stream().sorted().toList());
        languageSoundClusters.setVowelClustersStart(vowelClustersStart.stream().sorted().toList());
        languageSoundClusters.setVowelClustersEnd(vowelClustersEnd.stream().sorted().toList());
        return languageSoundClusters;
    }

    private void getClusters(Set<String> constClustersStart, Set<String> constClustersEnd, Set<String> constClusters, String w, String[] constClustersArr) {
        if (constClustersArr.length > 0) {
            String start = constClustersArr[0];
            if (w.startsWith(start) && !start.isBlank())
                constClustersStart.add(start);
            String end = constClustersArr[constClustersArr.length - 1];
            if (w.endsWith(end) && !start.isBlank())
                constClustersEnd.add(end);
            constClusters.addAll(Arrays.stream(constClustersArr).filter(s -> !s.isBlank()).toList());
        }
    }


}
