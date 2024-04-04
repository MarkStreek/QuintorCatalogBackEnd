package quintor.bioinf.catalog.dto;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;
import java.util.List;

@Setter
@Getter
public class DeviceDTO {
    private Long id;
    private String type;
    private String brandName;
    private String model;
    @Length(min = 1, max = 15)
    private String serialNumber;
    private String invoiceNumber;
    private String locationCity;
    private String locationAddress;
    private String locationName;
    private List<SpecDetail> specs;

}
