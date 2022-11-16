package by.c7d5a6.languageparser.repository;

import by.c7d5a6.languageparser.entity.EDeclensionConnection;
import by.c7d5a6.languageparser.entity.EGrammaticalCategoryConnection;
import by.c7d5a6.languageparser.repository.helper.IdLongVerRepository;

import java.util.List;

public interface DeclensionConnectionRepository extends IdLongVerRepository<EDeclensionConnection> {
    List<EDeclensionConnection> findByLanguage_IdAndPos_Id(Long posId, Long languageId);
}
