import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class DeckTest {

    @Test
    public void testDeckSize() {
        final Deck deck = Deck.newUnshuffledSingleDeck();
        assertEquals(deck.size(), 52);
    }

}