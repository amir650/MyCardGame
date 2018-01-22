package com.cardgames.fivecardpoker;

import com.cardgames.*;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.SortedSet;
import java.util.stream.Collectors;

public class FiveCardHandClassifier implements HandClassifier {

    private final FiveCardHandAnalyzer handAnalyzer;

    FiveCardHandClassifier(final FiveCardHandAnalyzer handAnalyzer) {
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
        return isFlush() && isNormalStraight();
    }

    private boolean isStraightFlushWheel() {
        return isFlush() && isWheel();
    }

    private boolean isRoyalFlush() {
        final SortedSet<Card> cards = this.handAnalyzer.getCards();
        return  cards.containsAll(HandUtils.ROYAL_FLUSH_SPADES) ||
                cards.containsAll(HandUtils.ROYAL_FLUSH_HEARTS) ||
                cards.containsAll(HandUtils.ROYAL_FLUSH_CLUBS)  ||
                cards.containsAll(HandUtils.ROYAL_FLUSH_DIAMONDS);
    }
}
