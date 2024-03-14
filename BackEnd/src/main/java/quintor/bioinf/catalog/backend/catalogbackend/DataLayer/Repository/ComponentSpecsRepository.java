package quintor.bioinf.catalog.backend.catalogbackend.DataLayer.Repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import quintor.bioinf.catalog.backend.catalogbackend.DataLayer.Entities.Component;
import quintor.bioinf.catalog.backend.catalogbackend.DataLayer.Entities.ComponentSpecs;

import java.util.List;

@Repository
public interface ComponentSpecsRepository extends CrudRepository<ComponentSpecs, Long> {

    List<ComponentSpecs> findByComponent(Component component);
}
