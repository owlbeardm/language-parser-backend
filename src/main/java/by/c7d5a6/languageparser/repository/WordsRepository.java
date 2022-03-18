package by.c7d5a6.languageparser.repository;

import com.owlbeardm.languageparser.entity.EWord;
import com.owlbeardm.languageparser.repository.helper.IdLongVerRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

//https://www.baeldung.com/spring-data-jpa-query
//https://www.baeldung.com/hibernate-criteria-queries
public interface WordsRepository extends IdLongVerRepository<EWord>, JpaSpecificationExecutor<EWord> {
    List<EWord> findByLanguage_Id(Long languageId);
    long countByLanguage_Id(Long languageId);

    @Query(value = "SELECT w1 " +
            "FROM Word AS w1 " +
            "INNER JOIN Language AS l1 " +
            " ON w1.language.id = l1.id " +
            "LEFT JOIN LanguageConnection AS lc " +
            " ON lc.langFrom.id = l1.id " +
            "LEFT JOIN Language AS l2 " +
            " ON lc.langTo.id = l2.id " +
            "LEFT JOIN WordSource AS ws " +
            " ON ws.word.id=w1.id " +
            "LEFT JOIN Word AS w2 " +
            " ON ws.wordSource.id=w2.id" +
            " ORDER BY w1.word,w1.id,l1.displayName,l2.id")
    List<Object[]> findWithEvolutions();
}
