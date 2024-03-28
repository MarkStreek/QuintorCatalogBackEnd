package quintor.bioinf.catalog.dummy;

import java.util.Arrays;
import java.util.List;

/**
 * DummyRepository is a simple repository that returns some dummy data
 */
public class DummyRepository {
    // Hardcode list of DummyData to simulate database data.
    private final List<DummyData> dummyData = Arrays.asList(
        new DummyData(1, "dummy1", "value1"),
        new DummyData(2, "dummy2", "value2"),
        new DummyData(3, "dummy3", "value3"),
        new DummyData(4, "dummy4", "value4"),
        new DummyData(5, "dummy5", "value5")
    );

    /**
     * Fetch all available dummy data
     * @return List of dummy data
     */
    public List<DummyData> getDummyData() {
        return dummyData;
    }
}