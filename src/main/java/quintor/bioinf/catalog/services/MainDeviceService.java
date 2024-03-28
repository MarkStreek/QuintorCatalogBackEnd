package quintor.bioinf.catalog.services;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import quintor.bioinf.catalog.dto.DeviceDTO;
import quintor.bioinf.catalog.dto.DeviceDTOConverter;
import quintor.bioinf.catalog.dto.SpecDetail;
import quintor.bioinf.catalog.entities.Device;
import quintor.bioinf.catalog.entities.Location;
import quintor.bioinf.catalog.repository.DeviceRepository;

import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

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

    private final DeviceDTOConverter deviceDTOConverter;

    @Autowired
    public MainDeviceService(
            DeviceRepository deviceRepository,
            SpecsService specsService,
            LocationService locationService,
            DeviceDTOConverter deviceDTOConverter
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
     * @param name Name of the component
     * @param brandName Brand name of the component
     * @param model Model of the component
     * @param serialNumber Serial number of the component
     * @param invoiceNumber Invoice number of the component
     * @param city City of the location
     * @param locationAddress Address of the location
     * @param locationName Name of the location (i.e. "Server room")
     * @param specs The specifications of the component
     */
    public void addDevice(
            String name,
            String brandName,
            String model,
            String serialNumber,
            String invoiceNumber,
            String city,
            String locationAddress,
            String locationName,
            List<SpecDetail> specs)
    {
        // 1 - Create the device, so it can be added to the spec
        // 2 - Create the location
        Location location = this.locationService.addLocation(locationName, city, locationAddress);
        Long LocationId = location.getId();
        this.deviceRepository.addDevice(name, brandName, model, serialNumber, invoiceNumber, LocationId);
        // 3 - Add location to the device
        // 4 - Create device specs
        // 5 - Save the device to the database

    }

    /**
     * This method creates a new Device object and sets its properties based on the provided arguments.
     * It uses varargs to accept an arbitrary number of arguments,
     * which should correspond to the properties of the Device.
     * The order of the arguments should be: name, brandName, model, serialNumber, invoiceNumber.
     *
     * @param args The properties of the Device.
     * @return A new Device object with its properties set to the provided arguments.
     * @throws IllegalArgumentException If any of the provided arguments are null or empty.
     */
    public Device createDevice(String... args) {
        // The names of the arguments, used for the exception message.
        String[] argNames = {"name", "brandName", "model", "serialNumber", "invoiceNumber"};

        // Create a new Device object.
        Device device = new Device();

        // Iterate over the provided arguments.
        for (int i = 0; i < args.length; i++) {
            if (args[i] == null || args[i].isEmpty()) {
                throw new IllegalArgumentException(argNames[i] + " cannot be null or empty");
            }
        }

        // Set the properties of the Device object based on the provided arguments.
        device.setName(args[0]);
        device.setBrandName(args[1]);
        device.setModel(args[2]);
        device.setSerialNumber(args[3]);
        device.setInvoiceNumber(args[4]);

        return device;
    }
//
//    /**
//     * Method that saves a device to the database.
//     * It uses the componentRepository to save the device
//     *
//     * @param device the device that needs to be saved
//     */
//    public void saveDevice(Device device) {
//        try {
//            this.deviceRepository.save(device);
//            log.info("Device successfully saved to the database");
//        } catch (Exception e) {
//            log.error("Error saving device to the database: " + e.getMessage());
//        }
//    }

    /**
     * Method that deletes a component from the database.
     * It also deletes the component specs from the database,
     * with calling the deleteComponentSpecs method
     *
     * @param Id The id of the component that needs to be deleted
     * @return
     */
    public ResponseEntity<String> deleteDevice(Long Id) {
        // Use AtomicBoolean to track if the device was found and deleted.
        AtomicBoolean deviceFoundAndDeleted = new AtomicBoolean(false);

        // Check if the component exists in the database
        this.deviceRepository.findById(Id).ifPresentOrElse(
                device -> {
                    try {
                        // Delete the device specs
                        this.specsService.deleteDeviceSpecs(device);
                        // Delete the device from the database
                        this.deviceRepository.deleteDevice(Id);
                        deviceFoundAndDeleted.set(true);
                    } catch (Exception e) {
                        log.error("Failed to delete device: " + e.getMessage());
                    }
                },
                // Log an error if the component does not exist
                () -> log.error("No component found with the given ID")
        );

        // Check if the device was found and deleted, and return an appropriate response
        if (deviceFoundAndDeleted.get()) {
            return ResponseEntity.ok("Device successfully deleted.");
        } else {
            return ResponseEntity.notFound().build();
        }
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
