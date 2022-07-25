package by.c7d5a6.languageparser.repository;

import by.c7d5a6.languageparser.entity.ETranslation;
import by.c7d5a6.languageparser.entity.EWordOriginSource;
import by.c7d5a6.languageparser.repository.helper.IdLongVerRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface WordsOriginSourceRepository extends IdLongVerRepository<EWordOriginSource>, JpaSpecificationExecutor<EWordOriginSource> {

    Optional<EWordOriginSource> findByWordSource_IdAndWord_Language_Id(@Param("wordSourceId") Long wordSourceId, @Param("languageId") Long languageId);
}
