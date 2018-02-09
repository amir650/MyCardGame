package com.cardgames.poker.holdem;

import com.cardgames.cards.Card;
import com.cardgames.poker.shared.Hand;

import java.util.Optional;
import java.util.Set;
import java.util.SortedSet;
import java.util.TreeSet;
import java.util.stream.Collectors;
import java.util.stream.Stream;


public class HoldemHand implements Hand {

    private final HoldemHandAnalyzer handAnalyzer;

    private static final int NUM_HOLE_CARDS = 2;
    private static final int NUM_COMMUNITY_CARDS = 5;

    HoldemHand(final Builder builder) {
        this.handAnalyzer = new com.cardgames.poker.holdem.HoldemHandAnalyzer(builder.holeCards, builder.communityCards);
    }

    @Override
    public HoldemHandAnalyzer getHandAnalyzer() {
        return this.handAnalyzer;
    }

    @Override
    public String toString() {
        return this.handAnalyzer.getClassification() + " : hole cards " +
               getHandAnalyzer().getHoleCards().toString() + " : community cards " +
                "" +
               getHandAnalyzer().getCommunityCards().toString();
    }

    public static class Builder {

        private SortedSet<Card> holeCards;
        private SortedSet<Card> communityCards;

        public Builder() {
            this.holeCards = new TreeSet<>();
            this.communityCards = new TreeSet<>();
        }

        public Builder addHoleCard(final Optional<Card> card) {
            this.holeCards.add(card.orElseThrow(IllegalStateException::new));
            return this;
        }

        public Builder addCommunityCard(final Optional<Card> card) {
            this.communityCards.add(card.orElseThrow(IllegalStateException::new));
            return this;
        }

        public Builder addCommunityCards(final Set<Optional<Card>> cards) {
            final Stream<Card> cardStream = cards.
                                            stream().
                                            flatMap(optionalCard -> optionalCard.map((Stream::of)).orElseThrow(IllegalStateException::new));
            this.communityCards.addAll(cardStream.collect(Collectors.toSet()));
            return this;
        }

        public HoldemHand build() {
            if (this.holeCards.size() != NUM_HOLE_CARDS) {
                throw new RuntimeException("Invalid hand size for hand " + this.holeCards.toString());
            }
            if (this.communityCards.size() != NUM_COMMUNITY_CARDS) {
                throw new RuntimeException("Invalid community holeCards! " + this.communityCards.toString());
            }
            return new HoldemHand(this);
        }

    }
}
