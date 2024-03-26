//package quintor.bioinf.catalog.backend.catalogbackend.Controller;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.web.bind.annotation.*;
//import quintor.bioinf.catalog.backend.catalogbackend.DataLayer.Dto.SpecDetail;
//import quintor.bioinf.catalog.backend.catalogbackend.DataLayer.Services.MainDeviceService;
//
//import java.util.HashMap;
//import java.util.Map;
//
//@RestController
//@RequestMapping("/test")
//public class TestController {
//
//    /*
//    mysql> create database spring_db; -- Creates the new database
//    mysql> create user 'springuser'@'%' identified by 'QUINTOR';
//    mysql> grant all on db_example.* to 'springuser'@'%';
//     */
//
//    private final MainDeviceService mainComponentService;
//
//    @Autowired
//    public TestController(MainDeviceService mainComponentService) {
//        this.mainComponentService = mainComponentService;
//    }
//
//    @RequestMapping("/test")
//    public String test() {
//        return "Test";
//    }
//
//    @RequestMapping("/add")
//    public @ResponseBody String add(
//            @RequestParam String name,
//            @RequestParam String brandName,
//            @RequestParam String model,
//            @RequestParam String serialNumber,
//            @RequestParam String invoiceNumber,
//            @RequestParam String city,
//            @RequestParam String locationAddress,
//            @RequestParam String locationName,
//            @RequestBody Map<SpecDetail, Object> specs
//    ) {
//        try {
//            mainComponentService.addComponent(
//                    name,
//                    brandName,
//                    model,
//                    serialNumber,
//                    invoiceNumber,
//                    city,
//                    locationAddress,
//                    locationName,
//                    specs);
//            return "Added component";
//        } catch (Exception e) {
//            return "Failed to add component";
//        }
//    }
//
//    @RequestMapping("/delete/{Id}")
//    public @ResponseBody String deleteById(
//            @PathVariable Long Id
//    ) {
//        try {
//            mainComponentService.deleteComponent(Id);
//            return "Deleted component";
//        } catch (Exception e) {
//            return "Failed to delete component";
//        }
//    }
//
//}
