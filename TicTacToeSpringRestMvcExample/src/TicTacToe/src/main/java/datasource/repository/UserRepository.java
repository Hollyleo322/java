package datasource.repository;

import datasource.model.S21User;
import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends CrudRepository<S21User, Integer> {
}
