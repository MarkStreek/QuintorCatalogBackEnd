package quintor.bioinf.catalog.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import quintor.bioinf.catalog.entities.BorrowedStatus;

/**
 * This interface is used to interact with the BorrowedStatus table in the database.
 *
 * @see BorrowedStatus
 */
@Repository
public interface BorrowedStatusRepository extends JpaRepository<BorrowedStatus, Integer> {
}
