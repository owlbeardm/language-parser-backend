package by.c7d5a6.languageparser.repository;

import by.c7d5a6.languageparser.entity.ELanguagePhoneme;
import by.c7d5a6.languageparser.entity.EPOS;
import by.c7d5a6.languageparser.repository.helper.IdLongVerRepository;

import java.util.List;

public interface LanguagePhonemeRepository extends IdLongVerRepository<ELanguagePhoneme> {
    List<ELanguagePhoneme> findByLanguage_Id(Long languageId);
}
