package quintor.bioinf.catalog.backend.catalogbackend.DataLayer.Services;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import quintor.bioinf.catalog.backend.catalogbackend.DataLayer.Entities.Component;
import quintor.bioinf.catalog.backend.catalogbackend.DataLayer.Entities.ComponentSpecs;
import quintor.bioinf.catalog.backend.catalogbackend.DataLayer.Entities.Location;
import quintor.bioinf.catalog.backend.catalogbackend.DataLayer.Repository.ComponentRepository;
import quintor.bioinf.catalog.backend.catalogbackend.DataLayer.Repository.ComponentSpecsRepository;
import quintor.bioinf.catalog.backend.catalogbackend.DataLayer.Repository.LocationRepository;

@Service
public class ComponentService {

    private static final Logger log = LoggerFactory.getLogger(ComponentService.class);

    private final ComponentRepository componentRepository;
    private final ComponentSpecsRepository componentSpecsRepository;
    private final LocationRepository locationRepository;

    @Autowired
    public ComponentService(
            ComponentRepository componentRepository,
            ComponentSpecsRepository componentSpecsRepository,
            LocationRepository locationRepository
    ) {
        this.componentRepository = componentRepository;
        this.componentSpecsRepository = componentSpecsRepository;
        this.locationRepository = locationRepository;
    }

    public void addComponent(
            String name,
            String brandName,
            String model,
            String serialNumber,
            String invoiceNumber,
            String city,
            String locationAddress,
            String componentSpecsStorage
    ) {
        log.info("Trying to add component");
        log.info("Here");
        Location location = createLocation(city, locationAddress);
        if (!checkIfLocationExists(location)) {
            locationRepository.save(location);
        }

        ComponentSpecs componentSpecs = createComponentSpecs(componentSpecsStorage);
        componentSpecsRepository.save(componentSpecs);
        componentRepository.save(createComponent(
                name,
                brandName,
                model,
                serialNumber,
                invoiceNumber,
                componentSpecs,
                location
        ));
        log.info("Component Successfully added!");
    }

    protected Location createLocation(
            String city,
            String locationAddress
    ) {
        Location location = new Location();
        if (city.isEmpty() || locationAddress.isEmpty()) {
            throw new IllegalArgumentException();

        }
        if (city.matches("^[a-zA-Z]+$") && locationAddress.matches("^[A-z]{1,} [0-9]{1,}")) {
                location.setCity(city);
                location.setAddress(locationAddress);
            } else throw new IllegalArgumentException();
        return location;
    }

    protected boolean checkIfLocationExists(Location findingLocation) {
        if (locationRepository.findByAddress(findingLocation.getAddress()) != null) {
            Location location = locationRepository.findByAddress(findingLocation.getAddress());
            return findingLocation.equals(location);
        }
        return false;
    }

    private ComponentSpecs createComponentSpecs(
            String storage
    ) {
        ComponentSpecs componentSpecs = new ComponentSpecs();
        componentSpecs.setStorage(storage);
        return componentSpecs;
    }

    private Component createComponent(
            String name,
            String brandName,
            String model,
            String serialNumber,
            String invoiceNumber,
            ComponentSpecs componentSpecs,
            Location location
    ) {
        Component component = new Component();
        component.setName(name);
        component.setBrandName(brandName);
        component.setModel(model);
        component.setSerialNumber(serialNumber);
        component.setInvoiceNumber(invoiceNumber);
        component.setComponentSpecs(componentSpecs);
        component.setLocation(location);

        return component;
    }

}
