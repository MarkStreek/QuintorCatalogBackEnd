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

   @Autowired
   private LocationRepository locationRepository;

    @Test
    void TestCreateLocation1() {
//        String LocationName = "Groningen";
//        String locationAddress = "Noordsingel 250";
//        Location location1 = new Location();
//        location1.setCity(LocationName);
//        location1.setAddress(locationAddress);
//
//        Location location2 = componentService.createLocation(LocationName, locationAddress);
//
//        assertEquals(location1, location2);
    }

    @Test
    void TestCreateLocation2() {
//        String LocationName = "Groningen";
//        String locationAddress = "Noordsingel 250";
//        Location location1 = new Location();
//        location1.setCity("Zwolle");
//        location1.setAddress(locationAddress);
//
//        Location location2 = componentService.createLocation(LocationName, locationAddress);
//
//        assertNotEquals(location1, location2);
    }

    @Test
    void TestCreateLocation3(){
//        String LocationName = "";
//        String locationAddress = "Noordsingel 250";
//        assertThrows(IllegalArgumentException.class, () -> componentService.createLocation(LocationName, locationAddress));
    }

    @Test
    void TestCreateLocation4(){
//        String LocationName = "%$#";
//        String locationAddress = "Noordsingel 250";
//        assertThrows(IllegalArgumentException.class, () -> componentService.createLocation(LocationName, locationAddress));
    }
}
