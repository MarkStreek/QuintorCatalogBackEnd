package quintor.bioinf.catalog.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;
import java.util.List;

@Setter
@Getter
public class DeviceDTO {

    private Long id;

    @NotNull
    @NotEmpty
    private String type;

    @NotNull
    @NotEmpty
    private String brandName;

    @NotNull
    @NotEmpty
    private String model;

    @NotNull
    @NotEmpty
    @Length(min = 3, max = 50)
    private String serialNumber;

    @NotNull
    @NotEmpty
    @Length(min = 3, max = 50)
    private String invoiceNumber;

    @NotNull
    @NotEmpty
    private String locationCity;

    @NotEmpty
    private String locationAddress;

    @NotNull
    @NotEmpty
    private String locationName;

    @NotEmpty
    private List<SpecDetail> specs;

}
