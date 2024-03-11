package quintor.bioinf.catalog.backend.catalogbackend;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import quintor.bioinf.catalog.backend.catalogbackend.DataLayer.Services.MainComponentService;
import quintor.bioinf.catalog.backend.catalogbackend.DataLayer.Services.createSpecsService;

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
    private createSpecsService createSpecsService;

    @Autowired
    public TestController(MainComponentService mainComponentService, createSpecsService createSpecsService) {
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
            @RequestParam String locationAddress
    ) {
        Map<String, Object> componentSpecsStorage = new HashMap<>();
        componentSpecsStorage.put("opslag", "test");
        componentSpecsStorage.put("snelheid", 4);
        try {
//            mainComponentService.addComponent(
//                    name,
//                    brandName,
//                    model,
//                    serialNumber,
//                    invoiceNumber,
//                    city,
//                    locationAddress,
//                    componentSpecsStorage
            this.createSpecsService.addComponentSpecs(componentSpecsStorage);
            return "Added component";
        } catch (Exception e) {
            return "Failed to add component";
        }
    }
}
