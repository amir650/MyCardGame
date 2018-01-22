package com.cardgames.fivecardpoker;

import com.cardgames.Card;
import com.cardgames.HandAnalyzer;
import com.cardgames.Rank;
import com.cardgames.Suit;

import java.util.*;
import java.util.stream.Collectors;

public class FiveCardHandAnalyzer implements HandAnalyzer {

    private final SortedSet<Card> cards;
    private final Map<Rank, List<Card>> rankGroup;
    private final Map<Suit, List<Card>> suitGroup;
    private final int quadCount;
    private final int setCount;
    private final int pairCount;

    FiveCardHandAnalyzer(final SortedSet<Card> cards) {
        this.cards = Collections.unmodifiableSortedSet(cards);
        this.rankGroup = initRankGroup(cards);
        this.suitGroup = initSuitGroup(cards);
        this.quadCount = groupCount(4);
        this.setCount = groupCount(3);
        this.pairCount = groupCount(2);
    }

    Map<Rank, List<Card>> getRankGroup() {
        return this.rankGroup;
    }

    Map<Suit, List<Card>> getSuitGroup() {
        return this.suitGroup;
    }

    public SortedSet<Card> getCards() {
        return this.cards;
    }

    int getQuadCount() {
        return this.quadCount;
    }

    int getSetCount() {
        return this.setCount;
    }

    int getPairCount() {
        return this.pairCount;
    }

    private static Map<Rank, List<Card>> initRankGroup(final SortedSet<Card> cards) {

        final Comparator<Map.Entry<Rank, List<Card>>> valueComparator =
                (o1, o2) -> o2.getValue().size() == o1.getValue().size() ? o2.getKey().getRankValue() - o1.getKey().getRankValue() :
                        o2.getValue().size() - o1.getValue().size();

        final List<Map.Entry<Rank, List<Card>>> listOfEntries =
                new ArrayList<>(cards.stream().collect(Collectors.groupingBy(Card::getRank)).entrySet());

        listOfEntries.sort(valueComparator);

        final LinkedHashMap<Rank, List<Card>> sortedResults = new LinkedHashMap<>();

        for (final Map.Entry<Rank, List<Card>> entry : listOfEntries) {
            sortedResults.put(entry.getKey(), entry.getValue());
        }

        return sortedResults;
    }

    private static Map<Suit, List<Card>> initSuitGroup(final SortedSet<Card> cards) {
        return new TreeMap<>(cards.stream().collect(Collectors.groupingBy(Card::getSuit)));
    }

    private int groupCount(final int count) {
        int matches = 0;
        for (final Map.Entry<Rank, List<Card>> entry : this.rankGroup.entrySet()) {
            if (entry.getValue().size() == count) {
                matches++;
            }
        }
        return matches;
    }
}
