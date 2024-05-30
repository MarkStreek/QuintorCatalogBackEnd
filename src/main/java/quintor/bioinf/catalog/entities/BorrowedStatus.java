package quintor.bioinf.catalog.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

/**
 * This class represents the BorrowedStatus table in the database.
 * <p>
 * The BorrowedStatus table is used to keep track of which user has borrowed which device.
 *
 * @see Device
 * @see User
 */
@Getter
@Setter
@Entity
@Table(name = "borrowed_status")
public class BorrowedStatus {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "device_id")
    private Device device;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @Column(name = "created_borrowed_date")
    private Date createdBorrowedDate;

    @NotNull
    @NotEmpty
    @Column(name = "status")
    private String status;

    @NotNull
    @NotEmpty
    @Column(name = "description")
    private String description;

}
