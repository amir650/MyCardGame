import java.util.*;
import java.util.stream.Collectors;


public class FiveCardPokerHand {

    private final SortedSet<Card> cards;
    private final Classification handClassification;

    private static final int POKER_HAND_SIZE = 5;

    enum Classification {
        ROYAL_FLUSH,
        STRAIGHT_FLUSH,
        FOUR_OF_A_KIND,
        FULL_HOUSE,
        FLUSH,
        STRAIGHT,
        SET,
        TWO_PAIR,
        PAIR,
        HIGH_CARD
    }

    FiveCardPokerHand(final SortedSet<Card> cards) {
        this.cards = cards;
        this.handClassification = classifyHand(this.cards);
    }

    public Classification getHandClassification() {
        return this.handClassification;
    }

    private Classification classifyHand(final SortedSet<Card> cards) {

        if(isRoyalFlush(cards)) {
            return Classification.ROYAL_FLUSH;
        } else if(isStraightFlush(cards)) {
            return Classification.STRAIGHT_FLUSH;
        } else if(isFourOfAKind(cards)) {
            return Classification.FOUR_OF_A_KIND;
        } else if(isFullHouse(cards)) {
            return Classification.FULL_HOUSE;
        } else if(isFlush(cards)) {
            return Classification.FLUSH;
        } else if(isStraight(cards)) {
            return Classification.STRAIGHT;
        } else if(isSet(cards)) {
            return Classification.SET;
        } else if(isTwoPair(cards)) {
            return Classification.TWO_PAIR;
        } else if(isPair(cards)) {
            return Classification.PAIR;
        }

        return Classification.HIGH_CARD;
    }

    private static boolean isPair(final SortedSet<Card> cards) {
        final Map<Card.Rank, Long> groupResult =
                cards.stream()
                     .collect(Collectors.groupingBy(Card::getRank, Collectors.counting()));
        int numPairs = 0;
        for(final Map.Entry<Card.Rank, Long> entry : groupResult.entrySet()) {
            if(entry.getValue() == 2) {
                numPairs++;
            }
        }
        return numPairs == 1;
    }

    private static boolean isTwoPair(final SortedSet<Card> cards) {
        final Map<Card.Rank, Long> groupResult =
                cards.stream()
                     .collect(Collectors.groupingBy(Card::getRank, Collectors.counting()));
        int numPairs = 0;
        for(final Map.Entry<Card.Rank, Long> entry : groupResult.entrySet()) {
            if(entry.getValue() == 2) {
                numPairs++;
            }
        }
        return numPairs == 2;
    }

    private static boolean isSet(final SortedSet<Card> cards) {
        final Map<Card.Rank, Long> groupResult = cards.stream()
                .collect(Collectors.groupingBy(Card::getRank, Collectors.counting()));
        int numSets = 0;
        for(final Map.Entry<Card.Rank, Long> entry : groupResult.entrySet()) {
            if(entry.getValue() == 3) {
                numSets++;
            }
        }
        return numSets == 1;
    }

    private static boolean isStraight(final SortedSet<Card> cards) {
        if(isWheel(cards)) {
            return true;
        }
        final Card[] cardArray = cards.toArray(new Card[cards.size()]);
        for(int i = 0; i < cardArray.length - 1; i++) {
            if(cardArray[i].getRank().getRankValue() != cardArray[i+1].getRank().getRankValue() - 1) {
                return false;
            }
        }
        return true;
    }

    private static boolean isWheel(final SortedSet<Card> cards) {
        final Card[] cardArray = cards.toArray(new Card[cards.size()]);
        return  (cardArray[0].getRank().equals(Card.Rank.TWO)) &&
                (cardArray[1].getRank().equals(Card.Rank.THREE)) &&
                (cardArray[2].getRank().equals(Card.Rank.FOUR)) &&
                (cardArray[3].getRank().equals(Card.Rank.FIVE)) &&
                (cardArray[4].getRank().equals(Card.Rank.ACE));
    }

    private static boolean isFlush(final SortedSet<Card> cards) {
        return cards.stream()
                    .collect(Collectors.groupingBy(Card::getSuit, Collectors.counting())).containsValue(5L);
    }

    private static boolean isFullHouse(final SortedSet<Card> cards) {
        final Map<Card.Rank, Long> groupResult = cards.stream()
                .collect(Collectors.groupingBy(Card::getRank, Collectors.counting()));
        int numPairs = 0;
        int numSets = 0;
        for(final Map.Entry<Card.Rank, Long> entry : groupResult.entrySet()) {
            if(entry.getValue() == 2L) {
                numPairs++;
            } else if(entry.getValue() == 3L) {
                numSets++;
            }
        }
        return numPairs == 1 && numSets == 1;
    }

    private static boolean isFourOfAKind(final SortedSet<Card> cards) {
        return cards.stream()
                    .collect(Collectors.groupingBy(Card::getRank, Collectors.counting())).containsValue(4L);
    }

    private static boolean isStraightFlush(final SortedSet<Card> cards) {
        return isFlush(cards) && isStraight(cards);
    }

    private static boolean isRoyalFlush(final SortedSet<Card> cards) {
        final Card[] cardArray = cards.toArray(new Card[cards.size()]);
        if(cardArray[0].getRank().equals(Card.Rank.TEN) &&
           cardArray[1].getRank().equals(Card.Rank.JACK) &&
           cardArray[2].getRank().equals(Card.Rank.QUEEN) &&
           cardArray[3].getRank().equals(Card.Rank.KING) &&
           cardArray[4].getRank().equals(Card.Rank.ACE)) {
            return isFlush(cards);
        }
        return false;
    }

    @Override
    public String toString() {
        return this.cards.toString();
    }

    static class Builder {

        SortedSet<Card> cards;

        public Builder() {
            this.cards = new TreeSet<>(Comparator.naturalOrder());
        }

        public Builder addCard(final Optional<Card> card) {
            this.cards.add(card.orElseThrow(IllegalStateException::new));
            return this;
        }

        public FiveCardPokerHand build() {
            return new FiveCardPokerHand(initCards(this.cards));
        }

        private static SortedSet<Card> initCards(final SortedSet<Card> cards) {
            if(cards.size() != POKER_HAND_SIZE) {
                throw new RuntimeException("Invalid hand size for hand " +cards.toString());
            }
            return Collections.unmodifiableSortedSet(cards);
        }
    }
}
