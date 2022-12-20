package by.c7d5a6.languageparser.repository;

import by.c7d5a6.languageparser.entity.EGrammaticalValueWordConnection;
import by.c7d5a6.languageparser.repository.helper.IdLongVerRepository;

import java.util.List;
import java.util.Optional;

public interface GrammaticalValueWordRepository extends IdLongVerRepository<EGrammaticalValueWordConnection> {

    List<EGrammaticalValueWordConnection> findByWord_Id(Long wordId);
    Optional<EGrammaticalValueWordConnection> findByWord_IdAndValue_Id(Long wordId, Long valueId);
    void deleteAllByWord_Id(Long wordId);
    void deleteAllByWord_IdAndValue_Category_Id(Long wordId, Long categoryId);
}
