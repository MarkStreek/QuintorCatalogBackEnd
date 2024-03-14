package quintor.bioinf.catalog.backend.catalogbackend.DataLayer.Entities;

import jakarta.persistence.*;

import java.util.Objects;

/**
 * This class represents the BorrowedStatus table in the database.
 * <p>
 * The Component table is the main table in the database,
 * it contains all the information about the components.
 * It holds certain objects that points to other tables in the database:
 *  - Location
 *  - BorrowedStatus
 *
 * @see Location
 * @see BorrowedStatus
 *
 */
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Component component = (Component) o;
        return Objects.equals(name, component.name) && Objects.equals(brandName, component.brandName) && Objects.equals(model, component.model) && Objects.equals(serialNumber, component.serialNumber) && Objects.equals(invoiceNumber, component.invoiceNumber) && Objects.equals(location, component.location);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, brandName, model, serialNumber, invoiceNumber, location);
    }
}
