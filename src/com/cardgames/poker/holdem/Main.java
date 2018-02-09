package com.cardgames.poker.holdem;

import com.cardgames.cards.Card;
import com.cardgames.cards.Deck;
import com.cardgames.cards.Rank;
import com.cardgames.poker.ClassificationRank;
import com.cardgames.poker.fivecardpoker.FiveCardPokerHand;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Main {

    private static final int NUM_EXPERIMENTS = 1000000 * 10;

    public static void main(String[] args) {

        System.out.println("Running Experiments with Holdem poker...");
        runExp1();
        runExp2();

    }

    private static void runExp1() {
        final long startTime = System.currentTimeMillis();
        final int[] frequencyTable = new int[ClassificationRank.values().length];

        IntStream.range(0, NUM_EXPERIMENTS).mapToObj(i -> new HoldemHand.Builder()).forEach(builder -> {
            final Deck deck = Deck.newShuffledSingleDeck();
            final HoldemHand hand = builder.addHoleCard(deck.deal())
                   .addHoleCard(deck.deal())
                   .addCommunityCard(deck.deal())
                   .addCommunityCard(deck.deal())
                   .addCommunityCard(deck.deal())
                   .addCommunityCard(deck.deal())
                   .addCommunityCard(deck.deal())
                   .build();

            final ClassificationRank classificationRank = hand.getHandAnalyzer().getClassification().getClassificationRank();
            frequencyTable[classificationRank.ordinal()]++;
        });

        System.out.println("Finished experiment with " + NUM_EXPERIMENTS + " iterations in " + (System.currentTimeMillis() - startTime) + " milliseconds");
        System.out.println(Arrays.toString(frequencyTable) + "\n");
    }

    private static void runExp2() {
        final long startTime = System.currentTimeMillis();
        final AtomicInteger pocketPairCount = new AtomicInteger(0);
        final AtomicInteger pocketAcesCount = new AtomicInteger(0);
        final AtomicInteger pocketKingsCount = new AtomicInteger(0);
        final AtomicInteger aceKingCount = new AtomicInteger(0);

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

            final Map<Rank, List<Card>> groupByResult = hand.getHandAnalyzer().getHoleCards().stream().collect(Collectors.groupingBy(Card::getRank));

            if(groupByResult.keySet().size() == 1) {
                pocketPairCount.incrementAndGet();
                if(groupByResult.keySet().contains(Rank.ACE)) {
                    pocketAcesCount.getAndIncrement();
                } else if(groupByResult.keySet().contains(Rank.KING)) {
                    pocketKingsCount.getAndIncrement();
                }
            } else if(groupByResult.keySet().contains(Rank.KING) &&
               groupByResult.keySet().contains(Rank.ACE)) {
                aceKingCount.getAndIncrement();
            }

        });

        System.out.println("Finished experiment with " + NUM_EXPERIMENTS + " iterations in " + (System.currentTimeMillis() - startTime) + " milliseconds");
        System.out.println("Pocket pair percentage = " + ((float)pocketPairCount.get()/NUM_EXPERIMENTS) * 100);
        System.out.println("Pocket aces percentage = " + ((float)pocketAcesCount.get()/NUM_EXPERIMENTS) * 100);
        System.out.println("Pocket Aces or Pocket Kings percentage = " + ((float)(pocketAcesCount.get() + pocketKingsCount.get())/NUM_EXPERIMENTS) * 100);
        System.out.println("Any Ace/King percentage = " + ((float)aceKingCount.get()/NUM_EXPERIMENTS) * 100);

    }
}
