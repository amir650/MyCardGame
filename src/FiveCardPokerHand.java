import java.util.*;
import java.util.stream.Collectors;


public class FiveCardPokerHand implements Comparable<FiveCardPokerHand> {

    private final SortedSet<Card> cards;
    private final Classification handClassification;
    private final HandGroupResult handGroupResult;

    private static final int POKER_HAND_SIZE = 5;

    enum Classification {
        HIGH_CARD(1) {
            @Override
            int deepCompare(final FiveCardPokerHand hand,
                            final FiveCardPokerHand otherHand) {
                return 0;
            }
        },
        PAIR(2) {
            @Override
            int deepCompare(final FiveCardPokerHand hand,
                            final FiveCardPokerHand otherHand) {
                return 0;
            }
        },
        TWO_PAIR(3) {
            @Override
            int deepCompare(final FiveCardPokerHand hand,
                            final FiveCardPokerHand otherHand) {
                return 0;
            }
        },
        SET(4) {
            @Override
            int deepCompare(final FiveCardPokerHand hand,
                            final FiveCardPokerHand otherHand) {
                final int setComparison = setRankComparison(hand, otherHand);

                if(setComparison != 0) {
                    return setComparison;
                }

                return setKickerComparison(hand, otherHand);
            }

            private int setKickerComparison(final FiveCardPokerHand hand,
                                            final FiveCardPokerHand otherHand) {
                return 0;
            }

            private int setRankComparison(final FiveCardPokerHand hand,
                                          final FiveCardPokerHand otherHand) {
                return 0;
            }
        },
        WHEEL(5) {
            @Override
            int deepCompare(final FiveCardPokerHand hand,
                            final FiveCardPokerHand otherHand) {
                return 0;
            }
        },
        STRAIGHT(6) {
            @Override
            int deepCompare(final FiveCardPokerHand hand,
                            final FiveCardPokerHand otherHand) {
                final int straightRankComparison = straightRankComparison(hand, otherHand);
                return straightRankComparison;
            }

            private int straightRankComparison(final FiveCardPokerHand hand,
                                               final FiveCardPokerHand otherHand) {
                return 0;
            }
        },
        FLUSH(7) {
            @Override
            int deepCompare(final FiveCardPokerHand hand,
                            final FiveCardPokerHand otherHand) {
                final int flushRankComparison = flushRankComparison(hand, otherHand);
                return flushRankComparison;
            }

            private int flushRankComparison(final FiveCardPokerHand hand,
                                            final FiveCardPokerHand otherHand) {
                return Integer.compare(hand.getCards().last().getRank().getRankValue(),
                        otherHand.getCards().last().getRank().getRankValue());
            }
        },
        FULL_HOUSE(8) {
            @Override
            int deepCompare(final FiveCardPokerHand hand,
                            final FiveCardPokerHand otherHand) {

                final int setComparison = fullHouseSetComparison(hand, otherHand);

                if(setComparison != 0) {
                    return setComparison;
                }

                return fullHousePairComparison(hand, otherHand);
            }

            private int fullHousePairComparison(final FiveCardPokerHand hand,
                                                final FiveCardPokerHand otherHand) {
                return 0;
            }

            private int fullHouseSetComparison(final FiveCardPokerHand hand,
                                               final FiveCardPokerHand otherHand) {
                return 0;
            }
        },
        FOUR_OF_A_KIND(9) {
            @Override
            int deepCompare(final FiveCardPokerHand hand,
                            final FiveCardPokerHand otherHand) {

                final int quadComparison = compareQuadsRank(hand, otherHand);

                if(quadComparison != 0) {
                    return quadComparison;
                }

                return quadsKickerRankComparison(hand, otherHand);
            }

            private int compareQuadsRank(final FiveCardPokerHand hand,
                                         final FiveCardPokerHand otherHand) {

                final Card.Rank quadsHandRank = extractQuadsRank(hand);
                final Card.Rank quadsOtherHandRank = extractQuadsRank(otherHand);

                final int quadsRankComparison = Integer.compare(quadsHandRank.getRankValue(), quadsOtherHandRank.getRankValue());

                if(quadsRankComparison != 0) {
                    return quadsRankComparison;
                }

                return quadsKickerRankComparison(hand, otherHand);
            }

            private int quadsKickerRankComparison(final FiveCardPokerHand hand,
                                                  final FiveCardPokerHand otherHand) {
                return 0;
            }

            private Card.Rank extractQuadsRank(final FiveCardPokerHand hand) {
                return null;
            }

        },
        STRAIGHT_FLUSH(10) {
            @Override
            int deepCompare(final FiveCardPokerHand hand,
                            final FiveCardPokerHand otherHand) {
                return Integer.compare(hand.getCards().last().getRank().getRankValue(),
                                       otherHand.getCards().last().getRank().getRankValue());
            }
        },
        STRAIGHT_FLUSH_WHEEL(11) {
            @Override
            int deepCompare(final FiveCardPokerHand hand,
                            final FiveCardPokerHand otherHand) {
                return 0;
            }
        },
        ROYAL_FLUSH(12) {
            @Override
            int deepCompare(final FiveCardPokerHand hand,
                            final FiveCardPokerHand otherHand) {
                return 0;
            }
        };

