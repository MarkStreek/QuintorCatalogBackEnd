package quintor.bioinf.catalog.backend.catalogbackend.DataLayer.Entities;

import jakarta.persistence.*;

@Entity
@Table(name = "components")
public class Component {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "brandName")
    private String brandName;

    @Column(name = "model")
    private String model;

    @Column(name = "serialNumber")
    private String serialNumber;

    @Column(name = "invoiceNumber")
    private String invoiceNumber;

    @ManyToOne
    @JoinColumn(name = "location_id")
    private Location location;

    @ManyToOne
    @JoinColumn(name = "componentSpecs_id")
    private ComponentSpecs componentSpecs;


    public Long getId() {
        return id;
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

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    public ComponentSpecs getComponentSpecs() {
        return componentSpecs;
    }

    public void setComponentSpecs(ComponentSpecs componentSpecs) {
        this.componentSpecs = componentSpecs;
    }
}
