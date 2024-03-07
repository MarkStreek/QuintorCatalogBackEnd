package quintor.bioinf.catalog.backend.catalogbackend.DataLayer.Repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import quintor.bioinf.catalog.backend.catalogbackend.DataLayer.Entities.Location;

@Repository
public interface LocationRepository extends CrudRepository<Location, Integer> {

    Location findByAddress(String address);
}
