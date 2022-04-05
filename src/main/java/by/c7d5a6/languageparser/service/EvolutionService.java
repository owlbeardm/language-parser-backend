package by.c7d5a6.languageparser.service;

import by.c7d5a6.languageparser.entity.*;
import by.c7d5a6.languageparser.entity.base.BaseEntity;
import by.c7d5a6.languageparser.entity.enums.SoundChangePurpose;
import by.c7d5a6.languageparser.repository.LanguageConnectionRepository;
import by.c7d5a6.languageparser.repository.SoundChangeRepository;
import by.c7d5a6.languageparser.repository.WordsRepository;
import by.c7d5a6.languageparser.repository.WordsSourceRepository;
import by.c7d5a6.languageparser.rest.model.*;
import by.c7d5a6.languageparser.rest.model.base.PageResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.lang.invoke.MethodHandles;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

@Service
public class EvolutionService extends BaseService {

    private static final Logger logger = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

    private final SoundChangeRepository soundChangeRepository;
    private final WordsRepository wordsRepository;
    private final WordsSourceRepository wordsSourceRepository;
    private final LanguageConnectionRepository languageConnectionRepository;

    @Autowired
    public EvolutionService(SoundChangeRepository soundChangeRepository, WordsRepository wordsRepository, LanguageConnectionRepository languageConnectionRepository, WordsSourceRepository wordsSourceRepository) {
        this.soundChangeRepository = soundChangeRepository;
        this.wordsRepository = wordsRepository;
        this.languageConnectionRepository = languageConnectionRepository;
        this.wordsSourceRepository = wordsSourceRepository;
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

    private List<ESoundChange> getSoundChanges(ELanguage languageFrom, ELanguage languageTo) {
        return getSoundChanges(languageFrom.getId(), languageTo.getId());
    }

    private List<ESoundChange> getSoundChanges(Language languageFrom, Language languageTo) {
        return getSoundChanges(languageFrom.getId(), languageTo.getId());
    }

    private List<ESoundChange> getSoundChanges(Long languageFromId, Long languageToId) {
        return soundChangeRepository.findByLangFrom_IdAndLangTo_IdAndAndSoundChangePurposeOrderByPriority(languageFromId, languageToId, SoundChangePurpose.SOUND_CHANGE);
    }

    private Map<Long, Map<Long, String>> evolveWords(List<EWord> words, List<ELanguageConnection> languageConnections) {
        return languageConnections
                .stream()
                .collect(Collectors.toMap(BaseEntity::getId, languageConnection -> evolveWords(words, languageConnection)));
    }

    private Map<Long, String> evolveWords(List<EWord> words, ELanguageConnection languageConnection) {
        List<ESoundChange> soundChanges = getSoundChanges(languageConnection.getLangFrom(), languageConnection.getLangTo());
        final Map<Long, String> result = new HashMap<>();
        words
                .stream()
                .filter(word -> word.getLanguage().getId().equals(languageConnection.getLangFrom().getId()))
                .forEach(word -> {
                    result.put(word.getId(), evolveWord(word.getWord(), soundChanges));
                });
        return result;
    }

    public String evolveWord(String word, List<ESoundChange> soundChanges) {
        String result = word;
        for (ESoundChange soundChange : soundChanges) {
            result = evolveWordBySingleSoundChange(result, soundChange);
        }
        return result;
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

    public PageResult<WordWithEvolution> getAllWordsWithEvolutions(WordWithEvolutionsListFilter filter) {
        Page<EWordWithEvolutionConnectionsIds> withEvolutions = wordsRepository.findWithEvolutions(filter.getWord(), filter.getLanguageFromId(), filter.getLanguageToId(), filter.isCanBeForgotten(), filter.toPageable());
        List<Long> wordsIds = withEvolutions.stream().map(EWordWithEvolutionConnectionsIds::getWordId).distinct().collect(Collectors.toList());
        List<EWord> sourceWords = wordsRepository.findAllById(wordsIds);
        Map<Long, EWord> wordsSources = sourceWords.stream().collect(Collectors.toMap(BaseEntity::getId, e -> e));
        List<Long> connectionsIds = withEvolutions.stream().map(EWordWithEvolutionConnectionsIds::getLanguageConnectionId).distinct().collect(Collectors.toList());
        List<ELanguageConnection> languageConnections = languageConnectionRepository.findAllById(connectionsIds);
        Map<Long, ELanguageConnection> languageConnectionsFrom = languageConnections.stream().collect(Collectors.toMap(BaseEntity::getId, e -> e));
        List<EWordSource> eWordsSources = wordsRepository.findEvolvedWordsFrom(wordsIds);
        Map<Long, Map<Long, String>> calculatedEvolvedWords = evolveWords(sourceWords, languageConnections);

        return PageResult.from(withEvolutions, (wwE) -> {
            WordWithEvolution wordWithEvolution = new WordWithEvolution();
            EWord eWord = wordsSources.get(wwE.getWordId());
            wordWithEvolution.setWord(convertToRestModel(eWord));
            final ELanguageConnection languageConnection = languageConnectionsFrom.get(wwE.getLanguageConnectionId());
            wordWithEvolution.setLanguageConnection(convertToRestModel(languageConnection));
            eWordsSources
                    .stream()
                    .filter(ews ->
                            ews.getWordSource().getId().equals(wwE.getWordId())
                                    && languageConnection.getLangTo().getId().equals(ews.getWord().getLanguage().getId())
                    )
                    .findFirst()
                    .ifPresent(eWordSource -> {
                        wordWithEvolution.setWordEvolved(convertToRestModel(eWordSource.getWord()));
                        wordWithEvolution.setWordEvolvedType(eWordSource.getSourceType());
                    });
            wordWithEvolution.setCalculatedEvolution(calculatedEvolvedWords.get(wwE.getLanguageConnectionId()).get(wwE.getWordId()));
            return wordWithEvolution;
        });
    }

    public List<WordWithEvolution> addEvolvedWords(List<WordToEvolve> wordsToEvolve) {
        List<LanguageConnection> connections = wordsToEvolve.stream().map(WordToEvolve::getLanguageConnection).distinct().collect(Collectors.toList());
        Map<Long, List<Word>> wordsToEvolveMap = wordsToEvolve.stream().map(WordToEvolve::getWord).collect(Collectors.groupingBy(w -> w.getLanguage().getId()));
        List<WordWithEvolution> result = new ArrayList<>();
        connections.forEach(languageConnection -> {
            List<Word> words = wordsToEvolveMap.get(languageConnection.getLangFrom().getId()).stream().distinct().collect(Collectors.toList());
            result.addAll(addEvolvedWords(words, languageConnection));
        });
        return result;
    }

    public List<WordWithEvolution> addEvolvedWord(Word wordToEvolve, LanguageConnection languageConnection) {
        return addEvolvedWords(List.of(wordToEvolve), languageConnection);
    }

    public List<WordWithEvolution> addEvolvedWords(List<Word> wordToEvolve, LanguageConnection languageConnection) {
        List<EWord> words = wordToEvolve.stream().map(word -> wordsRepository.findById(word.getId()).orElseThrow(() -> new IllegalArgumentException("Word not found"))).collect(Collectors.toList());
        ELanguageConnection eLanguageConnection = languageConnectionRepository.findById(languageConnection.getId()).orElseThrow(() -> new IllegalArgumentException("Language connection not found"));
        words.forEach(word -> {
            if (!word.getLanguage().getId().equals(eLanguageConnection.getLangFrom().getId())) {
                throw new IllegalArgumentException("Language connection is not valid");
            }
        });
        List<ESoundChange> soundChanges = getSoundChanges(languageConnection.getLangFrom(), languageConnection.getLangTo());
        return words.stream().map(word -> addEvolvedWord(word, eLanguageConnection, soundChanges)).collect(Collectors.toList());
    }

    private WordWithEvolution addEvolvedWord(EWord word, ELanguageConnection eLanguageConnection, List<ESoundChange> soundChanges) {
        EWord newWord;
        EWordSource newWordSource;
        Optional<EWordSource> optionalEWordSource = wordsSourceRepository.findByWordSource_IdAndWord_Language_Id(word.getId(), eLanguageConnection.getLangTo().getId());
        if (optionalEWordSource.isPresent()) {
            newWordSource = optionalEWordSource.get();
            newWord = optionalEWordSource.get().getWord();
        } else {
            newWord = new EWord();
            newWordSource = new EWordSource();
        }
        String newWordText = evolveWord(word.getWord(), soundChanges);
        newWord.setWord(newWordText);
        newWord.setLanguage(eLanguageConnection.getLangTo());
        newWord.setPartOfSpeech(word.getPartOfSpeech());
        newWord = wordsRepository.save(newWord);
        newWordSource.setWordSource(word);
        newWordSource.setSourceType(eLanguageConnection.getConnectionType());
        newWordSource.setWord(newWord);
        wordsSourceRepository.save(newWordSource);

        WordWithEvolution wordWithEvolution = new WordWithEvolution();
        wordWithEvolution.setCalculatedEvolution(newWordText);
        wordWithEvolution.setWordEvolved(convertToRestModel(newWord));
        wordWithEvolution.setWord(convertToRestModel(word));
        wordWithEvolution.setWordEvolvedType(eLanguageConnection.getConnectionType());
        wordWithEvolution.setLanguageConnection(convertToRestModel(eLanguageConnection));
        return wordWithEvolution;
    }
}
