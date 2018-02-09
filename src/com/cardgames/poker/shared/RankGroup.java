package com.cardgames.poker.shared;

import com.cardgames.cards.Card;
import com.cardgames.cards.Rank;

import java.util.*;
import java.util.stream.Collectors;

public class RankGroup implements Iterable<Map.Entry<Rank, List<Card>>> {

    private final Map<Rank, List<Card>> rankMap;
    private final int quadCount;
    private final int setCount;
    private final int pairCount;

    public RankGroup(final SortedSet<Card> cards) {
        this.rankMap = initRankGroup(cards);
        this.quadCount = groupCount(4);
        this.setCount = groupCount(3);
        this.pairCount = groupCount(2);
    }

    Map<Rank, List<Card>> getRankMap() {
        return this.rankMap;
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

        return Collections.unmodifiableMap(sortedResults);
    }

    private int groupCount(final int groupSize) {
        return Math.toIntExact(this.rankMap.values().stream().filter(n -> n.size() == groupSize).count());
    }

    @Override
    public Iterator<Map.Entry<Rank, List<Card>>> iterator() {
        return this.rankMap.entrySet().iterator();
    }
}
