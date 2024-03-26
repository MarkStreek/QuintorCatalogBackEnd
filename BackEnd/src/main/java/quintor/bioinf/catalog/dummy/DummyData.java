package quintor.bioinf.catalog.dummy;

/**
 * DummyData is a simple model class that represents some dummy data
 */
public class DummyData {
    private int id;
    private String name;
    private String value;

    /**
     * Constructor to create a new DummyData
     *
     * @param id
     * @param name
     * @param value
     */
    public DummyData(int id, String name, String value) {
        this.id = id;
        this.name = name;
        this.value = value;
    }

    // getters and setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}