        private final int value;

        Classification(int value) {
            this.value = value;
        }

        abstract int deepCompare(final FiveCardPokerHand hand,
                                 final FiveCardPokerHand otherHand);

        public int getValue() {
            return this.value;
        }
    }

    FiveCardPokerHand(final Builder builder) {
        this.cards = Collections.unmodifiableSortedSet(builder.cards);
        this.handGroupResult = new HandGroupResult(builder.cards);
        this.handClassification = classifyHand();
    }

    public Classification getHandClassification() {
        return this.handClassification;
    }

    public HandGroupResult getHandGroupResult() {
        return this.handGroupResult;
    }

    public SortedSet<Card> getCards() {
        return this.cards;
    }

    @Override
    public int compareTo(final FiveCardPokerHand otherHand) {

        final int classificationComparison =
                Integer.compare(this.handClassification.ordinal(), otherHand.handClassification.ordinal());
        return classificationComparison != 0 ? classificationComparison :
                deepHandCompare(this, otherHand);
    }

    private static int deepHandCompare(final FiveCardPokerHand hand,
                                       final FiveCardPokerHand otherHand) {
        return hand.getHandClassification().deepCompare(hand, otherHand);
    }

    private static int compareTwoPairs(final FiveCardPokerHand hand,
                                       final FiveCardPokerHand otherHand) {
        final Card handKicker = extractKicker(hand);
        final Card otherHandKicker = extractKicker(otherHand);
        return Integer.compare(handKicker.getRank().getRankValue(), otherHandKicker.getRank().getRankValue());
    }

    private static Card extractKicker(final FiveCardPokerHand hand) {
        final Map<Card.Rank, List<Card>> handRankResults = hand.getHandGroupResult().rankGroup;
        for(Map.Entry<Card.Rank, List<Card>> entry : handRankResults.entrySet()) {
            if(entry.getValue().size() == 1) {
                return entry.getValue().iterator().next();
            }
        }
        throw new RuntimeException("Should not reach here!");
    }

    private Classification classifyHand() {

        if(isRoyalFlush()) {
            return Classification.ROYAL_FLUSH;
        } else if(isStraightFlushWheel()) {
            return Classification.STRAIGHT_FLUSH_WHEEL;
        }else if(isStraightFlush()) {
            return Classification.STRAIGHT_FLUSH;
        } else if(isFourOfAKind()) {
            return Classification.FOUR_OF_A_KIND;
        } else if(isFullHouse()) {
            return Classification.FULL_HOUSE;
        } else if(isFlush()) {
            return Classification.FLUSH;
        } else if(isWheel()) {
            return Classification.WHEEL;
        } else if(isNormalStraight()) {
            return Classification.STRAIGHT;
        } else if(isSet()) {
            return Classification.SET;
        } else if(isTwoPair()) {
            return Classification.TWO_PAIR;
        } else if(isPair()) {
            return Classification.PAIR;
        }

        return Classification.HIGH_CARD;
    }

    private boolean isPair() {
        return this.handGroupResult.getPairCount() == 1;
    }

    private boolean isTwoPair() {
        return this.handGroupResult.getPairCount() == 2;
    }

