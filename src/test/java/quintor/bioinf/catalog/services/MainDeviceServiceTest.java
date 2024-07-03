package quintor.bioinf.catalog.services;

import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import quintor.bioinf.catalog.dto.DeviceDTO;
import quintor.bioinf.catalog.dto.SpecDetail;
import quintor.bioinf.catalog.entities.Device;
import quintor.bioinf.catalog.repository.DeviceRepository;
import quintor.bioinf.catalog.repository.DeviceSpecsRepository;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;

import static org.hibernate.validator.internal.util.Contracts.assertNotNull;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@SpringBootTest
class MainDeviceServiceTest {

    @Mock
    private DeviceRepository deviceRepository;

    @Mock
    private LocationService locationService;

    @Mock
    private DeviceSpecsRepository deviceSpecsRepository;

    @Mock
    private SpecsService specsService;

    @Mock
    private Function<Device, DeviceDTO> deviceDTOConverter;

    @InjectMocks
    private MainDeviceService mainDeviceService;

    @Test
    void testAddDevice_ValidParameters() {

        String type = "Router";
        String brandName = "Cisco";
        String model = "XYZ123";
        String serialNumber = "SN123456";
        String invoiceNumber = "INV123456";
        String city = "Springfield";
        String locationAddress = "123 Main St";
        String locationName = "Server room";
        List<SpecDetail> specs = new ArrayList<>();

        when(locationService
                .findOrCreateLocation(anyString(), anyString(), anyString())).thenReturn(1L);

        when(deviceRepository
                .addDevice(anyString(), anyString(), anyString(),
                        anyString(), anyString(), anyLong())).thenReturn(1L);

        when(deviceRepository
                .findById(anyLong()))
                .thenReturn(Optional.of(new Device()));

        assertDoesNotThrow(() -> mainDeviceService.addDevice(type, brandName, model, serialNumber,
                invoiceNumber, city, locationAddress, locationName, specs));

        verify(deviceRepository,
                times(1))
                .addDevice(anyString(), anyString(), anyString(), anyString(), anyString(), anyLong());

        verify(specsService,
                times(1))
                .createDeviceSpecs(anyList(), any(Device.class));
    }

    @Test
    void testDeleteDevice_DeviceExists() {
        Long deviceId = 1L;
        Device device = new Device();
        device.setId(deviceId);
        when(deviceRepository.findById(deviceId)).thenReturn(Optional.of(device));

        assertDoesNotThrow(() -> mainDeviceService.deleteDevice(deviceId));

        verify(deviceRepository, times(1)).delete(device);
    }

    @Test
    void testGetAllDevices_NoDevices() {

        when(deviceRepository.findAll()).thenReturn(new ArrayList<>());

        List<DeviceDTO> result = mainDeviceService.getAllDevices();
        assertTrue(result.isEmpty());
    }

    @Test
    void testGetAllDevices_MultipleDevices() {

        List<Device> devices = Arrays.asList(new Device(), new Device());
        when(deviceRepository.findAll()).thenReturn(devices);

        List<DeviceDTO> result = mainDeviceService.getAllDevices();

        assertEquals(devices.size(), result.size());
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
    void testGetDevice_DeviceNotFound() {
        Long deviceId = 1L;
        when(deviceRepository.findById(deviceId)).thenReturn(Optional.empty());

        assertThrows(RuntimeException.class, () -> {
            mainDeviceService.getDevice(deviceId);
        });
    }
}