package by.c7d5a6.languageparser.service;

import by.c7d5a6.languageparser.entity.*;
import by.c7d5a6.languageparser.entity.models.EWordId;
import by.c7d5a6.languageparser.entity.specification.EWordSpecification;
import by.c7d5a6.languageparser.entity.specification.SearchCriteria;
import by.c7d5a6.languageparser.repository.TranslationRepository;
import by.c7d5a6.languageparser.repository.WordsRepository;
import by.c7d5a6.languageparser.repository.WordsSourceRepository;
import by.c7d5a6.languageparser.rest.model.*;
import by.c7d5a6.languageparser.rest.model.base.PageResult;
import by.c7d5a6.languageparser.rest.model.filter.TranslationListFilter;
import by.c7d5a6.languageparser.rest.model.filter.WordListFilter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.lang.invoke.MethodHandles;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
public class TranslationService extends BaseService {

    private static final Logger logger = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

    private final WordsRepository wordsRepository;
    private final TranslationRepository translationRepository;
    private final WordService wordService;

    @Autowired
    public TranslationService(WordsRepository wordsRepository, TranslationRepository translationRepository, WordService wordService) {
        this.wordsRepository = wordsRepository;
        this.translationRepository = translationRepository;
        this.wordService = wordService;
    }

    public PageResult<WordWithTranslations> getAllWords(TranslationListFilter filter) {
        Page<EWord> words = wordsRepository.findWordsWithTranslations(filter.getWord(), filter.getLanguageFromId(), filter.getTranslation(), filter.getLanguageToId(), filter.toPageable());
        Page<WordWithTranslations> map = words.map((eid) -> {
            WordWithTranslations word = mapper.map(eid, WordWithTranslations.class);
            word.setWrittenWord(wordService.getWrittenForm(word));
            List<Translation> translations = translationRepository.findByWordFrom_Id(word.getId()).stream().map(this::convertToRestModelWithWord).collect(Collectors.toList());
            word.setTranslations(translations);
            return word;
        });
        return PageResult.from(map);
    }

    private Translation convertToRestModelWithWord(ETranslation translation) {
        Translation t = mapper.map(translation, Translation.class);
        if (t.getWordTo() != null)
            t.getWordTo().setWrittenWord(wordService.getWrittenForm(t.getWordTo()));
        return t;
    }

    public void deleteTranslation(Long id) {
        this.translationRepository.deleteById(id);
    }

    public Long addTranlation(Translation tr) {
        ETranslation translation = mapper.map(tr, ETranslation.class);
        if (translation.getId() == null) {
            if (translation.getWordTo() != null) {
                ETranslation newTranslation = new ETranslation();
                newTranslation.setType(translation.getType());
                newTranslation.setWordFrom(translation.getWordTo());
                newTranslation.setWordTo(translation.getWordFrom());
                this.translationRepository.save(newTranslation);
            }
        }
        return this.translationRepository.save(translation).getId();
    }

    public List<Translation> getTranslationsForWord(Long id) {
        return translationRepository.findByWordFrom_Id(id).stream().map(this::convertToRestModelWithWord).collect(Collectors.toList());
    }
}
