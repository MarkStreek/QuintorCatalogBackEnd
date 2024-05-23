package quintor.bioinf.catalog.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import quintor.bioinf.catalog.dto.DeviceDTO;
import quintor.bioinf.catalog.services.MainDeviceService;

import java.util.Date;
import java.util.List;

/**
 * Controller class that handles the incoming requests for the Device entity.
 * The class has several endpoints:
 * - POST /devices: Add a new device to the database
 * - GET /devices/{id}: Get a device by its id
 * - GET /devices: Get all devices
 * - PUT /devices/{id}: Update a device by its id
 * - DELETE /devices/{id}: Delete a device by its id
 * <p>
 * The class uses the MainDeviceService to handle the business logic.
 * See the individual methods for more information.
 * @see MainDeviceService
 */
@RestController
@RequestMapping("/devices")
public class DeviceController {

    private final MainDeviceService mainDeviceService;

    @Autowired
    public DeviceController(MainDeviceService mainDeviceService) {
        this.mainDeviceService = mainDeviceService;
    }

    /**
     * POST endpoint to add a new device to the database. The incoming request is a DeviceDTO object.
     * This DTO is validated and the addDevice method of the service is called with the validated DTO.
     * <p>
     * Error handling is done by the exception handler. And a ReturnMessage is returned.
     * @param deviceDTO The incoming request object (DeviceDTO)
     * @param request The incoming request
     * @return ReturnMessage object with status and message
     */
    @PostMapping
    public ReturnMessage addDevice(@RequestBody @Valid DeviceDTO deviceDTO, HttpServletRequest request) {
        Logging.logIncomingRequest(request);
        mainDeviceService.addDevice(
                deviceDTO.getType(),
                deviceDTO.getBrandName(),
                deviceDTO.getModel(),
                deviceDTO.getSerialNumber(),
                deviceDTO.getInvoiceNumber(),
                deviceDTO.getLocationCity(),
                deviceDTO.getLocationAddress(),
                deviceDTO.getLocationName(),
                deviceDTO.getSpecs()
        );
        return new ReturnMessage(
                HttpStatus.OK.value(),
                new Date(),
                "Apparaat toegevoegd aan de database",
                "Een nieuw apparaat is toegevoegd aan de Device tabel in de database"
        );
    }

    /**
     * GET endpoint to get a device by its id. The id is passed as a path variable.
     * The service class is called with the id and the device is returned.
     * If not found, the exception handler will catch the exception and return a ReturnMessage.
     *
     * @param id The id of the device
     * @param request The incoming request
     * @return The DeviceDTO object
     */
    @GetMapping("/{id}")
    public DeviceDTO getDevice(@PathVariable Long id, HttpServletRequest request) {
        Logging.logIncomingRequest(request);
        return mainDeviceService.getDevice(id);
    }

    /**
     * GET endpoint to get all devices. All the devices are retrieved from the database.
     * All the devices are converted to a DeviceDTO object.
     * The DeviceDTO's are placed in a list and returned to the client.
     *
     * @param request The incoming request
     * @return List<DeviceDTO> list of devices
     */
    @GetMapping
    public List<DeviceDTO> getAllDevices(HttpServletRequest request) {
        Logging.logIncomingRequest(request);
        return mainDeviceService.getAllDevices();
    }

    /**
     * PUT endpoint to update a device by its id.
     * The id and DeviceDTO are passed to the service class method.
     *
     * @param id The id of the device
     * @param deviceDTO The incoming request object (DeviceDTO)
     * @param request The incoming request
     * @return ReturnMessage object with status and message
     */
    @PutMapping("/{id}")
    public ReturnMessage updateDevice(@PathVariable Long id,
                                      @RequestBody @Valid DeviceDTO deviceDTO,
                                      HttpServletRequest request) {
        Logging.logIncomingRequest(request);
        deviceDTO.setId(id);
        mainDeviceService.updateDeviceAndLocation(deviceDTO);

        return new ReturnMessage(
                HttpStatus.OK.value(),
                new Date(),
                "Apparaat succesvol geüpdatet",
                "Het apparaat is succesvol geüpdatet in de database");
    }

    /**
     * DELETE endpoint to delete a device by its id. The id is passed as a path variable.
     * The service class is called with the id and the device is deleted.
     *
     * @param id The id of the device
     * @return ReturnMessage object with status and message
     */
    @DeleteMapping("/{id}")
    public ReturnMessage deleteDevices(@PathVariable Long id) {
        return mainDeviceService.deleteDevice(id);
    }

}
