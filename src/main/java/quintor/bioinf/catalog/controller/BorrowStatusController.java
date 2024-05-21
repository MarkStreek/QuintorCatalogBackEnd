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

    private void logIncomingRequest(HttpServletRequest request) {
        log.info("New incoming request. CLIENT IP: {}, PORT: {}, REQUEST URI: {}",
                request.getRemoteAddr(), request.getRemotePort(), request.getRequestURI());
    }

    @Autowired
    public BorrowStatusController(BorrowedStatusService borrowedStatusService) {
        this.borrowedStatusService = borrowedStatusService;
    }

    @PostMapping
    public ReturnMessage borrowDevice(@RequestBody BorrowRequest borrowRequest, HttpServletRequest request) {
        logIncomingRequest(request);
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

    /**
     * Simple GET endpoint that searches for a borrowedRequest by id.
     * The id is passed as a path variable. And the founded object is
     * converted to a BorrowDTO object.
     *
     * @param id The requested id of the borrowed device
     * @param request The incoming request
     * @return The BorrowDTO object
     */
    @GetMapping("/{id}")
    public BorrowDTO getBorrowStatus(@PathVariable Long id, HttpServletRequest request) {
        logIncomingRequest(request);
        return borrowedStatusService.getBorrowStatus(id);
    }

    /**
     * Get endpoint to get all borrowed devices. All the borrowed devices are converted to a BorrowDTO object.
     * The BorrowDTO's are placed in a list and returned to the client.
     *
     * @return List<BorrowDTO> list of borrowed devices
     */
    @GetMapping()
    public List<BorrowDTO> getAllBorrowStatus(HttpServletRequest request) {
        logIncomingRequest(request);
        return borrowedStatusService.getAllBorrowStatus();
    }

    /**
     * GET endpoint to get all pending borrowed devices.
     * All the pending borrowed devices are converted to a BorrowDTO object.
     *
     * @param request The incoming request
     * @return List of BorrowDTO objects with status approval needed
     */
    @GetMapping("/pending")
    public List<BorrowDTO> getAllPendingBorrowStatus(HttpServletRequest request) {
        logIncomingRequest(request);
        return borrowedStatusService.getAllPendingBorrowStatus();
    }

    /**
     * POST endpoint to approve a borrow request. The borrow request is approved by id.
     * The id is passed as a path variable.
     * The borrow request is approved and the device is now available for use.
     *
     * @param id The id of the borrow request
     * @param request The incoming request
     * @return a ReturnMessage record with status and message
     * @see ReturnMessage
     */
    @PostMapping("/approve/{id}")
    public ReturnMessage approveBorrowRequest(@PathVariable Long id, HttpServletRequest request) {
        logIncomingRequest(request);
        borrowedStatusService.approveBorrowedStatus(id);
        return new ReturnMessage(
                HttpStatus.OK.value(),
                new Date(),
                "Apparaat succesvol goedgekeurd",
                "Het apparaat is succesvol goedgekeurd en is nu beschikbaar voor gebruik");
    }
}
