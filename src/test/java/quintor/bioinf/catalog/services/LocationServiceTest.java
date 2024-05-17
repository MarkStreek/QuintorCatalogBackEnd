package quintor.bioinf.catalog.services;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import quintor.bioinf.catalog.entities.Location;
import quintor.bioinf.catalog.repository.LocationRepository;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest
class LocationServiceTest {

    @Mock
    private LocationRepository locationRepository;

    @InjectMocks
    private LocationService locationService;

    /**
     * Method that provides a stream of location ids.
     * For the test method below.
     * @return Stream of location ids
     */
    public static Stream<Long> getLocationIds() {
        return Stream.of(1L, 2L, 3L, 4L, 5L,
                6L, 7L, 8L, 9L, 10L);
    }

    @ParameterizedTest
    @MethodSource("getLocationIds")
    void getExistingLocationIdTest(Long value) {
        Location foundLocation = new Location();
        foundLocation.setId(value);
        assertEquals(value, locationService.getExistingLocationId("address", foundLocation));
    }

    public static Stream<Long> getLocationIdsNULL() {
        return Stream.of(null, 1L, 2L, 3L, 4L, 5L,
                6L, 7L, 8L, null, 10L);
    }

    @ParameterizedTest
    @MethodSource("getLocationIdsNULL")
    void getExistingLocationIdTestNull(Long value) {
        Location foundLocation = new Location();
        foundLocation.setId(value);
        if (value == null) {
            assertNull(locationService.getExistingLocationId("address", foundLocation));
        } else {
            assertEquals(value, locationService.getExistingLocationId("address", foundLocation));
        }
    }

    @ParameterizedTest
    @MethodSource("getLocationIds")
    void findOrCreateLocationTestSunnyDay(Long value) {
        Location location = new Location();
        location.setId(value);
        when(locationRepository.findByAddress("address")).thenReturn(location);
        assertEquals(value, locationService.findOrCreateLocation("name", "city", "address"));
        verify(locationRepository, times(1)).findByAddress("address");
    }

    @Test
    void findOrCreateLocationTestNull() {
        when(locationRepository.findByAddress("address")).thenReturn(null);
        when(locationRepository.addLocation("name", "city", "address")).thenReturn(1L);
        assertEquals(1L, locationService.findOrCreateLocation("name", "city", "address"));
        verify(locationRepository, times(2)).findByAddress("address");
        verify(locationRepository, times(1)).addLocation("name", "city", "address");
    }

    @Test
    void checkIfLocationExistsTest() {
        Location location = new Location();
        location.setId(1L);
        location.setAddress("address");
        when(locationRepository.findByAddress("address")).thenReturn(location);

        assertTrue(locationService.checkIfLocationExists("address"));
        verify(locationRepository, times(1)).findByAddress("address");
    }

    @Test
    void checkIfLocationExistsTestNULL() {
        when(locationRepository.findByAddress("address")).thenReturn(null);
        assertFalse(locationService.checkIfLocationExists("address"));
        verify(locationRepository, times(1)).findByAddress("address");
    }
}