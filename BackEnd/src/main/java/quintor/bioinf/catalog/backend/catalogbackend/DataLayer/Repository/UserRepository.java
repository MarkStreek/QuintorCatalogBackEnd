package quintor.bioinf.catalog.backend.catalogbackend.DataLayer.Repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import quintor.bioinf.catalog.backend.catalogbackend.DataLayer.Entities.User;

/**
 * This interface is used to interact with the User table in the database.
 *
 * @see User
 */
@Repository
public interface UserRepository extends CrudRepository<User, Integer> {
}
