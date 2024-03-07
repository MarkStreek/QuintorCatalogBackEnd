package quintor.bioinf.catalog.backend.catalogbackend.DataLayer.Entities;

import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "componentSpecs")
public class ComponentSpecs {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "storage")
    private String storage;

    @OneToMany(mappedBy = "componentSpecs")
    private List<Component> components;

    public Long getId() {
        return id;
    }

    public String getStorage() {
        return storage;
    }

    public void setStorage(String storage) {
        this.storage = storage;
    }

    public List<Component> getComponents() {
        return components;
    }

    public void setComponents(List<Component> components) {
        this.components = components;
    }
}
