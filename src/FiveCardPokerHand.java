import java.util.*;
import java.util.stream.Collectors;


public class FiveCardPokerHand implements Comparable<FiveCardPokerHand> {

    private final Classification handClassification;
    private final HandInformation handInformation;

    private static final int POKER_HAND_SIZE = 5;

    enum Classification {
        HIGH_CARD(1) {
            @Override
            int tieBreaker(final FiveCardPokerHand hand,
                           final FiveCardPokerHand otherHand) {
                return HandUtils.compareHighCardHands(hand, otherHand);
            }
        },
        PAIR(2) {
            @Override
            int tieBreaker(final FiveCardPokerHand hand,
                           final FiveCardPokerHand otherHand) {
                return HandUtils.comparePairHands(hand, otherHand);
            }
        },
        TWO_PAIR(3) {
            @Override
            int tieBreaker(final FiveCardPokerHand hand,
                           final FiveCardPokerHand otherHand) {
                return HandUtils.compareTwoPairHands(hand, otherHand);
            }
        },
        SET(4) {
            @Override
            int tieBreaker(final FiveCardPokerHand hand,
                           final FiveCardPokerHand otherHand) {
                return HandUtils.compareSetHands(hand, otherHand);
            }
        },
        WHEEL(5) {
            @Override
            int tieBreaker(final FiveCardPokerHand hand,
                           final FiveCardPokerHand otherHand) {
                return HandUtils.compareWheelHands(hand, otherHand);
            }
        },
        STRAIGHT(6) {
            @Override
            int tieBreaker(final FiveCardPokerHand hand,
                           final FiveCardPokerHand otherHand) {
                return HandUtils.compareStraightHands(hand, otherHand);
            }
        },
        FLUSH(7) {
            @Override
            int tieBreaker(final FiveCardPokerHand hand,
                           final FiveCardPokerHand otherHand) {
                return HandUtils.compareFlushHands(hand, otherHand);
            }
        },
        FULL_HOUSE(8) {
            @Override
            int tieBreaker(final FiveCardPokerHand hand,
                           final FiveCardPokerHand otherHand) {
                return HandUtils.compareFullHouseHands(hand, otherHand);
            }
        },
        FOUR_OF_A_KIND(9) {
            @Override
            int tieBreaker(final FiveCardPokerHand hand,
                           final FiveCardPokerHand otherHand) {
                return HandUtils.compareQuadsHands(hand, otherHand);
            }
        },
        STRAIGHT_FLUSH_WHEEL(10) {
            @Override
            int tieBreaker(final FiveCardPokerHand hand,
                           final FiveCardPokerHand otherHand) {
                return HandUtils.compareStraightFlushWheelHands(hand, otherHand);
            }
        },
        STRAIGHT_FLUSH(11) {
            @Override
            int tieBreaker(final FiveCardPokerHand hand,
                           final FiveCardPokerHand otherHand) {
                return HandUtils.compareStraightFlushHands(hand, otherHand);
            }
        },
        ROYAL_FLUSH(12) {
            @Override
            int tieBreaker(final FiveCardPokerHand hand,
                           final FiveCardPokerHand otherHand) {
                return HandUtils.compareRoyalFlushHands(hand, otherHand);
            }
        };

        private final int value;

        Classification(int value) {
            this.value = value;
        }

        abstract int tieBreaker(final FiveCardPokerHand hand,
                                final FiveCardPokerHand otherHand);

        public int getValue() {
            return this.value;
        }
    }

    FiveCardPokerHand(final Builder builder) {
        this.handInformation = new HandInformation(builder.cards);
        this.handClassification = classifyHand();
    }

    Classification getHandClassification() {
        return this.handClassification;
    }

    HandInformation getHandInformation() {
        return this.handInformation;
    }

    Iterator<Map.Entry<Card.Rank, List<Card>>> getHandRankIterator() {
        return this.handInformation.getRankGroup().entrySet().iterator();
    }

    @Override
    public int compareTo(final FiveCardPokerHand otherHand) {

        final int classificationComparison =
                Integer.compare(this.handClassification.getValue(), otherHand.handClassification.getValue());

        return classificationComparison != 0 ? classificationComparison :
                                               deepHandCompare(this, otherHand);
    }

    private static int deepHandCompare(final FiveCardPokerHand hand,
                                       final FiveCardPokerHand otherHand) {
        return hand.getHandClassification().tieBreaker(hand, otherHand);
    }

    private Classification classifyHand() {

        if (isRoyalFlush()) {
            return Classification.ROYAL_FLUSH;
        } else if (isStraightFlushWheel()) {
            return Classification.STRAIGHT_FLUSH_WHEEL;
        } else if (isStraightFlush()) {
            return Classification.STRAIGHT_FLUSH;
        } else if (isFourOfAKind()) {
            return Classification.FOUR_OF_A_KIND;
        } else if (isFullHouse()) {
            return Classification.FULL_HOUSE;
        } else if (isFlush()) {
            return Classification.FLUSH;
        } else if (isWheel()) {
            return Classification.WHEEL;
        } else if (isNormalStraight()) {
            return Classification.STRAIGHT;
        } else if (isSet()) {
            return Classification.SET;
        } else if (isTwoPair()) {
            return Classification.TWO_PAIR;
        } else if (isPair()) {
            return Classification.PAIR;
        }

        return Classification.HIGH_CARD;
    }

