package quintor.bioinf.catalog.DataLayer.Services;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import quintor.bioinf.catalog.dto.DeviceDTO;
import quintor.bioinf.catalog.entities.Device;
import quintor.bioinf.catalog.repository.DeviceRepository;
import quintor.bioinf.catalog.services.LocationService;
import quintor.bioinf.catalog.services.MainDeviceService;
import quintor.bioinf.catalog.services.SpecsService;

class MainDeviceServiceTest {

    @Mock
    private DeviceRepository deviceRepository;
    @Mock
    private LocationService locationService;
    @Mock
    private SpecsService specsService;

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

        when(locationService.addLocation(locationName, city, locationAddress)).thenReturn(locationId);
        when(deviceRepository.addDevice(type, brandName, model, serialNumber, invoiceNumber, locationId)).thenReturn(1L);
        Device device = new Device();
        device.setId(1L);
        when(deviceRepository.findById(1L)).thenReturn(java.util.Optional.of(device));

        mainDeviceService.addDevice(type, brandName, model, serialNumber, invoiceNumber, city, locationAddress, locationName, null);

        verify(specsService, times(1)).createDeviceSpecs(any(), eq(device));
    }

//    @Test
//    void testDeleteDevice_DeviceNotFound() {
//        Long deviceId = 1L;
//        when(deviceRepository.findById(deviceId)).thenReturn(java.util.Optional.empty());
//
//        assertThrows(RuntimeException.class, () -> {
//            mainDeviceService.deleteDevice(deviceId);
//        });
//    }

    @Test
    void testUpdateDeviceAndLocation_ExistingDevice() {
        Long locationId = 1L;
        when(locationService.findOrCreateLocation(anyString(), anyString(), anyString())).thenReturn(locationId);

        assertDoesNotThrow(() -> {
            mainDeviceService.updateDeviceAndLocation(new DeviceDTO());
        });
    }
}
