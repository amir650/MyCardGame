import java.util.Iterator;
import java.util.List;
import java.util.Map;

enum  HandUtils {
; //no instance
    static final int TIE = 0;

    static int compareRoyalFlushHands(final FiveCardPokerHand hand,
                                      final FiveCardPokerHand otherHand) {
        checkHandClassification(hand, FiveCardPokerHand.Classification.ROYAL_FLUSH);
        checkHandClassification(otherHand, FiveCardPokerHand.Classification.ROYAL_FLUSH);

        return TIE;
    }

    static int compareStraightFlushHands(final FiveCardPokerHand hand,
                                         final FiveCardPokerHand otherHand) {
        checkHandClassification(hand, FiveCardPokerHand.Classification.STRAIGHT_FLUSH);
        checkHandClassification(otherHand, FiveCardPokerHand.Classification.STRAIGHT_FLUSH);

        return Integer.compare(hand.getHandInformation().getCards().last().getRank().getRankValue(),
                               otherHand.getHandInformation().getCards().last().getRank().getRankValue());
    }

    static int compareStraightFlushWheelHands(final FiveCardPokerHand hand,
                                              final FiveCardPokerHand otherHand) {
        checkHandClassification(hand, FiveCardPokerHand.Classification.STRAIGHT_FLUSH_WHEEL);
        checkHandClassification(otherHand, FiveCardPokerHand.Classification.STRAIGHT_FLUSH_WHEEL);

        return TIE;
    }

    static int compareQuadsHands(final FiveCardPokerHand hand,
                                 final FiveCardPokerHand otherHand) {
        checkHandClassification(hand, FiveCardPokerHand.Classification.FOUR_OF_A_KIND);
        checkHandClassification(otherHand, FiveCardPokerHand.Classification.FOUR_OF_A_KIND);

        final int quadsCompare =
                Integer.compare(hand.getHandInformation().getRankGroup().entrySet().iterator().next().getKey().getRankValue(),
                                otherHand.getHandInformation().getRankGroup().entrySet().iterator().next().getKey().getRankValue());
        if (quadsCompare != 0) {
            return quadsCompare;
        }

        throw new RuntimeException("should not reach here in 5 card poker : " + hand + " vs " + otherHand);
    }

    static int compareFullHouseHands(final FiveCardPokerHand hand,
                                     final FiveCardPokerHand otherHand) {
        checkHandClassification(hand, FiveCardPokerHand.Classification.FULL_HOUSE);
        checkHandClassification(otherHand, FiveCardPokerHand.Classification.FULL_HOUSE);

        final Iterator<Map.Entry<Card.Rank, List<Card>>> handIterator = hand.getHandInformation().getRankGroup().entrySet().iterator();
        final Iterator<Map.Entry<Card.Rank, List<Card>>> otherHandIterator = otherHand.getHandInformation().getRankGroup().entrySet().iterator();
        final int setCompare = Integer.compare(handIterator.next().getKey().getRankValue(),
                                               otherHandIterator.next().getKey().getRankValue());
        if (setCompare != 0) {
            return setCompare;
        }

        final int pairCompare = Integer.compare(handIterator.next().getKey().getRankValue(),
                                                otherHandIterator.next().getKey().getRankValue());
        if (pairCompare != 0) {
            return pairCompare;
        }

        throw new RuntimeException("should not reach here in 5 card poker : " + hand + " vs " + otherHand);
    }

    static int compareFlushHands(final FiveCardPokerHand hand,
                                 final FiveCardPokerHand otherHand) {
        checkHandClassification(hand, FiveCardPokerHand.Classification.FLUSH);
        checkHandClassification(otherHand, FiveCardPokerHand.Classification.FLUSH);

        final Iterator<Map.Entry<Card.Rank, List<Card>>> handIterator = hand.getHandInformation().getRankGroup().entrySet().iterator();
        final Iterator<Map.Entry<Card.Rank, List<Card>>> otherHandIterator = otherHand.getHandInformation().getRankGroup().entrySet().iterator();

        return iterateAndCompareHighCard(handIterator, otherHandIterator);
    }

    static int compareStraightHands(final FiveCardPokerHand hand,
                                    final FiveCardPokerHand otherHand) {
        return Integer.compare(hand.getHandInformation().getCards().last().getRank().getRankValue(),
                               otherHand.getHandInformation().getCards().last().getRank().getRankValue());
    }

    static int compareWheelHands(final FiveCardPokerHand hand,
                                 final FiveCardPokerHand otherHand) {
        checkHandClassification(hand, FiveCardPokerHand.Classification.WHEEL);
        checkHandClassification(otherHand, FiveCardPokerHand.Classification.WHEEL);

        return TIE;
    }

