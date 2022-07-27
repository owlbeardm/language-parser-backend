package by.c7d5a6.languageparser.service;

import by.c7d5a6.languageparser.entity.*;
import by.c7d5a6.languageparser.entity.enums.SoundChangePurpose;
import by.c7d5a6.languageparser.entity.enums.WordOriginType;
import by.c7d5a6.languageparser.entity.specification.EWordSpecification;
import by.c7d5a6.languageparser.entity.specification.SearchCriteria;
import by.c7d5a6.languageparser.repository.WordsOriginSourceRepository;
import by.c7d5a6.languageparser.repository.WordsRepository;
import by.c7d5a6.languageparser.repository.WordsSourceRepository;
import by.c7d5a6.languageparser.rest.model.*;
import by.c7d5a6.languageparser.rest.model.base.PageResult;
import by.c7d5a6.languageparser.rest.model.filter.WordListFilter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.lang.invoke.MethodHandles;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Stack;
import java.util.stream.Collectors;

@Service
public class WordService extends BaseService {

    private static final Logger logger = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

    private final WordsRepository wordsRepository;
    private final WordsSourceRepository wordsSourceRepository;
    private final WordsOriginSourceRepository wordsOriginSourceRepository;
    private final LanguageService languageService;
    private final EvolutionService evolutionService;
    private final SoundChangesService soundChangesService;
    private final POSService posService;

    @Autowired
    public WordService(WordsRepository wordsRepository, WordsOriginSourceRepository wordsOriginSourceRepository, WordsSourceRepository wordsSourceRepository, LanguageService languageService, EvolutionService evolutionService, SoundChangesService soundChangesService, POSService posService) {
        this.wordsRepository = wordsRepository;
        this.wordsSourceRepository = wordsSourceRepository;
        this.wordsOriginSourceRepository = wordsOriginSourceRepository;
        this.languageService = languageService;
        this.posService = posService;
        this.evolutionService = evolutionService;
        this.soundChangesService = soundChangesService;
    }

    public List<Word> getAllWordsFromLang(Long langId) {
        List<EWord> all = wordsRepository.findByLanguage_Id(langId);
        return all.stream().map(this::convertToRestModel).collect(Collectors.toList());
    }


    public PageResult<WordWithWritten> getAllWords(WordListFilter filter) {
        ELanguage eLanguage = Optional.ofNullable(filter.getLanguageId()).flatMap(languageService::getLangById).orElse(null);
        EPOS epos = Optional.ofNullable(filter.getPosId()).flatMap(posService::getPOSById).orElse(null);
        EWordSpecification spec1 = new EWordSpecification(new SearchCriteria("word", ":", filter.getWord()));
        EWordSpecification spec2 = new EWordSpecification(new SearchCriteria("language", ":", eLanguage));
        EWordSpecification spec3 = new EWordSpecification(new SearchCriteria("partOfSpeech", ":", epos));
        return PageResult.from(
                wordsRepository.findAll(Specification.where(spec1).and(spec2).and(spec3), filter.toPageable()),
                this::getWordWithWritten
        );
    }


    public void deleteWord(Long id) {
        EWord eWord = this.wordsRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Word " + id + " not found"));
        this.wordsRepository.delete(eWord);
    }

    public boolean canDeleteWord(Long wordId) {
        return true;
    }

    public Word saveWord(WordToAdd word) {
        if (word.getWordOriginType() != WordOriginType.NEW) {
            throw new IllegalArgumentException("Only new word allowed");
        }
        EWord eWord;
        if (word.getId() != null) {
            eWord = wordsRepository.findById(word.getId()).orElseThrow(() -> new IllegalArgumentException("Word " + word.getId() + " not found"));
            eWord.setWord(word.getWord());
            eWord.setForgotten(word.getForgotten());
            EPOS epos = posService.getPOSById(word.getPartOfSpeech().getId()).orElseThrow(() -> new IllegalArgumentException("Pos " + word.getPartOfSpeech().getId() + " not found"));
            eWord.setPartOfSpeech(epos);
            ELanguage eLanguage = languageService.getLangById(word.getLanguage().getId()).orElseThrow(() -> new IllegalArgumentException("Language " + word.getLanguage().getId() + " not found"));
            eWord.setLanguage(eLanguage);
        } else {
            eWord = mapper.map(word, EWord.class);
            eWord.setSourceType(word.getWordOriginType());
        }
        eWord = this.wordsRepository.save(eWord);
        return mapper.map(eWord, Word.class);
    }

    public long countWordsByLanguageId(Long languageId) {
        return wordsRepository.countByLanguage_Id(languageId);
    }

    public List<DetailedWord> getDetailedWordsByPhonetics(String word) {
        return wordsRepository.findByWord(word).stream().map(this::convertToRestModel).map(this::getWordWithDetails).collect(Collectors.toList());
    }

    private DetailedWord getWordWithDetails(Word word) {
        DetailedWord detailedWord = new DetailedWord();
        detailedWord.setWord(getWordWithWritten(word));
        detailedWord.setEtymology(getEtymology(word));
        ArrayList<WordWithWritten> wwwr = new ArrayList<>();
        WordWithWritten wordWithWritten = new WordWithWritten();
        wordWithWritten.setWrittenWord("222");
        wordWithWritten.setWord("111");
        wwwr.add(wordWithWritten);
        detailedWord.setDescendants(wwwr);
        detailedWord.setDirived(wwwr);

        ArrayList<String> translations = new ArrayList<>();
        translations.add("translation");
        translations.add("translation");
        getWordWithTranslations();
        detailedWord.setTranslations();
        return detailedWord;
    }

