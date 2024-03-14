package quintor.bioinf.catalog.backend.catalogbackend.DataLayer.Entities;

import jakarta.persistence.*;

/**
 * This class represents the Specs table in the database.
 * <p>
 * The Specs table only stores the name and datatype of the specs.
 * The ComponentSpecs table is used to store a value for the specs.
 *
 * @see ComponentSpecs
 */
@Entity
@Table(name = "specs")
public class Specs {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "datatype")
    private String datatype;

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDatatype() {
        return datatype;
    }

    public void setDatatype(String datatype) {
        this.datatype = datatype;
    }

}
