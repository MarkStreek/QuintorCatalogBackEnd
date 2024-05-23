package quintor.bioinf.catalog.services;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
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

/**
 * Service class responsible for borrowing devices.
 * This BorrowedStatusService class can can create, read and update borrowed statuses.
 * Interaction with the database is done through the BorrowedStatusRepository.
 * This repository capable of adding, deleting and updating borrowed statuses in the database.
 * See the methods for more information.
 */
@Service
public class BorrowedStatusService {

    private static final Logger log = LoggerFactory.getLogger(BorrowedStatusService.class);
    private final UserRepository userRepository;
    private final BorrowedStatusRepository borrowedStatusRepository;
    private final BorrowDTOConverter borrowDTOConverter;
    private final DeviceRepository DeviceRepository;

    @Autowired
    public BorrowedStatusService(UserRepository userRepository,
                                 BorrowedStatusRepository borrowedStatusRepository,
                                 BorrowDTOConverter borrowDTOConverter,
                                 DeviceRepository DeviceRepository) {
        this.userRepository = userRepository;
        this.borrowedStatusRepository = borrowedStatusRepository;
        this.borrowDTOConverter = borrowDTOConverter;
        this.DeviceRepository = DeviceRepository;
    }

    /**
     * The borrowDevice method is used to borrow a device.
     * This method is the 'main' method of the BorrowedStatusService class.
     * It calls other methods to check parameters and create new objects.
     * If everything is correct, the borrowed status is saved to the database.
     *
     * @param name user name
     * @param deviceId device id
     */
    @Transactional
    public void borrowDevice(String name, int deviceId) {
        BorrowedStatus borrowedStatus = new BorrowedStatus();
        // Find or create a new user and set it to the borrowed status
        borrowedStatus.setUser(findUser(name));
        // Set the status to waiting for approval
        borrowedStatus.setStatus("Wachten op goedkeuring");
        // Find the device by id and check for duplicates
        Device device = DeviceRepository.findById((long) deviceId).orElseThrow(() -> new IllegalArgumentException("Apparaat met id " + deviceId + " bestaat niet"));
        checkForDoubleDevices(deviceId);
        // Set the device, date and save to database
        borrowedStatus.setDevice(device);
        borrowedStatus.setCreatedBorrowedDate(new Date());
        borrowedStatusRepository.save(borrowedStatus);

    }

    /**
     * Method that checks if a device already exists in the borrow status database.
     * If the device already exists, an exception is thrown.
     * The error is logged as well.
     *
     * @param deviceId device id
     */
    private void checkForDoubleDevices(int deviceId) {
        if (borrowedStatusRepository.existsById((long) deviceId)) {
            log.error("Device with id {} already exist", deviceId);
            throw new IllegalArgumentException("Apparaat met id " + deviceId + " is al uitgeleend");
        }
    }

    /**
     * Method that finds a user by name. If the user does not exist,
     * the createNewUser method is called. The User object is returned.
     * @param name user name
     * @return User object
     */
    private User findUser(String name) {
        User user = userRepository.findByName(name);
        if (user == null) {
            throw new IllegalArgumentException("Gebruiker met naam " + name + " bestaat niet");
        } else return user;
    }


    /**
     * Method that returns a borrowed status from the database by id.
     * The status is converted to a BorrowDTO object.
     *
     * @param id borrowed status id
     * @return BorrowDTO object
     */
    public BorrowDTO getBorrowStatus(Long id) {
        System.out.println("id = " + id);
        return borrowedStatusRepository.findById(Math.toIntExact(id))
                .map(borrowDTOConverter)
                .orElseThrow(() -> new RuntimeException("Device not found with id: " + id));
    }

    /**
     * Method that returns all the borrowed statuses from the database.
     * The statuses are converted to BorrowDTO objects.
     *
     * @return list of BorrowDTO objects
     */
    public List<BorrowDTO> getAllBorrowStatus() {
        Iterable<BorrowedStatus> borrowedStatusRepositoryAll = borrowedStatusRepository.findAll();
        return StreamSupport.stream(borrowedStatusRepositoryAll.spliterator(), false)
                .map(borrowDTOConverter)
                .collect(Collectors.toList());
    }

    /**
     * Method that returns all the borrowed statuses with the status 'Wachten op goedkeuring'.
     * The statuses are converted to BorrowDTO objects.
     *
     * @return list of BorrowDTO objects
     */
    public List<BorrowDTO> getAllPendingBorrowStatus() {
        Iterable<BorrowedStatus> borrowedStatusRepositoryAll = borrowedStatusRepository.findAll();
        return StreamSupport.stream(borrowedStatusRepositoryAll.spliterator(), false)
                .filter(borrowedStatus -> borrowedStatus.getStatus().equals("Wachten op goedkeuring"))
                .map(borrowDTOConverter)
                .collect(Collectors.toList());
    }

    /**
     * Method that approves a borrowed status by id.
     *
     * @param id borrowed status id
     */
    public void approveBorrowedStatus(Long id) {
        BorrowedStatus borrowedStatus = borrowedStatusRepository
                .findById(Math.toIntExact(id))
                .orElseThrow(()
                        -> new IllegalArgumentException("Id: " + id + " niet gevonden"));
        borrowedStatus.setStatus("Goedgekeurd");
        borrowedStatusRepository.save(borrowedStatus);
    }

}
