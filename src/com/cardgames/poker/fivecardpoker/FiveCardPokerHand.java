package com.cardgames.poker.fivecardpoker;

import com.cardgames.cards.Card;
import com.cardgames.cards.Rank;
import com.cardgames.poker.*;

import java.util.*;


public class FiveCardPokerHand implements Hand {

    private final HandAnalyzer handAnalyzer;

    private static final int POKER_HAND_SIZE = 5;

    FiveCardPokerHand(final Builder builder) {
        this.handAnalyzer = new FiveCardHandAnalyzer(builder.cards);
    }

    public HandAnalyzer getHandAnalyzer() {
        return this.handAnalyzer;
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
