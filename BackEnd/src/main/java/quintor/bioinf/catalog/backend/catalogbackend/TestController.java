package quintor.bioinf.catalog.backend.catalogbackend;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import quintor.bioinf.catalog.backend.catalogbackend.DataLayer.Services.CreateSpecsService;
import quintor.bioinf.catalog.backend.catalogbackend.DataLayer.Services.MainComponentService;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/test")
public class TestController {

    /*
    mysql> create database spring_db; -- Creates the new database
    mysql> create user 'springuser'@'%' identified by 'QUINTOR';
    mysql> grant all on db_example.* to 'springuser'@'%';
     */

    private final MainComponentService mainComponentService;
    private CreateSpecsService createSpecsService;

    @Autowired
    public TestController(MainComponentService mainComponentService, CreateSpecsService createSpecsService) {
        this.mainComponentService = mainComponentService;
        this.createSpecsService = createSpecsService;
    }

    @RequestMapping("/test")
    public String test() {
        return "Test";
    }

    @RequestMapping("/add")
    public @ResponseBody String add(
            @RequestParam String name,
            @RequestParam String brandName,
            @RequestParam String model,
            @RequestParam String serialNumber,
            @RequestParam String invoiceNumber,
            @RequestParam String city,
            @RequestParam String locationAddress,
            @RequestParam String storage
    ) {
        Map<String, Object> specs = new HashMap<>();
        specs.put("storage", storage);
        try {
            mainComponentService.addComponent(
                    name,
                    brandName,
                    model,
                    serialNumber,
                    invoiceNumber,
                    city,
                    locationAddress,
                    specs);
            return "Added component";
        } catch (Exception e) {
            return "Failed to add component";
        }
    }
}
