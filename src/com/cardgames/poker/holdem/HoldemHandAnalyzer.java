package com.cardgames.poker.holdem;

import com.cardgames.cards.Card;
import com.cardgames.cards.Rank;
import com.cardgames.cards.Suit;
import com.cardgames.poker.HandAnalyzer;
import com.cardgames.poker.PokerHandUtils;

import java.util.*;

public class HoldemHandAnalyzer implements HandAnalyzer {

    private final SortedSet<Card> holeCards;
    private final SortedSet<Card> communityCards;
    private final SortedSet<Card> combinedCards;
    private final Map<Rank, List<Card>> rankGroup;
    private final Map<Suit, List<Card>> suitGroup;
    private final int quadCount;
    private final int setCount;
    private final int pairCount;

    HoldemHandAnalyzer(final SortedSet<Card> holeCards,
                       final SortedSet<Card> communityCards) {
        this.holeCards = Collections.unmodifiableSortedSet(holeCards);
        this.communityCards = Collections.unmodifiableSortedSet(communityCards);
        this.combinedCards = init(holeCards, communityCards);
        this.rankGroup = PokerHandUtils.initRankGroup(this.combinedCards);
        this.suitGroup = PokerHandUtils.initSuitGroup(this.combinedCards);
        this.quadCount = groupCount(4);
        this.setCount = groupCount(3);
        this.pairCount = groupCount(2);
    }

    @Override
    public Map<Rank, List<Card>> getRankGroup() {
        return this.rankGroup;
    }

    @Override
    public Map<Suit, List<Card>> getSuitGroup() {
        return this.suitGroup;
    }

    @Override
    public SortedSet<Card> getCards() {
        return this.combinedCards;
    }

    @Override
    public int getQuadCount() {
        return this.quadCount;
    }

    @Override
    public int getSetCount() {
        return this.setCount;
    }

    @Override
    public int getPairCount() {
        return this.pairCount;
    }

    @Override
    public SortedSet<Card> getHoleCards() {
        return this.holeCards;
    }

    @Override
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
