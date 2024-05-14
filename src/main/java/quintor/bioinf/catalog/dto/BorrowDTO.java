package quintor.bioinf.catalog.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;


@Getter
@Setter
public class BorrowDTO {

    private long id;

    @NotNull
    @NotEmpty
    private String userName;

    @NotNull
    @NotEmpty
    @Length(min = 1, max = 6)
    private int deviceId;
}