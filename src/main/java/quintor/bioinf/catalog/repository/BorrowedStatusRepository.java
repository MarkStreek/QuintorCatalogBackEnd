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
 *
 * @see BorrowedStatus
 */
@Repository
public interface BorrowedStatusRepository extends JpaRepository<BorrowedStatus, Integer> {

    List<BorrowedStatus> findAllByUser(User user);

    boolean existsById(Long id);
}
