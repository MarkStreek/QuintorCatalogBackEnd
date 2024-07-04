package quintor.bioinf.catalog.config;

import jakarta.validation.executable.ValidateOnExecution;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import quintor.bioinf.catalog.dto.SpecDetail;
import quintor.bioinf.catalog.entities.Role;
import quintor.bioinf.catalog.entities.User;
import quintor.bioinf.catalog.repository.UserRepository;
import quintor.bioinf.catalog.services.MainDeviceService;

import java.util.Arrays;
import java.util.List;

@Configuration
public class DummyDataLoader {

    @Value("${admin.password}")
    private String adminPassword;

    @Value("${admin.email}")
    private String adminEmail;

    @Value("${user.password}")
    private String userPassword;

    @Value("${user.email}")
    private String userEmail;

    @Value("${another.password}")
    private String anotherPassword;

    @Value("${another.email}")
    private String anotherEmail;

    @Bean
    @Profile("!test")
    CommandLineRunner loadUsers(UserRepository userService) {
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

        User user = new User();
        user.setName("admin");
        user.setEmail(adminEmail);
        String hashedPassword = passwordEncoder.encode(adminPassword);
        user.setPassword(hashedPassword);
        user.setRole(Role.ROLE_USER);

        User user2 = new User();
        user2.setName("user");
        user2.setEmail(userEmail);
        String hashedPassword2 = passwordEncoder.encode(userPassword);
        user2.setPassword(hashedPassword2);
        user2.setRole(Role.ROLE_CTO);

        User user3 = new User();
        user3.setName("Another User");
        user3.setEmail(anotherEmail);
        String hashedPassword3 = passwordEncoder.encode(anotherPassword);
        user3.setPassword(hashedPassword3);
        user3.setRole(Role.ROLE_ADMIN);


        return args -> {
            userService.save(user);
            userService.save(user2);
            userService.save(user3);
        };
    }
}

