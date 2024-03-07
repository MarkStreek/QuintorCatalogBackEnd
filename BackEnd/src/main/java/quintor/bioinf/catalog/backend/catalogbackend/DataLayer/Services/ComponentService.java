package quintor.bioinf.catalog.backend.catalogbackend.DataLayer.Services;

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
            String locationName,
            String locationAddress,
            String componentSpecsStorage
    ) {
        Location location = new Location();
        location.setName(locationName);
        location.setAddress(locationAddress);
        try {
            if (locationRepository.findByAddress(locationAddress) != null) {
                Location findingLocation = locationRepository.findByAddress(locationAddress);

                if (!findingLocation.equals(location)) {
                    locationRepository.save(location);
                } else {
                    location = findingLocation;
                }
            }
        } catch (Exception e) {
            // log warn
        }
        locationRepository.save(location);
        ComponentSpecs componentSpecs = new ComponentSpecs();
        componentSpecs.setStorage(componentSpecsStorage);
        componentSpecsRepository.save(componentSpecs);
        Component component = new Component();
        component.setName(name);
        component.setBrandName(brandName);
        component.setModel(model);
        component.setSerialNumber(serialNumber);
        component.setInvoiceNumber(invoiceNumber);
        component.setComponentSpecs(componentSpecs);
        component.setLocation(location);
        componentRepository.save(component);
    }
}
