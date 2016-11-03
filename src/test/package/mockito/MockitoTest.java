package mockito;

import org.mockito.MockitoAnnotations;
import org.testng.annotations.BeforeClass;

public class MockitoTest {
    @BeforeClass
    public void initMocks() {
        MockitoAnnotations.initMocks(this);
    }
}
