package quintor.bioinf.catalog.backend.catalogbackend.DataLayer.Services;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.HashMap;
import java.util.Map;

@SpringBootTest
class CreateSpecsServiceTest {

    @Autowired
    private CreateSpecsService createSpecsService;

    @Test
    void TestExistence() {
        Map<String, Object> specs = new HashMap<>();
        specs.put("name", "test");
        specs.put("description", "test");

        System.out.println(specs.containsKey("namde"));
    }
}