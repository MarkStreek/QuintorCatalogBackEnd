package quintor.bioinf.catalog.repository;

import org.springframework.data.jdbc.repository.query.Modifying;
import org.springframework.data.jpa.repository.JpaRepository;
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
public interface DeviceRepository extends JpaRepository<Device, Long> {

    @Modifying
    @Procedure(outputParameterName = "p_last_insert_id", procedureName = "add_device")
    Long addDevice(
            @Param("p_type") String type,
            @Param("p_brandName") String brandName,
            @Param("p_model") String model,
            @Param("p_serialNumber") String serialNumber,
            @Param("p_invoiceNumber") String invoiceNumber,
            @Param("p_location_id") Long locationId);

    @Modifying
    @Procedure("delete_device")
    void deleteDevice(@Param("p_device_id") Long deviceId);

    @Modifying
    @Procedure("update_device")
    void updateDevice(
            @Param("p_device_id") Long deviceId,
            @Param("p_type") String type,
            @Param("p_brandName") String brandName,
            @Param("p_model") String model,
            @Param("p_serialNumber") String serialNumber,
            @Param("p_invoiceNumber") String invoiceNumber,
            @Param("p_location_id") Long locationId);

}

