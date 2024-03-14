package quintor.bioinf.catalog.backend.catalogbackend.DataLayer.Entities;

import jakarta.persistence.*;

/**
 * This class represents the BorrowedStatus table in the database.
 * <p>
 * The BorrowedStatus table is used to keep track of which user has borrowed which component.
 *
 * @see Component
 * @see User
 */
@Entity
@Table(name = "borrowedStatus")
public class BorrowedStatus {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "component_id")
    private Component component;

    @OneToOne
    @JoinColumn(name = "user_id")
    private User user;

    public Long getId() {
        return id;
    }

    public Component getComponent() {
        return component;
    }

    public void setComponent(Component component) {
        this.component = component;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
