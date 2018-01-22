package com.cardgames.poker;

public enum Classification {

    HIGH_CARD(1),
    PAIR(2),
    TWO_PAIR(3),
    SET(4),
    WHEEL(5),
    STRAIGHT(6),
    FLUSH(7),
    FULL_HOUSE(8),
    FOUR_OF_A_KIND(9),
    STRAIGHT_FLUSH_WHEEL(10),
    STRAIGHT_FLUSH(11),
    ROYAL_FLUSH(12);

    private final int value;

    Classification(int value) {
        this.value = value;
    }

    public int getValue() {
        return this.value;
    }

}
