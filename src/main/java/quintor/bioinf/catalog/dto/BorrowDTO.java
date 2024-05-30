package quintor.bioinf.catalog.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import quintor.bioinf.catalog.entities.Device;
import quintor.bioinf.catalog.entities.User;

import java.util.Date;

/**
 * Data Transfer Object for the Borrow requests
 * It contains the User and Device object.
 * The BorrowDTOConverter is used to convert a database record to this object.
 * <p>
 * Getter and Setter annotations are used to generate the getters and setters for the fields.
 */
@Getter
@Setter
public class BorrowDTO {

    private long id;

    @NotNull
    @NotEmpty
    private String status;

    @NotNull
    @NotEmpty
    private Date borrowDate;

    @NotNull
    @NotEmpty
    private User user;

    @NotNull
    @NotEmpty
    private Device device;

    @NotNull
    @NotEmpty
    private String description;
}