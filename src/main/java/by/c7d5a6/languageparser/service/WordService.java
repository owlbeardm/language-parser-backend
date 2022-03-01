package by.c7d5a6.languageparser.service;

import by.c7d5a6.languageparser.entity.ELanguage;
import by.c7d5a6.languageparser.entity.EWord;
import by.c7d5a6.languageparser.repository.WordsRepository;
import by.c7d5a6.languageparser.rest.model.PaginationFilter;
import by.c7d5a6.languageparser.rest.model.Word;
import by.c7d5a6.languageparser.rest.model.base.PageResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.lang.invoke.MethodHandles;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class WordService extends BaseService {

    private static final Logger logger = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

    private final WordsRepository wordsRepository;

    @Autowired
    public WordService(WordsRepository wordsRepository) {
        this.wordsRepository = wordsRepository;
    }

    public List<Word> getAllWordsFromLang(Long langId) {
        List<EWord> all = wordsRepository.findByLanguage_Id(langId);
        return all.stream().map(this::convertToRestModel).collect(Collectors.toList());
    }

    private Word convertToRestModel(EWord eWord) {
        return mapper.map(eWord, Word.class);
    }

    public PageResult<Word> getAllWords(PaginationFilter filter) {
        return PageResult.from(
                wordsRepository.findAll(filter.toPageable()),
                this::convertToRestModel
        );
    }

    public String getLanguagePhonemes(ELanguage eLanguage) {
        return this.wordsRepository
                .findByLanguage_Id(eLanguage.getId())
                .stream()
                .map(EWord::getWord)
                .reduce((a, b) -> a + " " + b)
                .orElse("");
    }

    public void deleteWord(Long id) {
        EWord eWord = this.wordsRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Word " + id + " not found"));
        this.wordsRepository.delete(eWord);
    }

    public boolean canDeleteWord(Long wordId) {
        return true;
    }
}
