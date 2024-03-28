package quintor.bioinf.catalog.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import quintor.bioinf.catalog.entities.Specs;
import quintor.bioinf.catalog.services.SpecsService;

import java.util.List;

@RestController
@RequestMapping("/specs")
public class SpecController {
    private final SpecsService specsService;

    @Autowired
    public SpecController(SpecsService specsService) {
        this.specsService = specsService;
    }

    @GetMapping
    public List<Specs> getAllSpecs() {
        return specsService.getAllSpecs();
    }
}
