package com.cardgames.poker.fivecardpoker;

import com.cardgames.HandDetector;
import com.cardgames.poker.*;

public class FiveCardHandClassifier implements HandClassifier {

    private final HandDetector handDetector;

    FiveCardHandClassifier(final HandAnalyzer handAnalyzer) {
        this.handDetector = new PokerHandDetector(handAnalyzer);
    }

    @Override
    public Classification classifyHand() {
        return this.handDetector.detect();
    }

}
