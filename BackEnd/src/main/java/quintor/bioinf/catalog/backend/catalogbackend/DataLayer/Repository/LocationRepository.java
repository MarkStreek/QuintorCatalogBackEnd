package quintor.bioinf.catalog.backend.catalogbackend.DataLayer.Repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import quintor.bioinf.catalog.backend.catalogbackend.DataLayer.Entities.Location;

/**
 * This interface is used to interact with the Location table in the database.
 * <p>
 * The findByAddress method is added to this interface
 *
 * @see Location
 */
@Repository
public interface LocationRepository extends CrudRepository<Location, Integer> {

    /**
     * Method that retrieves a location by its address
     *
     * @param address The address of the location
     * @return The location with the given address
     */
    Location findByAddress(String address);
}
