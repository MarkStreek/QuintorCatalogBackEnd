package quintor.bioinf.catalog.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

/**
 * This class represents the User table in the database.
 *
 * @see BorrowedStatus
 * @see Device
 */
@Getter
@Setter
@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "role")
    private String role;
}
