package quintor.bioinf.catalog.backend.catalogbackend.DataLayer.Entities;

import jakarta.persistence.*;
import java.util.Objects;

/**
 * This class represents the ComponentSpecs table in the database.
 * <p>
 * The ComponentSpecs table is used to keep track of which component has which specs.
 * The Specs table is very important for the ComponentSpecs table:
 * The specs table contains all the possible specs that a component can have.
 * The ComponentSpecs table is the table that connects the Component table with the Specs table.
 * For every spec that a component has, there is a row in the ComponentSpecs table.
 * In this row, the spec, value of the spec and the component are stored.
 * The spec is stored as an object that points to the Specs table.
 * <p>
 * For example: Component1 has spec1 with value1, spec2 with value2 and spec3 with value3.
 * This means that there are 3 rows in the ComponentSpecs table that point to Component1.
 * And the Specs table contains 3 rows: spec1, spec2 and spec3 and their datatype.
 *
 * @see Specs
 */
@Entity
@Table(name = "componentSpecs")
public class ComponentSpecs {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "component_id")
    private Component component;

    @ManyToOne
    @JoinColumn(name = "specs_id")
    private Specs specs;

    @Column(name = "value")
    private String value;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Component getComponent() {
        return component;
    }

    public void setComponent(Component component) {
        this.component = component;
    }

    public Specs getSpecs() {
        return specs;
    }

    public void setSpecs(Specs specs) {
        this.specs = specs;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ComponentSpecs that = (ComponentSpecs) o;
        return Objects.equals(specs, that.specs) && Objects.equals(value, that.value);
    }

    @Override
    public int hashCode() {
        return Objects.hash(specs, value);
    }
}
