package datasource.repository;

import datasource.model.DSCurrentGame;
import org.springframework.data.repository.CrudRepository;


public interface DSRepository extends CrudRepository<DSCurrentGame, Integer> {
}

