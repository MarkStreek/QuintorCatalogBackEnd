package quintor.bioinf.catalog.backend.catalogbackend.DataLayer.Services;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import quintor.bioinf.catalog.backend.catalogbackend.DataLayer.Entities.ComponentSpecs;
import quintor.bioinf.catalog.backend.catalogbackend.DataLayer.Entities.Location;
import quintor.bioinf.catalog.backend.catalogbackend.DataLayer.Repository.ComponentRepository;

import java.util.Map;

@Service
public class ComponentService {

    private static final Logger log = LoggerFactory.getLogger(ComponentService.class);
    private final createLocationService createLocationService;
    private final createSpecsService createSpecsService;
    private final ComponentRepository componentRepository;

    @Autowired
    public ComponentService(
            ComponentRepository componentRepository,
            createSpecsService createSpecsService,
            createLocationService createLocationService)
    {
        this.componentRepository = componentRepository;
        this.createSpecsService = createSpecsService;
        this.createLocationService = createLocationService;
    }

    /**
     * Main Service method that adds a component to the database.
     *  1. Location is created and added to the database
     *  2. Component Specs are created and added to the database
     *  3. Location, component specs and other characteristics like brand name are added to the component
     *  4. Component is saved to the database
     *
     * @param name Name of the component
     * @param brandName Brand name of the component
     * @param model Model of the component
     * @param serialNumber Serial number of the component
     * @param invoiceNumber Invoice number of the component
     * @param city City of the location
     * @param locationAddress Address of the location
     * @param componentSpecsStorage Map of component specs
     */
    public void addComponent(
            String name,
            String brandName,
            String model,
            String serialNumber,
            String invoiceNumber,
            String city,
            String locationAddress,
            Map<String, String> componentSpecsStorage)
    {
        Location location = this.createLocationService.addLocation(city, locationAddress);
        ComponentSpecs componentSpecs = this.createSpecsService.addComponentSpecs(componentSpecsStorage);
    }
}
