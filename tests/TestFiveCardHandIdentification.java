import com.cardgames.cards.Card;
import com.cardgames.poker.shared.ClassificationRank;
import com.cardgames.cards.Rank;
import com.cardgames.cards.Suit;
import com.cardgames.poker.fivecardpoker.FiveCardPokerHand;
import org.junit.Test;

import java.util.Optional;

import static junit.framework.TestCase.assertEquals;

public class TestFiveCardHandIdentification {

    @Test
    public void testIdentifyPair() {

        final FiveCardPokerHand.Builder builder = new FiveCardPokerHand.Builder();

        builder.addCard(Optional.of(new Card(Rank.ACE, Suit.SPADES)));
        builder.addCard(Optional.of(new Card(Rank.ACE, Suit.DIAMONDS)));
        builder.addCard(Optional.of(new Card(Rank.TEN, Suit.SPADES)));
        builder.addCard(Optional.of(new Card(Rank.JACK, Suit.DIAMONDS)));
        builder.addCard(Optional.of(new Card(Rank.QUEEN, Suit.HEARTS)));

        final FiveCardPokerHand hand = builder.build();

        assertEquals(hand.getHandAnalyzer().getClassification().getClassificationRank(), ClassificationRank.PAIR);
    }

    @Test
    public void testIdentifyTwoPairs() {

        final FiveCardPokerHand.Builder builder = new FiveCardPokerHand.Builder();

        builder.addCard(Optional.of(new Card(Rank.ACE, Suit.SPADES)));
        builder.addCard(Optional.of(new Card(Rank.ACE, Suit.DIAMONDS)));
        builder.addCard(Optional.of(new Card(Rank.TEN, Suit.SPADES)));
        builder.addCard(Optional.of(new Card(Rank.TEN, Suit.DIAMONDS)));
        builder.addCard(Optional.of(new Card(Rank.QUEEN, Suit.HEARTS)));

        final FiveCardPokerHand hand = builder.build();

        assertEquals(hand.getHandAnalyzer().getClassification().getClassificationRank(), ClassificationRank.TWO_PAIR);
    }

    @Test
    public void testIdentifySet() {
        
        final FiveCardPokerHand.Builder builder = new FiveCardPokerHand.Builder();

        builder.addCard(Optional.of(new Card(Rank.ACE, Suit.SPADES)));
        builder.addCard(Optional.of(new Card(Rank.ACE, Suit.DIAMONDS)));
        builder.addCard(Optional.of(new Card(Rank.ACE, Suit.HEARTS)));
        builder.addCard(Optional.of(new Card(Rank.TEN, Suit.DIAMONDS)));
        builder.addCard(Optional.of(new Card(Rank.QUEEN, Suit.HEARTS)));

        final FiveCardPokerHand hand = builder.build();

        assertEquals(hand.getHandAnalyzer().getClassification().getClassificationRank(), ClassificationRank.SET);
    }

    @Test
    public void testIdentifyStraight() {
        
        final FiveCardPokerHand.Builder builder = new FiveCardPokerHand.Builder();

        builder.addCard(Optional.of(new Card(Rank.TWO, Suit.SPADES)));
        builder.addCard(Optional.of(new Card(Rank.THREE, Suit.DIAMONDS)));
        builder.addCard(Optional.of(new Card(Rank.FOUR, Suit.HEARTS)));
        builder.addCard(Optional.of(new Card(Rank.FIVE, Suit.DIAMONDS)));
        builder.addCard(Optional.of(new Card(Rank.SIX, Suit.HEARTS)));

        final FiveCardPokerHand hand = builder.build();

        assertEquals(hand.getHandAnalyzer().getClassification().getClassificationRank(), ClassificationRank.STRAIGHT);
    }

    @Test
    public void testIdentifyWheel() {
        
        final FiveCardPokerHand.Builder builder = new FiveCardPokerHand.Builder();

        builder.addCard(Optional.of(new Card(Rank.ACE, Suit.SPADES)));
        builder.addCard(Optional.of(new Card(Rank.TWO, Suit.DIAMONDS)));
        builder.addCard(Optional.of(new Card(Rank.THREE, Suit.HEARTS)));
        builder.addCard(Optional.of(new Card(Rank.FOUR, Suit.DIAMONDS)));
        builder.addCard(Optional.of(new Card(Rank.FIVE, Suit.HEARTS)));

        final FiveCardPokerHand hand = builder.build();

        assertEquals(hand.getHandAnalyzer().getClassification().getClassificationRank(), ClassificationRank.WHEEL);
    }

