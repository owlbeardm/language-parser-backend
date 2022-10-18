package by.c7d5a6.languageparser.repository;

import by.c7d5a6.languageparser.entity.EGrammaticalCategory;
import by.c7d5a6.languageparser.entity.EPOS;
import by.c7d5a6.languageparser.repository.helper.IdLongVerRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface GrammaticalCategoryRepository extends IdLongVerRepository<EGrammaticalCategory> {
}
