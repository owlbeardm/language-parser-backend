package by.c7d5a6.languageparser.repository;

import by.c7d5a6.languageparser.entity.EGrammaticalCategory;
import by.c7d5a6.languageparser.entity.EGrammaticalCategoryValue;
import by.c7d5a6.languageparser.repository.helper.IdLongVerRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface GrammaticalCategoryValueRepository extends IdLongVerRepository<EGrammaticalCategoryValue> {

    List<EGrammaticalCategoryValue> findByCategory_Id(Long categoryId);

    @Query("SELECT value " +
            "FROM GrammaticalCategoryValueConnection connection " +
            "JOIN connection.value value " +
            "ON connection.value.id = value.id  " +
            "WHERE connection.language.id = :langId " +
            "AND value.category.id = :categoryId")
    List<EGrammaticalCategoryValue> findByCategoryAndLang(Long categoryId, Long langId);
}
