package quintor.bioinf.catalog.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.Objects;

/**
 * This class represents the BorrowedStatus table in the database.
 * <p>
 * The Device table is the main table in the database,
 * it contains all the information about the Devices.
 * It holds certain objects that points to other tables in the database:
 *  - Location
 *  - BorrowedStatus
 *
 * @see Location
 * @see BorrowedStatus
 *
 */
@Getter
@Setter
@Entity
@Table(name = "devices")
public class Device {

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Device device = (Device) o;
        return Objects.equals(name, device.name) && Objects.equals(brandName, device.brandName) && Objects.equals(model, device.model) && Objects.equals(serialNumber, device.serialNumber) && Objects.equals(invoiceNumber, device.invoiceNumber) && Objects.equals(location, device.location);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, brandName, model, serialNumber, invoiceNumber, location);
    }

}
