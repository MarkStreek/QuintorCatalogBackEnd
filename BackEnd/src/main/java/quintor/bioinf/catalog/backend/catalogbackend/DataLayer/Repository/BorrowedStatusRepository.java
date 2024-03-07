package quintor.bioinf.catalog.backend.catalogbackend.DataLayer.Repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import quintor.bioinf.catalog.backend.catalogbackend.DataLayer.Entities.BorrowedStatus;

@Repository
public interface BorrowedStatusRepository extends CrudRepository<BorrowedStatus, Integer> {
}
