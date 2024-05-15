package quintor.bioinf.catalog.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;

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
