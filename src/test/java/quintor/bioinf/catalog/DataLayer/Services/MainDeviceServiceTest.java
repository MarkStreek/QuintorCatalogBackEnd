package quintor.bioinf.catalog.DataLayer.Services;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import quintor.bioinf.catalog.entities.Device;
import quintor.bioinf.catalog.services.MainDeviceService;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(properties = {
        "spring.datasource.url=jdbc:h2:mem:testdb;DB_CLOSE_DELAY=-1;DB_CLOSE_ON_EXIT=FALSE",
        "spring.datasource.driverClassName=org.h2.Driver",
        "spring.datasource.username=sa",
        "spring.datasource.password=",
        "spring.jpa.database-platform=org.hibernate.dialect.H2Dialect"
})
@AutoConfigureMockMvc
public class MainDeviceServiceTest {

    @MockBean
    MockMvc mockMvc;

    @Autowired
    private MainDeviceService mainDeviceService;

    @Test
    void createComponent_HappyPath() {
        Device device = mainDeviceService.createDevice("Name", "Brand", "Model", "Serial", "Invoice");
        assertNotNull(device);
        assertEquals("Name", device.getType());
        assertEquals("Brand", device.getBrandName());
        assertEquals("Model", device.getModel());
        assertEquals("Serial", device.getSerialNumber());
        assertEquals("Invoice", device.getInvoiceNumber());
    }

    @Test
    void createComponent_EmptyName_ThrowsException() {
        assertThrows(IllegalArgumentException.class, () -> {
            mainDeviceService.createDevice("", "Brand", "Model", "Serial", "Invoice");
        });
    }

    @Test
    void createComponent_EmptyBrandName_ThrowsException() {
        assertThrows(IllegalArgumentException.class, () -> {
            mainDeviceService.createDevice("Name", "", "Model", "Serial", "Invoice");
        });
    }

    @Test
    void createComponent_EmptyModel_ThrowsException() {
        assertThrows(IllegalArgumentException.class, () -> {
            mainDeviceService.createDevice("Name", "Brand", "", "Serial", "Invoice");
        });
    }

    @Test
    void createComponent_EmptySerialNumber_ThrowsException() {
        assertThrows(IllegalArgumentException.class, () -> {
            mainDeviceService.createDevice("Name", "Brand", "Model", "", "Invoice");
        });
    }

    @Test
    void createComponent_EmptyInvoiceNumber_ThrowsException() {
        assertThrows(IllegalArgumentException.class, () -> {
            mainDeviceService.createDevice("Name", "Brand", "Model", "Serial", "");
        });
    }
}
