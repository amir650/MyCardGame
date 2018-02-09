import com.cardgames.cards.Card;
import com.cardgames.cards.Rank;
import com.cardgames.cards.Suit;
import com.cardgames.poker.PokerHandComparator;
import com.cardgames.poker.fivecardpoker.FiveCardPokerHand;
import org.junit.Test;

import java.util.Optional;

import static org.junit.Assert.assertEquals;

public class TestFiveCardHandComparison {

    @Test
    public void testPairVsPair() {
        final FiveCardPokerHand.Builder builder = new FiveCardPokerHand.Builder();

        builder.addCard(Optional.of(new Card(Rank.ACE, Suit.SPADES)));
        builder.addCard(Optional.of(new Card(Rank.ACE, Suit.DIAMONDS)));
        builder.addCard(Optional.of(new Card(Rank.TEN, Suit.SPADES)));
        builder.addCard(Optional.of(new Card(Rank.JACK, Suit.DIAMONDS)));
        builder.addCard(Optional.of(new Card(Rank.QUEEN, Suit.HEARTS)));

        final FiveCardPokerHand hand = builder.build();

        final FiveCardPokerHand.Builder builder2 = new FiveCardPokerHand.Builder();

        builder2.addCard(Optional.of(new Card(Rank.ACE, Suit.SPADES)));
        builder2.addCard(Optional.of(new Card(Rank.JACK, Suit.DIAMONDS)));
        builder2.addCard(Optional.of(new Card(Rank.TEN, Suit.SPADES)));
        builder2.addCard(Optional.of(new Card(Rank.JACK, Suit.CLUBS)));
        builder2.addCard(Optional.of(new Card(Rank.QUEEN, Suit.HEARTS)));

        final FiveCardPokerHand otherHand = builder2.build();
        final PokerHandComparator comparator = new PokerHandComparator();

        final int result = comparator.compare(hand, otherHand);

        assertEquals(result, 1);
    }

    @Test
    public void testTwoPairVsTwoPair() {
        final FiveCardPokerHand.Builder builder = new FiveCardPokerHand.Builder();

        builder.addCard(Optional.of(new Card(Rank.ACE, Suit.SPADES)));
        builder.addCard(Optional.of(new Card(Rank.ACE, Suit.DIAMONDS)));
        builder.addCard(Optional.of(new Card(Rank.JACK, Suit.SPADES)));
        builder.addCard(Optional.of(new Card(Rank.JACK, Suit.DIAMONDS)));
        builder.addCard(Optional.of(new Card(Rank.QUEEN, Suit.HEARTS)));

        final FiveCardPokerHand hand = builder.build();

        final FiveCardPokerHand.Builder builder2 = new FiveCardPokerHand.Builder();

        builder2.addCard(Optional.of(new Card(Rank.ACE, Suit.SPADES)));
        builder2.addCard(Optional.of(new Card(Rank.ACE, Suit.DIAMONDS)));
        builder2.addCard(Optional.of(new Card(Rank.KING, Suit.SPADES)));
        builder2.addCard(Optional.of(new Card(Rank.KING, Suit.CLUBS)));
        builder2.addCard(Optional.of(new Card(Rank.QUEEN, Suit.HEARTS)));

        final FiveCardPokerHand otherHand = builder2.build();
        final PokerHandComparator comparator = new PokerHandComparator();

        final int result = comparator.compare(hand, otherHand);

        assertEquals(result, -1);
    }

    @Test
    public void testFlushVsFlush() {
        final FiveCardPokerHand.Builder builder = new FiveCardPokerHand.Builder();

        builder.addCard(Optional.of(new Card(Rank.TWO, Suit.SPADES)));
        builder.addCard(Optional.of(new Card(Rank.NINE, Suit.SPADES)));
        builder.addCard(Optional.of(new Card(Rank.JACK, Suit.SPADES)));
        builder.addCard(Optional.of(new Card(Rank.QUEEN, Suit.SPADES)));
        builder.addCard(Optional.of(new Card(Rank.KING, Suit.SPADES)));

        final FiveCardPokerHand hand = builder.build();

        final FiveCardPokerHand.Builder builder2 = new FiveCardPokerHand.Builder();

        builder2.addCard(Optional.of(new Card(Rank.TWO, Suit.HEARTS)));
        builder2.addCard(Optional.of(new Card(Rank.NINE, Suit.HEARTS)));
        builder2.addCard(Optional.of(new Card(Rank.FIVE, Suit.HEARTS)));
        builder2.addCard(Optional.of(new Card(Rank.KING, Suit.HEARTS)));
        builder2.addCard(Optional.of(new Card(Rank.THREE, Suit.HEARTS)));

        final FiveCardPokerHand otherHand = builder2.build();
        final PokerHandComparator comparator = new PokerHandComparator();

        final int result = comparator.compare(hand, otherHand);

        assertEquals(result, 1);

    }

