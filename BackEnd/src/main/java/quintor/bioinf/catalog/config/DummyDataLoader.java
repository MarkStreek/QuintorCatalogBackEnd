package quintor.bioinf.catalog.config;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import quintor.bioinf.catalog.dto.SpecDetail;
import quintor.bioinf.catalog.services.MainDeviceService;

import java.util.Arrays;
import java.util.List;

@Configuration
public class DummyDataLoader {

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
        };
    }
}
