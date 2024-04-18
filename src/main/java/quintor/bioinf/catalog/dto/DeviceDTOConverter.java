package quintor.bioinf.catalog.dto;

import org.springframework.stereotype.Service;
import quintor.bioinf.catalog.entities.Device;
import quintor.bioinf.catalog.entities.DeviceSpecs;
import quintor.bioinf.catalog.entities.Location;
import quintor.bioinf.catalog.entities.Specs;
import quintor.bioinf.catalog.repository.DeviceSpecsRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Service
public class DeviceDTOConverter implements Function<Device, DeviceDTO> {
    private static final Logger log = LoggerFactory.getLogger(DeviceDTOConverter.class);
    private final DeviceSpecsRepository deviceSpecsRepository;

    public DeviceDTOConverter(DeviceSpecsRepository deviceSpecsRepository) {
        this.deviceSpecsRepository = deviceSpecsRepository;
    }

    @Override
    public DeviceDTO apply(Device device) {
        // Create a new DeviceDTO object
        DeviceDTO deviceDTO = createNewDeviceDTO(device);
        // Set the Location of the DTO
        setDTOLocationWithDevice(device, deviceDTO);

        List<SpecDetail> specDetails = new ArrayList<>();
        List<DeviceSpecs> deviceSpecsList = deviceSpecsRepository.findByDevice(device);

        checkForCurrentDeviceSpecs(device, deviceSpecsList);
        getSpecDetailListOfDevice(deviceSpecsList, specDetails);

        log.info("Converted device: {}", deviceDTO);
        deviceDTO.setSpecs(specDetails);
        return deviceDTO;
    }

    private static void checkForCurrentDeviceSpecs(Device device, List<DeviceSpecs> deviceSpecsList) {
        if (deviceSpecsList.isEmpty()) {
            log.warn("No specs found for device ID: {}", device.getId());
        }
    }

    private static void getSpecDetailListOfDevice(List<DeviceSpecs> deviceSpecsList, List<SpecDetail> specDetails) {
        for (DeviceSpecs compSpec : deviceSpecsList) {
            Specs spec = compSpec.getSpecs();
            if (spec == null) {
                log.error("Spec is null for device spec ID: {}", compSpec.getId());
                continue;
            }
            SpecDetail specDetail = new SpecDetail(spec.getName(), compSpec.getValue(), spec.getDatatype());
            log.info("Adding spec detail: name={}, value={}, datatype={}", spec.getName(), compSpec.getValue(), spec.getDatatype());
            specDetails.add(specDetail);
        }
    }

    private static void setDTOLocationWithDevice(Device device, DeviceDTO deviceDTO) {
        Location location = device.getLocation();
        if (location != null) {
            deviceDTO.setLocationCity(location.getCity());
            deviceDTO.setLocationAddress(location.getAddress());
            deviceDTO.setLocationName(location.getName());
        }
    }

    private static DeviceDTO createNewDeviceDTO(Device device) {
        DeviceDTO deviceDTO = new DeviceDTO();
        deviceDTO.setId(device.getId());
        deviceDTO.setType(device.getType());
        deviceDTO.setBrandName(device.getBrandName());
        deviceDTO.setModel(device.getModel());
        deviceDTO.setSerialNumber(device.getSerialNumber());
        deviceDTO.setInvoiceNumber(device.getInvoiceNumber());
        return deviceDTO;
    }
}
