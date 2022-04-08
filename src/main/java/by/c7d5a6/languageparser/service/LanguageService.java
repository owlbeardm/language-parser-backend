package by.c7d5a6.languageparser.service;

import by.c7d5a6.languageparser.entity.ELanguage;
import by.c7d5a6.languageparser.entity.ELanguageConnection;
import by.c7d5a6.languageparser.entity.ELanguagePhoneme;
import by.c7d5a6.languageparser.entity.EWord;
import by.c7d5a6.languageparser.entity.enums.LanguageConnectionType;
import by.c7d5a6.languageparser.repository.*;
import by.c7d5a6.languageparser.rest.model.*;
import com.google.common.collect.Lists;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.lang.invoke.MethodHandles;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class LanguageService extends BaseService {

    private static final Logger logger = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

    private final LanguageRepository languageRepository;
    private final LanguageConnectionRepository languageConnectionRepository;
    private final LanguagePhonemeRepository languagePhonemeRepository;
    private final LanguagePOSRepository languagePOSRepository;
    private final WordsRepository wordsRepository;
    private final IPAService ipaService;


    @Autowired
    public LanguageService(LanguageRepository languageRepository, LanguageConnectionRepository languageConnectionRepository, WordsRepository wordsRepository, IPAService ipaService, LanguagePhonemeRepository languagePhonemeRepository, LanguagePOSRepository languagePOSRepository) {
        this.languageRepository = languageRepository;
        this.languageConnectionRepository = languageConnectionRepository;
        this.wordsRepository = wordsRepository;
        this.ipaService = ipaService;
        this.languagePhonemeRepository = languagePhonemeRepository;
        this.languagePOSRepository = languagePOSRepository;
    }

    public List<Language> getAllLanguages() {
        List<ELanguage> all = languageRepository.findAll();
        return all.stream().map(this::convertToRestModel).collect(Collectors.toList());
    }


    public List<List<Language>> getAllPaths(long fromId, long toId) {
        throw new UnsupportedOperationException("Not implemented yet");
    }

    public List<Language> getAllLanguagesFrom(long fromId) {
        List<ELanguage> result = new ArrayList<>();
        List<ELanguageConnection> allConnections = this.languageConnectionRepository.findAll();
        allConnections.forEach(connection -> {
            if (connection.getLangFrom().getId() == fromId) result.add(connection.getLangTo());
        });
        int i = 0;
        while (i < result.size()) {
            final int j = i;
            allConnections.forEach(connection -> {
                if (connection.getLangFrom().getId().equals(result.get(j).getId()) && !result.contains(connection.getLangTo()))
                    result.add(connection.getLangTo());
            });
            i++;
        }
        return result.stream().map(this::convertToRestModel).collect(Collectors.toList());
    }

    public List<List<Language>> getAllRoutes(long fromId, long toId) {
        List<ELanguageConnection> allConnections = this.languageConnectionRepository.findAll();
        List<ELanguage> all = languageRepository.findAll();
        Map<Long, ELanguage> langs = new HashMap<>(all.size());
        Map<Long, List<ELanguage>> route = new HashMap<>(all.size());
        Map<Long, List<ELanguageConnection>> connectionsFrom = new HashMap<>(all.size());
        all.forEach(lang -> {
            connectionsFrom.put(lang.getId(), new ArrayList<>());
            langs.put(lang.getId(), lang);
        });
        allConnections.forEach(connection -> {
            connectionsFrom.get(connection.getLangFrom().getId()).add(connection);
        });
        List<ELanguage> bsearch = new ArrayList<>();
        ELanguage from = langs.get(fromId);
        bsearch.add(from);
        route.put(fromId, Lists.newArrayList(from));
        int i = 0;
        while (i < bsearch.size()) {
            connectionsFrom.get(bsearch.get(i).getId()).forEach(connection -> {
                if (!bsearch.contains(connection.getLangTo())) {
                    bsearch.add(connection.getLangTo());
                    route.put(connection.getLangTo().getId(), Lists.newArrayList(route.get(connection.getLangFrom().getId())));
                    route.get(connection.getLangTo().getId()).add(connection.getLangTo());
                }
            });
            i++;
        }
        List<List<Language>> result = new ArrayList<>();
        result.add(route.get(toId).stream().map(this::convertToRestModel).collect(Collectors.toList()));
        return result;
    }

    public LanguageConnection getConnection(long fromLangId, long toLangId) {
        return this.languageConnectionRepository.findByLangFrom_IdAndLangTo_Id(fromLangId, toLangId).map(this::convertToRestModel).orElse(null);
    }

    public void updateConnection(long fromLangId, long toLangId, LanguageConnectionType connectionType) {
        this.languageConnectionRepository.findByLangFrom_IdAndLangTo_Id(fromLangId, toLangId).ifPresentOrElse(connection -> {
            connection.setConnectionType(connectionType);
            this.languageConnectionRepository.save(connection);
        }, () -> {
            ELanguage langFrom = this.languageRepository.findById(fromLangId).orElseThrow(() -> new IllegalArgumentException("Language with id " + fromLangId + " not found"));
            ELanguage langTo = this.languageRepository.findById(toLangId).orElseThrow(() -> new IllegalArgumentException("Language with id " + toLangId + " not found"));
            ELanguageConnection connection = new ELanguageConnection();
            connection.setLangFrom(langFrom);
            connection.setLangTo(langTo);
            connection.setConnectionType(connectionType);
            this.languageConnectionRepository.save(connection);
        });
    }

    public void deleteConnection(long fromLangId, long toLangId) {
        this.languageConnectionRepository.findByLangFrom_IdAndLangTo_Id(fromLangId, toLangId).ifPresent(this.languageConnectionRepository::delete);
    }

    private void createLanguagesGraph() {
    }

    public Language convertToRestModel(ELanguage language) {
        return mapper.map(language, Language.class);
    }


    public LanguagePhoneme convertToRestModel(ELanguagePhoneme eLanguagePhoneme) {
        return mapper.map(eLanguagePhoneme, LanguagePhoneme.class);
    }

    public Optional<ELanguage> getLangById(long fromLangId) {
        return languageRepository.findById(fromLangId);
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
//            lang.setFrequency(language.getFrequency());
        lang = languageRepository.save(lang);
        return convertToRestModel(lang);
    }

    public List<POS> getAllPartsOfSpeechByLanguage(Long languageId) {
        throw new UnsupportedOperationException();
    }

    public ListOfLanguagePhonemes getLanguagePhonemes(Long languageId) {
        ELanguage eLanguage = languageRepository.findById(languageId).orElseThrow(() -> new IllegalArgumentException("Language with id " + languageId + " not found"));
        ListOfLanguagePhonemes resultList = new ListOfLanguagePhonemes();
        resultList.setLangId(languageId);
        String languagePhonemes = ipaService.cleanIPA(getLanguagePhonemes(eLanguage));
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
                .map(this::convertToRestModel)
                .collect(Collectors.toList());
        resultList.setSelectedMainPhonemes(lpused);
        List<LanguagePhoneme> lprest = elp
                .stream()
                .filter((lp) -> Arrays.stream(allSoundsWithVariants).noneMatch(lp.getPhoneme()::equals))
                .map(this::convertToRestModel)
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
        return convertToRestModel(eLanguagePhoneme);
    }

    public void deleteLanguagePhoneme(Long phonemeId) {
        ELanguagePhoneme eLanguagePhoneme = languagePhonemeRepository.findById(phonemeId).orElseThrow(() -> new IllegalArgumentException("Phoneme with id " + phonemeId + " not found"));
        languagePhonemeRepository.delete(eLanguagePhoneme);
    }

    public void deleteLanguage(Long languageId) {
        ELanguage eLanguage = languageRepository.findById(languageId).orElseThrow(() -> new IllegalArgumentException("Language with id " + languageId + " not found"));
        languageConnectionRepository.deleteAll(languageConnectionRepository.findByLangTo_Id(languageId));
        languagePhonemeRepository.deleteAll(languagePhonemeRepository.findByLanguage_Id(languageId));
        languagePOSRepository.deleteAll(languagePOSRepository.findByLanguage_Id(languageId));
        languageRepository.delete(eLanguage);
    }

    public boolean canDeleteLanguage(Long languageId) {
        long lc = languageConnectionRepository.countByLangFrom_Id(languageId);
        long w = wordsRepository.countByLanguage_Id(languageId);
        return lc + w == 0;
    }

    public String getLanguagePhonemes(ELanguage eLanguage) {
        return this.wordsRepository
                .findByLanguage_Id(eLanguage.getId())
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
        this.wordsRepository.findByLanguage_Id(languageId).stream()
                .map(EWord::getWord)
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
