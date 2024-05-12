package quintor.bioinf.catalog.DataLayer.Services;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import quintor.bioinf.catalog.dto.SpecDetail;
import quintor.bioinf.catalog.entities.Device;
import quintor.bioinf.catalog.entities.DeviceSpecs;
import quintor.bioinf.catalog.entities.Specs;
import quintor.bioinf.catalog.repository.DeviceSpecsRepository;
import quintor.bioinf.catalog.repository.SpecsRepository;
import quintor.bioinf.catalog.services.SpecsService;

import java.util.Collections;
import java.util.List;

class SpecsServiceTest {

    @Mock
    private DeviceSpecsRepository deviceSpecsRepository;
    @Mock
    private SpecsRepository specsRepository;

    @InjectMocks
    private SpecsService specsService;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testCreateDeviceSpecs_ValidSpecs() {
        Device device = new Device();
        device.setId(1L);
        Specs spec = new Specs();
        spec.setName("CPU");
        spec.setDatatype("String");
        when(specsRepository.findByName("CPU")).thenReturn(spec);

        List<SpecDetail> specDetails = Collections.singletonList(new SpecDetail("CPU", "Intel i7", "String"));
        assertDoesNotThrow(() -> specsService.createDeviceSpecs(specDetails, device));
    }

    @Test
    void testDeleteDeviceSpecs_ExistingDeviceSpecs() {
        Device device = new Device();
        device.setId(1L);
        DeviceSpecs deviceSpecs = new DeviceSpecs();
        deviceSpecs.setDevice(device);
        List<DeviceSpecs> deviceSpecsList = Collections.singletonList(deviceSpecs);
        when(deviceSpecsRepository.findByDevice(device)).thenReturn(deviceSpecsList);

        assertDoesNotThrow(() -> specsService.deleteDeviceSpecs(device));
    }
}
