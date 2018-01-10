import org.junit.Test;

import java.util.Optional;

import static org.junit.Assert.assertEquals;

public class TestHandIdentification {

    @Test
    public void testIdentifyPair() {

        final FiveCardPokerHand.Builder builder = new FiveCardPokerHand.Builder();

        builder.addCard(Optional.of(new Card(Card.Rank.ACE, Card.Suit.SPADES)));
        builder.addCard(Optional.of(new Card(Card.Rank.ACE, Card.Suit.DIAMONDS)));
        builder.addCard(Optional.of(new Card(Card.Rank.TEN, Card.Suit.SPADES)));
        builder.addCard(Optional.of(new Card(Card.Rank.JACK, Card.Suit.DIAMONDS)));
        builder.addCard(Optional.of(new Card(Card.Rank.QUEEN, Card.Suit.HEARTS)));

        final FiveCardPokerHand hand = builder.build();

        assertEquals(hand.getHandClassification(), FiveCardPokerHand.Classification.PAIR);
    }

    @Test
    public void testIdentifyTwoPairs() {

        final FiveCardPokerHand.Builder builder = new FiveCardPokerHand.Builder();

        builder.addCard(Optional.of(new Card(Card.Rank.ACE, Card.Suit.SPADES)));
        builder.addCard(Optional.of(new Card(Card.Rank.ACE, Card.Suit.DIAMONDS)));
        builder.addCard(Optional.of(new Card(Card.Rank.TEN, Card.Suit.SPADES)));
        builder.addCard(Optional.of(new Card(Card.Rank.TEN, Card.Suit.DIAMONDS)));
        builder.addCard(Optional.of(new Card(Card.Rank.QUEEN, Card.Suit.HEARTS)));

        final FiveCardPokerHand hand = builder.build();

        assertEquals(hand.getHandClassification(), FiveCardPokerHand.Classification.TWO_PAIR);
    }

    @Test
    public void testIdentifySet() {
        
        final FiveCardPokerHand.Builder builder = new FiveCardPokerHand.Builder();

        builder.addCard(Optional.of(new Card(Card.Rank.ACE, Card.Suit.SPADES)));
        builder.addCard(Optional.of(new Card(Card.Rank.ACE, Card.Suit.DIAMONDS)));
        builder.addCard(Optional.of(new Card(Card.Rank.ACE, Card.Suit.HEARTS)));
        builder.addCard(Optional.of(new Card(Card.Rank.TEN, Card.Suit.DIAMONDS)));
        builder.addCard(Optional.of(new Card(Card.Rank.QUEEN, Card.Suit.HEARTS)));

        final FiveCardPokerHand hand = builder.build();

        assertEquals(hand.getHandClassification(), FiveCardPokerHand.Classification.SET);
    }

    @Test
    public void testIdentifyStraight() {
        
        final FiveCardPokerHand.Builder builder = new FiveCardPokerHand.Builder();

        builder.addCard(Optional.of(new Card(Card.Rank.TWO, Card.Suit.SPADES)));
        builder.addCard(Optional.of(new Card(Card.Rank.THREE, Card.Suit.DIAMONDS)));
        builder.addCard(Optional.of(new Card(Card.Rank.FOUR, Card.Suit.HEARTS)));
        builder.addCard(Optional.of(new Card(Card.Rank.FIVE, Card.Suit.DIAMONDS)));
        builder.addCard(Optional.of(new Card(Card.Rank.SIX, Card.Suit.HEARTS)));

        final FiveCardPokerHand hand = builder.build();

        assertEquals(hand.getHandClassification(), FiveCardPokerHand.Classification.STRAIGHT);
    }

    @Test
    public void testIdentifyWheel() {
        
        final FiveCardPokerHand.Builder builder = new FiveCardPokerHand.Builder();

        builder.addCard(Optional.of(new Card(Card.Rank.ACE, Card.Suit.SPADES)));
        builder.addCard(Optional.of(new Card(Card.Rank.TWO, Card.Suit.DIAMONDS)));
        builder.addCard(Optional.of(new Card(Card.Rank.THREE, Card.Suit.HEARTS)));
        builder.addCard(Optional.of(new Card(Card.Rank.FOUR, Card.Suit.DIAMONDS)));
        builder.addCard(Optional.of(new Card(Card.Rank.FIVE, Card.Suit.HEARTS)));

        final FiveCardPokerHand hand = builder.build();

        assertEquals(hand.getHandClassification(), FiveCardPokerHand.Classification.WHEEL);
    }

