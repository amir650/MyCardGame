package com.cardgames.poker.fivecardpoker;

import com.cardgames.cards.Card;
import com.cardgames.poker.HandAnalyzer;
import com.cardgames.cards.Rank;
import com.cardgames.cards.Suit;
import com.cardgames.poker.PokerHandUtils;

import java.util.*;

public class FiveCardHandAnalyzer implements HandAnalyzer {

    private final SortedSet<Card> cards;
    private final Map<Rank, List<Card>> rankGroup;
    private final Map<Suit, List<Card>> suitGroup;
    private final int quadCount;
    private final int setCount;
    private final int pairCount;

    FiveCardHandAnalyzer(final SortedSet<Card> cards) {
        this.cards = Collections.unmodifiableSortedSet(cards);
        this.rankGroup = PokerHandUtils.initRankGroup(cards);
        this.suitGroup = PokerHandUtils.initSuitGroup(cards);
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
        return this.cards;
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

}
