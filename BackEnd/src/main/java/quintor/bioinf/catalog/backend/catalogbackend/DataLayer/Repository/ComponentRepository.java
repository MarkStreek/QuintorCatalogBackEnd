package quintor.bioinf.catalog.backend.catalogbackend.DataLayer.Repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import quintor.bioinf.catalog.backend.catalogbackend.DataLayer.Entities.Component;

/**
 * This interface is used to interact with the Component table in the database.
 *
 * @see Component
 */
@Repository
public interface ComponentRepository extends CrudRepository<Component, Long>{
}
