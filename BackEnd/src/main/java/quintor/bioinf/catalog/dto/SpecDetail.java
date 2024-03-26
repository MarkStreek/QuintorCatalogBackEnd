package quintor.bioinf.catalog.dto;

public class SpecDetail {
    private String specName;
    private String value;
    private String datatype;

    public SpecDetail(String specName, String value, String datatype) {
        this.specName = specName;
        this.value = value;
        this.datatype = datatype;
    }

    // Getters en setters
    public String getSpecName() {
        return specName;
    }

    public void setSpecName(String spec) {
        this.specName = spec;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getDatatype() {
        return datatype;
    }

    public void setDatatype(String datatype) {
        this.datatype = datatype;
    }
}
