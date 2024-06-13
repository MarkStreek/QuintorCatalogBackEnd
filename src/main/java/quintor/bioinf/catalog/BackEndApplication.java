package quintor.bioinf.catalog;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Main class of the application.
 * This class is used to start the application.
 */
@SpringBootApplication()
public class BackEndApplication {

    private static final Logger log = LoggerFactory.getLogger(BackEndApplication.class);

    public static void main(String[] args) {
        log.info("Starting the application");
        SpringApplication.run(BackEndApplication.class, args);
    }

}