    private boolean isPair() {
        return this.handInformation.getPairCount() == 1;
    }

    private boolean isTwoPair() {
        return this.handInformation.getPairCount() == 2;
    }

    private boolean isSet() {
        return this.handInformation.getSetCount() == 1;
    }

    private boolean isNormalStraight() {
        final Card[] cardArray = this.handInformation.getCards().toArray(new Card[this.handInformation.getCards().size()]);
        for (int i = 0; i < cardArray.length - 1; i++) {
            if (cardArray[i].getRank().getRankValue() != cardArray[i + 1].getRank().getRankValue() - 1) {
                return false;
            }
        }
        return true;
    }

    private boolean isWheel() {
        final Card[] cardArray = this.handInformation.getCards().toArray(new Card[this.handInformation.getCards().size()]);
        return (cardArray[0].getRank().equals(Card.Rank.TWO)) &&
                (cardArray[1].getRank().equals(Card.Rank.THREE)) &&
                (cardArray[2].getRank().equals(Card.Rank.FOUR)) &&
                (cardArray[3].getRank().equals(Card.Rank.FIVE)) &&
                (cardArray[4].getRank().equals(Card.Rank.ACE));
    }

    private boolean isFlush() {
        return this.handInformation.getSuitGroup().size() == 1;
    }

    private boolean isFullHouse() {
        return this.handInformation.getPairCount() == 1 && this.handInformation.getSetCount() == 1;
    }

    private boolean isFourOfAKind() {
        return this.handInformation.getRankGroup().entrySet().iterator().next().getValue().size() == 4;
    }

    private boolean isStraightFlush() {
        return isFlush() && isNormalStraight();
    }

    private boolean isStraightFlushWheel() {
        return isFlush() && isWheel();
    }

    private boolean isRoyalFlush() {
        final Card[] cardArray = this.handInformation.getCards().toArray(new Card[this.handInformation.getCards().size()]);
        return cardArray[0].getRank().equals(Card.Rank.TEN) &&
                cardArray[1].getRank().equals(Card.Rank.JACK) &&
                cardArray[2].getRank().equals(Card.Rank.QUEEN) &&
                cardArray[3].getRank().equals(Card.Rank.KING) &&
                cardArray[4].getRank().equals(Card.Rank.ACE) &&
                isFlush();
    }

    @Override
    public String toString() {
        return this.getHandInformation().getCards().toString();
    }

    static class HandInformation {
        final SortedSet<Card> cards;
        final Map<Card.Rank, List<Card>> rankGroup;
        final Map<Card.Suit, List<Card>> suitGroup;
        final int quadCount;
        final int setCount;
        final int pairCount;

        HandInformation(final SortedSet<Card> cards) {
            this.cards = cards;
            this.rankGroup = initRankGroup(cards);
            this.suitGroup = initSuitGroup(cards);
            this.quadCount = groupCount(4);
            this.setCount = groupCount(3);
            this.pairCount = groupCount(2);
        }

        Map<Card.Rank, List<Card>> getRankGroup() {
            return this.rankGroup;
        }

        Map<Card.Suit, List<Card>> getSuitGroup() {
            return this.suitGroup;
        }

        SortedSet<Card> getCards() {
            return this.cards;
        }

        int getSetCount() {
            return this.setCount;
        }

        int getPairCount() {
            return this.pairCount;
        }

        private static Map<Card.Rank, List<Card>> initRankGroup(final SortedSet<Card> cards) {

            final Comparator<Map.Entry<Card.Rank, List<Card>>> valueComparator =
                    (o1, o2) -> o2.getValue().size() == o1.getValue().size() ? o2.getKey().getRankValue() - o1.getKey().getRankValue() :
                            o2.getValue().size() - o1.getValue().size();

            final List<Map.Entry<Card.Rank, List<Card>>> listOfEntries =
                    new ArrayList<>(cards.stream().collect(Collectors.groupingBy(Card::getRank)).entrySet());

            listOfEntries.sort(valueComparator);

            final LinkedHashMap<Card.Rank, List<Card>> sortedResults = new LinkedHashMap<>();

            for (final Map.Entry<Card.Rank, List<Card>> entry : listOfEntries) {
                sortedResults.put(entry.getKey(), entry.getValue());
            }

            return sortedResults;
        }

        private static Map<Card.Suit, List<Card>> initSuitGroup(final SortedSet<Card> cards) {
            return new TreeMap<>(cards.stream().collect(Collectors.groupingBy(Card::getSuit)));
        }

        private int groupCount(final int count) {
            int matches = 0;
            for (final Map.Entry<Card.Rank, List<Card>> entry : this.rankGroup.entrySet()) {
                if (entry.getValue().size() == count) {
                    matches++;
                }
            }
            return matches;
        }
    }

    static class Builder {

        private SortedSet<Card> cards;

        public Builder() {
            this.cards = new TreeSet<>();
        }

        public Builder addCard(final Optional<Card> card) {
            this.cards.add(card.orElseThrow(IllegalStateException::new));
            return this;
        }

        public FiveCardPokerHand build() {
            if (this.cards.size() != POKER_HAND_SIZE) {
                throw new RuntimeException("Invalid hand size for hand " + this.cards.toString());
            }
            return new FiveCardPokerHand(this);
        }

    }
}
