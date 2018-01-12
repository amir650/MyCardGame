import java.util.Arrays;
import java.util.stream.IntStream;

public class Main {

    private static final int NUM_EXPERIMENTS = 50000000;

    public static void main(String[] args) {
        runExp1();
        runExp2();
    }

    private static void runExp1() {
        final long startTime = System.currentTimeMillis();
        final int[] frequencyTable = new int[FiveCardPokerHand.Classification.values().length];

        IntStream.range(0, NUM_EXPERIMENTS).mapToObj(i -> new FiveCardPokerHand.Builder()).forEach(builder -> {
            final Deck deck = Deck.newShuffledSingleDeck();
            builder.addCard(deck.deal());
            builder.addCard(deck.deal());
            builder.addCard(deck.deal());
            builder.addCard(deck.deal());
            builder.addCard(deck.deal());
            final FiveCardPokerHand hand = builder.build();
            final FiveCardPokerHand.Classification classification = hand.getHandClassification();
            frequencyTable[classification.ordinal()]++;
        });

        System.out.println("Finished experiment with " + NUM_EXPERIMENTS + " iterations in " + (System.currentTimeMillis() - startTime) + " milliseconds");
        System.out.println(Arrays.toString(frequencyTable));
    }

    private static void runExp2() {
        final long startTime = System.currentTimeMillis();
        final int[] frequencyTable = new int[3];

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

            final int comparison = hand.compareTo(hand2);

            if(comparison == -1) {
                frequencyTable[0]++;
            } else if(comparison == 0) {
                frequencyTable[1]++;
            } else if(comparison == 1) {
                frequencyTable[2]++;
            } else {
                throw new RuntimeException("WTF");
            }

        });

        System.out.println("Finished experiment with " + NUM_EXPERIMENTS + " iterations in " + (System.currentTimeMillis() - startTime) + " milliseconds");
        System.out.println(Arrays.toString(frequencyTable));
    }

}
