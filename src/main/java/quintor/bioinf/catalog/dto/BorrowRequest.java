package quintor.bioinf.catalog.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;

/**
 * Incoming request for borrowing a device. This request is used to specify the userName and deviceId.
 * It's difficult to pass a BorrowDTO object to the BorrowController, therefore this object is used.
 * This class is also used to validate the incoming request.
 * <p>
 * Getter annotation is used to generate the getters for the fields.
 * NotEmpty and NotNull annotations are used to validate the fields.
 * Pattern annotation is used to validate the userName and deviceId.
 * The userName should only contain letters and the deviceId should only contain numbers.
 * If the validation fails, the exception handler will come into action.
 *
 * @see quintor.bioinf.catalog.controller.BorrowStatusController
 */
@Getter
public class BorrowRequest {

    @NotEmpty
    @NotNull
    @Pattern(regexp = "^[a-zA-Z ]*$", message = "Naam mag alleen letters bevatten")
    private String userName;

    @NotEmpty
    @NotNull
    @Pattern(regexp = "^[0-9]{1,7}$", message = "DeviceId mag alleen cijfers bevatten")
    private int deviceId;
}
