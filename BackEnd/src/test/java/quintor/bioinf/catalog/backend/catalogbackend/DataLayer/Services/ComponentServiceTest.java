package quintor.bioinf.catalog.backend.catalogbackend.DataLayer.Services;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import quintor.bioinf.catalog.backend.catalogbackend.DataLayer.Entities.Location;
import quintor.bioinf.catalog.backend.catalogbackend.DataLayer.Repository.LocationRepository;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@AutoConfigureMockMvc
public class ComponentServiceTest {

    @MockBean
    MockMvc mockMvc;

    @Autowired
    private ComponentService componentService;

    @Test
    void TestCreateLocation1() {

    }
}
