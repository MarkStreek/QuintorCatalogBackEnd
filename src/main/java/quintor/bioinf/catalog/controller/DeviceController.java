package quintor.bioinf.catalog.controller;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import quintor.bioinf.catalog.dto.DeviceDTO;
import quintor.bioinf.catalog.services.MainDeviceService;

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
    public ResponseEntity<String> addDevice(@RequestBody @Valid DeviceDTO deviceDTO) {
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
        return ResponseEntity.ok("Added component");
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
