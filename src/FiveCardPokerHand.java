import java.util.*;
import java.util.stream.Collectors;


public class FiveCardPokerHand implements Comparable<FiveCardPokerHand> {

    private final Classification handClassification;
    private final HandGroupResult handGroupResult;

    private static final int POKER_HAND_SIZE = 5;

    enum Classification {
        HIGH_CARD(1) {
            @Override
            int tieBreaker(final FiveCardPokerHand hand,
                           final FiveCardPokerHand otherHand) {

                final Iterator<Map.Entry<Card.Rank, List<Card>>> handIterator = hand.getHandGroupResult().getRankGroup().entrySet().iterator();
                final Iterator<Map.Entry<Card.Rank, List<Card>>> otherHandIterator = otherHand.getHandGroupResult().getRankGroup().entrySet().iterator();

                return Classification.compareHighCards(handIterator, otherHandIterator);
            }
        },
        PAIR(2) {
            @Override
            int tieBreaker(final FiveCardPokerHand hand,
                           final FiveCardPokerHand otherHand) {
                final Iterator<Map.Entry<Card.Rank, List<Card>>> handIterator = hand.getHandGroupResult().getRankGroup().entrySet().iterator();
                final Iterator<Map.Entry<Card.Rank, List<Card>>> otherHandIterator = otherHand.getHandGroupResult().getRankGroup().entrySet().iterator();
                final int highPairComparison = comparePairs(handIterator.next(), otherHandIterator.next());

                if (highPairComparison != 0) {
                    return highPairComparison;
                }

                return compareHighCards(handIterator, otherHandIterator);

            }

        },
        TWO_PAIR(3) {
            @Override
            int tieBreaker(final FiveCardPokerHand hand,
                           final FiveCardPokerHand otherHand) {


                final Iterator<Map.Entry<Card.Rank, List<Card>>> handIterator = hand.getHandGroupResult().getRankGroup().entrySet().iterator();
                final Iterator<Map.Entry<Card.Rank, List<Card>>> otherHandIterator = otherHand.getHandGroupResult().getRankGroup().entrySet().iterator();

                final int highPairComparison = comparePairs(handIterator.next(), otherHandIterator.next());

                if (highPairComparison != 0) {
                    return highPairComparison;
                }

                final int lowPairComparison = comparePairs(handIterator.next(), otherHandIterator.next());

                if (lowPairComparison != 0) {
                    return lowPairComparison;
                }

                return Integer.compare(handIterator.next().getKey().getRankValue(), otherHandIterator.next().getKey().getRankValue());
            }

        },
        SET(4) {
            @Override
            int tieBreaker(final FiveCardPokerHand hand,
                           final FiveCardPokerHand otherHand) {
                throw new RuntimeException("should not reach here in 5 card poker : " + hand + " vs " + otherHand);
            }
        },
        WHEEL(5) {
            @Override
            int tieBreaker(final FiveCardPokerHand hand,
                           final FiveCardPokerHand otherHand) {
                return TIE;
            }
        },
        STRAIGHT(6) {
            @Override
            int tieBreaker(final FiveCardPokerHand hand,
                           final FiveCardPokerHand otherHand) {
                return straightRankComparison(hand, otherHand);
            }

            private int straightRankComparison(final FiveCardPokerHand hand,
                                               final FiveCardPokerHand otherHand) {
                return Integer.compare(hand.getHandGroupResult().getCards().last().getRank().getRankValue(),
                        otherHand.getHandGroupResult().getCards().last().getRank().getRankValue());
            }
        },
        FLUSH(7) {
            @Override
            int tieBreaker(final FiveCardPokerHand hand,
                           final FiveCardPokerHand otherHand) {
                return flushRankComparison(hand, otherHand);
            }

            private int flushRankComparison(final FiveCardPokerHand hand,
                                            final FiveCardPokerHand otherHand) {

                final Card[] handCards = hand.getHandGroupResult().getCards().toArray(new Card[hand.getHandGroupResult().getCards().size()]);
                final Card[] otherHandCards = otherHand.getHandGroupResult().getCards().toArray(new Card[hand.getHandGroupResult().getCards().size()]);

                final int fifthCardCompare = Integer.compare(handCards[4].getRank().getRankValue(),
                        otherHandCards[4].getRank().getRankValue());
                if (fifthCardCompare != 0) {
                    return fifthCardCompare;
                }

                final int fourthCardCompare = Integer.compare(handCards[3].getRank().getRankValue(),
                        otherHandCards[3].getRank().getRankValue());

                if (fourthCardCompare != 0) {
                    return fourthCardCompare;
                }

                final int thirdCardCompare = Integer.compare(handCards[2].getRank().getRankValue(),
                        otherHandCards[2].getRank().getRankValue());

                if (thirdCardCompare != 0) {
                    return thirdCardCompare;
                }

                final int secondCardCompare = Integer.compare(handCards[1].getRank().getRankValue(),
                        otherHandCards[1].getRank().getRankValue());

                if (secondCardCompare != 0) {
                    return secondCardCompare;
                }

                final int firstCardCompare = Integer.compare(handCards[0].getRank().getRankValue(),
                        otherHandCards[0].getRank().getRankValue());

                if (firstCardCompare != 0) {
                    return fifthCardCompare;
                }

                return TIE;
            }
        },
        FULL_HOUSE(8) {
            @Override
            int tieBreaker(final FiveCardPokerHand hand,
                           final FiveCardPokerHand otherHand) {

                final int setComparison = compareSets(hand, otherHand);

                if (setComparison != 0) {
                    return setComparison;
                }

                return comparePairs(hand, otherHand);
            }

        },
        FOUR_OF_A_KIND(9) {
            @Override
            int tieBreaker(final FiveCardPokerHand hand,
                           final FiveCardPokerHand otherHand) {
                throw new RuntimeException("should not reach here in 5 card poker : " + hand + " vs " + otherHand);
            }
        },
        STRAIGHT_FLUSH_WHEEL(10) {
            @Override
            int tieBreaker(final FiveCardPokerHand hand,
                           final FiveCardPokerHand otherHand) {
                return TIE;
            }
        },
        STRAIGHT_FLUSH(11) {
            @Override
            int tieBreaker(final FiveCardPokerHand hand,
                           final FiveCardPokerHand otherHand) {
                return Integer.compare(hand.getHandGroupResult().getCards().last().getRank().getRankValue(),
                        otherHand.getHandGroupResult().getCards().last().getRank().getRankValue());
            }
        },
        ROYAL_FLUSH(12) {
            @Override
            int tieBreaker(final FiveCardPokerHand hand,
                           final FiveCardPokerHand otherHand) {
                return TIE;
            }
        };

