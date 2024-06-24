package quintor.bioinf.catalog.services;

import jakarta.persistence.EntityNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import quintor.bioinf.catalog.dto.DeviceDTO;
import quintor.bioinf.catalog.dto.SpecDetail;
import quintor.bioinf.catalog.entities.Device;
import quintor.bioinf.catalog.entities.DeviceSpecs;
import quintor.bioinf.catalog.repository.DeviceRepository;
import quintor.bioinf.catalog.repository.DeviceSpecsRepository;
import quintor.bioinf.catalog.repository.SpecsRepository;

import java.util.List;
import java.util.NoSuchElementException;
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
    private final DeviceSpecsRepository deviceSpecsRepository;

    @Autowired
    public MainDeviceService(
            DeviceRepository deviceRepository,
            SpecsService specsService,
            LocationService locationService,
            Function<Device, DeviceDTO> deviceDTOConverter,
            SpecsRepository specsRepository,
            DeviceSpecsRepository deviceSpecsRepository)
    {
        this.deviceRepository = deviceRepository;
        this.specsService = specsService;
        this.locationService = locationService;
        this.deviceDTOConverter = deviceDTOConverter;
        this.deviceSpecsRepository = deviceSpecsRepository;
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
    public void deleteDevice(Long Id) {

        Device device = this.deviceRepository.findById(Id)
                .orElseThrow(() ->
                        new EntityNotFoundException("Apparaat niet gevonden met id: " + Id));
        List<DeviceSpecs> deviceSpecs = this.deviceSpecsRepository.findByDevice(device);
        this.deviceSpecsRepository.deleteAll(deviceSpecs);
        this.deviceRepository.delete(device);

    }

    /**
     * Method that updates a device in the database.
     * The device is updated based on the given DeviceDTO object.
     * The location is updated as well.
     * <p>
     * The function will call other sub-methods to update the device and location.
     *
     * @param deviceDTO DeviceDTO object with the updated device information
     */
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

    /**
     * Sub-method that updates a device in the database.
     * The device is updated based on the given DeviceDTO object.
     *
     * @param deviceDTO DeviceDTO object with the updated device information
     * @param locationId Location id of the device
     */
    protected void updateDevice(DeviceDTO deviceDTO, Long locationId) {
        this.deviceRepository.updateDevice(
                deviceDTO.getId(),
                deviceDTO.getType(),
                deviceDTO.getBrandName(),
                deviceDTO.getModel(),
                deviceDTO.getSerialNumber(),
                deviceDTO.getInvoiceNumber(),
                locationId);
    }

    /**
     * Sub-method that updates the location of a device in the database.
     *
     * @param deviceDTO DeviceDTO object with the updated device information
     * @param locationId Location id of the device
     */
    private void updateDeviceLocation(DeviceDTO deviceDTO, Long locationId) {
        this.locationService.updateLocation(
                locationId,
                deviceDTO.getLocationName(),
                deviceDTO.getLocationCity(),
                deviceDTO.getLocationAddress());
    }

    /**
     * Sub-method that finds the location of an updated device.
     * If the location does not exist, it is created.
     *
     * @param deviceDTO DeviceDTO object with the updated device information
     * @return LocationId of the updated device
     */
    private Long getLocationOfUpdatedDevice(DeviceDTO deviceDTO) {
        return this.locationService.findOrCreateLocation(
                deviceDTO.getLocationName(),
                deviceDTO.getLocationCity(),
                deviceDTO.getLocationAddress());
    }

    /**
     * Method that retrieves a device from the database by id.
     * The device is converted to a DeviceDTO object.
     *
     * @param id Device id
     * @return DeviceDTO object
     */
    public DeviceDTO getDevice(Long id) {
        return deviceRepository.findById(id)
                .map(deviceDTOConverter)
                .orElseThrow(() -> new NoSuchElementException("Device not found with id: " + id));
    }

    /**
     * Method that retrieves all the devices from the database.
     * The devices are converted to DeviceDTO objects.
     *
     * @return list of DeviceDTO objects
     */
    public List<DeviceDTO> getAllDevices() {
        Iterable<Device> devices = deviceRepository.findAll();
        return StreamSupport.stream(devices.spliterator(), false)
                .map(deviceDTOConverter)
                .collect(Collectors.toList());
    }
}