    @Test
    public void testSetOverSet() {
        //[TWO of DIAMONDS, TWO of CLUBS, TWO of SPADES, FIVE of HEARTS, QUEEN of SPADES] vs
        //[THREE of DIAMONDS, EIGHT of DIAMONDS, EIGHT of CLUBS, EIGHT of SPADES, NINE of DIAMONDS]
        final FiveCardPokerHand.Builder builder = new FiveCardPokerHand.Builder();
        builder.addCard(Optional.of(new Card(Rank.TWO, Suit.DIAMONDS)));
        builder.addCard(Optional.of(new Card(Rank.TWO, Suit.CLUBS)));
        builder.addCard(Optional.of(new Card(Rank.TWO, Suit.SPADES)));
        builder.addCard(Optional.of(new Card(Rank.FIVE, Suit.HEARTS)));
        builder.addCard(Optional.of(new Card(Rank.QUEEN, Suit.SPADES)));

        final FiveCardPokerHand hand = builder.build();

        final FiveCardPokerHand.Builder builder2 = new FiveCardPokerHand.Builder();
        builder2.addCard(Optional.of(new Card(Rank.THREE, Suit.DIAMONDS)));
        builder2.addCard(Optional.of(new Card(Rank.EIGHT, Suit.DIAMONDS)));
        builder2.addCard(Optional.of(new Card(Rank.EIGHT, Suit.CLUBS)));
        builder2.addCard(Optional.of(new Card(Rank.EIGHT, Suit.SPADES)));
        builder2.addCard(Optional.of(new Card(Rank.NINE, Suit.DIAMONDS)));

        final FiveCardPokerHand otherHand = builder2.build();
        final PokerHandComparator comparator = new PokerHandComparator();
        final int result = comparator.compare(hand, otherHand);

        assertEquals(result, -1);

    }

    @Test
    public void testQuadsOverQuads() {
        //[TWO of DIAMONDS, TWO of CLUBS, TWO of HEARTS, TWO of SPADES, EIGHT of DIAMONDS] vs
        //[SIX of DIAMONDS, SIX of CLUBS, SIX of HEARTS, SIX of SPADES, NINE of HEARTS]

        final FiveCardPokerHand.Builder builder = new FiveCardPokerHand.Builder();
        builder.addCard(Optional.of(new Card(Rank.TWO, Suit.DIAMONDS)));
        builder.addCard(Optional.of(new Card(Rank.TWO, Suit.CLUBS)));
        builder.addCard(Optional.of(new Card(Rank.TWO, Suit.SPADES)));
        builder.addCard(Optional.of(new Card(Rank.TWO, Suit.HEARTS)));
        builder.addCard(Optional.of(new Card(Rank.EIGHT, Suit.SPADES)));

        final FiveCardPokerHand hand = builder.build();

        final FiveCardPokerHand.Builder builder2 = new FiveCardPokerHand.Builder();
        builder2.addCard(Optional.of(new Card(Rank.SIX, Suit.DIAMONDS)));
        builder2.addCard(Optional.of(new Card(Rank.SIX, Suit.CLUBS)));
        builder2.addCard(Optional.of(new Card(Rank.SIX, Suit.HEARTS)));
        builder2.addCard(Optional.of(new Card(Rank.SIX, Suit.SPADES)));
        builder2.addCard(Optional.of(new Card(Rank.NINE, Suit.HEARTS)));

        final FiveCardPokerHand otherHand = builder2.build();
        final PokerHandComparator comparator = new PokerHandComparator();
        final int result = comparator.compare(hand, otherHand);
        assertEquals(result, -1);
    }

    @Test
    public void testHighCardvsHighCard() {
        final FiveCardPokerHand.Builder builder = new FiveCardPokerHand.Builder();

        builder.addCard(Optional.of(new Card(Rank.TWO, Suit.SPADES)));
        builder.addCard(Optional.of(new Card(Rank.NINE, Suit.HEARTS)));
        builder.addCard(Optional.of(new Card(Rank.JACK, Suit.CLUBS)));
        builder.addCard(Optional.of(new Card(Rank.QUEEN, Suit.DIAMONDS)));
        builder.addCard(Optional.of(new Card(Rank.KING, Suit.HEARTS)));

        final FiveCardPokerHand hand = builder.build();

        final FiveCardPokerHand.Builder builder2 = new FiveCardPokerHand.Builder();

        builder2.addCard(Optional.of(new Card(Rank.FIVE, Suit.HEARTS)));
        builder2.addCard(Optional.of(new Card(Rank.NINE, Suit.DIAMONDS)));
        builder2.addCard(Optional.of(new Card(Rank.JACK, Suit.CLUBS)));
        builder2.addCard(Optional.of(new Card(Rank.QUEEN, Suit.HEARTS)));
        builder2.addCard(Optional.of(new Card(Rank.KING, Suit.SPADES)));

        final FiveCardPokerHand otherHand = builder2.build();
        final PokerHandComparator comparator = new PokerHandComparator();
        final int result = comparator.compare(hand, otherHand);

        assertEquals(result, -1);

    }

}