    private Etymology getEtymology(Word word) {
        Etymology etymology = new Etymology();
        etymology.setFrom(getEtymologyFrom(word));
        etymology.setCognate(getEtymologyCognate(word, etymology.getFrom()));
        return etymology;
    }

    private List<WordWithTranslations> getEtymologyFrom(Word word) {
        final List<WordWithTranslations> etymologyFrom = new ArrayList<>();
        Stack<Long> newWordIds = new Stack<>();
        newWordIds.push(word.getId());
        while (!newWordIds.empty()) {
            List<EWordOriginSource> wordOriginSource = wordsOriginSourceRepository.findByWord_Id(newWordIds.pop());
            wordOriginSource.forEach((wos)->{
                EWord wordSource = wos.getWordSource();
                etymologyFrom.add(getWordWithTranslations(wordSource));
                newWordIds.push(wordSource.getId());
            });
        }
        return etymologyFrom;
    }

    private List<WordWithTranslations> getEtymologyCognate(Word word, List<WordWithTranslations> etymologyFrom) {
        final List<WordWithTranslations> etymologyCognate = new ArrayList<>();
        List<Long> langIds = etymologyFrom.stream().map(wfrom -> wfrom.getLanguage().getId()).distinct().collect(Collectors.toList());
        if (!langIds.contains(word.getLanguage().getId())) {
            langIds.add(word.getLanguage().getId());
        }
        List<Word> toCheck = new ArrayList<>(etymologyFrom);
        int i = 0;
        while (i < toCheck.size()) {
            List<EWordOriginSource> wordSources = wordsOriginSourceRepository.findByWordSource_Id(toCheck.get(i).getId());
            wordSources.stream().map(EWordOriginSource::getWord).forEach((w) -> {
                if (!langIds.contains(w.getLanguage().getId())) {
                    etymologyCognate.add(getWordWithTranslations(w));
                    toCheck.add(convertToRestModel(w));
                }
            });
            i++;
        }
        return etymologyCognate;
    }


    private WordWithTranslations getWordWithTranslations(EWord eWord) {
        WordWithTranslations wordWithTranslations = mapper.map(getWordWithWritten(eWord), WordWithTranslations.class);
//        ArrayList<String> translations = new ArrayList<>();
//        translations.add("translation");
//        translations.add("translation");
//        wordWithTranslations.setTranslations(translations);
        return wordWithTranslations;
    }

    private WordWithWritten getWordWithWritten(EWord word) {
        WordWithWritten ww = mapper.map(word, WordWithWritten.class);
        return _getWithWritten(ww);
    }

    private WordWithWritten getWordWithWritten(Word word) {
        WordWithWritten ww = mapper.map(word, WordWithWritten.class);
        return _getWithWritten(ww);
    }

    private WordWithWritten _getWithWritten(WordWithWritten wordWithWritten) {
        String written = getWrittenForm(wordWithWritten);
        wordWithWritten.setWrittenWord(written);
        return wordWithWritten;
    }

    public String getWrittenForm(Word word) {
        List<ESoundChange> soundChangesByLang = soundChangesService.getESoundChangesByLang(word.getLanguage().getId(), SoundChangePurpose.WRITING_SYSTEM);
        String written = evolutionService.evolveWord(word.getWord(), soundChangesByLang);
        return written;
    }

    public Word saveDerivedWord(DerivedWordToAdd word) {
        List<Word> derivedFrom = word.getDerivedFrom();
        if (derivedFrom == null || derivedFrom.isEmpty())
            throw new IllegalArgumentException("Should be at least one word derived from");
        if (word.getWordOriginType() != WordOriginType.DERIVED) {
            throw new IllegalArgumentException("Only derived word allowed");
        }
        EWord eWord;
        if (word.getId() != null) {
            eWord = wordsRepository.findById(word.getId()).orElseThrow(() -> new IllegalArgumentException("Word " + word.getId() + " not found"));
            eWord.setWord(word.getWord());
            eWord.setForgotten(word.getForgotten());
            EPOS epos = posService.getPOSById(word.getPartOfSpeech().getId()).orElseThrow(() -> new IllegalArgumentException("Pos " + word.getPartOfSpeech().getId() + " not found"));
            eWord.setPartOfSpeech(epos);
            ELanguage eLanguage = languageService.getLangById(word.getLanguage().getId()).orElseThrow(() -> new IllegalArgumentException("Language " + word.getLanguage().getId() + " not found"));
            eWord.setLanguage(eLanguage);
        } else {
            eWord = mapper.map(word, EWord.class);
            eWord.setSourceType(word.getWordOriginType());
        }
        eWord = this.wordsRepository.save(eWord);
        for (Word derivedFromWord : derivedFrom) {
            EWord eWordSource = wordsRepository.findById(derivedFromWord.getId()).orElseThrow(() -> new IllegalArgumentException("Word " + derivedFromWord.getId() + " not found"));
            EWordOriginSource eWordOriginSource = new EWordOriginSource();
            eWordOriginSource.setWord(eWord);
            eWordOriginSource.setWordSource(eWordSource);
            eWordOriginSource.setSourceInitialVersion(eWordSource.getWord());
//            TODO: comment
//            eWordOriginSource.setComment();
            wordsOriginSourceRepository.save(eWordOriginSource);
        }
        return mapper.map(eWord, Word.class);
    }
}
