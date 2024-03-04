package quintor.bioinf.catalog.backend.catalogbackend.HelloTestPackage;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/test")
public class TestAPI {

    @GetMapping("/hello")
    public String hello() {
        return "Hello, World!";
    }
}
