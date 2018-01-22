package com.cardgames.poker.holdem;

import com.cardgames.cards.Card;
import com.cardgames.poker.Classification;
import com.cardgames.poker.Hand;
import com.cardgames.cards.Rank;

import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class HoldemHandComparator implements Comparator<HoldemHand> {

    @Override
    public int compare(final HoldemHand hand,
                       final HoldemHand otherHand) {

        final int classificationComparison =
                Integer.compare(hand.getClassification().getValue(), otherHand.getClassification().getValue());

        if(classificationComparison != 0) {
            return classificationComparison;
        }

        final int tieBreaker;
        switch (hand.getClassification()) {
            case ROYAL_FLUSH:
                tieBreaker = compareRoyalFlushHands(hand, otherHand);
                break;
            case STRAIGHT_FLUSH:
                tieBreaker = compareStraightFlushHands(hand, otherHand);
                break;
            case STRAIGHT_FLUSH_WHEEL:
                tieBreaker = compareStraightFlushWheelHands(hand, otherHand);
                break;
            case FOUR_OF_A_KIND:
                tieBreaker = compareQuadsHands(hand, otherHand);
                break;
            case FULL_HOUSE:
                tieBreaker = compareFullHouseHands(hand, otherHand);
                break;
            case FLUSH:
                tieBreaker = compareFlushHands(hand, otherHand);
                break;
            case STRAIGHT:
                tieBreaker = compareStraightHands(hand, otherHand);
                break;
            case WHEEL:
                tieBreaker = compareWheelHands(hand, otherHand);
                break;
            case SET:
                tieBreaker = compareSetHands(hand, otherHand);
                break;
            case TWO_PAIR:
                tieBreaker = compareTwoPairHands(hand, otherHand);
                break;
            case PAIR:
                tieBreaker = comparePairHands(hand, otherHand);
                break;
            case HIGH_CARD:
                tieBreaker = compareHighCardHands(hand, otherHand);
                break;
            default:
                throw new RuntimeException("wtf");
        }

        return tieBreaker;
    }

    private static int compareRoyalFlushHands(final Hand hand,
                                              final Hand otherHand) {
        checkHandClassification(hand, Classification.ROYAL_FLUSH);
        checkHandClassification(otherHand, Classification.ROYAL_FLUSH);

        return 0;
    }

    private static int compareStraightFlushHands(final Hand hand,
                                                 final Hand otherHand) {
        checkHandClassification(hand, Classification.STRAIGHT_FLUSH);
        checkHandClassification(otherHand, Classification.STRAIGHT_FLUSH);

        return Integer.compare(hand.getHandAnalyzer().getCards().last().getRank().getRankValue(),
                otherHand.getHandAnalyzer().getCards().last().getRank().getRankValue());
    }

    private static int compareStraightFlushWheelHands(final Hand hand,
                                                      final Hand otherHand) {
        checkHandClassification(hand, Classification.STRAIGHT_FLUSH_WHEEL);
        checkHandClassification(otherHand, Classification.STRAIGHT_FLUSH_WHEEL);

        return 0;
    }

    private static int compareQuadsHands(final Hand hand,
                                         final Hand otherHand) {
        checkHandClassification(hand, Classification.FOUR_OF_A_KIND);
        checkHandClassification(otherHand, Classification.FOUR_OF_A_KIND);

        final Iterator<Map.Entry<Rank, List<Card>>> handIterator = hand.getHandRankIterator();
        final Iterator<Map.Entry<Rank, List<Card>>> otherHandIterator = otherHand.getHandRankIterator();

        final int quadsCompare = Integer.compare(handIterator.next().getKey().getRankValue(),
                otherHandIterator.next().getKey().getRankValue());

        if (quadsCompare != 0) {
            return quadsCompare;
        }

        return iterateAndCompareHighCard(handIterator, otherHandIterator);
    }

    private static int compareFullHouseHands(final Hand hand,
                                             final Hand otherHand) {
        checkHandClassification(hand, Classification.FULL_HOUSE);
        checkHandClassification(otherHand, Classification.FULL_HOUSE);

        final Iterator<Map.Entry<Rank, List<Card>>> handIterator = hand.getHandRankIterator();
        final Iterator<Map.Entry<Rank, List<Card>>> otherHandIterator = otherHand.getHandRankIterator();
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

    private static int compareFlushHands(final Hand hand,
                                         final Hand otherHand) {
        checkHandClassification(hand, Classification.FLUSH);
        checkHandClassification(otherHand, Classification.FLUSH);

        final Iterator<Map.Entry<Rank, List<Card>>> handIterator = hand.getHandRankIterator();
        final Iterator<Map.Entry<Rank, List<Card>>> otherHandIterator = otherHand.getHandRankIterator();

        return iterateAndCompareHighCard(handIterator, otherHandIterator);
    }

    private static int compareStraightHands(final Hand hand,
                                            final Hand otherHand) {
        return Integer.compare(hand.getHandAnalyzer().getCards().last().getRank().getRankValue(),
                otherHand.getHandAnalyzer().getCards().last().getRank().getRankValue());
    }

    private static int compareWheelHands(final Hand hand,
                                         final Hand otherHand) {
        checkHandClassification(hand, Classification.WHEEL);
        checkHandClassification(otherHand, Classification.WHEEL);

        return 0;
    }

    private static int compareSetHands(final Hand hand,
                                       final Hand otherHand) {
        checkHandClassification(hand, Classification.SET);
        checkHandClassification(otherHand, Classification.SET);

        final Iterator<Map.Entry<Rank, List<Card>>> handIterator = hand.getHandRankIterator();
        final Iterator<Map.Entry<Rank, List<Card>>> otherHandIterator = otherHand.getHandRankIterator();
        final int setCompare = Integer.compare(handIterator.next().getKey().getRankValue(),
                otherHandIterator.next().getKey().getRankValue());

        if (setCompare != 0) {
            return setCompare;
        }

        throw new RuntimeException("should not reach here in 5 card poker : " + hand + " vs " + otherHand);
    }

    private static int compareTwoPairHands(final Hand hand,
                                           final Hand otherHand) {
        checkHandClassification(hand, Classification.TWO_PAIR);
        checkHandClassification(otherHand, Classification.TWO_PAIR);

        final Iterator<Map.Entry<Rank, List<Card>>> handIterator = hand.getHandRankIterator();
        final Iterator<Map.Entry<Rank, List<Card>>> otherHandIterator = otherHand.getHandRankIterator();
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

    private static int comparePairHands(final Hand hand,
                                        final Hand otherHand) {
        checkHandClassification(hand, Classification.PAIR);
        checkHandClassification(otherHand, Classification.PAIR);

        final Iterator<Map.Entry<Rank, List<Card>>> handIterator = hand.getHandRankIterator();
        final Iterator<Map.Entry<Rank, List<Card>>> otherHandIterator = otherHand.getHandRankIterator();
        final int highPairComparison =
                Integer.compare(handIterator.next().getKey().getRankValue(), otherHandIterator.next().getKey().getRankValue());

        if (highPairComparison != 0) {
            return highPairComparison;
        }

        return iterateAndCompareHighCard(handIterator, otherHandIterator);
    }

    private static int compareHighCardHands(final Hand hand,
                                            final Hand otherHand) {
        checkHandClassification(hand, Classification.HIGH_CARD);
        checkHandClassification(otherHand, Classification.HIGH_CARD);

        final Iterator<Map.Entry<Rank, List<Card>>> handIterator = hand.getHandRankIterator();
        final Iterator<Map.Entry<Rank, List<Card>>> otherHandIterator = otherHand.getHandRankIterator();

        return iterateAndCompareHighCard(handIterator, otherHandIterator);
    }

    private static int iterateAndCompareHighCard(final Iterator<Map.Entry<Rank, List<Card>>> handIterator,
                                                 final Iterator<Map.Entry<Rank, List<Card>>> otherHandIterator) {
        while (handIterator.hasNext() && otherHandIterator.hasNext()) {
            final int rankComparison = Integer.compare(handIterator.next().getKey().getRankValue(),
                    otherHandIterator.next().getKey().getRankValue());
            if (rankComparison != 0) {
                return rankComparison;
            }
        }
        return 0;
    }

    private static void checkHandClassification(final Hand hand,
                                                final Classification classification) {
        if(hand.getClassification() != classification) {
            throw new RuntimeException("Hand : " +hand+ " does not match expected classification " +classification);
        }
    }
}
