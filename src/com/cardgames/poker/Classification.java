package com.cardgames.poker;

import com.cardgames.cards.Card;

import java.util.SortedSet;

public class Classification {

    private final ClassificationRank classificationRank;
    private final SortedSet<Card> cards;

    Classification(final ClassificationRank classificationRank,
                          final SortedSet<Card> cards) {
        this.classificationRank = classificationRank;
        this.cards = cards;
    }

    public SortedSet<Card> getCards() {
        return this.cards;
    }

    public ClassificationRank getClassificationRank() {
        return this.classificationRank;
    }
}