package com.cardgames.poker;

import com.cardgames.cards.Card;
import com.cardgames.cards.Rank;
import com.cardgames.cards.Suit;

import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public enum PokerHandUtils {
;   //no instance
    public static final int TIE = 0;

    public static final List<Card> ROYAL_FLUSH_SPADES = Arrays.asList(new Card(Rank.ACE, Suit.SPADES),
                                                                      new Card(Rank.KING, Suit.SPADES),
                                                                      new Card(Rank.QUEEN, Suit.SPADES),
                                                                      new Card(Rank.JACK, Suit.SPADES),
                                                                      new Card(Rank.TEN, Suit.SPADES));


    public static final List<Card> ROYAL_FLUSH_HEARTS = Arrays.asList(new Card(Rank.ACE, Suit.HEARTS),
                                                                      new Card(Rank.KING, Suit.HEARTS),
                                                                      new Card(Rank.QUEEN, Suit.HEARTS),
                                                                      new Card(Rank.JACK, Suit.HEARTS),
                                                                      new Card(Rank.TEN, Suit.HEARTS));

    public static final List<Card> ROYAL_FLUSH_CLUBS = Arrays.asList(new Card(Rank.ACE, Suit.CLUBS),
                                                                     new Card(Rank.KING, Suit.CLUBS),
                                                                     new Card(Rank.QUEEN, Suit.CLUBS),
                                                                     new Card(Rank.JACK, Suit.CLUBS),
                                                                     new Card(Rank.TEN, Suit.CLUBS));

    public static final List<Card> ROYAL_FLUSH_DIAMONDS = Arrays.asList(new Card(Rank.ACE, Suit.DIAMONDS),
                                                                        new Card(Rank.KING, Suit.DIAMONDS),
                                                                        new Card(Rank.QUEEN, Suit.DIAMONDS),
                                                                        new Card(Rank.JACK, Suit.DIAMONDS),
                                                                        new Card(Rank.TEN, Suit.DIAMONDS));

    public static final List<Card> STRAIGHT_WHEEL_SPADES = Arrays.asList(new Card(Rank.ACE, Suit.SPADES),
                                                                         new Card(Rank.TWO, Suit.SPADES),
                                                                         new Card(Rank.THREE, Suit.SPADES),
                                                                         new Card(Rank.FOUR, Suit.SPADES),
                                                                         new Card(Rank.FIVE, Suit.SPADES));

    public static final List<Card> STRAIGHT_WHEEL_HEARTS = Arrays.asList(new Card(Rank.ACE, Suit.HEARTS),
                                                                         new Card(Rank.TWO, Suit.HEARTS),
                                                                         new Card(Rank.THREE, Suit.HEARTS),
                                                                         new Card(Rank.FOUR, Suit.HEARTS),
                                                                         new Card(Rank.FIVE, Suit.HEARTS));

    public static final List<Card> STRAIGHT_WHEEL_CLUBS = Arrays.asList(new Card(Rank.ACE, Suit.CLUBS),
                                                                        new Card(Rank.TWO, Suit.CLUBS),
                                                                        new Card(Rank.THREE, Suit.CLUBS),
                                                                        new Card(Rank.FOUR, Suit.CLUBS),
                                                                        new Card(Rank.FIVE, Suit.CLUBS));

    public static final List<Card> STRAIGHT_WHEEL_DIAMONDS = Arrays.asList(new Card(Rank.ACE, Suit.DIAMONDS),
                                                                           new Card(Rank.TWO, Suit.DIAMONDS),
                                                                           new Card(Rank.THREE, Suit.DIAMONDS),
                                                                           new Card(Rank.FOUR, Suit.DIAMONDS),
                                                                           new Card(Rank.FIVE, Suit.DIAMONDS));

    private static int iterateAndCompareHighCard(final Iterator<Map.Entry<Rank, List<Card>>> handIterator,
                                                 final Iterator<Map.Entry<Rank, List<Card>>> otherHandIterator) {
        while (handIterator.hasNext() && otherHandIterator.hasNext()) {
            final int rankComparison = Integer.compare(handIterator.next().getKey().getRankValue(),
                    otherHandIterator.next().getKey().getRankValue());
            if (rankComparison != 0) {
                return rankComparison;
            }
        }
        return TIE;
    }

    public static void checkHandClassification(final Hand hand,
                                                final Classification classification) {
        if(hand.getClassification() != classification) {
            throw new RuntimeException("Hand : " +hand+ " does not match expected classification " +classification);
        }
    }

}
