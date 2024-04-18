package quintor.bioinf.catalog.dto;

import lombok.Getter;
import lombok.Setter;

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
