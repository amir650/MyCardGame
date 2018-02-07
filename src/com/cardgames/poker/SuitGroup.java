package com.cardgames.poker;

import com.cardgames.cards.Card;
import com.cardgames.cards.Suit;

import java.util.*;
import java.util.stream.Collectors;

public class SuitGroup implements Iterable<Map.Entry<Suit, List<Card>>> {

    private final Map<Suit, List<Card>> suitMap;

    public SuitGroup(final SortedSet<Card> cards) {
        this.suitMap = initSuitGroup(cards);
    }

    Map<Suit, List<Card>> getSuitMap() {
        return this.suitMap;
    }

    @Override
    public Iterator<Map.Entry<Suit, List<Card>>> iterator() {
        return this.suitMap.entrySet().iterator();
    }

    private static Map<Suit, List<Card>> initSuitGroup(final SortedSet<Card> cards) {
        return Collections.unmodifiableMap(new TreeMap<>(cards.stream().collect(Collectors.groupingBy(Card::getSuit))));
    }

}
