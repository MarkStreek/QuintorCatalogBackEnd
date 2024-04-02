package quintor.bioinf.catalog.services;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import quintor.bioinf.catalog.dto.SpecDetail;
import quintor.bioinf.catalog.entities.Device;
import quintor.bioinf.catalog.entities.DeviceSpecs;
import quintor.bioinf.catalog.entities.Specs;
import quintor.bioinf.catalog.repository.DeviceSpecsRepository;
import quintor.bioinf.catalog.repository.SpecsRepository;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

/**
 * This service class is the service that provides the main functionality for creating,
 * updating and deleting specs.
 * It is used to interact with two dataRepositories: (1) DeviceSpecsRepository and (2) SpecsRepository.
 * <p>
 * @see DeviceSpecsRepository
 * @see SpecsRepository
 */
@Service
public class SpecsService {

    private static final Logger log = LoggerFactory.getLogger(SpecsService.class);
    private final DeviceSpecsRepository deviceSpecsRepository;
    private final SpecsRepository specsRepository;

    @Autowired
    public SpecsService(DeviceSpecsRepository deviceSpecsRepository, SpecsRepository specsRepository) {
        this.deviceSpecsRepository = deviceSpecsRepository;
        this.specsRepository = specsRepository;
    }

    /**
     * Method that creates a new DeviceSpecs:
     *  1. The input specs are checked if they are not null or empty
     *  2. The already used specs are retrieved from database
     *  3. The specs are iterated and checked if they already exist:
     *      3a. If the spec does not exist, it is created and saved to the database
     *      3b. If the spec already exists, it is not added again
     *  4. The given value is added to the new or existing spec
     *  5. new DeviceSpecs is saved to the database using the saveComponentSpecs method
     *
     * @param specDetails Object with name, value and datatype of the specs
     * @param device The device to which the specs belong
     * @throws IllegalArgumentException if the specs or device are null or empty
     */
    public void createDeviceSpecs(List<SpecDetail> specDetails, Device device) {
        if (specDetails == null || specDetails.isEmpty() || device == null) {
            log.error("Error with parameters: specDetails or device is null or empty.");
            throw new IllegalArgumentException();
        }

        for (SpecDetail detail : specDetails) {
            DeviceSpecs deviceSpecs = new DeviceSpecs();
            deviceSpecs.setDevice(device);

            // Check if the spec already exists
            Specs spec = specsRepository.findByName(detail.getSpecName());
            if (spec == null) {
                // If the spec does not exist, create and save a new one
                spec = new Specs();
                spec.setName(detail.getSpecName());
                spec.setDatatype(detail.getDatatype());
                specsRepository.save(spec);
                log.info("A new Spec has been created and saved: " + detail.getSpecName());
            }

            // Now, associate the spec and its value with the device spec
            deviceSpecs.setSpecs(spec);
            deviceSpecs.setValue(detail.getValue());

            // Save the device spec
            saveDeviceSpecs(deviceSpecs);
        }
    }


    /**
     * Method that saves the component specs to the database.
     * It calls the componentSpecsRepository to save the component specs.
     *
     * @param deviceSpecs DeviceSpecs to be saved
     */
    protected void saveDeviceSpecs(DeviceSpecs deviceSpecs) {
        try {
            this.deviceSpecsRepository.save(deviceSpecs);
        } catch (Exception e) {
            log.error("Failed to save component specs: " + e.getMessage());
        }
    }

    /**
     * Method that deletes the device specs from the database.
     * All the possible device specs are retrieved from the database,
     * and then deleted using the componentSpecsRepository.
     *
     * @param device The device of which the specs need to be deleted
     */
    public void deleteDeviceSpecs(Device device) {
        try {
            // Retrieve all the device specs from the database
            List<DeviceSpecs> deviceSpecsList = this.deviceSpecsRepository.findByDevice(device);
            // Delete all the device specs from the database
            this.deviceSpecsRepository.deleteAll(deviceSpecsList);
        } catch (Exception e) {
            log.error("Failed to delete device specs: " + e.getMessage());
        }
    }

    /**
     * Method that retrieves all the specs from the database.
     * It retrieves all the specs from the database using the specsRepository.
     *
     * @return List of all the specs
     */
    public List<Specs> getAllSpecs() {
        Iterable<Specs> specsIterable = specsRepository.findAll();
        return StreamSupport.stream(specsIterable.spliterator(), false)
                .collect(Collectors.toList());
    }

    /**
     * Method that adds one new spec to the database.
     * It saves the new spec to the database using the specsRepository.
     *
     * @param specs The new spec to be saved
     */
    public void addSpecs(Specs specs) {
        specsRepository.save(specs);
    }
}
