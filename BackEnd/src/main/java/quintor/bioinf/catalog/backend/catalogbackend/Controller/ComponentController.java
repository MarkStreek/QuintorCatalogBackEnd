package quintor.bioinf.catalog.backend.catalogbackend.Controller;


import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import quintor.bioinf.catalog.backend.catalogbackend.DataLayer.Dto.ComponentDTO;
import quintor.bioinf.catalog.backend.catalogbackend.DataLayer.Services.MainComponentService;

import java.util.List;

@RestController
@RequestMapping("/components")
public class ComponentController {

    private final MainComponentService mainComponentService;

    @Autowired
    public ComponentController(MainComponentService mainComponentService) {
        this.mainComponentService = mainComponentService;

    }

    @PostMapping
    public ResponseEntity<String> addComponent(@RequestBody @Valid ComponentDTO componentDTO) {
//        try {
            mainComponentService.addComponent(
                    componentDTO.getName(),
                    componentDTO.getBrandName(),
                    componentDTO.getModel(),
                    componentDTO.getSerialNumber(),
                    componentDTO.getInvoiceNumber(),
                    componentDTO.getLocationCity(),
                    componentDTO.getLocationAddress(),
                    componentDTO.getLocationName(),
                    componentDTO.getSpecs()
            );
            return ResponseEntity.ok("Added component");
//        } catch (Exception e) {
//            return ResponseEntity.status(500).body("Failed to add component");
//        }
    }

    @GetMapping("/{id}")
    public ComponentDTO getComponent(@PathVariable Long id) {
        return mainComponentService.getComponent(id);
    }

    @GetMapping
    public List<ComponentDTO> getAllComponents() {
        return mainComponentService.getAllComponents();
    }

    @PutMapping("/{id}")
    public ResponseEntity<String> updateComponent(@PathVariable Long id, @RequestBody ComponentDTO componentDTO) {
        return null;
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteComponent(@PathVariable Long id) {
        return null;
    }

}
