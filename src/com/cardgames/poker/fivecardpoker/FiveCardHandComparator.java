package com.cardgames.poker.fivecardpoker;

import com.cardgames.cards.Card;
import com.cardgames.poker.Hand;
import com.cardgames.cards.Rank;
import com.cardgames.poker.Classification;
import com.cardgames.poker.PokerHandUtils;

import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class FiveCardHandComparator implements Comparator<FiveCardPokerHand> {

    @Override
    public int compare(final FiveCardPokerHand hand,
                       final FiveCardPokerHand otherHand) {

        final int classificationComparison =
                Integer.compare(hand.getClassification().getValue(), otherHand.getClassification().getValue());

        if(classificationComparison != 0) {
            return classificationComparison;
        }

        final int tiebreaker;
        switch (hand.getClassification()) {
            case ROYAL_FLUSH:
                tiebreaker = compareRoyalFlushHands(hand, otherHand);
                break;
            case STRAIGHT_FLUSH:
                tiebreaker = compareStraightFlushHands(hand, otherHand);
                break;
            case STRAIGHT_FLUSH_WHEEL:
                tiebreaker = compareStraightFlushWheelHands(hand, otherHand);
                break;
            case FOUR_OF_A_KIND:
                tiebreaker = compareQuadsHands(hand, otherHand);
                break;
            case FULL_HOUSE:
                tiebreaker = compareFullHouseHands(hand, otherHand);
                break;
            case FLUSH:
                tiebreaker = compareFlushHands(hand, otherHand);
                break;
            case STRAIGHT:
                tiebreaker = compareStraightHands(hand, otherHand);
                break;
            case WHEEL:
                tiebreaker = compareWheelHands(hand, otherHand);
                break;
            case SET:
                tiebreaker = compareSetHands(hand, otherHand);
                break;
            case TWO_PAIR:
                tiebreaker = compareTwoPairHands(hand, otherHand);
                break;
            case PAIR:
                tiebreaker = comparePairHands(hand, otherHand);
                break;
            case HIGH_CARD:
                tiebreaker = compareHighCardHands(hand, otherHand);
                break;
            default:
                throw new RuntimeException("wtf");
        }

        return tiebreaker;
    }

    private static int compareRoyalFlushHands(final Hand hand,
                                              final Hand otherHand) {
        PokerHandUtils.checkHandClassification(hand, Classification.ROYAL_FLUSH);
        PokerHandUtils.checkHandClassification(otherHand, Classification.ROYAL_FLUSH);

        return PokerHandUtils.TIE;
    }

    private static int compareStraightFlushHands(final Hand hand,
                                                 final Hand otherHand) {
        PokerHandUtils.checkHandClassification(hand, Classification.STRAIGHT_FLUSH);
        PokerHandUtils.checkHandClassification(otherHand, Classification.STRAIGHT_FLUSH);

        return Integer.compare(hand.getHandAnalyzer().getCards().last().getRank().getRankValue(),
                               otherHand.getHandAnalyzer().getCards().last().getRank().getRankValue());
    }

    private static int compareStraightFlushWheelHands(final Hand hand,
                                                     final Hand otherHand) {
        PokerHandUtils.checkHandClassification(hand, Classification.STRAIGHT_FLUSH_WHEEL);
        PokerHandUtils.checkHandClassification(otherHand, Classification.STRAIGHT_FLUSH_WHEEL);

        return PokerHandUtils.TIE;
    }

    private static int compareQuadsHands(final Hand hand,
                                        final Hand otherHand) {
        PokerHandUtils.checkHandClassification(hand, Classification.FOUR_OF_A_KIND);
        PokerHandUtils.checkHandClassification(otherHand, Classification.FOUR_OF_A_KIND);

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
        PokerHandUtils.checkHandClassification(hand, Classification.FULL_HOUSE);
        PokerHandUtils.checkHandClassification(otherHand, Classification.FULL_HOUSE);

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
        PokerHandUtils.checkHandClassification(hand, Classification.FLUSH);
        PokerHandUtils.checkHandClassification(otherHand, Classification.FLUSH);

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
        PokerHandUtils.checkHandClassification(hand, Classification.WHEEL);
        PokerHandUtils.checkHandClassification(otherHand, Classification.WHEEL);

        return PokerHandUtils.TIE;
    }

    private static int compareSetHands(final Hand hand,
                                      final Hand otherHand) {
        PokerHandUtils.checkHandClassification(hand, Classification.SET);
        PokerHandUtils.checkHandClassification(otherHand, Classification.SET);

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
        PokerHandUtils.checkHandClassification(hand, Classification.TWO_PAIR);
        PokerHandUtils.checkHandClassification(otherHand, Classification.TWO_PAIR);

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
        PokerHandUtils.checkHandClassification(hand, Classification.PAIR);
        PokerHandUtils.checkHandClassification(otherHand, Classification.PAIR);

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
        PokerHandUtils.checkHandClassification(hand, Classification.HIGH_CARD);
        PokerHandUtils.checkHandClassification(otherHand, Classification.HIGH_CARD);

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
        return PokerHandUtils.TIE;
    }

}
