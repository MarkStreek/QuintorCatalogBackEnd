package quintor.bioinf.catalog.backend.catalogbackend.DataLayer.Services;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import quintor.bioinf.catalog.backend.catalogbackend.DataLayer.Entities.Component;
import quintor.bioinf.catalog.backend.catalogbackend.DataLayer.Entities.Location;
import quintor.bioinf.catalog.backend.catalogbackend.DataLayer.Repository.ComponentRepository;

import java.util.Map;

@Service
public class MainComponentService {

    private static final Logger log = LoggerFactory.getLogger(MainComponentService.class);
    private final CreateLocationService createLocationService;
    private final CreateSpecsService createSpecsService;
    private final ComponentRepository componentRepository;

    @Autowired
    public MainComponentService(
            ComponentRepository componentRepository,
            CreateSpecsService createSpecsService,
            CreateLocationService createLocationService)
    {
        this.componentRepository = componentRepository;
        this.createSpecsService = createSpecsService;
        this.createLocationService = createLocationService;
    }

    /**
     * Main Service method that adds a component to the database.
     *  1. Create the component with the give characteristics
     *  2. Location is created and added to the database
     *  3. The Location is added to the component
     *  4. Component is saved to the database
     *  5. Component Specs are created and added to the database
     *  <br>
     *  The Component must first be stored in the database,
     *  before it can be used in the ComponentSpecs
     *
     * @param name Name of the component
     * @param brandName Brand name of the component
     * @param model Model of the component
     * @param serialNumber Serial number of the component
     * @param invoiceNumber Invoice number of the component
     * @param city City of the location
     * @param locationAddress Address of the location
     *
     */
    public void addComponent(
            String name,
            String brandName,
            String model,
            String serialNumber,
            String invoiceNumber,
            String city,
            String locationAddress,
            Map<String, Object> specs)
    {
        // 1 - Create the component
        Component component = this.createComponent(name, brandName, model, serialNumber, invoiceNumber);
        // 2 - Create the location
        Location location = this.createLocationService.addLocation(city, locationAddress);
        // 3 - Add location to the component
        component.setLocation(location);
        // 4 - Save the component to the database
        this.saveComponent(component);
        // 5 - Create component specs
        this.createSpecsService.createComponentSpecs(specs,component);
    }


    /**
     * Method that creates a component object
     * It uses the location and specs that are created in:
     *  1. CreateLocationService
     *  2. CreateSpecsService
     *
     * @param name Name of the component
     * @param brandName Brand name of the component
     * @param model Model of the component
     * @param serialNumber Serial number of the component
     * @param invoiceNumber Invoice number of the component
     * @return component the newly created component
     */
    private Component createComponent(
            String name,
            String brandName,
            String model,
            String serialNumber,
            String invoiceNumber)
    {
        Component component = new Component();
        component.setName(name);
        component.setBrandName(brandName);
        component.setModel(model);
        component.setSerialNumber(serialNumber);
        component.setInvoiceNumber(invoiceNumber);

        return component;
    }

    /**
     * Method that saves a component to the database.
     * It uses the componentRepository to save the component
     * @param component the component that needs to be saved
     */
    public void saveComponent(Component component) {
        try {
            this.componentRepository.save(component);
            log.info("Component successfully saved to the database");
        } catch (Exception e) {
            log.error("Error saving component to the database: " + e.getMessage());
        }
    }
}
