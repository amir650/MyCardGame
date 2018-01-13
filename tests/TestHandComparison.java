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

        final int result = hand.compareTo(otherHand);

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

        final int result = hand.compareTo(otherHand);

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

    @Test
    public void testSetOverSet() {
        //[TWO of DIAMONDS, TWO of CLUBS, TWO of SPADES, FIVE of HEARTS, QUEEN of SPADES] vs
        //[THREE of DIAMONDS, EIGHT of DIAMONDS, EIGHT of CLUBS, EIGHT of SPADES, NINE of DIAMONDS]

        final FiveCardPokerHand.Builder builder = new FiveCardPokerHand.Builder();
        builder.addCard(Optional.of(new Card(Card.Rank.TWO, Card.Suit.DIAMONDS)));
        builder.addCard(Optional.of(new Card(Card.Rank.TWO, Card.Suit.CLUBS)));
        builder.addCard(Optional.of(new Card(Card.Rank.TWO, Card.Suit.SPADES)));
        builder.addCard(Optional.of(new Card(Card.Rank.FIVE, Card.Suit.HEARTS)));
        builder.addCard(Optional.of(new Card(Card.Rank.QUEEN, Card.Suit.SPADES)));

        final FiveCardPokerHand hand = builder.build();

        final FiveCardPokerHand.Builder builder2 = new FiveCardPokerHand.Builder();
        builder2.addCard(Optional.of(new Card(Card.Rank.THREE, Card.Suit.DIAMONDS)));
        builder2.addCard(Optional.of(new Card(Card.Rank.EIGHT, Card.Suit.DIAMONDS)));
        builder2.addCard(Optional.of(new Card(Card.Rank.EIGHT, Card.Suit.CLUBS)));
        builder2.addCard(Optional.of(new Card(Card.Rank.EIGHT, Card.Suit.SPADES)));
        builder2.addCard(Optional.of(new Card(Card.Rank.NINE, Card.Suit.DIAMONDS)));

        final FiveCardPokerHand hand2 = builder2.build();

        assertEquals(hand.compareTo(hand2), -1);

    }

    @Test
    public void testQuadsOverQuads() {
        //[TWO of DIAMONDS, TWO of CLUBS, TWO of HEARTS, TWO of SPADES, EIGHT of DIAMONDS] vs
        //[SIX of DIAMONDS, SIX of CLUBS, SIX of HEARTS, SIX of SPADES, NINE of HEARTS]

        final FiveCardPokerHand.Builder builder = new FiveCardPokerHand.Builder();
        builder.addCard(Optional.of(new Card(Card.Rank.TWO, Card.Suit.DIAMONDS)));
        builder.addCard(Optional.of(new Card(Card.Rank.TWO, Card.Suit.CLUBS)));
        builder.addCard(Optional.of(new Card(Card.Rank.TWO, Card.Suit.SPADES)));
        builder.addCard(Optional.of(new Card(Card.Rank.TWO, Card.Suit.HEARTS)));
        builder.addCard(Optional.of(new Card(Card.Rank.EIGHT, Card.Suit.SPADES)));

        final FiveCardPokerHand hand = builder.build();

        final FiveCardPokerHand.Builder builder2 = new FiveCardPokerHand.Builder();
        builder2.addCard(Optional.of(new Card(Card.Rank.SIX, Card.Suit.DIAMONDS)));
        builder2.addCard(Optional.of(new Card(Card.Rank.SIX, Card.Suit.CLUBS)));
        builder2.addCard(Optional.of(new Card(Card.Rank.SIX, Card.Suit.HEARTS)));
        builder2.addCard(Optional.of(new Card(Card.Rank.SIX, Card.Suit.SPADES)));
        builder2.addCard(Optional.of(new Card(Card.Rank.NINE, Card.Suit.HEARTS)));

        final FiveCardPokerHand hand2 = builder2.build();

        assertEquals(hand.compareTo(hand2), -1);
    }

    @Test
    public void testHighCardvsHighCard() {
        final FiveCardPokerHand.Builder builder = new FiveCardPokerHand.Builder();

        builder.addCard(Optional.of(new Card(Card.Rank.TWO, Card.Suit.SPADES)));
        builder.addCard(Optional.of(new Card(Card.Rank.NINE, Card.Suit.HEARTS)));
        builder.addCard(Optional.of(new Card(Card.Rank.JACK, Card.Suit.CLUBS)));
        builder.addCard(Optional.of(new Card(Card.Rank.QUEEN, Card.Suit.DIAMONDS)));
        builder.addCard(Optional.of(new Card(Card.Rank.KING, Card.Suit.HEARTS)));

        final FiveCardPokerHand hand = builder.build();

        final FiveCardPokerHand.Builder builder2 = new FiveCardPokerHand.Builder();

        builder2.addCard(Optional.of(new Card(Card.Rank.FIVE, Card.Suit.HEARTS)));
        builder2.addCard(Optional.of(new Card(Card.Rank.NINE, Card.Suit.DIAMONDS)));
        builder2.addCard(Optional.of(new Card(Card.Rank.JACK, Card.Suit.CLUBS)));
        builder2.addCard(Optional.of(new Card(Card.Rank.QUEEN, Card.Suit.HEARTS)));
        builder2.addCard(Optional.of(new Card(Card.Rank.KING, Card.Suit.SPADES)));

        final FiveCardPokerHand otherHand = builder2.build();

        final int result = hand.compareTo(otherHand);

        assertEquals(result, -1);

    }

    @Test
    public void testFailure() {
       // [FOUR of SPADES, NINE of DIAMONDS, JACK of DIAMONDS, JACK of CLUBS, JACK of SPADES]
       // [FOUR of DIAMONDS, FIVE of HEARTS, JACK of DIAMONDS, JACK of CLUBS, JACK of SPADES]

        final FiveCardPokerHand.Builder builder = new FiveCardPokerHand.Builder();

        builder.addCard(Optional.of(new Card(Card.Rank.FOUR, Card.Suit.DIAMONDS)));
        builder.addCard(Optional.of(new Card(Card.Rank.NINE, Card.Suit.HEARTS)));
        builder.addCard(Optional.of(new Card(Card.Rank.JACK, Card.Suit.DIAMONDS)));
        builder.addCard(Optional.of(new Card(Card.Rank.JACK, Card.Suit.CLUBS)));
        builder.addCard(Optional.of(new Card(Card.Rank.JACK, Card.Suit.SPADES)));

        final FiveCardPokerHand hand = builder.build();

        final FiveCardPokerHand.Builder builder2 = new FiveCardPokerHand.Builder();

        builder2.addCard(Optional.of(new Card(Card.Rank.FOUR, Card.Suit.DIAMONDS)));
        builder2.addCard(Optional.of(new Card(Card.Rank.FIVE, Card.Suit.HEARTS)));
        builder2.addCard(Optional.of(new Card(Card.Rank.JACK, Card.Suit.DIAMONDS)));
        builder2.addCard(Optional.of(new Card(Card.Rank.JACK, Card.Suit.CLUBS)));
        builder2.addCard(Optional.of(new Card(Card.Rank.JACK, Card.Suit.SPADES)));

        final FiveCardPokerHand otherHand = builder2.build();

        final int result = hand.compareTo(otherHand);

        assertEquals(result, -1);

    }

}
