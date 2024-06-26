package quintor.bioinf.catalog.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import quintor.bioinf.catalog.entities.User;

import java.util.Optional;

/**
 * This interface is used to interact with the User table in the database.
 *
 * @see User
 */
@Repository
public interface UserRepository extends JpaRepository<User, Integer> {

    User findByName(String name);

    Optional<User> findByEmail(String email);

}
