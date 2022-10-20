package by.c7d5a6.languageparser.repository;

import by.c7d5a6.languageparser.entity.EGrammaticalCategoryValueConnection;
import by.c7d5a6.languageparser.repository.helper.IdLongVerRepository;

import java.util.List;

public interface GrammaticalCategoryValueConnectionRepository extends IdLongVerRepository<EGrammaticalCategoryValueConnection> {

    List<EGrammaticalCategoryValueConnection> findByLanguage_Id(Long langId);
}
