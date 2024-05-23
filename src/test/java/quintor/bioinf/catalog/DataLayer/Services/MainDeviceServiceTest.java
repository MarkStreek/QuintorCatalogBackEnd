package quintor.bioinf.catalog.DataLayer.Services;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;
import quintor.bioinf.catalog.dto.DeviceDTO;
import quintor.bioinf.catalog.dto.SpecDetail;
import quintor.bioinf.catalog.entities.Device;
import quintor.bioinf.catalog.repository.DeviceRepository;
import quintor.bioinf.catalog.services.LocationService;
import quintor.bioinf.catalog.services.MainDeviceService;
import quintor.bioinf.catalog.services.SpecsService;

import java.util.List;
import java.util.Optional;
import java.util.function.Function;

class MainDeviceServiceTest {

    @Mock
    private DeviceRepository deviceRepository;

    @Mock
    private LocationService locationService;

    @Mock
    private SpecsService specsService;

    @Mock
    private Function<Device, DeviceDTO> deviceDTOConverter;

    @InjectMocks
    private MainDeviceService mainDeviceService;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testAddDevice_NewDevice() {
        String type = "Router";
        String brandName = "Cisco";
        String model = "XYZ123";
        String serialNumber = "SN123456";
        String invoiceNumber = "INV123456";
        String city = "Springfield";
        String locationAddress = "123 Main St";
        String locationName = "Server room";
        Long locationId = 1L;
        List<SpecDetail> specs = List.of(new SpecDetail("Spec1", "Value1", "String"));

        when(locationService.findOrCreateLocation(locationName, city, locationAddress)).thenReturn(locationId);
        when(deviceRepository.addDevice(type, brandName, model, serialNumber, invoiceNumber, locationId)).thenReturn(1L);
        Device device = new Device();
        device.setId(1L);
        when(deviceRepository.findById(1L)).thenReturn(Optional.of(device));

        mainDeviceService.addDevice(type, brandName, model, serialNumber, invoiceNumber, city, locationAddress, locationName, specs);

        verify(specsService, times(1)).createDeviceSpecs(eq(specs), eq(device));
    }

    @Test
    void testAddDevice_LocationExists() {
        String type = "Router";
        String brandName = "Cisco";
        String model = "XYZ123";
        String serialNumber = "SN123456";
        String invoiceNumber = "INV123456";
        String city = "Springfield";
        String locationAddress = "123 Main St";
        String locationName = "Server room";
        Long locationId = 1L;
        List<SpecDetail> specs = List.of(new SpecDetail("Spec1", "Value1", "String"));

        when(locationService.checkIfLocationExists(locationAddress)).thenReturn(true);
        when(locationService.findOrCreateLocation(locationName, city, locationAddress)).thenReturn(locationId);
        when(deviceRepository.addDevice(type, brandName, model, serialNumber, invoiceNumber, locationId)).thenReturn(1L);
        Device device = new Device();
        device.setId(1L);
        when(deviceRepository.findById(1L)).thenReturn(Optional.of(device));

        mainDeviceService.addDevice(type, brandName, model, serialNumber, invoiceNumber, city, locationAddress, locationName, specs);

        verify(specsService, times(1)).createDeviceSpecs(eq(specs), eq(device));
    }

    @Test
    void testDeleteDevice_DeviceNotFound() {
        Long deviceId = 1L;
        when(deviceRepository.findById(deviceId)).thenReturn(Optional.empty());

        ResponseEntity<String> response = mainDeviceService.deleteDevice(deviceId);

        assertEquals(ResponseEntity.notFound().build(), response);
    }

    @Test
    void testDeleteDevice_DeviceFound() {
        Long deviceId = 1L;
        Device device = new Device();
        device.setId(deviceId);
        when(deviceRepository.findById(deviceId)).thenReturn(Optional.of(device));

        ResponseEntity<String> response = mainDeviceService.deleteDevice(deviceId);

        verify(specsService, times(1)).deleteDeviceSpecs(device);
        verify(deviceRepository, times(1)).deleteDevice(deviceId);
        assertEquals(ResponseEntity.ok("Device successfully deleted."), response);
    }

    @Test
    void testUpdateDeviceAndLocation_ExistingDevice() {
        Long locationId = 1L;
        DeviceDTO deviceDTO = new DeviceDTO();
        deviceDTO.setId(1L);
        deviceDTO.setType("Router");
        deviceDTO.setBrandName("Cisco");
        deviceDTO.setModel("XYZ123");
        deviceDTO.setSerialNumber("SN123456");
        deviceDTO.setInvoiceNumber("INV123456");
        deviceDTO.setLocationCity("Springfield");
        deviceDTO.setLocationAddress("123 Main St");
        deviceDTO.setLocationName("Server room");

        when(locationService.findOrCreateLocation(anyString(), anyString(), anyString())).thenReturn(locationId);

        mainDeviceService.updateDeviceAndLocation(deviceDTO);

        verify(deviceRepository, times(1)).updateDevice(
                eq(deviceDTO.getId()),
                eq(deviceDTO.getType()),
                eq(deviceDTO.getBrandName()),
                eq(deviceDTO.getModel()),
                eq(deviceDTO.getSerialNumber()),
                eq(deviceDTO.getInvoiceNumber()),
                eq(locationId)
        );
        verify(locationService, times(1)).updateLocation(
                eq(locationId),
                eq(deviceDTO.getLocationName()),
                eq(deviceDTO.getLocationCity()),
                eq(deviceDTO.getLocationAddress())
        );
    }

    @Test
    void testGetDevice_DeviceFound() {
        Long deviceId = 1L;
        Device device = new Device();
        device.setId(deviceId);
        DeviceDTO deviceDTO = new DeviceDTO();
        when(deviceRepository.findById(deviceId)).thenReturn(Optional.of(device));
        when(deviceDTOConverter.apply(device)).thenReturn(deviceDTO);

        DeviceDTO result = mainDeviceService.getDevice(deviceId);

        assertEquals(deviceDTO, result);
    }

    @Test
    void testGetDevice_DeviceNotFound() {
        Long deviceId = 1L;
        when(deviceRepository.findById(deviceId)).thenReturn(Optional.empty());

        Exception exception = assertThrows(RuntimeException.class, () -> {
            mainDeviceService.getDevice(deviceId);
        });

        assertNotNull(exception.getMessage());
        assertTrue(exception.getMessage().contains("Device not found with id: " + deviceId));
    }
}
