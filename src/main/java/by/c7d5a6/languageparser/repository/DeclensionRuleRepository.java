package by.c7d5a6.languageparser.repository;

import by.c7d5a6.languageparser.entity.EDeclension;
import by.c7d5a6.languageparser.entity.EDeclensionRule;
import by.c7d5a6.languageparser.repository.helper.IdLongVerRepository;

import java.util.List;

public interface DeclensionRuleRepository extends IdLongVerRepository<EDeclensionRule> {
    List<EDeclensionRule> findByDeclension_Id(Long declensionId);
}
