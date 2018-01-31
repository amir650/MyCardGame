package com.cardgames.poker.holdem;

import com.cardgames.HandDetector;
import com.cardgames.poker.Classification;
import com.cardgames.poker.HandClassifier;
import com.cardgames.poker.PokerHandDetector;

public class HoldemHandClassifier implements HandClassifier {

    private final HandDetector handDetector;

    HoldemHandClassifier(final HoldemHandAnalyzer handAnalyzer) {
        this.handDetector = new PokerHandDetector(handAnalyzer);
    }

    @Override
    public Classification classifyHand() {
        return this.handDetector.detect();
    }

}
