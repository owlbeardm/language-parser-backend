package by.c7d5a6.languageparser.repository;

import by.c7d5a6.languageparser.entity.EWordSource;
import by.c7d5a6.languageparser.repository.helper.IdLongVerRepository;

import java.util.Optional;

public interface WordsSourceRepository extends IdLongVerRepository<EWordSource> {
    Optional<EWordSource> findByWordSource_IdAndWord_Language_Id(Long id, Long languageId);
}
