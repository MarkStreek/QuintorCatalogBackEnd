package quintor.bioinf.catalog.backend.catalogbackend.DataLayer.Services;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import quintor.bioinf.catalog.backend.catalogbackend.DataLayer.Entities.Component;
import quintor.bioinf.catalog.backend.catalogbackend.DataLayer.Entities.ComponentSpecs;
import quintor.bioinf.catalog.backend.catalogbackend.DataLayer.Entities.Specs;
import quintor.bioinf.catalog.backend.catalogbackend.DataLayer.Repository.ComponentSpecsRepository;
import quintor.bioinf.catalog.backend.catalogbackend.DataLayer.Repository.SpecsRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * This service class is the service that provides the main functionality for creating,
 * updating and deleting specs.
 * It is used to interact with two dataRepositories: (1) ComponentSpecsRepository and (2) SpecsRepository.
 * <p>
 * @see ComponentSpecsRepository
 * @see SpecsRepository
 */
@Service
public class SpecsService {

    private static final Logger log = LoggerFactory.getLogger(SpecsService.class);
    private final ComponentSpecsRepository componentSpecsRepository;
    private final SpecsRepository specsRepository;

    private final List<String> alreadyUsedSpecs = new ArrayList<>();

    @Autowired
    public SpecsService(ComponentSpecsRepository componentSpecsRepository, SpecsRepository specsRepository) {
        this.componentSpecsRepository = componentSpecsRepository;
        this.specsRepository = specsRepository;
    }

    /**
     * Method that creates a new ComponentSpecs:
     *  1. The input specs are checked if they are not null or empty
     *  2. The already used specs are retrieved from database
     *  3. The specs are iterated and checked if they already exist:
     *      3a. If the spec does not exist, it is created and saved to the database
     *      3b. If the spec already exists, it is not added again
     *  4. The given value is added to the new or existing spec
     *  5. new ComponentSpecs is saved to the database using the saveComponentSpecs method
     *
     * @param specs All the specs of the component
     * @param component The component to which the specs belong
     * @throws IllegalArgumentException if the specs or component are null or empty
     */
    public void createComponentSpecs(Map<String, Object> specs, Component component) {
        // Check if the specs and component are not null or empty
        if (specs == null || specs.isEmpty() || component == null) {
            log.error("Error with parameters: specs or component is null or empty.");
            throw new IllegalArgumentException();
        }
        // Retrieve all the already used specs from the database
        Iterable<Specs> specsIterable = this.specsRepository.findAll();

        // TODO: Maybe it's better to loop through all the specs and check directly if the spec exists in the specs HashMap, Then we have only one nest loop...?
        specsIterable.forEach(spec -> this.alreadyUsedSpecs.add(spec.getName()));

        for (String key : specs.keySet()) {
            ComponentSpecs componentSpecs = new ComponentSpecs();
            componentSpecs.setComponent(component);
            key = key.toLowerCase();
            Specs spec;
            if (this.alreadyUsedSpecs.contains(key)) {
                spec = this.specsRepository.findByName(key);
            } else {
                spec = new Specs();
                spec.setName(key);
                this.specsRepository.save(spec);
                log.info("A new Spec is created and saved to the database");
            }
            componentSpecs.setSpecs(spec);
            componentSpecs.setValue(specs.get(key).toString());
            this.saveComponentSpecs(componentSpecs);
            log.info("ComponentSpec is successfully saved to the database");
        }
    }

    /**
     * Method that saves the component specs to the database.
     * It calls the componentSpecsRepository to save the component specs.
     *
     * @param componentSpecs ComponentSpecs to be saved
     */
    protected void saveComponentSpecs(ComponentSpecs componentSpecs) {
        try {
            this.componentSpecsRepository.save(componentSpecs);
        } catch (Exception e) {
            log.error("Failed to save component specs: " + e.getMessage());
        }
    }

    /**
     * Method that deletes the component specs from the database.
     * All the possible component specs are retrieved from the database,
     * and then deleted using the componentSpecsRepository.
     *
     * @param component The component of which the specs need to be deleted
     */
    public void deleteComponentSpecs(Component component) {
        try {
            List<ComponentSpecs> componentSpecsList = this.componentSpecsRepository.findByComponent(component);
            this.componentSpecsRepository.deleteAll(componentSpecsList);
        } catch (Exception e) {
            log.error("Failed to delete component specs: " + e.getMessage());
        }
    }
}