    private boolean isSet() {
        return this.handGroupResult.getSetCount() == 1;
    }

    private boolean isNormalStraight() {
        final Card[] cardArray = this.handGroupResult.getCards().toArray(new Card[this.handGroupResult.getCards().size()]);
        for(int i = 0; i < cardArray.length - 1; i++) {
            if(cardArray[i].getRank().getRankValue() != cardArray[i+1].getRank().getRankValue() - 1) {
                return false;
            }
        }
        return true;
    }

    private boolean isWheel() {
        final Card[] cardArray = this.handGroupResult.getCards().toArray(new Card[this.handGroupResult.getCards().size()]);
        return  (cardArray[0].getRank().equals(Card.Rank.TWO)) &&
                (cardArray[1].getRank().equals(Card.Rank.THREE)) &&
                (cardArray[2].getRank().equals(Card.Rank.FOUR)) &&
                (cardArray[3].getRank().equals(Card.Rank.FIVE)) &&
                (cardArray[4].getRank().equals(Card.Rank.ACE));
    }

    private boolean isFlush() {
        return this.handGroupResult.getSuitGroup().containsValue(5L);
    }

    private boolean isFullHouse() {
        return this.handGroupResult.getPairCount() == 1 && this.handGroupResult.getSetCount() == 1;
    }

    private boolean isFourOfAKind() {
        return this.handGroupResult.getRankGroup().size() == 4;
    }

    private boolean isStraightFlush() {
        return isFlush() && isNormalStraight();
    }

    private boolean isStraightFlushWheel() {
        return isFlush() && isWheel();
    }

    private boolean isRoyalFlush() {
        final Card[] cardArray = this.handGroupResult.getCards().toArray(new Card[this.handGroupResult.getCards().size()]);
        if(cardArray[0].getRank().equals(Card.Rank.TEN) &&
           cardArray[1].getRank().equals(Card.Rank.JACK) &&
           cardArray[2].getRank().equals(Card.Rank.QUEEN) &&
           cardArray[3].getRank().equals(Card.Rank.KING) &&
           cardArray[4].getRank().equals(Card.Rank.ACE)) {
            return isFlush();
        }
        return false;
    }

    @Override
    public String toString() {
        return this.cards.toString();
    }

    static class HandGroupResult {
        final SortedSet<Card> cards;
        final Map<Card.Rank, List<Card>> rankGroup;
        final Map<Card.Suit, List<Card>> suitGroup;
        final int setCount;
        final int pairCount;

        HandGroupResult(final SortedSet<Card> cards) {
            this.cards = cards;
            this.rankGroup = cards.stream()
                    .collect(Collectors.groupingBy(Card::getRank));
            this.suitGroup = cards.stream()
                    .collect(Collectors.groupingBy(Card::getSuit));
            this.setCount = calculateSetCount(this.rankGroup);
            this.pairCount = calculatePairCount(this.rankGroup);
        }

        private static int calculateSetCount(final Map<Card.Rank, List<Card>> rankGroup) {
            int numSets = 0;
            for(final Map.Entry<Card.Rank, List<Card>> entry : rankGroup.entrySet()) {
                if(entry.getValue().size() == 3) {
                    numSets++;
                }
            }
            return numSets;
        }

        private static int calculatePairCount(final Map<Card.Rank, List<Card>> rankGroup) {
            int numPairs = 0;
            for(final Map.Entry<Card.Rank, List<Card>> entry : rankGroup.entrySet()) {
                if(entry.getValue().size() == 2) {
                    numPairs++;
                }
            }
            return numPairs;
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
    }

    static class Builder {

        private SortedSet<Card> cards;

        public Builder() {
            this.cards = new TreeSet<>(Comparator.naturalOrder());
        }

        public Builder addCard(final Optional<Card> card) {
            this.cards.add(card.orElseThrow(IllegalStateException::new));
            return this;
        }

        public FiveCardPokerHand build() {
            if(this.cards.size() != POKER_HAND_SIZE) {
                throw new RuntimeException("Invalid hand size for hand " +this.cards.toString());
            }
            return new FiveCardPokerHand(this);
        }

    }
}
