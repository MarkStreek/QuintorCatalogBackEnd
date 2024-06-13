package quintor.bioinf.catalog.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;
import java.util.List;

/**
 * Data Transfer Object for the Device requests
 * An incoming new device request will be converted to this object.
 * The controller will validate the incoming request and pass it to the service.
 * <p>
 * Getter and Setter annotations are used to generate the getters and setters for the fields.
 * The fields are validated with the NotNull, NotEmpty, Length and Pattern annotations.
 * The fields are also checked for the length of the input.
 * The specs field is a list of SpecDetail objects.
 */
@Setter
@Getter
public class DeviceDTO {
    
    private Long id;

    @NotNull
    @NotEmpty
    @Length(min = 1, max = 50, message = "Type moet tussen de 1 en 50 karakters lang zijn")
    private String type;

    @NotNull
    @NotEmpty
    @Length(min = 1, max = 50, message = "Merk moet tussen de 1 en 50 karakters lang zijn")
    private String brandName;

    @NotNull
    @NotEmpty
    @Length(min = 1, max = 50, message = "Model moet tussen de 1 en 50 karakters lang zijn")
    private String model;

    @NotNull
    @NotEmpty(message = "Serienummer mag niet leeg zijn")
    @Length(min = 3, max = 50, message = "Serienummer moet tussen de 3 en 50 karakters lang zijn")
    private String serialNumber;

    @NotNull
    @NotEmpty
    @Length(min = 3, max = 50)
    private String invoiceNumber;

    @NotNull
    @NotEmpty
    @Pattern(regexp = "^[a-zA-Z ]*$", message = "Locatie stad mag alleen letters bevatten")
    private String locationCity;

    @NotNull
    @NotEmpty
    private String locationAddress;

    @NotNull
    @NotEmpty
    private String locationName;

    @NotNull(message = "Specificaties mogen niet leeg zijn")
    @NotEmpty(message = "Specificaties mogen niet leeg zijn")
    private List<SpecDetail> specs;

}
