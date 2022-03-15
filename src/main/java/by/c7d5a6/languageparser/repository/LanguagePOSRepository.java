package by.c7d5a6.languageparser.repository;

import by.c7d5a6.languageparser.entity.ELanguagePOS;
import by.c7d5a6.languageparser.entity.EPOS;
import by.c7d5a6.languageparser.repository.helper.IdLongVerRepository;

import java.util.List;

public interface LanguagePOSRepository extends IdLongVerRepository<ELanguagePOS> {
    List<ELanguagePOS> findByLanguage_Id(Long languageId);
}
