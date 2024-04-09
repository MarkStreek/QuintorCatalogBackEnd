package quintor.bioinf.catalog.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import quintor.bioinf.catalog.entities.Specs;

/**
 * This interface is used to interact with the Specs table in the database.
 * <p>
 * The findByName method is added to this interface
 *
 * @see Specs
 */
@Repository
public interface SpecsRepository extends JpaRepository<Specs, Long> {

    /**
     * Method that retrieves specs by their name
     *
     * @param name The name of the specs
     * @return The specs with the given name
     */
    Specs findByName(String name);

}
