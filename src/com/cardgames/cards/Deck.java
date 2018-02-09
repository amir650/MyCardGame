package com.cardgames.cards;

import java.util.Collections;
import java.util.Optional;
import java.util.Stack;
import java.util.stream.IntStream;

public class Deck {

    private final Stack<Card> deckCards;

    private Deck(final boolean shouldShuffle) {
        this.deckCards = initDeck(shouldShuffle);
    }

    private Stack<Card> initDeck(boolean shouldShuffle) {
        final Stack<Card> deckCards = new Stack<>();
        for(final Suit suit : Suit.values()) {
            for(final Rank rank : Rank.values()) {
                deckCards.push(Card.getCard(rank, suit));
            }
        }
        if(shouldShuffle) {
            Collections.shuffle(deckCards);
        } else {
            Collections.sort(deckCards);
        }
        return deckCards;
    }

    public static void main(String args[]) {
        final Deck deck = Deck.newShuffledSingleDeck();
        final int numCardsToDeal = 52;
        IntStream.range(0, numCardsToDeal).forEach(n -> System.out.println(deck.deal()));
    }

    public static Deck newShuffledSingleDeck() {
        return new Deck(true);
    }

    public static Deck newUnshuffledSingleDeck() {
        return new Deck(false);
    }

    public int size() {
        return this.deckCards.size();
    }

    public boolean contains(final Card card) {
        return this.deckCards.contains(card);
    }

    public Optional<Card> deal() {
        return this.deckCards.empty() ? Optional.empty() :
                Optional.of(this.deckCards.pop());
    }

}
