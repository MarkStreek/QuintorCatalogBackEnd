package quintor.bioinf.catalog.DataLayer.Services;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import quintor.bioinf.catalog.entities.Location;
import quintor.bioinf.catalog.repository.LocationRepository;
import quintor.bioinf.catalog.services.LocationService;

class LocationServiceTest {

    @Mock
    private LocationRepository locationRepository;

    @InjectMocks
    private LocationService locationService;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testAddLocation_NewLocation() {
        String name = "Server room";
        String city = "Springfield";
        String address = "123 Main St";
        when(locationRepository.findByAddress(address)).thenReturn(null);
        when(locationRepository.addLocation(name, city, address)).thenReturn(1L);

        Long result = locationService.addLocation(name, city, address);
        assertNotNull(result);
        assertEquals(Long.valueOf(1), result);
    }

    @Test
    void testAddLocation_ExistingLocation() {
        String name = "Server room";
        String city = "Springfield";
        String address = "123 Main St";
        Location location = new Location();
        location.setId(1L);
        location.setAddress(address);  // Make sure to set the address
        when(locationRepository.findByAddress(address)).thenReturn(location);

        Long result = locationService.addLocation(name, city, address);
        assertNotNull(result);
        assertEquals(Long.valueOf(1), result);
    }

}
