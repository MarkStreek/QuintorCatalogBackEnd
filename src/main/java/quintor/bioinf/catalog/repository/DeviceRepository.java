package quintor.bioinf.catalog.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import quintor.bioinf.catalog.entities.Device;

/**
 * This interface is used to interact with the Device table in the database.
 *
 * @see Device
 */
@Repository
public interface DeviceRepository extends CrudRepository<Device, Long>{
}
