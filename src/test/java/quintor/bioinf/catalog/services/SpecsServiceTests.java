package quintor.bioinf.catalog.services;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.mock;

import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Profile;
import quintor.bioinf.catalog.dto.SpecDetail;
import quintor.bioinf.catalog.entities.Device;
import quintor.bioinf.catalog.entities.DeviceSpecs;
import quintor.bioinf.catalog.entities.Specs;
import quintor.bioinf.catalog.repository.DeviceSpecsRepository;
import quintor.bioinf.catalog.repository.SpecsRepository;

import java.util.Collections;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@Nested
@SpringBootTest
@Profile("test")
class SpecsServiceTests {

    @Mock
    private DeviceSpecsRepository deviceSpecsRepository;

    @Mock
    private SpecsRepository specsRepository;

    @InjectMocks
    private SpecsService specsService;

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

    @Test
    void testSaveDeviceSpecs() {
        DeviceSpecs mockDeviceSpecs = mock(DeviceSpecs.class);
        specsService.saveDeviceSpecs(mockDeviceSpecs);
        verify(deviceSpecsRepository, times(1)).save(mockDeviceSpecs);
    }

    @Test
    void checkForValidParameters() {
        List<SpecDetail> specDetails = List.of(
                new SpecDetail("test", "value", "datatype"),
                new SpecDetail("name", "value", "datatype"),
                new SpecDetail("name", "value", "datatype"));
        Device device = new Device();
        assertTrue(specsService.checkForInValidParameters(specDetails, device));
    }

    @Test
    void checkForValidParametersEmptySpecs() {
        List<SpecDetail> specDetails = List.of();
        Device device = new Device();
        assertFalse(specsService.checkForInValidParameters(specDetails, device));
    }

    @Test
    void checkForValidParametersNullSpecs() {
        List<SpecDetail> specDetails = null;
        Device device = new Device();
        assertFalse(specsService.checkForInValidParameters(specDetails, device));
    }

    @Test
    void checkForValidParametersNullDevice() {
        List<SpecDetail> specDetails = List.of(
                new SpecDetail("test", "value", "datatype"),
                new SpecDetail("name", "value", "datatype"),
                new SpecDetail("name", "value", "datatype"));
        Device device = null;
        assertFalse(specsService.checkForInValidParameters(specDetails, device));
    }

    @Test
    void testCheckIfSpecExistsOrCreateNew() {
        SpecDetail mockSpecDetail = mock(SpecDetail.class);
        Specs mockSpecs = null;
        when(mockSpecDetail.getSpecName()).thenReturn("testName");
        when(mockSpecDetail.getDatatype()).thenReturn("testDatatype");

        Specs result = specsService.checkIfSpecExistsOrCreateNew(mockSpecDetail, mockSpecs);

        verify(specsRepository, times(1)).save(any(Specs.class));
        assertEquals("testName", result.getName());
        assertEquals("testDatatype", result.getDatatype());
    }

    @ParameterizedTest
    @CsvSource({
            "1234",
            "test",
            "1234test",
    })
    void CheckIfSpecExistsOrCreateNewNullSpec(String name) {
        Specs spec = new Specs();
        spec.setName(name);

        Specs result = specsService.checkIfSpecExistsOrCreateNew(new SpecDetail("testName", "value", "datatype"), spec);

        assertEquals(name, result.getName());
    }

    @Test
    void getAllSpecsMockTest() {

        List<Specs> mockSpecs = List.of(
                new Specs(),
                new Specs(),
                new Specs()
        );

        when(specsRepository.findAll()).thenReturn(mockSpecs);
        Map<String, String> result = specsService.getAllSpecs();
        verify(specsRepository, times(1)).findAll();
    }

}