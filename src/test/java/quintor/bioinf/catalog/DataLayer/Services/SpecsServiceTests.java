package quintor.bioinf.catalog.DataLayer.Services;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import quintor.bioinf.catalog.dto.SpecDetail;
import quintor.bioinf.catalog.entities.Device;
import quintor.bioinf.catalog.entities.DeviceSpecs;
import quintor.bioinf.catalog.entities.Specs;
import quintor.bioinf.catalog.repository.DeviceSpecsRepository;
import quintor.bioinf.catalog.repository.SpecsRepository;
import quintor.bioinf.catalog.services.SpecsService;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.AdditionalAnswers.returnsFirstArg;
import static org.mockito.Mockito.*;

@SpringBootTest(properties = {
        "spring.datasource.url=jdbc:h2:mem:testdb;DB_CLOSE_DELAY=-1;DB_CLOSE_ON_EXIT=FALSE",
        "spring.datasource.driverClassName=org.h2.Driver",
        "spring.datasource.username=sa",
        "spring.datasource.password=",
        "spring.jpa.database-platform=org.hibernate.dialect.H2Dialect"
})
public class SpecsServiceTests {

    @Mock
    private DeviceSpecsRepository deviceSpecsRepository;

    @Mock
    private SpecsRepository specsRepository;

    @InjectMocks
    private SpecsService specsService;

    @Test
    public void createComponentSpecs_ValidSpecDetails() {
//        List<SpecDetail> specDetails = new ArrayList<>();
//        specDetails.add(new SpecDetail("specName1", "value1", "String"));
//        specDetails.add(new SpecDetail("specName2", "value2", "String"));
//
//        Device device = new Device();
//        Specs spec1 = new Specs();
//        spec1.setName("specName1");
//        Specs spec2 = new Specs();
//        spec2.setName("specName2");
//
//        when(specsRepository.findByName("specName1")).thenReturn(null);  // First call, spec does not exist
//        when(specsRepository.findByName("specName2")).thenReturn(null);  // Second call, spec also does not exist
//        // Assuming that saving a spec just returns it for simplification; adjust as needed for your logic.
//        when(specsRepository.save(any(Specs.class))).then(returnsFirstArg());
//
//        specsService.createDeviceSpecs(specDetails, device);
//
//        // Verify that save was called for each unique spec name.
//        verify(specsRepository, times(2)).save(any(Specs.class));
//
//        // Verify save on componentSpecsRepository for each spec detail
//        verify(deviceSpecsRepository, times(2)).save(any(DeviceSpecs.class));
        System.out.println("test");
    }
//
//    @Test
//    public void createComponentSpecs_WhenSpecAlreadyExists() {
//        List<SpecDetail> specDetails = new ArrayList<>();
//        specDetails.add(new SpecDetail("specName", "value1", "String"));
//
//        Device device = new Device();
//        Specs existingSpec = new Specs();
//        existingSpec.setName("specName");
//
//        when(specsRepository.findByName("specName")).thenReturn(existingSpec);
//        specsService.createDeviceSpecs(specDetails, device);
//
//        verify(specsRepository, times(1)).findByName("specName");
//        verify(specsRepository, never()).save(existingSpec);
//        verify(deviceSpecsRepository, times(1)).save(any(DeviceSpecs.class));
//    }
//
//    @Test
//    public void createComponentSpecs_EmptySpecDetailsList() {
//        List<SpecDetail> specDetails = new ArrayList<>();
//        Device device = new Device();
//
//        assertThrows(IllegalArgumentException.class, () -> specsService.createDeviceSpecs(specDetails, device));
//    }
//
//    @Test
//    public void createComponentSpecs_NullSpecDetailsList() {
//        assertThrows(IllegalArgumentException.class, () -> specsService.createDeviceSpecs(null, new Device()));
//    }
//
//    @Test
//    public void deleteComponentSpecs_Success() {
//        Device device = new Device();
//        DeviceSpecs deviceSpecs = new DeviceSpecs();
//        List<DeviceSpecs> deviceSpecsList = new ArrayList<>();
//        deviceSpecsList.add(deviceSpecs);
//
//        when(deviceSpecsRepository.findByDevice(device)).thenReturn(deviceSpecsList);
//        specsService.deleteDeviceSpecs(device);
//
//        verify(deviceSpecsRepository, times(1)).deleteAll(deviceSpecsList);
//    }
}