    @Test
    public void testIdentifyFlush() {
        
        final FiveCardPokerHand.Builder builder = new FiveCardPokerHand.Builder();

        builder.addCard(Optional.of(new Card(Rank.TWO, Suit.SPADES)));
        builder.addCard(Optional.of(new Card(Rank.KING, Suit.SPADES)));
        builder.addCard(Optional.of(new Card(Rank.QUEEN, Suit.SPADES)));
        builder.addCard(Optional.of(new Card(Rank.FIVE, Suit.SPADES)));
        builder.addCard(Optional.of(new Card(Rank.SIX, Suit.SPADES)));

        final FiveCardPokerHand hand = builder.build();

        assertEquals(hand.getHandAnalyzer().getClassification().getClassificationRank(), ClassificationRank.FLUSH);
    }

    @Test
    public void testIdentifyFullHouse() {
        
        final FiveCardPokerHand.Builder builder = new FiveCardPokerHand.Builder();

        builder.addCard(Optional.of(new Card(Rank.KING, Suit.SPADES)));
        builder.addCard(Optional.of(new Card(Rank.KING, Suit.HEARTS)));
        builder.addCard(Optional.of(new Card(Rank.QUEEN, Suit.CLUBS)));
        builder.addCard(Optional.of(new Card(Rank.QUEEN, Suit.SPADES)));
        builder.addCard(Optional.of(new Card(Rank.QUEEN, Suit.HEARTS)));

        final FiveCardPokerHand hand = builder.build();

        assertEquals(hand.getHandAnalyzer().getClassification().getClassificationRank(), ClassificationRank.FULL_HOUSE);
    }

    @Test
    public void testIdentifyFourOfAKind() {
        
        final FiveCardPokerHand.Builder builder = new FiveCardPokerHand.Builder();

        builder.addCard(Optional.of(new Card(Rank.KING, Suit.SPADES)));
        builder.addCard(Optional.of(new Card(Rank.QUEEN, Suit.HEARTS)));
        builder.addCard(Optional.of(new Card(Rank.QUEEN, Suit.CLUBS)));
        builder.addCard(Optional.of(new Card(Rank.QUEEN, Suit.SPADES)));
        builder.addCard(Optional.of(new Card(Rank.QUEEN, Suit.DIAMONDS)));

        final FiveCardPokerHand hand = builder.build();

        assertEquals(hand.getHandAnalyzer().getClassification().getClassificationRank(), ClassificationRank.FOUR_OF_A_KIND);
    }

    @Test
    public void testIdentifyStraightFlush() {
        
        final FiveCardPokerHand.Builder builder = new FiveCardPokerHand.Builder();

        builder.addCard(Optional.of(new Card(Rank.TWO, Suit.DIAMONDS)));
        builder.addCard(Optional.of(new Card(Rank.THREE, Suit.DIAMONDS)));
        builder.addCard(Optional.of(new Card(Rank.FOUR, Suit.DIAMONDS)));
        builder.addCard(Optional.of(new Card(Rank.FIVE, Suit.DIAMONDS)));
        builder.addCard(Optional.of(new Card(Rank.SIX, Suit.DIAMONDS)));

        final FiveCardPokerHand hand = builder.build();

        assertEquals(hand.getHandAnalyzer().getClassification().getClassificationRank(), ClassificationRank.STRAIGHT_FLUSH);
    }

    @Test
    public void testIdentifyRoyalFlush() {
        
        final FiveCardPokerHand.Builder builder = new FiveCardPokerHand.Builder();

        builder.addCard(Optional.of(new Card(Rank.TEN, Suit.DIAMONDS)));
        builder.addCard(Optional.of(new Card(Rank.JACK, Suit.DIAMONDS)));
        builder.addCard(Optional.of(new Card(Rank.QUEEN, Suit.DIAMONDS)));
        builder.addCard(Optional.of(new Card(Rank.KING, Suit.DIAMONDS)));
        builder.addCard(Optional.of(new Card(Rank.ACE, Suit.DIAMONDS)));

        final FiveCardPokerHand hand = builder.build();

        assertEquals(hand.getHandAnalyzer().getClassification().getClassificationRank(), ClassificationRank.ROYAL_FLUSH);
    }
    
}