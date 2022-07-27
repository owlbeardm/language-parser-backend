package by.c7d5a6.languageparser.repository;

import by.c7d5a6.languageparser.entity.ETranslation;
import by.c7d5a6.languageparser.entity.EWordOriginSource;
import by.c7d5a6.languageparser.entity.EWordSource;
import by.c7d5a6.languageparser.repository.helper.IdLongVerRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface WordsOriginSourceRepository extends IdLongVerRepository<EWordOriginSource>, JpaSpecificationExecutor<EWordOriginSource> {

    Optional<EWordOriginSource> findByWordSource_IdAndWord_Language_Id(@Param("wordSourceId") Long wordSourceId, @Param("languageId") Long languageId);
    List<EWordOriginSource> findByWord_Id(Long wordId);
    List<EWordOriginSource> findByWordSource_Id(Long wordId);
    long countByWordSource_Language_IdAndWord_Language_Id(Long langFromId, Long langToId);


}
