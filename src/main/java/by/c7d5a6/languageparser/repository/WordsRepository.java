package by.c7d5a6.languageparser.repository;

import by.c7d5a6.languageparser.entity.ELanguage;
import by.c7d5a6.languageparser.entity.EWord;
import by.c7d5a6.languageparser.repository.helper.IdLongVerRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;

public interface WordsRepository extends IdLongVerRepository<EWord>, JpaSpecificationExecutor<EWord> {
    List<EWord> findByLanguage_Id(Long languageId);
    long countByLanguage_Id(Long languageId);
}
