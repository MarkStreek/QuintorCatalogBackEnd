package quintor.bioinf.catalog.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.Objects;

/**
 * This class represents the Location table in the database.
 * <p>
 * The Location table is used to keep track of the locations of the devices.
 *
 * @see Device
 */
@Getter
@Setter
@Entity
@Table(name = "locations")
public class Location {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "city")
    private String city;

    @Column(name = "address")
    private String address;

    @OneToMany(mappedBy = "location")
    private List<Device> devices;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Location location = (Location) o;
        return Objects.equals(city, location.city) && Objects.equals(address, location.address);
    }

    @Override
    public int hashCode() {
        return Objects.hash(city, address);
    }

}
