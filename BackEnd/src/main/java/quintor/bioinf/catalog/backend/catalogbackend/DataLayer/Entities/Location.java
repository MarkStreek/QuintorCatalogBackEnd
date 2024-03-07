package quintor.bioinf.catalog.backend.catalogbackend.DataLayer.Entities;

import jakarta.persistence.*;

import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "locations")
public class Location {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name")
    private String city;

    @Column(name = "address")
    private String address;

    @OneToMany(mappedBy = "location")
    private List<Component> components;

    public Long getId() {
        return id;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String name) {
        this.city = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public List<Component> getComponents() {
        return components;
    }

    public void setComponents(List<Component> components) {
        this.components = components;
    }

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