    static int compareSetHands(final FiveCardPokerHand hand,
                               final FiveCardPokerHand otherHand) {
        checkHandClassification(hand, FiveCardPokerHand.Classification.SET);
        checkHandClassification(otherHand, FiveCardPokerHand.Classification.SET);

        final Iterator<Map.Entry<Card.Rank, List<Card>>> handIterator = hand.getHandInformation().getRankGroup().entrySet().iterator();
        final Iterator<Map.Entry<Card.Rank, List<Card>>> otherHandIterator = otherHand.getHandInformation().getRankGroup().entrySet().iterator();
        final int setCompare = Integer.compare(handIterator.next().getKey().getRankValue(),
                otherHandIterator.next().getKey().getRankValue());

        if (setCompare != 0) {
            return setCompare;
        }

        throw new RuntimeException("should not reach here in 5 card poker : " + hand + " vs " + otherHand);
    }

    static int compareTwoPairHands(final FiveCardPokerHand hand,
                                   final FiveCardPokerHand otherHand) {
        checkHandClassification(hand, FiveCardPokerHand.Classification.TWO_PAIR);
        checkHandClassification(otherHand, FiveCardPokerHand.Classification.TWO_PAIR);

        final Iterator<Map.Entry<Card.Rank, List<Card>>> handIterator = hand.getHandInformation().getRankGroup().entrySet().iterator();
        final Iterator<Map.Entry<Card.Rank, List<Card>>> otherHandIterator = otherHand.getHandInformation().getRankGroup().entrySet().iterator();
        final int highPairComparison =
                Integer.compare(handIterator.next().getKey().getRankValue(), otherHandIterator.next().getKey().getRankValue());
        if (highPairComparison != 0) {
            return highPairComparison;
        }

        final int lowPairComparison =
                Integer.compare(handIterator.next().getKey().getRankValue(), otherHandIterator.next().getKey().getRankValue());

        if(lowPairComparison != 0) {
            return lowPairComparison;
        }

        return iterateAndCompareHighCard(handIterator, otherHandIterator);
    }

    static int comparePairHands(final FiveCardPokerHand hand,
                                final FiveCardPokerHand otherHand) {
        checkHandClassification(hand, FiveCardPokerHand.Classification.PAIR);
        checkHandClassification(otherHand, FiveCardPokerHand.Classification.PAIR);

        final Iterator<Map.Entry<Card.Rank, List<Card>>> handIterator = hand.getHandInformation().getRankGroup().entrySet().iterator();
        final Iterator<Map.Entry<Card.Rank, List<Card>>> otherHandIterator = otherHand.getHandInformation().getRankGroup().entrySet().iterator();
        final int highPairComparison =
                Integer.compare(handIterator.next().getKey().getRankValue(), otherHandIterator.next().getKey().getRankValue());

        if (highPairComparison != 0) {
            return highPairComparison;
        }

        return iterateAndCompareHighCard(handIterator, otherHandIterator);
    }

    static int compareHighCardHands(final FiveCardPokerHand hand,
                                    final FiveCardPokerHand otherHand) {
        checkHandClassification(hand, FiveCardPokerHand.Classification.HIGH_CARD);
        checkHandClassification(otherHand, FiveCardPokerHand.Classification.HIGH_CARD);

        final Iterator<Map.Entry<Card.Rank, List<Card>>> handIterator = hand.getHandInformation().getRankGroup().entrySet().iterator();
        final Iterator<Map.Entry<Card.Rank, List<Card>>> otherHandIterator = otherHand.getHandInformation().getRankGroup().entrySet().iterator();

        return iterateAndCompareHighCard(handIterator, otherHandIterator);
    }

    private static int iterateAndCompareHighCard(final Iterator<Map.Entry<Card.Rank, List<Card>>> handIterator,
                                                 final Iterator<Map.Entry<Card.Rank, List<Card>>> otherHandIterator) {
        while (handIterator.hasNext() && otherHandIterator.hasNext()) {
            final int rankComparison = Integer.compare(handIterator.next().getKey().getRankValue(),
                    otherHandIterator.next().getKey().getRankValue());
            if (rankComparison != 0) {
                return rankComparison;
            }
        }
        return TIE;
    }

    private static void checkHandClassification(final FiveCardPokerHand hand,
                                                final FiveCardPokerHand.Classification classification) {
        if(hand.getHandClassification() != classification) {
            throw new RuntimeException("Hand : " +hand+ " does not match expected classification " +classification);
        }
    }
}
