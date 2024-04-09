package quintor.bioinf.catalog.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import quintor.bioinf.catalog.entities.Specs;
import quintor.bioinf.catalog.services.SpecsService;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/specs")
public class SpecController {
    private final SpecsService specsService;

    @Autowired
    public SpecController(SpecsService specsService) {
        this.specsService = specsService;
    }

    @GetMapping
    public Map<String, String> getAllSpecs() {
        return specsService.getAllSpecs();
    }
}
