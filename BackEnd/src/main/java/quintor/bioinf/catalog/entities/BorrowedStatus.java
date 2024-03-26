package quintor.bioinf.catalog.entities;

import jakarta.persistence.*;

/**
 * This class represents the BorrowedStatus table in the database.
 * <p>
 * The BorrowedStatus table is used to keep track of which user has borrowed which device.
 *
 * @see Device
 * @see User
 */
@Entity
@Table(name = "borrowedStatus")
public class BorrowedStatus {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "Device_id")
    private Device device;

    @OneToOne
    @JoinColumn(name = "user_id")
    private User user;

    public Long getId() {
        return id;
    }

    public Device getComponent() {
        return device;
    }

    public void setComponent(Device device) {
        this.device = device;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
