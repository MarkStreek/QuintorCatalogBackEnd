package quintor.bioinf.catalog.backend.catalogbackend.DataLayer.Repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import quintor.bioinf.catalog.backend.catalogbackend.DataLayer.Entities.BorrowedStatus;

/**
 * This interface is used to interact with the BorrowedStatus table in the database.
 *
 * @see BorrowedStatus
 */
@Repository
public interface BorrowedStatusRepository extends CrudRepository<BorrowedStatus, Integer> {
}
