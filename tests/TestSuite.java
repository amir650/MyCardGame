
import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@RunWith(Suite.class)
@Suite.SuiteClasses({TestDeck.class,
                     TestFiveCardHandComparison.class,
                     TestHoldemHandComparison.class,
                     TestFiveCardHandIdentification.class
                     })
public class TestSuite {
}