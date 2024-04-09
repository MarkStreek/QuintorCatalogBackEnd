package quintor.bioinf.catalog.repository;

import org.springframework.data.jdbc.repository.query.Modifying;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.query.Procedure;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import quintor.bioinf.catalog.entities.Location;

/**
 * This interface is used to interact with the Location table in the database.
 * <p>
 * The findByAddress method is added to this interface
 *
 * @see Location
 */
@Repository
public interface LocationRepository extends JpaRepository<Location, Integer> {

    /**
     * Method that retrieves a location by its address
     *
     * @param address The address of the location
     * @return The location with the given address
     */
    Location findByAddress(String address);

    @Modifying
    @Procedure(outputParameterName = "p_last_insert_id", procedureName = "add_location")
    Long addLocation(
            @Param("p_name") String name,
            @Param("p_city") String city,
            @Param("p_address") String address);

    @Modifying
    @Procedure("update_location")
    void updateLocation(
            @Param("p_location_id") Long locationId,
            @Param("p_name") String name,
            @Param("p_city") String city,
            @Param("p_address") String address);

}
