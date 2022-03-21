package by.c7d5a6.languageparser.repository;

import by.c7d5a6.languageparser.entity.EWord;
import by.c7d5a6.languageparser.entity.EWordWithEvolutionConnectionsIds;
import by.c7d5a6.languageparser.entity.EWordWithSourcesIds;
import by.c7d5a6.languageparser.repository.helper.IdLongVerRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

//https://www.baeldung.com/spring-data-jpa-query
//https://www.baeldung.com/hibernate-criteria-queries
public interface WordsRepository extends IdLongVerRepository<EWord>, JpaSpecificationExecutor<EWord> {
    List<EWord> findByLanguage_Id(Long languageId);

    long countByLanguage_Id(Long languageId);

    //https://stackoverflow.com/questions/50569238/set-optional-parameters-in-jpql-query
    @Query(value = "SELECT w1.id as wordId, lc.id as languageConnectionId " +
            "FROM word_tbl AS w1 " +
            "INNER JOIN language_tbl AS l1 " +
            " ON w1.lang_id = l1.id " +
            "LEFT JOIN language_connection_tbl AS lc " +
            " ON lc.lang_from_id = l1.id " +
            "WHERE lc.connection_type = 'EVOLVING' " +
            " AND (w1.word LIKE CONCAT('%',cast(:word as TEXT),'%') OR :word IS NULL) " +
            " AND (lc.lang_from_id = cast(cast(:langFromId as text) as bigint) OR COALESCE(:langFromId, NULL) IS NULL) " +
            " AND (lc.lang_to_id = cast(cast(:langToId as text) as bigint) OR COALESCE(:langToId, NULL) IS NULL) " +
            "ORDER BY w1.word, w1.id, l1.display_name, lc.id", nativeQuery = true)
    Page<EWordWithEvolutionConnectionsIds> findWithEvolutions(@Param("word") String word, @Param("langFromId") Long langFromId, @Param("langToId") Long langToId, Pageable pageRequest);

    @Query(value = "SELECT w.id as wordEvolvedId, ws.id as wordSourceEvolvedId " +
            "FROM word_tbl as w " +
            "INNER JOIN word_source_tbl as ws " +
            " ON w.id = ws.word_source_id " +
            "WHERE ws.source_type = 'EVOLVING' AND ws.word_id IN :wordIds", nativeQuery = true)
    List<EWordWithSourcesIds> findEvolvedWords(List<Long> wordIds);
}
