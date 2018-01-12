
import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@RunWith(Suite.class)
@Suite.SuiteClasses({TestDeck.class,
                     TestHandComparison.class,
                     TestHandIdentification.class
                     })
public class TestSuite {
}