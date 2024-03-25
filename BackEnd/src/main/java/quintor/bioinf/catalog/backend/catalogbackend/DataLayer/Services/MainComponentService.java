package quintor.bioinf.catalog.backend.catalogbackend.DataLayer.Services;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import quintor.bioinf.catalog.backend.catalogbackend.DataLayer.Dto.ComponentDTO;
import quintor.bioinf.catalog.backend.catalogbackend.DataLayer.Dto.ComponentDTOConverter;
import quintor.bioinf.catalog.backend.catalogbackend.DataLayer.Dto.SpecDetail;
import quintor.bioinf.catalog.backend.catalogbackend.DataLayer.Entities.Component;
import quintor.bioinf.catalog.backend.catalogbackend.DataLayer.Entities.Location;
import quintor.bioinf.catalog.backend.catalogbackend.DataLayer.Repository.ComponentRepository;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

/**
 * The main service method of the application. The service class is autowired by a controller class.
 * <p>
 * The service class is responsible for adding, deleting and updating components in the database.
 * For these operations, LocationService and CreateComponentSpecs are required as well.
 * Therefore, the service class uses methods from these services to add, delete and update the components.
 *
 * @see LocationService
 * @see SpecsService
 */
@Service
public class MainComponentService {

    private static final Logger log = LoggerFactory.getLogger(MainComponentService.class);
    private final LocationService locationService;
    private final SpecsService specsService;
    private final ComponentRepository componentRepository;

    private final ComponentDTOConverter componentDTOConverter;

    @Autowired
    public MainComponentService(
            ComponentRepository componentRepository,
            SpecsService specsService,
            LocationService locationService,
            ComponentDTOConverter componentDTOConverter
    )
    {
        this.componentRepository = componentRepository;
        this.specsService = specsService;
        this.locationService = locationService;
        this.componentDTOConverter = componentDTOConverter;
    }

    /**
     * Main Service method that adds a component to the database.
     *  1. Create the component with the give characteristics
     *  2. Location is created and added to the database
     *  3. The Location is added to the component
     *  4. Component is saved to the database
     *  5. Component Specs are created and added to the database
     *  <p>
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
     * @param locationName Name of the location (i.e. "Server room")
     * @param specs The specifications of the component
     */
    public void addComponent(
            String name,
            String brandName,
            String model,
            String serialNumber,
            String invoiceNumber,
            String city,
            String locationAddress,
            String locationName,
            List<SpecDetail> specs)
    {
        // 1 - Create the component
        Component component = this.createComponent(name, brandName, model, serialNumber, invoiceNumber);
        // 2 - Create the location
        Location location = this.locationService.addLocation(locationName, city, locationAddress);
        // 3 - Add location to the component
        component.setLocation(location);
        // 4 - Save the component to the database
        this.saveComponent(component);
        // 5 - Create component specs
        this.specsService.createComponentSpecs(specs,component);
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

        return component;
    }

    /**
     * Method that saves a component to the database.
     * It uses the componentRepository to save the component
     *
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

    /**
     * Method that deletes a component from the database.
     * It also deletes the component specs from the database,
     * with calling the deleteComponentSpecs method
     *
     * @param Id The id of the component that needs to be deleted
     */
    public void deleteComponent(Long Id) {
        // Check if the component exists in the database
        this.componentRepository.findById(Id).ifPresentOrElse(
                component -> {
                    try {
                        // Delete the component specs
                        this.specsService.deleteComponentSpecs(component);
                        // Delete the component from the database
                        this.componentRepository.deleteById(Id);
                    } catch (Exception e) {
                        log.error("Failed to delete component: " + e.getMessage());
                    }
                },
                // Log an error if the component does not exist
                () -> log.error("No component found with the given ID")
        );
    }

    public ComponentDTO getComponent(Long id) {
        return componentRepository.findById(id)
                .map(componentDTOConverter)
                .orElseThrow(() -> new RuntimeException("Component not found with id: " + id));
    }

    public List<ComponentDTO> getAllComponents() {
        Iterable<Component> components = componentRepository.findAll();
        return StreamSupport.stream(components.spliterator(), false)
                .map(componentDTOConverter)
                .collect(Collectors.toList());
    }
}