        private final int value;

        private static final int TIE = 0;

        Classification(int value) {
            this.value = value;
        }

        private static int compareHighCards(final Iterator<Map.Entry<Card.Rank, List<Card>>> handIterator,
                                            final Iterator<Map.Entry<Card.Rank, List<Card>>> otherHandIterator) {
            while (handIterator.hasNext() && otherHandIterator.hasNext()) {
                int compare = Integer.compare(handIterator.next().getKey().getRankValue(),
                        otherHandIterator.next().getKey().getRankValue());
                if (compare != 0) {
                    return compare;
                }
            }
            return TIE;
        }

        private static int comparePairs(final Map.Entry<Card.Rank, List<Card>> pair,
                                        final Map.Entry<Card.Rank, List<Card>> otherPair) {
            return Integer.compare(pair.getKey().getRankValue(), otherPair.getKey().getRankValue());
        }

        private static int comparePairs(final FiveCardPokerHand hand,
                                        final FiveCardPokerHand otherHand) {
            final Card.Rank handSetRank = extractPairRank(hand);
            final Card.Rank otherHandSetRank = extractPairRank(otherHand);
            return Integer.compare(handSetRank.getRankValue(), otherHandSetRank.getRankValue());
        }

        private static int compareSets(final FiveCardPokerHand hand,
                                       final FiveCardPokerHand otherHand) {
            final Card.Rank handSetRank = extractSetRank(hand);
            final Card.Rank otherHandSetRank = extractSetRank(otherHand);
            return Integer.compare(handSetRank.getRankValue(), otherHandSetRank.getRankValue());
        }

        private static Card.Rank extractSetRank(final FiveCardPokerHand hand) {
            for (final Map.Entry<Card.Rank, List<Card>> entry : hand.getHandGroupResult().getRankGroup().entrySet()) {
                if (entry.getValue().size() == 3) {
                    return entry.getValue().iterator().next().getRank();
                }
            }
            throw new RuntimeException("WTF");
        }

