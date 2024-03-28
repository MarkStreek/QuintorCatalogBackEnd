package quintor.bioinf.catalog.repository;

import org.springframework.data.jdbc.repository.query.Modifying;
import org.springframework.data.jpa.repository.query.Procedure;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import quintor.bioinf.catalog.entities.Device;

/**
 * This interface is used to interact with the Device table in the database.
 *
 * @see Device
 */
@Repository
public interface DeviceRepository extends CrudRepository<Device, Long>{

    @Modifying
    @Procedure("add_device")
    void addDevice(
            @Param("p_name") String name,
            @Param("p_brandName") String brandName,
            @Param("p_model") String model,
            @Param("p_serialNumber") String serialNumber,
            @Param("p_invoiceNumber") String invoiceNumber,
            @Param("p_location_id") Long locationId);

    @Modifying
    @Procedure("delete_device")
    void deleteDevice(@Param("p_device_id") Long deviceId);

    // Derived query method
    Device findFirstByOrderByIdDesc();

}
