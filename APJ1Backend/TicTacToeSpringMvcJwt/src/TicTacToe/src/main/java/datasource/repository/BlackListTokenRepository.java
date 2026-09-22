package datasource.repository;

import datasource.model.BlackListOfTokens;
import org.springframework.data.repository.CrudRepository;

public interface BlackListTokenRepository extends CrudRepository<BlackListOfTokens, Integer> {
}
