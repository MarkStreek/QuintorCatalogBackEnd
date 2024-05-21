package quintor.bioinf.catalog.services;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import quintor.bioinf.catalog.dto.DeviceDTO;
import quintor.bioinf.catalog.dto.SpecDetail;
import quintor.bioinf.catalog.entities.Device;
import quintor.bioinf.catalog.repository.DeviceRepository;
import quintor.bioinf.catalog.services.LocationService;
import quintor.bioinf.catalog.services.MainDeviceService;
import quintor.bioinf.catalog.services.SpecsService;

import java.util.List;
import java.util.Optional;
import java.util.zip.DeflaterInputStream;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest
public class MainDeviceServiceTest {

    @Mock
    private DeviceRepository deviceRepository;

    @Mock
    private SpecsService specsService;

    @Mock
    private LocationService locationService;

    @InjectMocks
    private MainDeviceService mainDeviceService;

    @Test
    void Test() {
        System.out.println("test");
    }
}