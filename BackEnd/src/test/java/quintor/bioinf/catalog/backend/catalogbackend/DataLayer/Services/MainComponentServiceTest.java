package quintor.bioinf.catalog.backend.catalogbackend.DataLayer.Services;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import quintor.bioinf.catalog.backend.catalogbackend.DataLayer.Entities.Component;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(properties = {
        "spring.datasource.url=jdbc:h2:mem:testdb;DB_CLOSE_DELAY=-1;DB_CLOSE_ON_EXIT=FALSE",
        "spring.datasource.driverClassName=org.h2.Driver",
        "spring.datasource.username=sa",
        "spring.datasource.password=",
        "spring.jpa.database-platform=org.hibernate.dialect.H2Dialect"
})
@AutoConfigureMockMvc
public class MainComponentServiceTest {

    @MockBean
    MockMvc mockMvc;

    @Autowired
    private MainComponentService mainComponentService;

    @Test
    void createComponent_HappyPath() {
        Component component = mainComponentService.createComponent("Name", "Brand", "Model", "Serial", "Invoice");
        assertNotNull(component);
        assertEquals("Name", component.getName());
        assertEquals("Brand", component.getBrandName());
        assertEquals("Model", component.getModel());
        assertEquals("Serial", component.getSerialNumber());
        assertEquals("Invoice", component.getInvoiceNumber());
    }

    @Test
    void createComponent_EmptyName_ThrowsException() {
        assertThrows(IllegalArgumentException.class, () -> {
            mainComponentService.createComponent("", "Brand", "Model", "Serial", "Invoice");
        });
    }

    @Test
    void createComponent_EmptyBrandName_ThrowsException() {
        assertThrows(IllegalArgumentException.class, () -> {
            mainComponentService.createComponent("Name", "", "Model", "Serial", "Invoice");
        });
    }

    @Test
    void createComponent_EmptyModel_ThrowsException() {
        assertThrows(IllegalArgumentException.class, () -> {
            mainComponentService.createComponent("Name", "Brand", "", "Serial", "Invoice");
        });
    }

    @Test
    void createComponent_EmptySerialNumber_ThrowsException() {
        assertThrows(IllegalArgumentException.class, () -> {
            mainComponentService.createComponent("Name", "Brand", "Model", "", "Invoice");
        });
    }

    @Test
    void createComponent_EmptyInvoiceNumber_ThrowsException() {
        assertThrows(IllegalArgumentException.class, () -> {
            mainComponentService.createComponent("Name", "Brand", "Model", "Serial", "");
        });
    }
}
