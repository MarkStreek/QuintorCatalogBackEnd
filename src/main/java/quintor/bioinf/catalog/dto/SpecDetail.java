package quintor.bioinf.catalog.dto;

import lombok.Getter;
import lombok.Setter;

/**
 * Class that represents a SpecDetail object.
 * It is a sort of DTO object that is used to represent a SpecDetail object.
 * It contains the specName, value and datatype of a SpecDetail object.
 */
@Setter
@Getter
public class SpecDetail {
    private String specName;
    private String value;
    private String datatype;

    public SpecDetail(String specName, String value, String datatype) {
        this.specName = specName;
        this.value = value;
        this.datatype = datatype;
    }

}
