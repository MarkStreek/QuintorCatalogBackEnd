package quintor.bioinf.catalog.dto;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import quintor.bioinf.catalog.entities.BorrowedStatus;
import quintor.bioinf.catalog.entities.Device;
import quintor.bioinf.catalog.entities.Location;
import quintor.bioinf.catalog.entities.User;

import java.util.function.Function;

/**
 * Converter class that converts a BorrowedStatus object to a BorrowDTO object.
 * I.e., this class is used to convert a database record to a BorrowDTO object.
 * The apply method is used to convert the BorrowedStatus object to a BorrowDTO object.
 * There are several methods that are used to create a User, Device and Location object.
 * <p>
 * The class implements the Function interface,
 * which is a functional interface that has only one abstract method.
 *
 * @see BorrowDTO
 */
@Service
public class BorrowDTOConverter implements Function<BorrowedStatus, BorrowDTO> {

    private static final Logger log = LoggerFactory.getLogger(BorrowDTOConverter.class);

    /**
     * Method that converts a BorrowedStatus object to a BorrowDTO object.
     *
     * @param borrowedStatus the function argument
     * @return The BorrowDTO object
     */
    @Override
    public BorrowDTO apply(BorrowedStatus borrowedStatus) {
        BorrowDTO borrowDTO = new BorrowDTO();
        borrowDTO.setId(borrowedStatus.getId());
        // Create a new User and Device object
        User user = createUser(borrowedStatus);
        Device device = createDevice(borrowedStatus);
        Location location = createLocation(borrowedStatus);

        // Set the User and Device object to the BorrowDTO object
        borrowDTO.setUser(user);
        borrowDTO.setDevice(device);
        device.setLocation(location);

        log.info("Converted borrowed status: {}", borrowDTO);
        return borrowDTO;
    }

    private static Location createLocation(BorrowedStatus borrowedStatus) {
        Location location = new Location();
        location.setId(borrowedStatus.getDevice().getLocation().getId());
        location.setCity(borrowedStatus.getDevice().getLocation().getCity());
        location.setName(borrowedStatus.getDevice().getLocation().getName());
        location.setAddress(borrowedStatus.getDevice().getLocation().getAddress());
        return location;
    }

    private static Device createDevice(BorrowedStatus borrowedStatus) {
        Device device = new Device();
        device.setId(borrowedStatus.getDevice().getId());
        device.setType(borrowedStatus.getDevice().getType());
        device.setBrandName(borrowedStatus.getDevice().getBrandName());
        device.setModel(borrowedStatus.getDevice().getModel());
        device.setSerialNumber(borrowedStatus.getDevice().getSerialNumber());
        device.setInvoiceNumber(borrowedStatus.getDevice().getInvoiceNumber());
        return device;
    }

    private static User createUser(BorrowedStatus borrowedStatus) {
        User user = new User();
        user.setId(borrowedStatus.getUser().getId());
        user.setName(borrowedStatus.getUser().getName());
        user.setEmail(borrowedStatus.getUser().getEmail());
        user.setRole(borrowedStatus.getUser().getRole());
        return user;
    }
}