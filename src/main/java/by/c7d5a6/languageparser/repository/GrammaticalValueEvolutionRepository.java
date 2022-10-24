package by.c7d5a6.languageparser.repository;

import by.c7d5a6.languageparser.entity.EGrammaticalValueEvolution;
import by.c7d5a6.languageparser.repository.helper.IdLongVerRepository;

import java.util.Optional;

public interface GrammaticalValueEvolutionRepository extends IdLongVerRepository<EGrammaticalValueEvolution> {

    Optional<EGrammaticalValueEvolution> findByLanguageFrom_IdAndLanguageTo_IdAndPos_IdAndValueFrom_Id(Long langFromId, Long langToId, Long posId, Long valueFromId);

}
