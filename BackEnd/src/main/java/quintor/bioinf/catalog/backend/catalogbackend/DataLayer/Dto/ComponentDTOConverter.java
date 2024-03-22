package quintor.bioinf.catalog.backend.catalogbackend.DataLayer.Dto;

import org.springframework.stereotype.Service;
import quintor.bioinf.catalog.backend.catalogbackend.DataLayer.Entities.Component;
import quintor.bioinf.catalog.backend.catalogbackend.DataLayer.Entities.ComponentSpecs;
import quintor.bioinf.catalog.backend.catalogbackend.DataLayer.Entities.Location;
import quintor.bioinf.catalog.backend.catalogbackend.DataLayer.Entities.Specs;
import quintor.bioinf.catalog.backend.catalogbackend.DataLayer.Repository.ComponentSpecsRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Service
public class ComponentDTOConverter implements Function<Component, ComponentDTO> {
    private static final Logger log = LoggerFactory.getLogger(ComponentDTOConverter.class);
    private final ComponentSpecsRepository componentSpecsRepository;

    public ComponentDTOConverter(ComponentSpecsRepository componentSpecsRepository) {
        this.componentSpecsRepository = componentSpecsRepository;
    }

    @Override
    public ComponentDTO apply(Component component) {
        log.info("Converting component: {} (ID: {})", component.getName(), component.getId());

        ComponentDTO componentDTO = new ComponentDTO();
        componentDTO.setId(component.getId());
        componentDTO.setName(component.getName());
        componentDTO.setBrandName(component.getBrandName());
        componentDTO.setModel(component.getModel());
        componentDTO.setSerialNumber(component.getSerialNumber());
        componentDTO.setInvoiceNumber(component.getInvoiceNumber());

        Location location = component.getLocation();
        if (location != null) {
            componentDTO.setLocationCity(location.getCity());
            componentDTO.setLocationAddress(location.getAddress());
            componentDTO.setLocationName(location.getName());
        }

        List<SpecDetail> specDetails = new ArrayList<>();
        List<ComponentSpecs> componentSpecsList = componentSpecsRepository.findByComponent(component);
        if (componentSpecsList.isEmpty()) {
            log.warn("No specs found for component ID: {}", component.getId());
        }

        for (ComponentSpecs compSpec : componentSpecsList) {
            Specs spec = compSpec.getSpecs();
            if (spec == null) {
                log.error("Spec is null for component spec ID: {}", compSpec.getId());
                continue;
            }
            SpecDetail specDetail = new SpecDetail(spec.getName(), compSpec.getValue(), spec.getDatatype());
            log.info("Adding spec detail: name={}, value={}, datatype={}", spec.getName(), compSpec.getValue(), spec.getDatatype());
            specDetails.add(specDetail);
        }
        log.info("Converted component: {}", componentDTO);
        componentDTO.setSpecs(specDetails);
        return componentDTO;
    }
}
