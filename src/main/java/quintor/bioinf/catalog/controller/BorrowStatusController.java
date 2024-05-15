package quintor.bioinf.catalog.controller;

import jakarta.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import quintor.bioinf.catalog.dto.BorrowDTO;
import quintor.bioinf.catalog.dto.BorrowRequest;
import quintor.bioinf.catalog.services.BorrowedStatusService;

import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/borrowedstatus")
public class BorrowStatusController {

    private static final Logger log = LoggerFactory.getLogger(BorrowStatusController.class);
    private final BorrowedStatusService borrowedStatusService;

    @Autowired
    public BorrowStatusController(BorrowedStatusService borrowedStatusService) {
        this.borrowedStatusService = borrowedStatusService;
    }

    @PostMapping
    public ReturnMessage borrowDevice(@RequestBody BorrowRequest borrowRequest, HttpServletRequest request) {
        // Log the incoming request
        log.info("New incoming request. CLIENT IP: {}, PORT: {}, REQUEST URI: {}",
                request.getRemoteAddr(), request.getRemotePort(), request.getRequestURI());

        // Create a new borrow request with the incoming "BorrowRequest" object
        // BorrowRequest is a simple (DTO) object that contains the username and device id
        this.borrowedStatusService.borrowDevice(
                borrowRequest.getUserName(),
                borrowRequest.getDeviceId());

        // Return a new (OK) message to the client
        return new ReturnMessage(
                HttpStatus.OK.value(),
                new Date(),
                "Apparaat succesvol uitgeleend",
                "Een nieuw apparaat is succesvol toegevoegd aan de database en " +
                        "alle checks zijn uitgevoerd");
    }

    @GetMapping("/{id}")
    public BorrowDTO getBorrowStatus(@PathVariable Long id) {
        return borrowedStatusService.getBorrowStatus(id);
    }

    /**
     * Get endpoint to get all borrowed devices. All the borrowed devices are converted to a BorrowDTO object.
     * The BorrowDTO's are placed in a list and returned to the client.
     *
     * @return List<BorrowDTO> list of borrowed devices
     */
    @GetMapping()
    public List<BorrowDTO> getAllBorrowStatus() {
        return borrowedStatusService.getAllBorrowStatus();
    }
}
