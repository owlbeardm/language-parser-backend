package by.c7d5a6.languageparser.repository;

import by.c7d5a6.languageparser.entity.ELanguage;
import by.c7d5a6.languageparser.entity.ELanguageConnection;
import by.c7d5a6.languageparser.repository.helper.IdLongVerRepository;

import java.util.List;
import java.util.Optional;

public interface LanguageConnectionRepository extends IdLongVerRepository<ELanguageConnection> {
    Optional<ELanguageConnection> findByLangFrom_IdAndLangTo_Id(Long idFrom, Long idTo);
    List<ELanguageConnection> findByLangTo_Id(Long id);
    long countByLangFrom_Id(Long id);
}
