package quintor.bioinf.catalog.services;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
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
     * @return Location that is added to the database
     */
    public Location addLocation(
            String name,
            String city,
            String locationAddress)
    {
        Location location = createLocation(name, city, locationAddress);
        if (!this.checkIfLocationExists(location)) {
            this.saveLocation(location);
            return location;
        } else {
            log.warn("Location already exists, not adding it again.");
            return locationRepository.findByAddress(location.getAddress());
        }
    }


    /**
     * Method that creates a location and checks the input for validity
     * <p>
     * The input is checked for emptiness of the city and address.
     * It then sets the name, city and address of the location
     *
     * @param name Name of the location
     * @param city City of the location
     * @param locationAddress Address of the location
     * @return Location that is created
     * @throws IllegalArgumentException if the city or locationAddress is empty
     */
    public Location createLocation(
            String name,
            String city,
            String locationAddress
    ) {
        Location location = new Location();
        if (city.isEmpty() || locationAddress.isEmpty()) {
            log.error("City or locationAddress is empty");
            throw new IllegalArgumentException();
        }
            location.setCity(city);
            location.setAddress(locationAddress);
            location.setName(name);
        return location;
    }

    /**
     * Simple method that tests if an equal location already exists in the database
     * The address is searched for, if it exists, the location is compared to the findingLocation
     * If the location is equal, the location already exists
     *
     * @param findingLocation Location to be checked
     * @return boolean if the location already exists
     */
    public boolean checkIfLocationExists(Location findingLocation) {
        if (locationRepository.findByAddress(findingLocation.getAddress()) != null) {
            Location location = locationRepository.findByAddress(findingLocation.getAddress());
            return findingLocation.equals(location);
        }
        return false;
    }

    /**
     * Method that saves a location to the database
     * It calls the locationRepository to save the location
     *
     * @param location Location to be saved
     */
    protected void saveLocation(Location location) {
        locationRepository.save(location);
    }
}
