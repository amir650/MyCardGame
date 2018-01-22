package com.cardgames;

import java.util.Iterator;
import java.util.List;
import java.util.Map;

public interface Hand {

    Classification getClassification();

    HandAnalyzer getHandAnalyzer();

    HandClassifier getClassifier();

    Iterator<Map.Entry<Rank, List<Card>>> getHandRankIterator();

}
