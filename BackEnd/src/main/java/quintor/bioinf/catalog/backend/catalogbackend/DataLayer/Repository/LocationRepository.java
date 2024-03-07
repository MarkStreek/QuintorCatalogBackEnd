/*
LocationRepository.java is a class that is used to interact with the database.

It is extended from the CrudRepository interface,
which is a generic interface that provides CRUD operations on the database.

A custom method is added to search by address. Therefore, a location can be re-used for a component.
 */

package quintor.bioinf.catalog.backend.catalogbackend.DataLayer.Repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import quintor.bioinf.catalog.backend.catalogbackend.DataLayer.Entities.Location;

@Repository
public interface LocationRepository extends CrudRepository<Location, Integer> {

    /*
    This method is used to search for a location by address.
    @param address: String that contains the address of searched location
    @return The location with the given address.
     */
    Location findByAddress(String address);
}
