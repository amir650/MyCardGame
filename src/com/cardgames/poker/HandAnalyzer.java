package com.cardgames.poker;

import com.cardgames.cards.Card;
import com.cardgames.cards.Rank;
import com.cardgames.cards.Suit;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.SortedSet;

public interface HandAnalyzer {

    SortedSet<Card> getCards();

    Map<Rank, List<Card>> getRankGroup();

    Map<Suit, List<Card>> getSuitGroup();

    int getQuadCount();

    int getSetCount();

    int getPairCount();

    default SortedSet<Card> getHoleCards() {
        return Collections.emptySortedSet();
    }

    default SortedSet<Card> getCommunityCards() {
        return Collections.emptySortedSet();
    }

    default int groupCount(final int groupSize) {
        return Math.toIntExact(getRankGroup().values().stream().filter(n -> n.size() == groupSize).count());
    }
}
