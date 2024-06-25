package quintor.bioinf.catalog.controller;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import quintor.bioinf.catalog.dto.BorrowDTO;
import quintor.bioinf.catalog.dto.BorrowRequest;
import quintor.bioinf.catalog.entities.User;
import quintor.bioinf.catalog.model.ReturnMessage;
import quintor.bioinf.catalog.services.BorrowedStatusService;

import java.util.Date;
import java.util.List;


/**
 * Controller class that handles all incoming requests for the borrow status.
 * The class uses the Logging class to log incoming requests.
 * <p>
 * The controller has the following endpoints:
 * - POST endpoint to create a new borrow request
 * - GET endpoint to get a borrow request by id
 * - GET endpoint to get all borrow requests
 * - GET endpoint to get all pending borrow requests
 * - POST endpoint to approve a borrow request
 * <p>
 * See the individual methods for more information.
 * @see Logging
 */
@RestController
@RequestMapping("/borrowedstatus")
public class BorrowStatusController {

    private final BorrowedStatusService borrowedStatusService;

    @Autowired
    public BorrowStatusController(BorrowedStatusService borrowedStatusService) {
        this.borrowedStatusService = borrowedStatusService;
    }

    /**
     * POST endpoint to create a new borrow request.
     * The incoming request is a BorrowRequest object. The object contains the username and device id.
     * The service class is called with the incoming BorrowRequest object.
     * The request is logged and the borrow request is created.
     * <p>
     * If the request was successful, the return message will contain a status code of 200.
     * If not, the exception handler will catch the exception and return a ReturnMessage.
     *
     * @param borrowRequest The incoming request object (Username and device id)
     * @param request The incoming request
     * @return ReturnMessage object with status and message
     * @see BorrowRequest
     */
    @PostMapping
    public ReturnMessage borrowDevice(@RequestBody BorrowRequest borrowRequest, HttpServletRequest request) {
        Logging.logIncomingRequest(request);
        this.borrowedStatusService.borrowDevice(
                borrowRequest.getUserName(),
                borrowRequest.getDeviceId(),
                borrowRequest.getDescription());

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
        Logging.logIncomingRequest(request);
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
        Logging.logIncomingRequest(request);
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
        Logging.logIncomingRequest(request);
        return borrowedStatusService.getAllPendingBorrowStatus();
    }


    /**
     * GET endpoint to get all users.
     * All the users are converted to a User object.
     *
     * @param request The incoming request
     * @return List of User objects
     */
    @GetMapping("/users")
    public List<User> getAllUsers(HttpServletRequest request) {
        Logging.logIncomingRequest(request);
        return borrowedStatusService.getAllUsers();
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
    @PreAuthorize("hasRole('ROLE_CTO')")
    public ReturnMessage approveBorrowRequest(@PathVariable Long id, HttpServletRequest request) {
        Logging.logIncomingRequest(request);
        borrowedStatusService.approveBorrowedStatus(id);
        return new ReturnMessage(
                HttpStatus.OK.value(),
                new Date(),
                "Apparaat succesvol goedgekeurd",
                "Het apparaat is succesvol goedgekeurd en is nu beschikbaar voor gebruik");
    }

    @PostMapping("/delete/{id}")
    public ReturnMessage deleteBorrowRequest(@PathVariable Long id, HttpServletRequest request) {
        Logging.logIncomingRequest(request);
        borrowedStatusService.deleteBorrowedStatus(id);
        return new ReturnMessage(
                HttpStatus.OK.value(),
                new Date(),
                "Uitleenverzoek succesvol verwijderd",
                "Het uitleenverzoek is succesvol verwijderd uit de database");
    }

}
