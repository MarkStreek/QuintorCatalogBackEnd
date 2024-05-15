package quintor.bioinf.catalog.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import quintor.bioinf.catalog.entities.Device;
import quintor.bioinf.catalog.entities.User;


@Getter
@Setter
public class BorrowDTO {

    private long id;

    @NotNull
    @NotEmpty
    private User user;

    @NotNull
    @NotEmpty
    private Device device;
}