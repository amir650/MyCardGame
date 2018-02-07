package com.cardgames.poker.fivecardpoker;

import com.cardgames.poker.ClassificationRank;
import com.cardgames.cards.Deck;
import com.cardgames.poker.FiveCardHandComparator;

import java.util.Arrays;
import java.util.stream.IntStream;

public class Main {

    private static final int NUM_EXPERIMENTS = 10000000;

    public static void main(String[] args) {
        System.out.println("Running Experiments with 5 card poker...");
        runExp1();
        runExp2();
    }

    private static void runExp1() {
        final long startTime = System.currentTimeMillis();
        final int[] frequencyTable = new int[ClassificationRank.values().length];

        IntStream.range(0, NUM_EXPERIMENTS).mapToObj(i -> new FiveCardPokerHand.Builder()).forEach(builder -> {
            final Deck deck = Deck.newShuffledSingleDeck();
            builder.addCard(deck.deal());
            builder.addCard(deck.deal());
            builder.addCard(deck.deal());
            builder.addCard(deck.deal());
            builder.addCard(deck.deal());
            final FiveCardPokerHand hand = builder.build();
            final ClassificationRank classificationRank = hand.getHandAnalyzer().getClassification().getClassificationRank();
            frequencyTable[classificationRank.ordinal()]++;
        });

        System.out.println("Finished experiment with " + NUM_EXPERIMENTS + " iterations in " + (System.currentTimeMillis() - startTime) + " milliseconds");
        System.out.println(Arrays.toString(frequencyTable) + "\n");
    }

    private static void runExp2() {
        final long startTime = System.currentTimeMillis();
        final int[] frequencyTable = new int[3];
        final FiveCardHandComparator comparator = new FiveCardHandComparator();

        IntStream.range(0, NUM_EXPERIMENTS).forEach(i -> {
            final Deck deck = Deck.newShuffledSingleDeck();
            FiveCardPokerHand.Builder builder = new FiveCardPokerHand.Builder();
            builder.addCard(deck.deal());
            builder.addCard(deck.deal());
            builder.addCard(deck.deal());
            builder.addCard(deck.deal());
            builder.addCard(deck.deal());
            final FiveCardPokerHand hand = builder.build();

            FiveCardPokerHand.Builder builder2 = new FiveCardPokerHand.Builder();
            builder2.addCard(deck.deal());
            builder2.addCard(deck.deal());
            builder2.addCard(deck.deal());
            builder2.addCard(deck.deal());
            builder2.addCard(deck.deal());
            final FiveCardPokerHand hand2 = builder2.build();

            final int comparison = comparator.compare(hand, hand2);

            if(comparison < 0) {
                frequencyTable[0]++;
            } else if(comparison == 0) {
                frequencyTable[1]++;
            } else if(comparison > 0) {
                frequencyTable[2]++;
            } else {
                throw new RuntimeException("WTF");
            }

        });

        System.out.println("Finished experiment with " + NUM_EXPERIMENTS + " iterations in " + (System.currentTimeMillis() - startTime) + " milliseconds");
        System.out.println(Arrays.toString(frequencyTable));
    }

}
