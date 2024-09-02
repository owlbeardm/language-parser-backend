package by.c7d5a6.languageparser.repository;

import by.c7d5a6.languageparser.entity.ETranslation;
import by.c7d5a6.languageparser.repository.helper.IdLongVerRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;

public interface TranslationRepository extends IdLongVerRepository<ETranslation>, JpaSpecificationExecutor<ETranslation> {
    List<ETranslation> findByWordFrom_Id(Long id);

}
