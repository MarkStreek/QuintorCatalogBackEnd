package quintor.bioinf.catalog.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import quintor.bioinf.catalog.entities.Device;
import quintor.bioinf.catalog.entities.DeviceSpecs;

import java.util.List;

/**
 * This interface is used to interact with the DeviceSpecs table in the database.
 * <p>
 * The findByComponent method is added to this interface
 *
 * @see DeviceSpecs
 */
@Repository
public interface DeviceSpecsRepository extends CrudRepository<DeviceSpecs, Long> {

    /**
     * Method that retrieves all the DeviceSpecs from the database that belong to a certain device.
     *
     * @param device The device to which the specs belong
     * @return A list of all the DeviceSpecs that belong to the given device
     */
    List<DeviceSpecs> findByDevice(Device device);
}
