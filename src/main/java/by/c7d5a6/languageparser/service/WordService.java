package by.c7d5a6.languageparser.service;

import by.c7d5a6.languageparser.entity.ELanguage;
import by.c7d5a6.languageparser.entity.EPOS;
import by.c7d5a6.languageparser.entity.EWord;
import by.c7d5a6.languageparser.entity.specification.EWordSpecification;
import by.c7d5a6.languageparser.entity.specification.SearchCriteria;
import by.c7d5a6.languageparser.repository.WordsRepository;
import by.c7d5a6.languageparser.rest.model.Word;
import by.c7d5a6.languageparser.rest.model.WordListFilter;
import by.c7d5a6.languageparser.rest.model.base.PageResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.lang.invoke.MethodHandles;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class WordService extends BaseService {

    private static final Logger logger = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

    private final WordsRepository wordsRepository;
    private final LanguageService languageService;
    private final POSService posService;

    @Autowired
    public WordService(WordsRepository wordsRepository, LanguageService languageService, POSService posService) {
        this.wordsRepository = wordsRepository;
        this.languageService = languageService;
        this.posService = posService;
    }

    public List<Word> getAllWordsFromLang(Long langId) {
        List<EWord> all = wordsRepository.findByLanguage_Id(langId);
        return all.stream().map(this::convertToRestModel).collect(Collectors.toList());
    }


    public PageResult<Word> getAllWords(WordListFilter filter) {
        ELanguage eLanguage = Optional.ofNullable(filter.getLanguageId()).flatMap(languageService::getLangById).orElse(null);
        EPOS epos = Optional.ofNullable(filter.getPosId()).flatMap(posService::getPOSById).orElse(null);
        EWordSpecification spec1 = new EWordSpecification(new SearchCriteria("word", ":", filter.getWord()));
        EWordSpecification spec2 = new EWordSpecification(new SearchCriteria("language", ":", eLanguage));
        EWordSpecification spec3 = new EWordSpecification(new SearchCriteria("partOfSpeech", ":", epos));
        return PageResult.from(
                wordsRepository.findAll(Specification.where(spec1).and(spec2).and(spec3), filter.toPageable()),
                this::convertToRestModel
        );
    }


    public void deleteWord(Long id) {
        EWord eWord = this.wordsRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Word " + id + " not found"));
        this.wordsRepository.delete(eWord);
    }

    public boolean canDeleteWord(Long wordId) {
        return true;
    }

    public Word saveWord(Word word) {
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
        }
        eWord = this.wordsRepository.save(eWord);
        return mapper.map(eWord, Word.class);
    }

    public long countWordsByLanguageId(Long languageId) {
        return wordsRepository.countByLanguage_Id(languageId);
    }
}
