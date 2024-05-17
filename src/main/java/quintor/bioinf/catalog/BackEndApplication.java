package quintor.bioinf.catalog;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import quintor.bioinf.catalog.services.BorrowedStatusService;

@SpringBootApplication()
public class BackEndApplication {

    private static final Logger log = LoggerFactory.getLogger(BackEndApplication.class);

    public static void main(String[] args) {
        log.info("Starting the application");
        SpringApplication.run(BackEndApplication.class, args);
    }

}
