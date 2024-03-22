package quintor.bioinf.catalog.backend.catalogbackend.DataLayer.Services;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import quintor.bioinf.catalog.backend.catalogbackend.DataLayer.Dto.SpecDetail;
import quintor.bioinf.catalog.backend.catalogbackend.DataLayer.Entities.Component;
import quintor.bioinf.catalog.backend.catalogbackend.DataLayer.Entities.ComponentSpecs;
import quintor.bioinf.catalog.backend.catalogbackend.DataLayer.Entities.Specs;
import quintor.bioinf.catalog.backend.catalogbackend.DataLayer.Repository.ComponentSpecsRepository;
import quintor.bioinf.catalog.backend.catalogbackend.DataLayer.Repository.SpecsRepository;

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
    private ComponentSpecsRepository componentSpecsRepository;

    @Mock
    private SpecsRepository specsRepository;

    @InjectMocks
    private SpecsService specsService;

    @Test
    public void createComponentSpecs_ValidSpecDetails() {
        List<SpecDetail> specDetails = new ArrayList<>();
        specDetails.add(new SpecDetail("specName1", "value1", "String"));
        specDetails.add(new SpecDetail("specName2", "value2", "String"));

        Component component = new Component();
        Specs spec1 = new Specs();
        spec1.setName("specName1");
        Specs spec2 = new Specs();
        spec2.setName("specName2");

        when(specsRepository.findByName("specName1")).thenReturn(null);  // First call, spec does not exist
        when(specsRepository.findByName("specName2")).thenReturn(null);  // Second call, spec also does not exist
        // Assuming that saving a spec just returns it for simplification; adjust as needed for your logic.
        when(specsRepository.save(any(Specs.class))).then(returnsFirstArg());

        specsService.createComponentSpecs(specDetails, component);

        // Verify that save was called for each unique spec name.
        verify(specsRepository, times(2)).save(any(Specs.class));

        // Verify save on componentSpecsRepository for each spec detail
        verify(componentSpecsRepository, times(2)).save(any(ComponentSpecs.class));
    }

    @Test
    public void createComponentSpecs_WhenSpecAlreadyExists() {
        List<SpecDetail> specDetails = new ArrayList<>();
        specDetails.add(new SpecDetail("specName", "value1", "String"));

        Component component = new Component();
        Specs existingSpec = new Specs();
        existingSpec.setName("specName");

        when(specsRepository.findByName("specName")).thenReturn(existingSpec);
        specsService.createComponentSpecs(specDetails, component);

        verify(specsRepository, times(1)).findByName("specName");
        verify(specsRepository, never()).save(existingSpec);
        verify(componentSpecsRepository, times(1)).save(any(ComponentSpecs.class));
    }

    @Test
    public void createComponentSpecs_EmptySpecDetailsList() {
        List<SpecDetail> specDetails = new ArrayList<>();
        Component component = new Component();

        assertThrows(IllegalArgumentException.class, () -> specsService.createComponentSpecs(specDetails, component));
    }

    @Test
    public void createComponentSpecs_NullSpecDetailsList() {
        assertThrows(IllegalArgumentException.class, () -> specsService.createComponentSpecs(null, new Component()));
    }

    @Test
    public void deleteComponentSpecs_Success() {
        Component component = new Component();
        ComponentSpecs componentSpecs = new ComponentSpecs();
        List<ComponentSpecs> componentSpecsList = new ArrayList<>();
        componentSpecsList.add(componentSpecs);

        when(componentSpecsRepository.findByComponent(component)).thenReturn(componentSpecsList);
        specsService.deleteComponentSpecs(component);

        verify(componentSpecsRepository, times(1)).deleteAll(componentSpecsList);
    }
}