package by.c7d5a6.languageparser.service;

import by.c7d5a6.languageparser.repository.WordsRepository;
import by.c7d5a6.languageparser.rest.model.Word;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.lang.invoke.MethodHandles;
import java.util.List;

@Service
public class WordService extends BaseService {

    private static final Logger logger = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

    private final WordsRepository wordsRepository;

    @Autowired
    public WordService(WordsRepository wordsRepository) {
        this.wordsRepository = wordsRepository;
    }

    public List<Word> getAllWords(String from, String text) {
        throw new UnsupportedOperationException("Not implemented yet");
    }
}
