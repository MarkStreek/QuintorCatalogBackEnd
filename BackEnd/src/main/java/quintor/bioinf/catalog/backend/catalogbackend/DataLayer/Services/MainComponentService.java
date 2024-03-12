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
     * This method creates a new Component object and sets its properties based on the provided arguments.
     * It uses varargs to accept an arbitrary number of arguments,
     * which should correspond to the properties of the Component.
     * The order of the arguments should be: name, brandName, model, serialNumber, invoiceNumber.
     *
     * @param args The properties of the Component.
     * @return A new Component object with its properties set to the provided arguments.
     * @throws IllegalArgumentException If any of the provided arguments are null or empty.
     */
    public Component createComponent(String... args) {
        // The names of the arguments, used for the exception message.
        String[] argNames = {"name", "brandName", "model", "serialNumber", "invoiceNumber"};

        // Create a new Component object.
        Component component = new Component();

        // Iterate over the provided arguments.
        for (int i = 0; i < args.length; i++) {
            // If the argument is null or empty, throw an IllegalArgumentException.
            if (args[i] == null || args[i].isEmpty()) {
                throw new IllegalArgumentException(argNames[i] + " cannot be null or empty");
            }
        }

        // Set the properties of the Component object based on the provided arguments.
        component.setName(args[0]);
        component.setBrandName(args[1]);
        component.setModel(args[2]);
        component.setSerialNumber(args[3]);
        component.setInvoiceNumber(args[4]);

        // Return the created Component object.
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
