package quintor.bioinf.catalog.backend.catalogbackend.DataLayer.Services;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.context.SpringBootTest;
import quintor.bioinf.catalog.backend.catalogbackend.DataLayer.Entities.Component;
import quintor.bioinf.catalog.backend.catalogbackend.DataLayer.Entities.ComponentSpecs;
import quintor.bioinf.catalog.backend.catalogbackend.DataLayer.Entities.Specs;
import quintor.bioinf.catalog.backend.catalogbackend.DataLayer.Repository.ComponentSpecsRepository;
import quintor.bioinf.catalog.backend.catalogbackend.DataLayer.Repository.SpecsRepository;

import java.util.*;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@SpringBootTest(properties = {
        "spring.datasource.url=jdbc:h2:mem:testdb;DB_CLOSE_DELAY=-1;DB_CLOSE_ON_EXIT=FALSE",
        "spring.datasource.driverClassName=org.h2.Driver",
        "spring.datasource.username=sa",
        "spring.datasource.password=",
        "spring.jpa.database-platform=org.hibernate.dialect.H2Dialect"
})
public class CreateSpecsServiceTests {

    @Mock
    private ComponentSpecsRepository componentSpecsRepository;

    @Mock
    private SpecsRepository specsRepository;

    @InjectMocks
    private CreateSpecsService createSpecsService;

    @Test
    public void testCreateComponentSpecs() {
        Map<String, Object> specsMap = new HashMap<>();
        specsMap.put("spec1", "value1");
        specsMap.put("spec2", "value2");
        Component component = new Component();
        Specs spec1 = new Specs();
        spec1.setName("spec1");
        Specs spec2 = new Specs();
        spec2.setName("spec2");
        List<Specs> specsList = Arrays.asList(spec1, spec2);

        when(specsRepository.findAll()).thenReturn(specsList);
        when(specsRepository.findByName(anyString())).thenReturn(spec1);
        when(componentSpecsRepository.save(any(ComponentSpecs.class))).thenAnswer(i -> i.getArguments()[0]);

        createSpecsService.createComponentSpecs(specsMap, component);

        verify(specsRepository, times(2)).findByName(anyString());
        verify(componentSpecsRepository, times(2)).save(any(ComponentSpecs.class));
    }

    @Test
    public void testCreateComponentSpecsWhenSpecAlreadyExists() {
        Map<String, Object> specsMap = new HashMap<>();
        specsMap.put("spec1", "value1");
        specsMap.put("spec2", "value2");
        specsMap.put("spec3", "value3");

        Component component = new Component();
        Specs spec1 = new Specs();
        spec1.setName("spec1");
        Specs spec2 = new Specs();
        spec2.setName("spec2");
        List<Specs> specsList = List.of(spec1, spec2);

        when(specsRepository.findAll()).thenReturn(specsList);
        when(specsRepository.findByName(anyString())).thenReturn(spec1);
        when(componentSpecsRepository.save(any(ComponentSpecs.class))).thenAnswer(i -> i.getArguments()[0]);
        createSpecsService.createComponentSpecs(specsMap, component);

        verify(specsRepository, times(2)).findByName(anyString());
        verify(specsRepository, times(1)).save(any(Specs.class));
        verify(componentSpecsRepository, times(3)).save(any(ComponentSpecs.class));
    }

    @Test
    public void testCreateComponentSpecsWhenSpecAlreadyExists2() {
        Map<String, Object> specsMap = new HashMap<>();
        specsMap.put("specs", "value1");
        specsMap.put("spec2", "value2");
        specsMap.put("spec3", "value3");
        specsMap.put("spec4", "value4");

        Component component = new Component();
        Specs spec1 = new Specs();
        spec1.setName("spec1");
        Specs spec2 = new Specs();
        spec2.setName("spec4");
        List<Specs> specsList = List.of(spec1, spec2);
        when(specsRepository.findAll()).thenReturn(specsList);
        when(specsRepository.findByName(anyString())).thenReturn(spec1);
        when(componentSpecsRepository.save(any(ComponentSpecs.class))).thenAnswer(i -> i.getArguments()[0]);

        createSpecsService.createComponentSpecs(specsMap, component);

        verify(specsRepository, times(1)).findByName(anyString());
        verify(specsRepository, times(3)).save(any(Specs.class));
        verify(componentSpecsRepository, times(4)).save(any(ComponentSpecs.class));
    }

    @Test
    public void testCreateComponentSpecsWhenNoneExist() {
        Map<String, Object> specsMap = new HashMap<>();
        specsMap.put("specs", "value1");
        specsMap.put("spec2", "value2");
        specsMap.put("spec3", "value3");
        specsMap.put("spec4", "value4");
        Component component = new Component();
        Specs spec1 = new Specs();
        spec1.setName("spec1");
        List<Specs> specsList = List.of(spec1);

        when(specsRepository.findAll()).thenReturn(specsList);
        when(componentSpecsRepository.save(any(ComponentSpecs.class))).thenAnswer(i -> i.getArguments()[0]);
        createSpecsService.createComponentSpecs(specsMap, component);

        verify(specsRepository, times(4)).save(any(Specs.class));
        verify(componentSpecsRepository, times(4)).save(any(ComponentSpecs.class));
    }

    @Test
    public void testCreateComponentSpecsWhenSpecsAreEmpty() {
        Map<String, Object> specsMap = new HashMap<>();
        Component component = new Component();

        assertThrows(IllegalArgumentException.class,
                () -> createSpecsService.createComponentSpecs(specsMap, component));
    }

    @Test
    public void testDeleteComponentSpecs() {
        Component com = new Component();
        ComponentSpecs componentSpecs = new ComponentSpecs();
        componentSpecs.setId(1L);
        componentSpecs.setValue("value");
        componentSpecs.setSpecs(new Specs());
        componentSpecs.setComponent(com);

        when(componentSpecsRepository.findByComponent(com)).thenReturn(List.of(componentSpecs));
        createSpecsService.deleteComponentSpecs(com);
        verify(componentSpecsRepository, times(11)).deleteAll(any());
    }
}
