package com.cardgames.poker.holdem;

import com.cardgames.poker.ClassificationRank;
import com.cardgames.cards.Deck;
import com.cardgames.poker.fivecardpoker.FiveCardPokerHand;

import java.util.Arrays;
import java.util.stream.IntStream;

public class Main {

    private static final int NUM_EXPERIMENTS = 1000000;

    public static void main(String[] args) {

        System.out.println("Running Experiments with Holdem poker...");

        final long startTime = System.currentTimeMillis();
        final int[] frequencyTable = new int[ClassificationRank.values().length];

        IntStream.range(0, NUM_EXPERIMENTS).mapToObj(i -> new FiveCardPokerHand.Builder()).forEach(builder -> {
            final Deck deck = Deck.newShuffledSingleDeck();
            final HoldemHand hand = new HoldemHand.Builder()
                    .addHoleCard(deck.deal())
                    .addHoleCard(deck.deal())
                    .addCommunityCard(deck.deal())
                    .addCommunityCard(deck.deal())
                    .addCommunityCard(deck.deal())
                    .addCommunityCard(deck.deal())
                    .addCommunityCard(deck.deal())
                    .build();

            final ClassificationRank classificationRank = hand.getHandAnalyzer().getClassification().getClassificationRank();
            frequencyTable[classificationRank.ordinal()]++;
            if(classificationRank == ClassificationRank.STRAIGHT_FLUSH) {
                System.out.println(hand);
            }
        });

        System.out.println("Finished experiment with " + NUM_EXPERIMENTS + " iterations in " + (System.currentTimeMillis() - startTime) + " milliseconds");
        System.out.println(Arrays.toString(frequencyTable) + "\n");
    }
}
