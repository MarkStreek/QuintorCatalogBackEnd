package quintor.bioinf.catalog.services;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.jdbc.DataSourceTransactionManagerAutoConfiguration;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import quintor.bioinf.catalog.dto.BorrowDTO;
import quintor.bioinf.catalog.dto.BorrowDTOConverter;
import quintor.bioinf.catalog.dto.DeviceDTO;
import quintor.bioinf.catalog.entities.BorrowedStatus;
import quintor.bioinf.catalog.entities.Device;
import quintor.bioinf.catalog.entities.User;
import quintor.bioinf.catalog.repository.BorrowedStatusRepository;
import quintor.bioinf.catalog.repository.DeviceRepository;
import quintor.bioinf.catalog.repository.UserRepository;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
public class BorrowedStatusService {

    private static final Logger log = LoggerFactory.getLogger(BorrowedStatusService.class);
    private final UserRepository userRepository;
    private final BorrowedStatusRepository borrowedStatusRepository;
    private final BorrowDTOConverter borrowDTOConverter;
    private final DeviceRepository DeviceRepository;
    private final DeviceRepository deviceRepository;
    private final DataSourceTransactionManagerAutoConfiguration dataSourceTransactionManagerAutoConfiguration;

    @Autowired
    public BorrowedStatusService(UserRepository userRepository,
                                 BorrowedStatusRepository borrowedStatusRepository,
                                 BorrowDTOConverter borrowDTOConverter,
                                 DeviceRepository DeviceRepository, DeviceRepository deviceRepository, DataSourceTransactionManagerAutoConfiguration dataSourceTransactionManagerAutoConfiguration) {
        this.userRepository = userRepository;
        this.borrowedStatusRepository = borrowedStatusRepository;
        this.borrowDTOConverter = borrowDTOConverter;
        this.DeviceRepository = DeviceRepository;
        this.deviceRepository = deviceRepository;
        this.dataSourceTransactionManagerAutoConfiguration = dataSourceTransactionManagerAutoConfiguration;
    }

    /**
     * Do the magic here!
     * @param borrowDTO the function argument
     */
    @Transactional
    public void borrowDevice(BorrowDTO borrowDTO) {

        BorrowedStatus borrowedStatus = new BorrowedStatus();
        User user = findOrCreateNewUser(borrowDTO);
        borrowedStatus.setUser(user);

        checkAmountOfDevicesForUser(user, borrowedStatus);

        Device device = DeviceRepository.findById((long) borrowDTO.getDeviceId()).orElseThrow();
        borrowedStatus.setDevice(device);
        borrowedStatus.setCreatedBorrowedDate(new Date());
        borrowedStatusRepository.save(borrowedStatus);
    }

    private void checkAmountOfDevicesForUser(User user, BorrowedStatus borrowedStatus) {
        List<BorrowedStatus> users = borrowedStatusRepository.findAllByUser(user);
        if (users.isEmpty()) {
            borrowedStatus.setStatus("No approval needed");
        } else {
            borrowedStatus.setStatus("Waiting for approval");
        }
    }

    private User findOrCreateNewUser(BorrowDTO borrowDTO) {
        User user = userRepository.findByName(borrowDTO.getUserName());
        if (user == null) {
            User newUser = new User();
            newUser.setName(borrowDTO.getUserName());
            userRepository.save(newUser);
            return newUser;
        } else return user;
    }


    public List<BorrowDTO> getAllBorrowStatus() {
        Iterable<BorrowedStatus> devices = borrowedStatusRepository.findAll();
        return StreamSupport.stream(devices.spliterator(), false)
                .map(borrowDTOConverter)
                .collect(Collectors.toList());
    }

}
