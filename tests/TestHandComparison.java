import org.junit.Test;

import java.util.Optional;

import static org.junit.Assert.assertEquals;

public class TestHandComparison {

    @Test
    public void testPairVsPair() {
        final FiveCardPokerHand.Builder builder = new FiveCardPokerHand.Builder();

        builder.addCard(Optional.of(new Card(Card.Rank.ACE, Card.Suit.SPADES)));
        builder.addCard(Optional.of(new Card(Card.Rank.ACE, Card.Suit.DIAMONDS)));
        builder.addCard(Optional.of(new Card(Card.Rank.TEN, Card.Suit.SPADES)));
        builder.addCard(Optional.of(new Card(Card.Rank.JACK, Card.Suit.DIAMONDS)));
        builder.addCard(Optional.of(new Card(Card.Rank.QUEEN, Card.Suit.HEARTS)));

        final FiveCardPokerHand hand = builder.build();

        final FiveCardPokerHand.Builder builder2 = new FiveCardPokerHand.Builder();

        builder2.addCard(Optional.of(new Card(Card.Rank.ACE, Card.Suit.SPADES)));
        builder2.addCard(Optional.of(new Card(Card.Rank.JACK, Card.Suit.DIAMONDS)));
        builder2.addCard(Optional.of(new Card(Card.Rank.TEN, Card.Suit.SPADES)));
        builder2.addCard(Optional.of(new Card(Card.Rank.JACK, Card.Suit.CLUBS)));
        builder2.addCard(Optional.of(new Card(Card.Rank.QUEEN, Card.Suit.HEARTS)));

        final FiveCardPokerHand otherHand = builder2.build();

        int result = hand.compareTo(otherHand);

        assertEquals(result, 1);

    }

    @Test
    public void testTwoPairVsTwoPair() {
        final FiveCardPokerHand.Builder builder = new FiveCardPokerHand.Builder();

        builder.addCard(Optional.of(new Card(Card.Rank.ACE, Card.Suit.SPADES)));
        builder.addCard(Optional.of(new Card(Card.Rank.ACE, Card.Suit.DIAMONDS)));
        builder.addCard(Optional.of(new Card(Card.Rank.JACK, Card.Suit.SPADES)));
        builder.addCard(Optional.of(new Card(Card.Rank.JACK, Card.Suit.DIAMONDS)));
        builder.addCard(Optional.of(new Card(Card.Rank.QUEEN, Card.Suit.HEARTS)));

        final FiveCardPokerHand hand = builder.build();

        final FiveCardPokerHand.Builder builder2 = new FiveCardPokerHand.Builder();

        builder2.addCard(Optional.of(new Card(Card.Rank.ACE, Card.Suit.SPADES)));
        builder2.addCard(Optional.of(new Card(Card.Rank.ACE, Card.Suit.DIAMONDS)));
        builder2.addCard(Optional.of(new Card(Card.Rank.KING, Card.Suit.SPADES)));
        builder2.addCard(Optional.of(new Card(Card.Rank.KING, Card.Suit.CLUBS)));
        builder2.addCard(Optional.of(new Card(Card.Rank.QUEEN, Card.Suit.HEARTS)));

        final FiveCardPokerHand otherHand = builder2.build();

        int result = hand.compareTo(otherHand);

        assertEquals(result, -1);

    }

    @Test
    public void testFlushVsFlush() {
        final FiveCardPokerHand.Builder builder = new FiveCardPokerHand.Builder();

        builder.addCard(Optional.of(new Card(Card.Rank.TWO, Card.Suit.SPADES)));
        builder.addCard(Optional.of(new Card(Card.Rank.NINE, Card.Suit.SPADES)));
        builder.addCard(Optional.of(new Card(Card.Rank.JACK, Card.Suit.SPADES)));
        builder.addCard(Optional.of(new Card(Card.Rank.QUEEN, Card.Suit.SPADES)));
        builder.addCard(Optional.of(new Card(Card.Rank.KING, Card.Suit.SPADES)));

        final FiveCardPokerHand hand = builder.build();

        final FiveCardPokerHand.Builder builder2 = new FiveCardPokerHand.Builder();

        builder2.addCard(Optional.of(new Card(Card.Rank.TWO, Card.Suit.HEARTS)));
        builder2.addCard(Optional.of(new Card(Card.Rank.NINE, Card.Suit.HEARTS)));
        builder2.addCard(Optional.of(new Card(Card.Rank.FIVE, Card.Suit.HEARTS)));
        builder2.addCard(Optional.of(new Card(Card.Rank.KING, Card.Suit.HEARTS)));
        builder2.addCard(Optional.of(new Card(Card.Rank.THREE, Card.Suit.HEARTS)));

        final FiveCardPokerHand otherHand = builder2.build();

        int result = hand.compareTo(otherHand);

        assertEquals(result, 1);

    }

}
