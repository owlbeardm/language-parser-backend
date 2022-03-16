package by.c7d5a6.languageparser.service;

import by.c7d5a6.languageparser.entity.ELanguage;
import by.c7d5a6.languageparser.entity.ELanguageConnection;
import by.c7d5a6.languageparser.entity.ELanguagePhoneme;
import by.c7d5a6.languageparser.entity.enums.LanguageConnectionType;
import by.c7d5a6.languageparser.repository.LanguageConnectionRepository;
import by.c7d5a6.languageparser.repository.LanguagePhonemeRepository;
import by.c7d5a6.languageparser.repository.LanguageRepository;
import by.c7d5a6.languageparser.repository.POSRepository;
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
    private final WordService wordService;
    private final IPAService ipaService;


    @Autowired
    public LanguageService(LanguageRepository languageRepository, LanguageConnectionRepository languageConnectionRepository, WordService wordService, IPAService ipaService, LanguagePhonemeRepository languagePhonemeRepository) {
        this.languageRepository = languageRepository;
        this.languageConnectionRepository = languageConnectionRepository;
        this.wordService = wordService;
        this.ipaService = ipaService;
        this.languagePhonemeRepository = languagePhonemeRepository;
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

    public LanguageConnection convertToRestModel(ELanguageConnection language) {
        return mapper.map(language, LanguageConnection.class);
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
        String languagePhonemes = ipaService.cleanIPA(this.wordService.getLanguagePhonemes(eLanguage));
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
        languageRepository.delete(eLanguage);
    }

    public boolean canDeleteLanguage(Long languageId) {
        long lc = languageConnectionRepository.countByLangFrom_Id(languageId);
        long w = wordService.countWordsByLanguageId(languageId);
        return lc + w == 0;
    }
}
