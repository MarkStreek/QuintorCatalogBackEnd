package quintor.bioinf.catalog.services;

import jakarta.persistence.EntityNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import quintor.bioinf.catalog.dto.DeviceDTO;
import quintor.bioinf.catalog.dto.DeviceDTOConverter;
import quintor.bioinf.catalog.dto.SpecDetail;
import quintor.bioinf.catalog.entities.Device;
import quintor.bioinf.catalog.repository.DeviceRepository;

import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;
import java.util.function.Function;

/**
 * The main service method of the application. The service class is autowired by a controller class.
 * <p>
 * The service class is responsible for adding, deleting and updating components in the database.
 * For these operations, LocationService and CreateComponentSpecs are required as well.
 * Therefore, the service class uses methods from these services to add, delete and update the components.
 *
 * @see LocationService
 * @see SpecsService
 */
@Service
public class MainDeviceService {

    private static final Logger log = LoggerFactory.getLogger(MainDeviceService.class);
    private final LocationService locationService;
    private final SpecsService specsService;
    private final DeviceRepository deviceRepository;
    private final Function<Device, DeviceDTO> deviceDTOConverter;

    @Autowired
    public MainDeviceService(
            DeviceRepository deviceRepository,
            SpecsService specsService,
            LocationService locationService,
            Function<Device, DeviceDTO> deviceDTOConverter
    )
    {
        this.deviceRepository = deviceRepository;
        this.specsService = specsService;
        this.locationService = locationService;
        this.deviceDTOConverter = deviceDTOConverter;
    }

    /**
     * Main Service method that adds a component to the database.
     *  1. Create the component with the give characteristics
     *  2. Location is created and added to the database
     *  3. The Location is added to the component
     *  4. Device is saved to the database
     *  5. Device Specs are created and added to the database
     *  <p>
     *  The Device must first be stored in the database,
     *  before it can be used in the DeviceSpecs
     *
     * @param type Name of the component
     * @param brandName Brand name of the component
     * @param model Model of the component
     * @param serialNumber Serial number of the component
     * @param invoiceNumber Invoice number of the component
     * @param city City of the location
     * @param locationAddress Address of the location
     * @param locationName Name of the location (i.e. "Server room")
     * @param specs The specifications of the component
     */
    @Transactional
    public void addDevice(
            String type,
            String brandName,
            String model,
            String serialNumber,
            String invoiceNumber,
            String city,
            String locationAddress,
            String locationName,
            List<SpecDetail> specs)
    {
        // 1 - Create the location
        Long locationId = this.locationService.findOrCreateLocation(locationName, city, locationAddress);
        // 2 - Add the device to the database and get the id of the inserted device
        Long deviceId = this.deviceRepository.addDevice(type, brandName, model, serialNumber, invoiceNumber, locationId);
        // 3 - Get the device from the database using the returned id
        Device device = this.deviceRepository.findById(deviceId)
                .orElseThrow(() -> new EntityNotFoundException("Device not found with id: " + deviceId));
        // 4 - Give that device to the spec service
        this.specsService.createDeviceSpecs(specs, device);
    }

    /**
     * Method that deletes a component from the database.
     * It also deletes the component specs from the database,
     * with calling the deleteComponentSpecs method
     *
     * @param Id The id of the component that needs to be deleted
     * @return
     */
    @Transactional
    public ResponseEntity<String> deleteDevice(Long Id) {
        // Use AtomicBoolean to track if the device was found and deleted.
        AtomicBoolean deviceFoundAndDeleted = new AtomicBoolean(false);

        // Check if the component exists in the database
        this.deviceRepository.findById(Id).ifPresentOrElse(
                device -> {
                    deleteSpecsAndDevice(Id, device, deviceFoundAndDeleted);
                },
                // Log an error if the component does not exist
                () -> log.error("No component found with the given ID")
        );

        // Check if the device was found and deleted, and return an appropriate response
        return checkSuccessfullyDeleted(deviceFoundAndDeleted);
    }

    private void deleteSpecsAndDevice(Long Id, Device device, AtomicBoolean deviceFoundAndDeleted) {
        try {
            // Delete the device specs
            this.specsService.deleteDeviceSpecs(device);
            // Delete the device from the database
            this.deviceRepository.deleteDevice(Id);
            deviceFoundAndDeleted.set(true);
        } catch (Exception e) {
            log.error("Failed to delete device: {}", e.getMessage());
        }
    }

    private static ResponseEntity<String> checkSuccessfullyDeleted(AtomicBoolean deviceFoundAndDeleted) {
        if (deviceFoundAndDeleted.get()) {
            return ResponseEntity.ok("Device successfully deleted.");
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @Transactional
    public void updateDeviceAndLocation(DeviceDTO deviceDTO) {
        // Find location based on dto, if it does not exist, create it
        Long locationId = getLocationOfUpdatedDevice(deviceDTO);
        // Update the device
        updateDevice(deviceDTO, locationId);
        // Update the location
        updateDeviceLocation(deviceDTO, locationId);
        log.info("Device was updated successfully.");
    }

    private void updateDevice(DeviceDTO deviceDTO, Long locationId) {
        this.deviceRepository.updateDevice(
                deviceDTO.getId(),
                deviceDTO.getType(),
                deviceDTO.getBrandName(),
                deviceDTO.getModel(),
                deviceDTO.getSerialNumber(),
                deviceDTO.getInvoiceNumber(),
                locationId);
    }

    private void updateDeviceLocation(DeviceDTO deviceDTO, Long locationId) {
        this.locationService.updateLocation(
                locationId,
                deviceDTO.getLocationName(),
                deviceDTO.getLocationCity(),
                deviceDTO.getLocationAddress());
    }

    private Long getLocationOfUpdatedDevice(DeviceDTO deviceDTO) {
        return this.locationService.findOrCreateLocation(
                deviceDTO.getLocationName(),
                deviceDTO.getLocationCity(),
                deviceDTO.getLocationAddress());
    }


    public DeviceDTO getDevice(Long id) {
        return deviceRepository.findById(id)
                .map(deviceDTOConverter)
                .orElseThrow(() -> new RuntimeException("Device not found with id: " + id));
    }

    public List<DeviceDTO> getAllDevices() {
        Iterable<Device> devices = deviceRepository.findAll();
        return StreamSupport.stream(devices.spliterator(), false)
                .map(deviceDTOConverter)
                .collect(Collectors.toList());
    }
}
