package quintor.bioinf.catalog.backend.catalogbackend.DataLayer.Dto;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;
import java.util.List;

@Setter
@Getter
public class ComponentDTO {
    private Long id;
    private String name;
    private String brandName;
    private String model;
    @Length(min = 1, max = 255)
    private String serialNumber;
    private String invoiceNumber;
    private String locationCity;
    private String locationAddress;
    private String locationName;
    private List<SpecDetail> specs;

}
