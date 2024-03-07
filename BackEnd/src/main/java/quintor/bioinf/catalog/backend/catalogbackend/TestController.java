package quintor.bioinf.catalog.backend.catalogbackend;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import quintor.bioinf.catalog.backend.catalogbackend.DataLayer.Services.ComponentService;

@RestController
@RequestMapping("/test")
public class TestController {


    private final ComponentService componentService;

    @Autowired
    public TestController(ComponentService componentService) {
        this.componentService = componentService;
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
            @RequestParam String locationName,
            @RequestParam String locationAddress,
            @RequestParam String componentSpecsStorage
    ) {
        try {
            componentService.addComponent(
                    name,
                    brandName,
                    model,
                    serialNumber,
                    invoiceNumber,
                    locationName,
                    locationAddress,
                    componentSpecsStorage
            );
            return "Added component";
        } catch (Exception e) {
            return "Failed to add component";
        }
    }
}
