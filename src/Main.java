import java.util.Arrays;
import java.util.stream.IntStream;

public class Main {

    private static final int NUM_EXPERIMENTS = 10000000;

    public static void main(String[] args) {

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
}
