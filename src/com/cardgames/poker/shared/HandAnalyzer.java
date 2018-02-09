package com.cardgames.poker.shared;

import com.cardgames.cards.Card;

import java.util.SortedSet;

public interface HandAnalyzer {

    SortedSet<Card> getCards();

    Classification getClassification();

    RankGroup getRankGroup();

    SuitGroup getSuitGroup();

}
