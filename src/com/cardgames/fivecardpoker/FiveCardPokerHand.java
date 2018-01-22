package com.cardgames.fivecardpoker;

import com.cardgames.*;

import java.util.*;


public class FiveCardPokerHand implements Hand {

    private final FiveCardHandAnalyzer handAnalyzer;
    private final FiveCardHandClassifier handClassifier;
    private final Classification handClassification;

    private static final int POKER_HAND_SIZE = 5;

    FiveCardPokerHand(final Builder builder) {
        this.handAnalyzer = new FiveCardHandAnalyzer(builder.cards);
        this.handClassifier = new FiveCardHandClassifier(this.handAnalyzer);
        this.handClassification = this.handClassifier.classifyHand();
    }

    @Override
    public Classification getClassification() {
        return this.handClassification;
    }

    public FiveCardHandAnalyzer getHandAnalyzer() {
        return this.handAnalyzer;
    }

    @Override
    public HandClassifier getClassifier() {
        return this.handClassifier;
    }

    public Iterator<Map.Entry<Rank, List<Card>>> getHandRankIterator() {
        return this.handAnalyzer.getRankGroup().entrySet().iterator();
    }

    @Override
    public String toString() {
        return this.getHandAnalyzer().getCards().toString();
    }

    public static class Builder {

        private SortedSet<Card> cards;

        public Builder() {
            this.cards = new TreeSet<>();
        }

        public Builder addCard(final Optional<Card> card) {
            this.cards.add(card.orElseThrow(IllegalStateException::new));
            return this;
        }

        public FiveCardPokerHand build() {
            if (this.cards.size() != POKER_HAND_SIZE) {
                throw new RuntimeException("Invalid hand size for hand " + this.cards.toString());
            }
            return new FiveCardPokerHand(this);
        }

    }
}
