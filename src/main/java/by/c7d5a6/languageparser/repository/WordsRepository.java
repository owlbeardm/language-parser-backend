package by.c7d5a6.languageparser.repository;

import by.c7d5a6.languageparser.entity.EWord;
import by.c7d5a6.languageparser.entity.EWordSource;
import by.c7d5a6.languageparser.entity.models.EWordId;
import by.c7d5a6.languageparser.entity.models.EWordWithEvolutionConnectionsIds;
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

    List<EWord> findByWord(String word);

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
            " AND (lc.lang_from_id = cast(cast(:langFromId as TEXT) as bigint) OR COALESCE(:langFromId, NULL) IS NULL) " +
            " AND (lc.lang_to_id = cast(cast(:langToId as TEXT) as bigint) OR COALESCE(:langToId, NULL) IS NULL) " +
            " AND (:canBeForgotten IS TRUE OR w1.forgotten IS FALSE ) " +
            "ORDER BY w1.word, w1.id, l1.display_name, lc.id", nativeQuery = true)
    Page<EWordWithEvolutionConnectionsIds> findWithEvolutions(@Param("word") String word, @Param("langFromId") Long langFromId, @Param("langToId") Long langToId, @Param("canBeForgotten") boolean canBeForgotten, Pageable pageRequest);

    @Query(value = "SELECT w1.id, w1.version, w1.createdwhen, w1.modiwhen, w1.word, w1.lang_id, w1.pos_id, w1.forgotten, w1.comment " +
            "FROM word_tbl AS w1 " +
            "LEFT JOIN translation_tbl AS t " +
            " ON t.word_from_id = w1.id " +
            "LEFT JOIN word_tbl AS w2 " +
            " ON t.word_to_id = w2.id " +
            "LEFT JOIN phrase_tbl AS ph " +
            " ON t.phrase_to_id = ph.id " +
            "WHERE (w1.word LIKE CONCAT('%',cast(:wordFrom as TEXT),'%') OR :wordFrom IS NULL) " +
            " AND (w1.lang_id = cast(cast(:langFromId as TEXT) as bigint) OR COALESCE(:langFromId, NULL) IS NULL) " +
            " AND ((w2.word LIKE CONCAT('%',cast(:wordTo as TEXT),'%') OR :wordTo IS NULL) OR (ph.phrase LIKE CONCAT('%',:wordTo,'%') OR :wordTo IS NULL)) " +
            " AND (w2.lang_id = cast(cast(:langToId as TEXT) as bigint) OR COALESCE(:langToId, NULL) IS NULL) " +
            "GROUP BY w1.id " +
            "ORDER BY w1.word", nativeQuery = true)
    Page<EWord> findWordsWithTranslations(@Param("wordFrom") String wordFrom, @Param("langFromId") Long langFromId, @Param("wordTo") String wordTo, @Param("langToId") Long langToId, Pageable pageRequest);

    @Query(value = "SELECT ws " +
            "FROM WordSource as ws " +
            "INNER JOIN ws.wordSource as w " +
            " ON w.id = ws.wordSource.id " +
            "WHERE ws.wordSource.id IN :evolvedFromIds")
    List<EWordSource> findEvolvedWordsFrom(List<Long> evolvedFromIds);
}
