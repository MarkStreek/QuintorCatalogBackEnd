package quintor.bioinf.catalog.services;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Profile;
import quintor.bioinf.catalog.dto.SpecDetail;
import quintor.bioinf.catalog.entities.Device;
import quintor.bioinf.catalog.entities.DeviceSpecs;
import quintor.bioinf.catalog.entities.Specs;
import quintor.bioinf.catalog.repository.DeviceSpecsRepository;
import quintor.bioinf.catalog.repository.SpecsRepository;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest
@Profile("test")
public class SpecsServiceTests {

    @Mock
    private DeviceSpecsRepository deviceSpecsRepository;

    @Mock
    private SpecsRepository specsRepository;

    @InjectMocks
    private SpecsService specsService;

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
        assertFalse(specsService.checkForValidParameters(specDetails, device));
    }

    @Test
    void checkForValidParametersEmptySpecs() {
        List<SpecDetail> specDetails = List.of();
        Device device = new Device();
        assertTrue(specsService.checkForValidParameters(specDetails, device));
    }

    @Test
    void checkForValidParametersNullSpecs() {
        List<SpecDetail> specDetails = null;
        Device device = new Device();
        assertTrue(specsService.checkForValidParameters(specDetails, device));
    }

    @Test
    void checkForValidParametersNullDevice() {
        List<SpecDetail> specDetails = List.of(
                new SpecDetail("test", "value", "datatype"),
                new SpecDetail("name", "value", "datatype"),
                new SpecDetail("name", "value", "datatype"));
        Device device = null;
        assertTrue(specsService.checkForValidParameters(specDetails, device));
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

}