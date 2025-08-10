package datasource.repository;

import datasource.model.DSCurrentGame;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import web.model.Statistic;

import java.util.Collection;



public interface DSRepository extends CrudRepository<DSCurrentGame, Integer> {
    @Query(value = "SELECT * FROM dscurrent_game ds WHERE :uuid = ANY(ds.user_id) AND (ds.condition LIKE 'Win%' AND :uuid = REGEXP_SUBSTR(ds.condition, '\\d+')::INTEGER OR ds.condition LIKE 'Tie%');", nativeQuery = true)
    Collection<DSCurrentGame> getSpecificCurrentGames(Integer uuid);
    @Query(value = "SELECT \n" +
            "    usr_id,\n" +
            "    COUNT(*) FILTER (\n" +
            "        WHERE usr_id = ANY(ds.user_id) AND ds.condition LIKE 'Win%' AND usr_id = REGEXP_SUBSTR(ds.condition, '\\d+')::INTEGER\n" +
            "    ) /\n" +
            "    NULLIF(COUNT(*) FILTER (\n" +
            "        WHERE ds.condition LIKE 'Win%' AND usr_id = ANY(ds.user_id) AND (usr_id <> REGEXP_SUBSTR(ds.condition, '\\d+')::INTEGER OR ds.condition LIKE 'Win of AI%')\n" +
            "\t\tOR ds.condition LIKE 'Tie%' AND usr_id = ANY(ds.user_id)),0)::REAL AS ratio\n" +
            "FROM \n" +
            "    dscurrent_game AS ds,\n" +
            "    LATERAL unnest(user_id) AS usr_id\n" +
            "GROUP BY \n" +
            "    usr_id\n" +
            "ORDER BY\n" +
            "\tratio desc\n" +
            "LIMIT :n;", nativeQuery = true)
    Collection<Statistic> getStatistics(Integer n);
    @Query(value = "SELECT * FROM dscurrent_game ds WHERE :uuid = ANY(ds.user_id) AND (ds.condition LIKE 'Win%' OR ds.condition LIKE 'Tie%');", nativeQuery = true)
    Collection<DSCurrentGame> getAllFinishedCurrentGames(Integer uuid);
}

