package quintor.bioinf.catalog.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

/**
 * This class represents the Specs table in the database.
 * <p>
 * The Specs table only stores the name and datatype of the specs.
 * The DeviceSpecs table is used to store a value for the specs.
 *
 * @see DeviceSpecs
 */
@Getter
@Setter
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

}
