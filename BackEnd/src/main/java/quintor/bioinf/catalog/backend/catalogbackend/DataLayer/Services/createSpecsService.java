package quintor.bioinf.catalog.backend.catalogbackend.DataLayer.Services;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import quintor.bioinf.catalog.backend.catalogbackend.DataLayer.Entities.ComponentSpecs;
import quintor.bioinf.catalog.backend.catalogbackend.DataLayer.Repository.ComponentSpecsRepository;
import quintor.bioinf.catalog.backend.catalogbackend.DataLayer.Repository.SpecsRepository;

import java.util.Map;

@Service
public class createSpecsService {

    private static final Logger log = LoggerFactory.getLogger(createSpecsService.class);

    private final ComponentSpecsRepository componentSpecsRepository;
    private final SpecsRepository specsRepository;

    @Autowired
    public createSpecsService(ComponentSpecsRepository componentSpecsRepository, SpecsRepository specsRepository) {
        this.componentSpecsRepository = componentSpecsRepository;
        this.specsRepository = specsRepository;
    }

    /**
     * Main method that adds component specs to the database.
     * It first checks if the specs already exist. If the specs already exist, it returns the specs.
     * If the specs do not exist, it creates new specs and saves them to the database.
     * Then it returns the new created specs.
     *
     * @param storage Storage of the component specs
     * @return ComponentSpecs
     */
    public ComponentSpecs addComponentSpecs(
            Map<String, String> storage)
    {
        // check if specs already exist
            // if specs already exist, return ComponentSpecs
            // if specs do not exist, create new ComponentSpecs
                // save ComponentSpecs and return ComponentSpecs

        ComponentSpecs componentSpecs = createComponentSpecs(storage);
        saveComponentSpecs(componentSpecs);
        return componentSpecs;
    }

    /**
     * Method that creates a component specs
     *
     * @param specs Map with the specs and values
     * @return ComponentSpecs new created component specs
     */
    protected ComponentSpecs createComponentSpecs(Map<String, String> specs) {
        return new ComponentSpecs();
    }

    /**
     * Method that saves the component specs to the database.
     * It calls the componentSpecsRepository to save the component specs.
     *
     * @param componentSpecs ComponentSpecs to be saved
     */
    protected void saveComponentSpecs(ComponentSpecs componentSpecs) {
        componentSpecsRepository.save(componentSpecs);
    }
}
