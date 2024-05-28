package quintor.bioinf.catalog.services;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import quintor.bioinf.catalog.controller.ReturnMessage;
import quintor.bioinf.catalog.dto.DeviceDTO;
import quintor.bioinf.catalog.dto.DeviceDTOConverter;
import quintor.bioinf.catalog.dto.SpecDetail;
import quintor.bioinf.catalog.entities.Device;
import quintor.bioinf.catalog.repository.DeviceRepository;

import java.util.Date;
import java.util.List;
import java.util.NoSuchElementException;
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
    private SpecsService specsService;

    @Mock
    private Function<Device, DeviceDTO> deviceDTOConverter;

    @InjectMocks
    private MainDeviceService mainDeviceService;

//    @Test
//    void testAddDevice_NewDevice() {
//        String type = "Router";
//        String brandName = "Cisco";
//        String model = "XYZ123";
//        String serialNumber = "SN123456";
//        String invoiceNumber = "INV123456";
//        String city = "Springfield";
//        String locationAddress = "123 Main St";
//        String locationName = "Server room";
//        Long locationId = 1L;
//        List<SpecDetail> specs = List.of(new SpecDetail("Spec1", "Value1", "String"));
//
//        when(locationService.findOrCreateLocation(locationName, city, locationAddress)).thenReturn(locationId);
//        when(deviceRepository.addDevice(type, brandName, model, serialNumber, invoiceNumber, locationId)).thenReturn(1L);
//        Device device = new Device();
//        device.setId(1L);
//        when(deviceRepository.findById(1L)).thenReturn(Optional.of(device));
//
//        mainDeviceService.addDevice(type, brandName, model, serialNumber, invoiceNumber, city, locationAddress, locationName, specs);
//
//        verify(specsService, times(1)).createDeviceSpecs(eq(specs), eq(device));
//    }

//    @Test
//    void testAddDevice_LocationExists() {
//        String type = "Router";
//        String brandName = "Cisco";
//        String model = "XYZ123";
//        String serialNumber = "SN123456";
//        String invoiceNumber = "INV123456";
//        String city = "Springfield";
//        String locationAddress = "123 Main St";
//        String locationName = "Server room";
//        Long locationId = 1L;
//        List<SpecDetail> specs = List.of(new SpecDetail("Spec1", "Value1", "String"));
//
//        when(locationService.checkIfLocationExists(locationAddress)).thenReturn(true);
//        when(locationService.findOrCreateLocation(locationName, city, locationAddress)).thenReturn(locationId);
//        when(deviceRepository.addDevice(type, brandName, model, serialNumber, invoiceNumber, locationId)).thenReturn(1L);
//        Device device = new Device();
//        device.setId(1L);
//        when(deviceRepository.findById(1L)).thenReturn(Optional.of(device));
//
//        mainDeviceService.addDevice(type, brandName, model, serialNumber, invoiceNumber, city, locationAddress, locationName, specs);
//
//        verify(specsService, times(1)).createDeviceSpecs(eq(specs), eq(device));
//    }

    @Test
    void testDeleteDevice_DeviceNotFound() {
        Device device = new Device();
        device.setId(1L);
        Long deviceId = 2L;
        when(deviceRepository.findById(deviceId)).thenReturn(Optional.of(device));

        assertThrows(NullPointerException.class, () -> {
            mainDeviceService.deleteDevice(deviceId);
        });
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

//    @Test
//    void testGetDevice_DeviceFound() {
//        Long deviceId = 1L;
//        Device device = new Device();
//        device.setId(deviceId);
//        DeviceDTO deviceDTO = new DeviceDTO();
//        when(deviceRepository.findById(deviceId)).thenReturn(Optional.of(device));
//        when(deviceDTOConverter.apply(device)).thenReturn(deviceDTO);
//
//        DeviceDTO result = mainDeviceService.getDevice(deviceId);
//
//        assertEquals(deviceDTO, result);
//    }

    @Test
    void testGetDevice_DeviceNotFound() {
        Long deviceId = 1L;
        when(deviceRepository.findById(deviceId)).thenReturn(Optional.empty());

        assertThrows(RuntimeException.class, () -> {
            mainDeviceService.getDevice(deviceId);
        });
    }
}