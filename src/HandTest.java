import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class HandTest {

    @Test
    public void testPair() {

        final Deck deck = Deck.newShuffledSingleDeck();
        final FiveCardPokerHand.Builder builder = new FiveCardPokerHand.Builder();

        builder.addCard(deck.fetchCard(Card.Rank.ACE, Card.Suit.SPADES));
        builder.addCard(deck.fetchCard(Card.Rank.ACE, Card.Suit.DIAMONDS));
        builder.addCard(deck.fetchCard(Card.Rank.TEN, Card.Suit.SPADES));
        builder.addCard(deck.fetchCard(Card.Rank.JACK, Card.Suit.DIAMONDS));
        builder.addCard(deck.fetchCard(Card.Rank.QUEEN, Card.Suit.HEARTS));

        final FiveCardPokerHand hand = builder.build();

        assertEquals(hand.getHandClassification(), FiveCardPokerHand.Classification.PAIR);
    }

    @Test
    public void testTwoPair() {
        final Deck deck = Deck.newShuffledSingleDeck();
        final FiveCardPokerHand.Builder builder = new FiveCardPokerHand.Builder();

        builder.addCard(deck.fetchCard(Card.Rank.ACE, Card.Suit.SPADES));
        builder.addCard(deck.fetchCard(Card.Rank.ACE, Card.Suit.DIAMONDS));
        builder.addCard(deck.fetchCard(Card.Rank.TEN, Card.Suit.SPADES));
        builder.addCard(deck.fetchCard(Card.Rank.TEN, Card.Suit.DIAMONDS));
        builder.addCard(deck.fetchCard(Card.Rank.QUEEN, Card.Suit.HEARTS));

        final FiveCardPokerHand hand = builder.build();

        assertEquals(hand.getHandClassification(), FiveCardPokerHand.Classification.TWO_PAIR);
    }

    @Test
    public void testSet() {
        final Deck deck = Deck.newShuffledSingleDeck();
        final FiveCardPokerHand.Builder builder = new FiveCardPokerHand.Builder();

        builder.addCard(deck.fetchCard(Card.Rank.ACE, Card.Suit.SPADES));
        builder.addCard(deck.fetchCard(Card.Rank.ACE, Card.Suit.DIAMONDS));
        builder.addCard(deck.fetchCard(Card.Rank.ACE, Card.Suit.HEARTS));
        builder.addCard(deck.fetchCard(Card.Rank.TEN, Card.Suit.DIAMONDS));
        builder.addCard(deck.fetchCard(Card.Rank.QUEEN, Card.Suit.HEARTS));

        final FiveCardPokerHand hand = builder.build();

        assertEquals(hand.getHandClassification(), FiveCardPokerHand.Classification.SET);
    }

    @Test
    public void testStraight() {
        final Deck deck = Deck.newShuffledSingleDeck();
        final FiveCardPokerHand.Builder builder = new FiveCardPokerHand.Builder();

        builder.addCard(deck.fetchCard(Card.Rank.TWO, Card.Suit.SPADES));
        builder.addCard(deck.fetchCard(Card.Rank.THREE, Card.Suit.DIAMONDS));
        builder.addCard(deck.fetchCard(Card.Rank.FOUR, Card.Suit.HEARTS));
        builder.addCard(deck.fetchCard(Card.Rank.FIVE, Card.Suit.DIAMONDS));
        builder.addCard(deck.fetchCard(Card.Rank.SIX, Card.Suit.HEARTS));

        final FiveCardPokerHand hand = builder.build();

        assertEquals(hand.getHandClassification(), FiveCardPokerHand.Classification.STRAIGHT);
    }

    @Test
    public void testWheel() {
        final Deck deck = Deck.newShuffledSingleDeck();
        final FiveCardPokerHand.Builder builder = new FiveCardPokerHand.Builder();

        builder.addCard(deck.fetchCard(Card.Rank.ACE, Card.Suit.SPADES));
        builder.addCard(deck.fetchCard(Card.Rank.TWO, Card.Suit.DIAMONDS));
        builder.addCard(deck.fetchCard(Card.Rank.THREE, Card.Suit.HEARTS));
        builder.addCard(deck.fetchCard(Card.Rank.FOUR, Card.Suit.DIAMONDS));
        builder.addCard(deck.fetchCard(Card.Rank.FIVE, Card.Suit.HEARTS));

        final FiveCardPokerHand hand = builder.build();

        assertEquals(hand.getHandClassification(), FiveCardPokerHand.Classification.STRAIGHT);
    }

    @Test
    public void testFlush() {
        final Deck deck = Deck.newShuffledSingleDeck();
        final FiveCardPokerHand.Builder builder = new FiveCardPokerHand.Builder();

        builder.addCard(deck.fetchCard(Card.Rank.TWO, Card.Suit.SPADES));
        builder.addCard(deck.fetchCard(Card.Rank.KING, Card.Suit.SPADES));
        builder.addCard(deck.fetchCard(Card.Rank.QUEEN, Card.Suit.SPADES));
        builder.addCard(deck.fetchCard(Card.Rank.FIVE, Card.Suit.SPADES));
        builder.addCard(deck.fetchCard(Card.Rank.SIX, Card.Suit.SPADES));

        final FiveCardPokerHand hand = builder.build();

        assertEquals(hand.getHandClassification(), FiveCardPokerHand.Classification.FLUSH);
    }

    @Test
    public void testFullHouse() {
        final Deck deck = Deck.newShuffledSingleDeck();
        final FiveCardPokerHand.Builder builder = new FiveCardPokerHand.Builder();

        builder.addCard(deck.fetchCard(Card.Rank.KING, Card.Suit.SPADES));
        builder.addCard(deck.fetchCard(Card.Rank.KING, Card.Suit.HEARTS));
        builder.addCard(deck.fetchCard(Card.Rank.QUEEN, Card.Suit.CLUBS));
        builder.addCard(deck.fetchCard(Card.Rank.QUEEN, Card.Suit.SPADES));
        builder.addCard(deck.fetchCard(Card.Rank.QUEEN, Card.Suit.HEARTS));

        final FiveCardPokerHand hand = builder.build();

        assertEquals(hand.getHandClassification(), FiveCardPokerHand.Classification.FULL_HOUSE);
    }

    @Test
    public void testFourOfAKind() {
        final Deck deck = Deck.newShuffledSingleDeck();
        final FiveCardPokerHand.Builder builder = new FiveCardPokerHand.Builder();

        builder.addCard(deck.fetchCard(Card.Rank.KING, Card.Suit.SPADES));
        builder.addCard(deck.fetchCard(Card.Rank.QUEEN, Card.Suit.HEARTS));
        builder.addCard(deck.fetchCard(Card.Rank.QUEEN, Card.Suit.CLUBS));
        builder.addCard(deck.fetchCard(Card.Rank.QUEEN, Card.Suit.SPADES));
        builder.addCard(deck.fetchCard(Card.Rank.QUEEN, Card.Suit.DIAMONDS));

        final FiveCardPokerHand hand = builder.build();

        assertEquals(hand.getHandClassification(), FiveCardPokerHand.Classification.FOUR_OF_A_KIND);
    }

    @Test
    public void testStraightFlush() {
        final Deck deck = Deck.newShuffledSingleDeck();
        final FiveCardPokerHand.Builder builder = new FiveCardPokerHand.Builder();

        builder.addCard(deck.fetchCard(Card.Rank.TWO, Card.Suit.DIAMONDS));
        builder.addCard(deck.fetchCard(Card.Rank.THREE, Card.Suit.DIAMONDS));
        builder.addCard(deck.fetchCard(Card.Rank.FOUR, Card.Suit.DIAMONDS));
        builder.addCard(deck.fetchCard(Card.Rank.FIVE, Card.Suit.DIAMONDS));
        builder.addCard(deck.fetchCard(Card.Rank.SIX, Card.Suit.DIAMONDS));

        final FiveCardPokerHand hand = builder.build();

        assertEquals(hand.getHandClassification(), FiveCardPokerHand.Classification.STRAIGHT_FLUSH);
    }

    @Test
    public void testRoyalFlush() {
        final Deck deck = Deck.newShuffledSingleDeck();
        final FiveCardPokerHand.Builder builder = new FiveCardPokerHand.Builder();

        builder.addCard(deck.fetchCard(Card.Rank.TEN, Card.Suit.DIAMONDS));
        builder.addCard(deck.fetchCard(Card.Rank.JACK, Card.Suit.DIAMONDS));
        builder.addCard(deck.fetchCard(Card.Rank.QUEEN, Card.Suit.DIAMONDS));
        builder.addCard(deck.fetchCard(Card.Rank.KING, Card.Suit.DIAMONDS));
        builder.addCard(deck.fetchCard(Card.Rank.ACE, Card.Suit.DIAMONDS));

        final FiveCardPokerHand hand = builder.build();

        assertEquals(hand.getHandClassification(), FiveCardPokerHand.Classification.ROYAL_FLUSH);
    }

}