package com.cardgames.poker.holdem;

import com.cardgames.cards.Card;
import com.cardgames.cards.Rank;
import com.cardgames.cards.Suit;
import com.cardgames.poker.Classification;
import com.cardgames.poker.HandClassifier;
import com.cardgames.poker.PokerHandUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class HoldemHandClassifier implements HandClassifier {

    private final HoldemHandAnalyzer handAnalyzer;

    HoldemHandClassifier(final HoldemHandAnalyzer handAnalyzer) {
        this.handAnalyzer = handAnalyzer;
    }

    @Override
    public Classification classifyHand() {

        if (isRoyalFlush()) {
            return Classification.ROYAL_FLUSH;
        } else if (isStraightFlushWheel()) {
            return Classification.STRAIGHT_FLUSH_WHEEL;
        } else if (isStraightFlush()) {
            return Classification.STRAIGHT_FLUSH;
        } else if (isFourOfAKind()) {
            return Classification.FOUR_OF_A_KIND;
        } else if (isFullHouse()) {
            return Classification.FULL_HOUSE;
        } else if (isFlush()) {
            return Classification.FLUSH;
        } else if (isWheel()) {
            return Classification.WHEEL;
        } else if (isNormalStraight()) {
            return Classification.STRAIGHT;
        } else if (isSet()) {
            return Classification.SET;
        } else if (isTwoPair()) {
            return Classification.TWO_PAIR;
        } else if (isPair()) {
            return Classification.PAIR;
        }

        return Classification.HIGH_CARD;
    }

    private boolean isPair() {
        return this.handAnalyzer.getPairCount() == 1;
    }

    private boolean isTwoPair() {
        return this.handAnalyzer.getPairCount() == 2;
    }

    private boolean isSet() {
        return this.handAnalyzer.getSetCount() == 1;
    }

    private boolean isNormalStraight() {
        final Card[] cardArray = this.handAnalyzer.getCards().toArray(new Card[this.handAnalyzer.getCards().size()]);
        for (int i = 0; i < cardArray.length - 1; i++) {
            if (cardArray[i].getRank().getRankValue() != cardArray[i + 1].getRank().getRankValue() - 1) {
                return false;
            }
        }
        return true;
    }

    private boolean isWheel() {
        final List<Rank> ranks = this.handAnalyzer.getCards().stream().map(Card::getRank).collect(Collectors.toList());
        return ranks.containsAll(Arrays.asList(Rank.ACE, Rank.TWO, Rank.THREE, Rank.FOUR, Rank.FIVE));
    }

    private boolean isFlush() {
        final Map<Suit, List<Card>> suitGroup = this.handAnalyzer.getSuitGroup();
        for(Map.Entry<Suit, List<Card>> entry : suitGroup.entrySet()) {
            if(entry.getValue().size() == 5) {
                return true;
            }
        }
        return false;
    }

    private boolean isFullHouse() {
        return this.handAnalyzer.getPairCount() == 1 && this.handAnalyzer.getSetCount() == 1;
    }

    private boolean isFourOfAKind() {
        return this.handAnalyzer.getQuadCount() == 1;
    }

    private boolean isStraightFlush() {
        final Map<Suit, List<Card>> suitGroup = this.handAnalyzer.getSuitGroup();
        for(Map.Entry<Suit, List<Card>> entry : suitGroup.entrySet()) {
            if(entry.getValue().size() == 5) {
                final Card[] cardArray = entry.getValue().toArray(new Card[entry.getValue().size()]);
                for (int i = 0; i < cardArray.length - 1; i++) {
                    if (cardArray[i].getRank().getRankValue() != cardArray[i + 1].getRank().getRankValue() - 1) {
                        return false;
                    }
                }
            }
        }
        return false;
    }

    private boolean isStraightFlushWheel() {
        final List<Card> handRanks = new ArrayList<>(this.handAnalyzer.getCards());
        return handRanks.containsAll(PokerHandUtils.STRAIGHT_WHEEL_SPADES) ||
               handRanks.containsAll(PokerHandUtils.STRAIGHT_WHEEL_HEARTS) ||
               handRanks.containsAll(PokerHandUtils.STRAIGHT_WHEEL_CLUBS)  ||
               handRanks.containsAll(PokerHandUtils.STRAIGHT_WHEEL_DIAMONDS);
    }

    private boolean isRoyalFlush() {
        final List<Card> handRanks = new ArrayList<>(this.handAnalyzer.getCards());
        return handRanks.containsAll(PokerHandUtils.ROYAL_FLUSH_SPADES) ||
                handRanks.containsAll(PokerHandUtils.ROYAL_FLUSH_HEARTS) ||
                handRanks.containsAll(PokerHandUtils.ROYAL_FLUSH_CLUBS) ||
                handRanks.containsAll(PokerHandUtils.ROYAL_FLUSH_DIAMONDS);
    }
}