    @Test
    public void testIdentifyFlush() {
        
        final FiveCardPokerHand.Builder builder = new FiveCardPokerHand.Builder();

        builder.addCard(Optional.of(new Card(Card.Rank.TWO, Card.Suit.SPADES)));
        builder.addCard(Optional.of(new Card(Card.Rank.KING, Card.Suit.SPADES)));
        builder.addCard(Optional.of(new Card(Card.Rank.QUEEN, Card.Suit.SPADES)));
        builder.addCard(Optional.of(new Card(Card.Rank.FIVE, Card.Suit.SPADES)));
        builder.addCard(Optional.of(new Card(Card.Rank.SIX, Card.Suit.SPADES)));

        final FiveCardPokerHand hand = builder.build();

        assertEquals(hand.getHandClassification(), FiveCardPokerHand.Classification.FLUSH);
    }

    @Test
    public void testIdentifyFullHouse() {
        
        final FiveCardPokerHand.Builder builder = new FiveCardPokerHand.Builder();

        builder.addCard(Optional.of(new Card(Card.Rank.KING, Card.Suit.SPADES)));
        builder.addCard(Optional.of(new Card(Card.Rank.KING, Card.Suit.HEARTS)));
        builder.addCard(Optional.of(new Card(Card.Rank.QUEEN, Card.Suit.CLUBS)));
        builder.addCard(Optional.of(new Card(Card.Rank.QUEEN, Card.Suit.SPADES)));
        builder.addCard(Optional.of(new Card(Card.Rank.QUEEN, Card.Suit.HEARTS)));

        final FiveCardPokerHand hand = builder.build();

        assertEquals(hand.getHandClassification(), FiveCardPokerHand.Classification.FULL_HOUSE);
    }

    @Test
    public void testIdentifyFourOfAKind() {
        
        final FiveCardPokerHand.Builder builder = new FiveCardPokerHand.Builder();

        builder.addCard(Optional.of(new Card(Card.Rank.KING, Card.Suit.SPADES)));
        builder.addCard(Optional.of(new Card(Card.Rank.QUEEN, Card.Suit.HEARTS)));
        builder.addCard(Optional.of(new Card(Card.Rank.QUEEN, Card.Suit.CLUBS)));
        builder.addCard(Optional.of(new Card(Card.Rank.QUEEN, Card.Suit.SPADES)));
        builder.addCard(Optional.of(new Card(Card.Rank.QUEEN, Card.Suit.DIAMONDS)));

        final FiveCardPokerHand hand = builder.build();

        assertEquals(hand.getHandClassification(), FiveCardPokerHand.Classification.FOUR_OF_A_KIND);
    }

    @Test
    public void testIdentifyStraightFlush() {
        
        final FiveCardPokerHand.Builder builder = new FiveCardPokerHand.Builder();

        builder.addCard(Optional.of(new Card(Card.Rank.TWO, Card.Suit.DIAMONDS)));
        builder.addCard(Optional.of(new Card(Card.Rank.THREE, Card.Suit.DIAMONDS)));
        builder.addCard(Optional.of(new Card(Card.Rank.FOUR, Card.Suit.DIAMONDS)));
        builder.addCard(Optional.of(new Card(Card.Rank.FIVE, Card.Suit.DIAMONDS)));
        builder.addCard(Optional.of(new Card(Card.Rank.SIX, Card.Suit.DIAMONDS)));

        final FiveCardPokerHand hand = builder.build();

        assertEquals(hand.getHandClassification(), FiveCardPokerHand.Classification.STRAIGHT_FLUSH);
    }

    @Test
    public void testIdentifyRoyalFlush() {
        
        final FiveCardPokerHand.Builder builder = new FiveCardPokerHand.Builder();

        builder.addCard(Optional.of(new Card(Card.Rank.TEN, Card.Suit.DIAMONDS)));
        builder.addCard(Optional.of(new Card(Card.Rank.JACK, Card.Suit.DIAMONDS)));
        builder.addCard(Optional.of(new Card(Card.Rank.QUEEN, Card.Suit.DIAMONDS)));
        builder.addCard(Optional.of(new Card(Card.Rank.KING, Card.Suit.DIAMONDS)));
        builder.addCard(Optional.of(new Card(Card.Rank.ACE, Card.Suit.DIAMONDS)));

        final FiveCardPokerHand hand = builder.build();

        assertEquals(hand.getHandClassification(), FiveCardPokerHand.Classification.ROYAL_FLUSH);
    }
    
}