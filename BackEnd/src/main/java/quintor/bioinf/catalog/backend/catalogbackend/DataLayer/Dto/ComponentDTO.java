package quintor.bioinf.catalog.backend.catalogbackend.DataLayer.Dto;

import quintor.bioinf.catalog.backend.catalogbackend.DataLayer.Entities.Specs;

import java.util.List;
import java.util.Map;

public class ComponentDTO {
    private Long id;
    private String name;
    private String brandName;
    private String model;
    private String serialNumber;
    private String invoiceNumber;
    private String locationCity;
    private String locationAddress;
    private String locationName;
    private List<SpecDetail> specs;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getBrandName() {
        return brandName;
    }

    public void setBrandName(String brandName) {
        this.brandName = brandName;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getSerialNumber() {
        return serialNumber;
    }

    public void setSerialNumber(String serialNumber) {
        this.serialNumber = serialNumber;
    }

    public String getInvoiceNumber() {
        return invoiceNumber;
    }

    public void setInvoiceNumber(String invoiceNumber) {
        this.invoiceNumber = invoiceNumber;
    }

    public String getLocationCity() {
        return locationCity;
    }

    public void setLocationCity(String locationCity) {
        this.locationCity = locationCity;
    }

    public String getLocationAddress() {
        return locationAddress;
    }

    public void setLocationAddress(String locationAddress) {
        this.locationAddress = locationAddress;
    }

    public String getLocationName() {
        return locationName;
    }

    public void setLocationName(String locationName) {
        this.locationName = locationName;
    }

    public List<SpecDetail> getSpecs() {
        return specs;
    }

    public void setSpecs(List<SpecDetail> specs) {
        this.specs = specs;
    }


    // Getters and setters omitted for brevity
}
