package com.cardgames.poker.holdem;

import com.cardgames.poker.Classification;
import com.cardgames.cards.Deck;
import com.cardgames.poker.fivecardpoker.FiveCardPokerHand;

import java.util.Arrays;
import java.util.stream.IntStream;

public class Main {

    private static final int NUM_EXPERIMENTS = 100000;

    public static void main(String[] args) {

        final long startTime = System.currentTimeMillis();
        final int[] frequencyTable = new int[Classification.values().length];

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

            final Classification classification = hand.getClassification();
            frequencyTable[classification.ordinal()]++;
            if(classification == Classification.STRAIGHT_FLUSH) {
                System.out.println(hand);
            }
        });

        System.out.println("Finished experiment with " + NUM_EXPERIMENTS + " iterations in " + (System.currentTimeMillis() - startTime) + " milliseconds");
        System.out.println(Arrays.toString(frequencyTable) + "\n");
    }
}
