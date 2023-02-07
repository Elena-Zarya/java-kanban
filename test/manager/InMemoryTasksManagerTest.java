package manager;

import org.junit.jupiter.api.BeforeEach;

public class InMemoryTasksManagerTest extends TaskManagerTest {

    @BeforeEach
    public void setManagerTest() {
        manager = Managers.getDefault();
    }
}
