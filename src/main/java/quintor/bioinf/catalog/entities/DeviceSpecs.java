package quintor.bioinf.catalog.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.Objects;

/**
 * This class represents the DeviceSpecs table in the database.
 * <p>
 * The DeviceSpecs table is used to keep track of which device has which specs.
 * The Specs table is very important for the DeviceSpecs table:
 * The specs table contains all the possible specs that a device can have.
 * The DeviceSpecs table is the table that connects the Device table with the Specs table.
 * For every spec that a device has, there is a row in the DeviceSpecs table.
 * In this row, the spec, value of the spec and the device are stored.
 * The spec is stored as an object that points to the Specs table.
 * <p>
 * For example: Component1 has spec1 with value1, spec2 with value2 and spec3 with value3.
 * This means that there are 3 rows in the DeviceSpecs table that point to Component1.
 * And the Specs table contains 3 rows: spec1, spec2 and spec3 and their datatype.
 *
 * @see Specs
 */
@Getter
@Setter
@Entity
@Table(name = "DeviceSpecs")
public class DeviceSpecs {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "device_id")
    private Device device;

    @ManyToOne
    @JoinColumn(name = "specs_id")
    private Specs specs;

    @Column(name = "value")
    private String value;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        DeviceSpecs that = (DeviceSpecs) o;
        return Objects.equals(specs, that.specs) && Objects.equals(value, that.value);
    }

    @Override
    public int hashCode() {
        return Objects.hash(specs, value);
    }
}
