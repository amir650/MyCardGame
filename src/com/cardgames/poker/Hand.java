package com.cardgames.poker;

import com.cardgames.cards.Card;
import com.cardgames.cards.Rank;

import java.util.Iterator;
import java.util.List;
import java.util.Map;

public interface Hand {

    Classification getClassification();

    HandAnalyzer getHandAnalyzer();

    Iterator<Map.Entry<Rank, List<Card>>> getHandRankIterator();

}
