package quintor.bioinf.catalog.controller;

import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import quintor.bioinf.catalog.dto.BorrowDTO;
import quintor.bioinf.catalog.entities.BorrowedStatus;
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
    public ReturnMessage borrowDevice(@RequestBody BorrowDTO borrowDTO) {


        borrowedStatusService.borrowDevice(borrowDTO);

        return new ReturnMessage(
                HttpStatus.OK.value(),
                new Date(),
                "Device borrowed",
                "Device borrowed");
    }
}
