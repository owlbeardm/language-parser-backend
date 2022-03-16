package by.c7d5a6.languageparser.repository;

import by.c7d5a6.languageparser.entity.EPOS;
import by.c7d5a6.languageparser.entity.ELanguagePOS;
import by.c7d5a6.languageparser.repository.helper.IdLongVerRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface POSRepository extends IdLongVerRepository<EPOS> {
    @Query("SELECT lp.pos " +
            "FROM LanguagePOS lp " +
            "JOIN lp.pos p " +
            "ON lp.pos.id = p.id  " +
            "WHERE lp.language.id = :languageId")
    List<EPOS> findByLanguage_Id(Long languageId);
}
