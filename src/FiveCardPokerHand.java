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

    private static Classification classifyHand(final SortedSet<Card> cards) {

        final HandGroupResult groupResult = new HandGroupResult(cards);

        if(isRoyalFlush(groupResult)) {
            return Classification.ROYAL_FLUSH;
        } else if(isStraightFlush(groupResult)) {
            return Classification.STRAIGHT_FLUSH;
        } else if(isFourOfAKind(groupResult)) {
            return Classification.FOUR_OF_A_KIND;
        } else if(isFullHouse(groupResult)) {
            return Classification.FULL_HOUSE;
        } else if(isFlush(groupResult)) {
            return Classification.FLUSH;
        } else if(isStraight(groupResult)) {
            return Classification.STRAIGHT;
        } else if(isSet(groupResult)) {
            return Classification.SET;
        } else if(isTwoPair(groupResult)) {
            return Classification.TWO_PAIR;
        } else if(isPair(groupResult)) {
            return Classification.PAIR;
        }

        return Classification.HIGH_CARD;
    }

    private static boolean isPair(final HandGroupResult groupResult) {
        return groupResult.getPairCount() == 1;
    }

    private static boolean isTwoPair(final HandGroupResult groupResult) {
        return groupResult.getPairCount() == 2;
    }

    private static boolean isSet(final HandGroupResult groupResult) {
        return groupResult.getSetCount() == 1;
    }

    private static boolean isStraight(final HandGroupResult groupResult) {
        if(isWheel(groupResult)) {
            return true;
        }
        final Card[] cardArray = groupResult.getCards().toArray(new Card[groupResult.getCards().size()]);
        for(int i = 0; i < cardArray.length - 1; i++) {
            if(cardArray[i].getRank().getRankValue() != cardArray[i+1].getRank().getRankValue() - 1) {
                return false;
            }
        }
        return true;
    }

    private static boolean isWheel(final HandGroupResult groupResult) {
        final Card[] cardArray = groupResult.getCards().toArray(new Card[groupResult.getCards().size()]);
        return  (cardArray[0].getRank().equals(Card.Rank.TWO)) &&
                (cardArray[1].getRank().equals(Card.Rank.THREE)) &&
                (cardArray[2].getRank().equals(Card.Rank.FOUR)) &&
                (cardArray[3].getRank().equals(Card.Rank.FIVE)) &&
                (cardArray[4].getRank().equals(Card.Rank.ACE));
    }

    private static boolean isFlush(final HandGroupResult groupResult) {
        return groupResult.getSuitGroup().containsValue(5L);
    }

    private static boolean isFullHouse(final HandGroupResult groupResult) {
        return groupResult.getPairCount() == 1 && groupResult.getSetCount() == 1;
    }

    private static boolean isFourOfAKind(final HandGroupResult groupResult) {
        return groupResult.getRankGroup().containsValue(4L);
    }

    private static boolean isStraightFlush(final HandGroupResult groupResult) {
        return isFlush(groupResult) && isStraight(groupResult);
    }

    private static boolean isRoyalFlush(final HandGroupResult groupResult) {
        final Card[] cardArray = groupResult.getCards().toArray(new Card[groupResult.getCards().size()]);
        if(cardArray[0].getRank().equals(Card.Rank.TEN) &&
           cardArray[1].getRank().equals(Card.Rank.JACK) &&
           cardArray[2].getRank().equals(Card.Rank.QUEEN) &&
           cardArray[3].getRank().equals(Card.Rank.KING) &&
           cardArray[4].getRank().equals(Card.Rank.ACE)) {
            return isFlush(groupResult);
        }
        return false;
    }

    @Override
    public String toString() {
        return this.cards.toString();
    }

    static class HandGroupResult {
        final SortedSet<Card> cards;
        final Map<Card.Rank, Long> rankGroup;
        final Map<Card.Suit, Long> suitGroup;
        final int setCount;
        final int pairCount;

        HandGroupResult(final SortedSet<Card> cards) {
            this.cards = cards;
            this.rankGroup = cards.stream()
                    .collect(Collectors.groupingBy(Card::getRank, Collectors.counting()));
            this.suitGroup = cards.stream()
                    .collect(Collectors.groupingBy(Card::getSuit, Collectors.counting()));
            this.setCount = calculateSetCount(this.rankGroup);
            this.pairCount = calculatePairCount(this.rankGroup);
        }

        private static int calculateSetCount(final Map<Card.Rank, Long> groupResult) {
            int numSets = 0;
            for(final Map.Entry<Card.Rank, Long> entry : groupResult.entrySet()) {
                if(entry.getValue() == 3) {
                    numSets++;
                }
            }
            return numSets;
        }

        private static int calculatePairCount(final Map<Card.Rank, Long> groupResult) {
            int numPairs = 0;
            for(final Map.Entry<Card.Rank, Long> entry : groupResult.entrySet()) {
                if(entry.getValue() == 2) {
                    numPairs++;
                }
            }
            return numPairs;
        }

        Map<Card.Rank, Long> getRankGroup() {
            return this.rankGroup;
        }

        Map<Card.Suit, Long> getSuitGroup() {
            return this.suitGroup;
        }

        SortedSet<Card> getCards() {
            return this.cards;
        }

        int getSetCount() {
            return this.setCount;
        }

        int getPairCount() {
            return this.pairCount;
        }
    }

    static class Builder {

        private SortedSet<Card> cards;

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
