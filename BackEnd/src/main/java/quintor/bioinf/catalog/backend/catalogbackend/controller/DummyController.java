package quintor.bioinf.catalog.backend.catalogbackend.controller;

import quintor.bioinf.catalog.backend.catalogbackend.repository.DummyRepository;
import quintor.bioinf.catalog.backend.catalogbackend.model.DummyData;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * DummyController is a simple REST controller that returns some dummy data
 */
@RestController
@RequestMapping("/api/v1/dummy-controller")
public class DummyController {
    // Instance of DummyRepository to access some dummy data
    private final DummyRepository dummyRepository = new DummyRepository();

    /**
     * Handles GET request to fetch all available dummy data
     * @return List of dummy data
     */
    @GetMapping
    public List<DummyData> getDummyData() {
        // Fetch and return dummy data from the repository
        return dummyRepository.getDummyData();
    }

}


