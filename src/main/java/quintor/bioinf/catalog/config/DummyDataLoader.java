package quintor.bioinf.catalog.config;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import quintor.bioinf.catalog.dto.SpecDetail;
import quintor.bioinf.catalog.entities.Role;
import quintor.bioinf.catalog.entities.User;
import quintor.bioinf.catalog.repository.UserRepository;
import quintor.bioinf.catalog.services.MainDeviceService;

import java.util.Arrays;
import java.util.List;

@Configuration
public class DummyDataLoader {

    @Bean
    @Profile("!test")
    CommandLineRunner loadUsers(UserRepository userService) {
        User user = new User();
        user.setName("admin");
        user.setEmail("mvdstreek2003@gmail.com");
        user.setPassword("admin");
        user.setRole(Role.USER);

        User user2 = new User();
        user2.setName("user");
        user2.setEmail("info@test.nl");
        user2.setPassword("user");
        user2.setRole(Role.CTO);

        User user3 = new User();
        user3.setName("Another User");
        user3.setEmail("another+test@gmail.com");
        user3.setPassword("another");
        user3.setRole(Role.ADMIN);


        return args -> {
            userService.save(user);
            userService.save(user2);
            userService.save(user3);
        };
    }

    @Bean
    @Profile("!test") // Ensure this doesn't run during testing
    CommandLineRunner loadData(MainDeviceService mainDeviceService) {
        return args -> {
            List<SpecDetail> dellSpecs = Arrays.asList(
                    new SpecDetail("Processor", "Intel Core i7 10th Gen", "String"),
                    new SpecDetail("RAM", "16GB DDR4", "Text"),
                    new SpecDetail("Storage", "512GB NVMe SSD", "Text"),
                    new SpecDetail("Screen Size", "15\" FHD", "Text"),
                    new SpecDetail("OS", "Windows 10 Pro", "Enum"),
                    new SpecDetail("Battery", "56 Wh", "Text"),
                    new SpecDetail("Weight", "1.8 kg", "Text")
            );

            mainDeviceService.addDevice(
                    "Laptop",
                    "Dell",
                    "XPS 15",
                    "12345XYZ",
                    "INV123456",
                    "New York",
                    "123 Tech Valley",
                    "Main Office",
                    dellSpecs
            );

            List<SpecDetail> appleSpecs = Arrays.asList(
                    new SpecDetail("Processor", "Apple M1 Pro", "String"),
                    new SpecDetail("RAM", "32GB Unified", "Text"),
                    new SpecDetail("Storage", "1TB SSD", "Text"),
                    new SpecDetail("Screen Size", "16\" Retina", "Text"),
                    new SpecDetail("OS", "macOS Monterey", "Enum"),
                    new SpecDetail("Battery", "70 Wh", "Text"),
                    new SpecDetail("Weight", "2.0 kg", "Text")
            );


            mainDeviceService.addDevice(
                    "Laptop",
                    "Apple",
                    "MacBook Pro",
                    "98765ZYX",
                    "INV654321",
                    "San Francisco",
                    "456 Tech Park",
                    "Tech HQ",
                    appleSpecs
            );

            List<SpecDetail> hpSpecs = Arrays.asList(
                    new SpecDetail("Processor", "AMD Ryzen 5 3500U", "String"),
                    new SpecDetail("RAM", "8GB DDR4", "Text"),
                    new SpecDetail("Storage", "256GB SSD", "Text"),
                    new SpecDetail("Screen Size", "15.6\" HD", "Text"),
                    new SpecDetail("OS", "Windows 11 Home", "Enum"),
                    new SpecDetail("Battery", "41 Wh", "Text"),
                    new SpecDetail("Weight", "1.75 kg", "Text")
            );

            mainDeviceService.addDevice(
                    "Laptop",
                    "HP",
                    "Pavilion 15",
                    "54321ZYX",
                    "INV987654",
                    "Los Angeles",
                    "789 Tech Lane",
                    "Tech Outlet",
                    hpSpecs
            );

            List<SpecDetail> alienwareSpecs = Arrays.asList(
                    new SpecDetail("Processor", "Intel Core i9-11900H", "String"),
                    new SpecDetail("RAM", "32GB DDR4", "Text"),
                    new SpecDetail("Storage", "1TB NVMe SSD", "Text"),
                    new SpecDetail("Screen Size", "17.3\" FHD", "Text"),
                    new SpecDetail("Graphics", "NVIDIA GeForce RTX 3080", "Text"),
                    new SpecDetail("OS", "Windows 11 Pro", "Enum"),
                    new SpecDetail("Battery", "99 Wh", "Text"),
                    new SpecDetail("Weight", "2.5 kg", "Text")
            );

            mainDeviceService.addDevice(
                    "Laptop",
                    "Alienware",
                    "M17 R4",
                    "98765XYZ",
                    "INV123456",
                    "New York",
                    "456 Tech Park",
                    "Elite Gaming Gear",
                    alienwareSpecs
            );

        };
    }
}
