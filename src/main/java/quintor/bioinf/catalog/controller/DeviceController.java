package quintor.bioinf.catalog.controller;

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

@RestController
@RequestMapping("/devices")
public class DeviceController {

    private final MainDeviceService mainDeviceService;

    @Autowired
    public DeviceController(MainDeviceService mainDeviceService) {
        this.mainDeviceService = mainDeviceService;
    }

    @PostMapping
    public ReturnMessage addDevice(@RequestBody @Valid DeviceDTO deviceDTO) {
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

    @GetMapping("/{id}")
    public DeviceDTO getDevice(@PathVariable Long id) {
        return mainDeviceService.getDevice(id);
    }

    @GetMapping
    public List<DeviceDTO> getAllDevices() {
        return mainDeviceService.getAllDevices();
    }

    @PutMapping("/{id}")
    public ResponseEntity<String> updateDevice(
            @PathVariable Long id,
            @RequestBody @Valid DeviceDTO deviceDTO) {

        deviceDTO.setId(id);
        mainDeviceService.updateDeviceAndLocation(deviceDTO);
        return ResponseEntity.ok("Updated component");
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteDevices(@PathVariable Long id) {
        return mainDeviceService.deleteDevice(id);
    }

}