        private static Card.Rank extractPairRank(final FiveCardPokerHand hand) {
            for (final Map.Entry<Card.Rank, List<Card>> entry : hand.getHandGroupResult().getRankGroup().entrySet()) {
                if (entry.getValue().size() == 2) {
                    return entry.getValue().iterator().next().getRank();
                }
            }
            throw new RuntimeException("WTF");
        }

        abstract int tieBreaker(final FiveCardPokerHand hand,
                                final FiveCardPokerHand otherHand);

        public int getValue() {
            return this.value;
        }
    }

    FiveCardPokerHand(final Builder builder) {
        this.handGroupResult = new HandGroupResult(builder.cards);
        this.handClassification = classifyHand();
    }

    Classification getHandClassification() {
        return this.handClassification;
    }

    HandGroupResult getHandGroupResult() {
        return this.handGroupResult;
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
        for (int i = 0; i < cardArray.length - 1; i++) {
            if (cardArray[i].getRank().getRankValue() != cardArray[i + 1].getRank().getRankValue() - 1) {
                return false;
            }
        }
        return true;
    }

    private boolean isWheel() {
        final Card[] cardArray = this.handGroupResult.getCards().toArray(new Card[this.handGroupResult.getCards().size()]);
        return (cardArray[0].getRank().equals(Card.Rank.TWO)) &&
                (cardArray[1].getRank().equals(Card.Rank.THREE)) &&
                (cardArray[2].getRank().equals(Card.Rank.FOUR)) &&
                (cardArray[3].getRank().equals(Card.Rank.FIVE)) &&
                (cardArray[4].getRank().equals(Card.Rank.ACE));
    }

    private boolean isFlush() {
        return this.handGroupResult.getSuitGroup().size() == 1;
    }

    private boolean isFullHouse() {
        return this.handGroupResult.getPairCount() == 1 && this.handGroupResult.getSetCount() == 1;
    }

    private boolean isFourOfAKind() {
        for (final Map.Entry<Card.Rank, List<Card>> entry : this.handGroupResult.getRankGroup().entrySet()) {
            if (entry.getValue().size() == 4) {
                return true;
            }
        }
        return false;
    }

    private boolean isStraightFlush() {
        return isFlush() && isNormalStraight();
    }

    private boolean isStraightFlushWheel() {
        return isFlush() && isWheel();
    }

    private boolean isRoyalFlush() {
        final Card[] cardArray = this.handGroupResult.getCards().toArray(new Card[this.handGroupResult.getCards().size()]);
        return cardArray[0].getRank().equals(Card.Rank.TEN) &&
                cardArray[1].getRank().equals(Card.Rank.JACK) &&
                cardArray[2].getRank().equals(Card.Rank.QUEEN) &&
                cardArray[3].getRank().equals(Card.Rank.KING) &&
                cardArray[4].getRank().equals(Card.Rank.ACE) &&
                isFlush();
    }

    @Override
    public String toString() {
        return this.getHandGroupResult().getCards().toString();
    }

    static class HandGroupResult {
        final SortedSet<Card> cards;
        final Map<Card.Rank, List<Card>> rankGroup;
        final Map<Card.Suit, List<Card>> suitGroup;
        final int setCount;
        final int pairCount;

        HandGroupResult(final SortedSet<Card> cards) {
            this.cards = cards;
            this.rankGroup = initRankGroup(cards);
            this.suitGroup = initSuitGroup(cards);
            this.setCount = calculateSetCount();
            this.pairCount = calculatePairCount();
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
            final TreeMap<Card.Suit, List<Card>> treeMap = new TreeMap<>(Comparator.reverseOrder());
            treeMap.putAll(cards.stream()
                    .collect(Collectors.groupingBy(Card::getSuit)));
            return treeMap;
        }

        private int calculateSetCount() {
            int numSets = 0;
            for (final Map.Entry<Card.Rank, List<Card>> entry : this.rankGroup.entrySet()) {
                if (entry.getValue().size() == 3) {
                    numSets++;
                }
            }
            return numSets;
        }

        private int calculatePairCount() {
            int numPairs = 0;
            for (final Map.Entry<Card.Rank, List<Card>> entry : this.rankGroup.entrySet()) {
                if (entry.getValue().size() == 2) {
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
            if (this.cards.size() != POKER_HAND_SIZE) {
                throw new RuntimeException("Invalid hand size for hand " + this.cards.toString());
            }
            return new FiveCardPokerHand(this);
        }

    }
}
