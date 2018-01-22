import com.cardgames.Card;
import com.cardgames.Rank;
import com.cardgames.Suit;
import com.cardgames.holdem.HoldemHand;
import com.cardgames.holdem.HoldemHandComparator;
import org.junit.Test;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import static org.junit.Assert.assertEquals;

public class TestHoldemHandComparison {

    @Test
    public void testPairVsPair() {

        final Set<Optional<Card>> communityCards = new HashSet<>();
        communityCards.add(Optional.of(new Card(Rank.THREE, Suit.DIAMONDS)));
        communityCards.add(Optional.of(new Card(Rank.NINE, Suit.HEARTS)));
        communityCards.add(Optional.of(new Card(Rank.JACK, Suit.CLUBS)));
        communityCards.add(Optional.of(new Card(Rank.FIVE, Suit.SPADES)));
        communityCards.add(Optional.of(new Card(Rank.THREE, Suit.HEARTS)));

        final HoldemHand.Builder builder = new HoldemHand.Builder();
        builder.addHoleCard(Optional.of(new Card(Rank.ACE, Suit.SPADES)));
        builder.addHoleCard(Optional.of(new Card(Rank.ACE, Suit.DIAMONDS)));
        builder.addCommunityCards(communityCards);

        final HoldemHand.Builder builder2 = new HoldemHand.Builder();
        builder2.addHoleCard(Optional.of(new Card(Rank.KING, Suit.SPADES)));
        builder2.addHoleCard(Optional.of(new Card(Rank.KING, Suit.DIAMONDS)));
        builder2.addCommunityCards(communityCards);

        final HoldemHand hand = builder.build();
        final HoldemHand otherHand = builder2.build();

        final HoldemHandComparator comparator = new HoldemHandComparator();

        final int result = comparator.compare(hand, otherHand);

        assertEquals(result, 1);
    }

}