package quintor.bioinf.catalog.services;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import quintor.bioinf.catalog.entities.Location;
import quintor.bioinf.catalog.repository.LocationRepository;

/**
 * Service class that is responsible for adding a location to the database.
 * <br>
 * The service class is using the LocationRepository to interact with the database.
 *
 * @see LocationRepository
 */
@Service
public class LocationService {

    private static final Logger log = LoggerFactory.getLogger(LocationService.class);
    private final LocationRepository locationRepository;

    @Autowired
    public LocationService(LocationRepository locationRepository) {
        this.locationRepository = locationRepository;
    }

    /**
     * Main method that adds a location to the database.
     * It first creates a location and then checks if the location already exists.
     * If the location does not exist, it is saved to the database.
     * Otherwise, The already used location is not added again.
     *
     * @param name Name of the location (i.e. "Server room")
     * @param city City of the location
     * @param locationAddress Address of the location
     * @return LocationId that can be used for adding a device
     */

    public Long addLocation(String name, String city, String locationAddress) {
        if (!this.checkIfLocationExists(locationAddress)) {
            Long locationId = locationRepository.addLocation(name, city, locationAddress);
            return locationId;
        } else {
            log.warn("Location already exists, not adding it again.");
            Location foundLocation = locationRepository.findByAddress(locationAddress);
            return getExistingLocationId(locationAddress, foundLocation);
        }
    }

    private static Long getExistingLocationId(String locationAddress, Location foundLocation) {
        if (foundLocation != null) {
            return foundLocation.getId();
        } else {
            log.error("Existing location not found after check. Address: {}", locationAddress);
            return null;
        }
    }

    public Long findOrCreateLocation(String name, String city, String address) {
        Location location = this.locationRepository.findByAddress(address);
        if (location == null) {
            return this.addLocation(name, city, address);
        } else {
            return location.getId();
        }
    }


    public ResponseEntity<String> updateLocation(Long id, String name, String city, String address) {
        this.locationRepository.updateLocation(id, name, city, address);
        return ResponseEntity.ok("Location updated successfully.");
        }


    /**
     * Simple method that tests if an equal location already exists in the database
     * The address is searched for, if it exists, the location is compared to the findingLocation
     * If the location is equal, the location already exists
     *
     * @param locationAddress Location address to be checked
     * @return boolean if the location already exists
     */
    public boolean checkIfLocationExists(String locationAddress) {
        Location location = locationRepository.findByAddress(locationAddress);
        return location != null && location.getAddress() != null && location.getAddress().equals(locationAddress);
    }


}