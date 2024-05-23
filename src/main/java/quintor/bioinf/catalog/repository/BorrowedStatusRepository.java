package quintor.bioinf.catalog.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import quintor.bioinf.catalog.entities.BorrowedStatus;
import quintor.bioinf.catalog.entities.Device;
import quintor.bioinf.catalog.entities.User;

import java.util.List;

/**
 * This interface is used to interact with the BorrowedStatus table in the database.
 * <p>
 * The findAllByUser method is added to this interface
 * The existsById method is added to this interface
 *
 * @see BorrowedStatus
 */
@Repository
public interface BorrowedStatusRepository extends JpaRepository<BorrowedStatus, Integer> {

    /**
     * Method that retrieves all the BorrowedStatus from the database that belong to a certain user.
     *
     * @param user The device to which the borrowed status belongs
     * @return A list of all the BorrowedStatus that belong to the given device
     */
    List<BorrowedStatus> findAllByUser(User user);

    /**
     * Method that checks if a BorrowedStatus with the given id exists in the database.
     *
     * @param id The id of the BorrowedStatus
     * @return boolean indicating if the BorrowedStatus exists
     */
    boolean existsById(Long id);
}
