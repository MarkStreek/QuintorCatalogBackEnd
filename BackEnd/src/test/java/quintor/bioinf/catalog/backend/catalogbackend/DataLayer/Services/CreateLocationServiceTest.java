package quintor.bioinf.catalog.backend.catalogbackend.DataLayer.Services;

import com.fasterxml.jackson.databind.annotation.JsonAppend;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import quintor.bioinf.catalog.backend.catalogbackend.DataLayer.Entities.Location;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class CreateLocationServiceTest {

    @Autowired
    private CreateLocationService createLocationService;

    @Test
    void createLocation_ValidCityAndAddress_ReturnsLocation() {
        Location location = createLocationService.createLocation("Basement","City", "Street 123");
        assertNotNull(location);
        assertEquals("City", location.getCity());
        assertEquals("Street 123", location.getAddress());
    }

    @Test
    void createLocation_EmptyCity_ThrowsException() {
        assertThrows(IllegalArgumentException.class, () -> {
            createLocationService.createLocation("Server Room","", "Street 123");
        });
    }

    @Test
    void createLocation_EmptyAddress_ThrowsException() {
        assertThrows(IllegalArgumentException.class, () -> {
            createLocationService.createLocation("Server Room","City", "");
        });
    }

    @Test
    void createLocation_InvalidAddress_ThrowsException() {
        assertThrows(IllegalArgumentException.class, () -> {
            createLocationService.createLocation("", "", "");
        });
    }
}