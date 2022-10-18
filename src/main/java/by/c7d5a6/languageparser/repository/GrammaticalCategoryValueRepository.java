package by.c7d5a6.languageparser.repository;

import by.c7d5a6.languageparser.entity.EGrammaticalCategory;
import by.c7d5a6.languageparser.entity.EGrammaticalCategoryValue;
import by.c7d5a6.languageparser.repository.helper.IdLongVerRepository;

import java.util.List;

public interface GrammaticalCategoryValueRepository extends IdLongVerRepository<EGrammaticalCategoryValue> {

    List<EGrammaticalCategoryValue> findByGrammaticalCategory_Id(Long categoryId);
}
