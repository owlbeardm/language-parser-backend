package by.c7d5a6.languageparser.repository;

import by.c7d5a6.languageparser.entity.EGrammaticalCategoryConnection;
import by.c7d5a6.languageparser.entity.EGrammaticalCategoryValue;
import by.c7d5a6.languageparser.repository.helper.IdLongVerRepository;

import java.util.List;

public interface GrammaticalCategoryConnectionRepository extends IdLongVerRepository<EGrammaticalCategoryConnection> {

    List<EGrammaticalCategoryConnection> findByGrammaticalCategory_IdAndLanguage_Id(Long categoryId, Long languageId);
    List<EGrammaticalCategoryConnection> findByPos_IdAndLanguage_Id(Long posId, Long languageId);
}
