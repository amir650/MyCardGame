import org.junit.Test;

import java.util.stream.IntStream;

import static org.junit.Assert.assertEquals;

public class TestDeck {

    @Test
    public void testDeckSize() {
        final Deck deck = Deck.newUnshuffledSingleDeck();
        assertEquals(deck.size(), 52);
        IntStream.range(0, 52).forEach(i->deck.deal());
        assertEquals(deck.size(), 0);
    }

}