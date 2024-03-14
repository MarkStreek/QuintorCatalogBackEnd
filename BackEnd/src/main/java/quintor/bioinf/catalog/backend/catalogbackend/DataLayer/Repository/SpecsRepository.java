package quintor.bioinf.catalog.backend.catalogbackend.DataLayer.Repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import quintor.bioinf.catalog.backend.catalogbackend.DataLayer.Entities.Specs;

/**
 * This interface is used to interact with the Specs table in the database.
 * <p>
 * The findByName method is added to this interface
 *
 * @see Specs
 */
@Repository
public interface SpecsRepository extends CrudRepository<Specs, Long> {

    /**
     * Method that retrieves specs by their name
     *
     * @param name The name of the specs
     * @return The specs with the given name
     */
    Specs findByName(String name);


}
