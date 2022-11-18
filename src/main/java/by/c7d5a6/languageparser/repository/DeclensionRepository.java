package by.c7d5a6.languageparser.repository;

import by.c7d5a6.languageparser.entity.EDeclension;
import by.c7d5a6.languageparser.entity.EDeclensionConnection;
import by.c7d5a6.languageparser.repository.helper.IdLongVerRepository;

import java.util.List;

public interface DeclensionRepository extends IdLongVerRepository<EDeclension> {
    List<EDeclension> findByLanguage_IdAndPos_Id(Long posId, Long languageId);
}
