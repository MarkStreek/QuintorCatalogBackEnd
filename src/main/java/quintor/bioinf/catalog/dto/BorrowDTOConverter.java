package quintor.bioinf.catalog.dto;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import quintor.bioinf.catalog.entities.BorrowedStatus;
import quintor.bioinf.catalog.entities.Device;
import quintor.bioinf.catalog.entities.User;
import quintor.bioinf.catalog.repository.UserRepository;

import java.util.function.Function;

@Service
public class BorrowDTOConverter implements Function<BorrowedStatus, BorrowDTO> {

    private static final Logger log = LoggerFactory.getLogger(BorrowDTOConverter.class);
    private final UserRepository userRepository;

    @Autowired
    public BorrowDTOConverter(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    /**
     * Method that converts a BorrowedStatus object to a BorrowDTO object.
     * @param borrowedStatus the function argument
     * @return
     */
    @Override
    public BorrowDTO apply(BorrowedStatus borrowedStatus) {
        BorrowDTO borrowDTO = new BorrowDTO();
        borrowDTO.setId(borrowedStatus.getId());
        User user = borrowedStatus.getUser();
        borrowDTO.setUserName(user.getName());
        borrowDTO.setDeviceId(Math.toIntExact(borrowedStatus.getDevice().getId()));
        log.info("Converted borrowed status: {}", borrowDTO);
        return borrowDTO;
    }
}
