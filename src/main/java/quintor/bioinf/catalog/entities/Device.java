package quintor.bioinf.catalog.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
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

    @Column(name = "type")
    private String type;

    @Column(name = "brand_name")
    @NotEmpty
    @NotNull
    private String brandName;

    @Column(name = "model")
    private String model;

    @Column(name = "serial_number")
    private String serialNumber;

    @Column(name = "invoice_number")
    private String invoiceNumber;

    @ManyToOne
    @JoinColumn(name = "location_id")
    private Location location;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Device device = (Device) o;
        return Objects.equals(type, device.type) && Objects.equals(brandName, device.brandName) && Objects.equals(model, device.model) && Objects.equals(serialNumber, device.serialNumber) && Objects.equals(invoiceNumber, device.invoiceNumber) && Objects.equals(location, device.location);
    }

    @Override
    public int hashCode() {
        return Objects.hash(type, brandName, model, serialNumber, invoiceNumber, location);
    }

}
