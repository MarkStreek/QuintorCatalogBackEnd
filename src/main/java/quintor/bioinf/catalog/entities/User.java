package quintor.bioinf.catalog.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
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
    @NotNull
    @NotEmpty
    private String name;

    @Column(name = "email")
    @Email
    @NotNull
    @NotEmpty
    private String email;

    @Column(name = "password")
    @NotNull
    @NotEmpty
    @JsonIgnore
    private String password;
}
