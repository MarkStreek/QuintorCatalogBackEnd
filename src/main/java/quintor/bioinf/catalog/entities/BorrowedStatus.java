package quintor.bioinf.catalog.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

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

    @OneToOne
    @JoinColumn(name = "user_id")
    private User user;
}
