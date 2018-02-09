package com.cardgames.poker.holdem;

import com.cardgames.cards.Card;
import com.cardgames.poker.*;

import java.util.Collections;
import java.util.SortedSet;
import java.util.TreeSet;

public class HoldemHandAnalyzer implements HandAnalyzer {

    private final SortedSet<Card> holeCards;
    private final SortedSet<Card> communityCards;
    private final SortedSet<Card> combinedCards;
    private final Classification handClassification;
    private final RankGroup rankGroup;
    private final SuitGroup suitGroup;

    HoldemHandAnalyzer(final SortedSet<Card> holeCards,
                       final SortedSet<Card> communityCards) {
        this.holeCards = Collections.unmodifiableSortedSet(holeCards);
        this.communityCards = Collections.unmodifiableSortedSet(communityCards);
        this.combinedCards = init(holeCards, communityCards);
        this.rankGroup = new RankGroup(this.combinedCards);
        this.suitGroup = new SuitGroup(this.combinedCards);
        this.handClassification = PokerHandUtils.classifyPokerHand(getRankGroup(), getSuitGroup(), getCards());
    }

    @Override
    public Classification getClassification() {
        return this.handClassification;
    }

    @Override
    public RankGroup getRankGroup() {
        return this.rankGroup;
    }

    @Override
    public SuitGroup getSuitGroup() {
        return this.suitGroup;
    }

    @Override
    public SortedSet<Card> getCards() {
        return this.combinedCards;
    }

    public SortedSet<Card> getHoleCards() {
        return this.holeCards;
    }

    public SortedSet<Card> getCommunityCards() {
        return this.communityCards;
    }

    private static SortedSet<Card> init(final SortedSet<Card> holeCards,
                                        final SortedSet<Card> communityCards) {
        final SortedSet<Card> combinedCards = new TreeSet<>();
        combinedCards.addAll(holeCards);
        combinedCards.addAll(communityCards);
        return Collections.unmodifiableSortedSet(combinedCards);
    }

}
