package quintor.bioinf.catalog.backend.catalogbackend.DataLayer.Repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import quintor.bioinf.catalog.backend.catalogbackend.DataLayer.Entities.Component;
import quintor.bioinf.catalog.backend.catalogbackend.DataLayer.Entities.ComponentSpecs;

import java.util.List;

/**
 * This interface is used to interact with the ComponentSpecs table in the database.
 * <p>
 * The findByComponent method is added to this interface
 *
 * @see ComponentSpecs
 */
@Repository
public interface ComponentSpecsRepository extends CrudRepository<ComponentSpecs, Long> {

    /**
     * Method that retrieves all the ComponentSpecs from the database that belong to a certain component.
     *
     * @param component The component to which the specs belong
     * @return A list of all the ComponentSpecs that belong to the given component
     */
    List<ComponentSpecs> findByComponent(Component component);
}
