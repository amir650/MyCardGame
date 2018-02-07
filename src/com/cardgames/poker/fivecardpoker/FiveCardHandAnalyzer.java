package com.cardgames.poker.fivecardpoker;

import com.cardgames.cards.Card;
import com.cardgames.poker.*;

import java.util.Collections;
import java.util.SortedSet;

public class FiveCardHandAnalyzer implements HandAnalyzer {

    private final SortedSet<Card> cards;
    private final Classification handClassification;
    private final RankGroup rankGroup;
    private final SuitGroup suitGroup;

    FiveCardHandAnalyzer(final SortedSet<Card> cards) {
        this.cards = Collections.unmodifiableSortedSet(cards);
        this.rankGroup = new RankGroup(this.cards);
        this.suitGroup = new SuitGroup(this.cards);
        this.handClassification = PokerHandUtils.classifyPokerHand(this.rankGroup, this.suitGroup, this.cards);
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
        return this.cards;
    }

}
