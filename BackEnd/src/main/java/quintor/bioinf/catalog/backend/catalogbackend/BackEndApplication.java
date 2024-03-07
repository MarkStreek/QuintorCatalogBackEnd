package quintor.bioinf.catalog.backend.catalogbackend;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

@SpringBootApplication()
public class BackEndApplication {



    public static void main(String[] args) {
        SpringApplication.run(BackEndApplication.class, args);
    }

}
