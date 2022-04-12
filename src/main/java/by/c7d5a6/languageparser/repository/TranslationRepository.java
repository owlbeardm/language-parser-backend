package by.c7d5a6.languageparser.repository;

import by.c7d5a6.languageparser.entity.ETranslation;
import by.c7d5a6.languageparser.entity.EWord;
import by.c7d5a6.languageparser.entity.EWordSource;
import by.c7d5a6.languageparser.entity.models.EWordWithEvolutionConnectionsIds;
import by.c7d5a6.languageparser.repository.helper.IdLongVerRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface TranslationRepository extends IdLongVerRepository<ETranslation>, JpaSpecificationExecutor<ETranslation> {
    List<ETranslation> findByWordFrom_Id(Long id);

}
