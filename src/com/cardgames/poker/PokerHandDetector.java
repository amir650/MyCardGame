package com.cardgames.poker;

import com.cardgames.cards.Card;
import com.cardgames.cards.Rank;
import com.cardgames.cards.Suit;

import java.util.*;
import java.util.stream.Collectors;

public class PokerHandDetector implements HandClassifier {

    private final RankGroup rankGroup;
    private final SuitGroup suitGroup;
    private final SortedSet<Card> cards;

    PokerHandDetector(final RankGroup rankGroup,
                      final SuitGroup suitGroup,
                      final SortedSet<Card> cards) {
        this.rankGroup = rankGroup;
        this.suitGroup = suitGroup;
        this.cards = Collections.unmodifiableSortedSet(cards);
    }

    private SortedSet<Card> calculateHighCards() {
        return new TreeSet<>(this.cards.stream().limit(5).collect(Collectors.toSet()));
    }

    private Classification isPair() {
        if (this.rankGroup.getPairCount() == 1) {
            final Iterator<Map.Entry<Rank, List<Card>>> rankGroup = this.rankGroup.iterator();
            final SortedSet<Card> cards = new TreeSet<>();
            cards.addAll(rankGroup.next().getValue());
            cards.addAll(rankGroup.next().getValue());
            cards.addAll(rankGroup.next().getValue());
            cards.addAll(rankGroup.next().getValue());
            return new Classification(ClassificationRank.PAIR, cards);
        }
        return new Classification(ClassificationRank.HIGH_CARD, calculateHighCards());
    }

    private Classification detectTwoPair() {
        if (this.rankGroup.getPairCount() == 2) {
            final Iterator<Map.Entry<Rank, List<Card>>> rankGroup = this.rankGroup.iterator();
            final SortedSet<Card> cards = new TreeSet<>();
            cards.addAll(rankGroup.next().getValue());
            cards.addAll(rankGroup.next().getValue());
            cards.addAll(rankGroup.next().getValue());
            return new Classification(ClassificationRank.TWO_PAIR, cards);
        }
        return isPair();
    }

    private Classification isSet() {
        if (this.rankGroup.getSetCount() == 1) {
            final Iterator<Map.Entry<Rank, List<Card>>> rankGroup = this.rankGroup.iterator();
            final SortedSet<Card> cards = new TreeSet<>();
            cards.addAll(rankGroup.next().getValue());
            cards.addAll(rankGroup.next().getValue());
            cards.addAll(rankGroup.next().getValue());
            return new Classification(ClassificationRank.SET, cards);
        }
        return detectTwoPair();
    }

    private Classification detectNormalStraight() {
        final Set<Rank> cardRanks = this.rankGroup.getRankMap().keySet();

        if (cardRanks.containsAll(PokerHandUtils.STRAIGHT_TEN_TO_ACE)) {
            return new Classification(ClassificationRank.STRAIGHT, calculateStraight(PokerHandUtils.STRAIGHT_TEN_TO_ACE));
        } else if (cardRanks.containsAll(PokerHandUtils.STRAIGHT_NINE_TO_KING)) {
            return new Classification(ClassificationRank.STRAIGHT, calculateStraight(PokerHandUtils.STRAIGHT_NINE_TO_KING));
        } else if (cardRanks.containsAll(PokerHandUtils.STRAIGHT_EIGHT_TO_QUEEN)) {
            return new Classification(ClassificationRank.STRAIGHT, calculateStraight(PokerHandUtils.STRAIGHT_EIGHT_TO_QUEEN));
        } else if (cardRanks.containsAll(PokerHandUtils.STRAIGHT_SEVEN_TO_JACK)) {
            return new Classification(ClassificationRank.STRAIGHT, calculateStraight(PokerHandUtils.STRAIGHT_SEVEN_TO_JACK));
        } else if (cardRanks.containsAll(PokerHandUtils.STRAIGHT_SIX_TO_TEN)) {
            return new Classification(ClassificationRank.STRAIGHT, calculateStraight(PokerHandUtils.STRAIGHT_SIX_TO_TEN));
        } else if (cardRanks.containsAll(PokerHandUtils.STRAIGHT_FIVE_TO_NINE)) {
            return new Classification(ClassificationRank.STRAIGHT, calculateStraight(PokerHandUtils.STRAIGHT_FIVE_TO_NINE));
        } else if (cardRanks.containsAll(PokerHandUtils.STRAIGHT_FOUR_TO_EIGHT)) {
            return new Classification(ClassificationRank.STRAIGHT, calculateStraight(PokerHandUtils.STRAIGHT_FOUR_TO_EIGHT));
        } else if (cardRanks.containsAll(PokerHandUtils.STRAIGHT_THREE_TO_SEVEN)) {
            return new Classification(ClassificationRank.STRAIGHT, calculateStraight(PokerHandUtils.STRAIGHT_THREE_TO_SEVEN));
        } else if (cardRanks.containsAll(PokerHandUtils.STRAIGHT_TWO_TO_SIX)) {
            return new Classification(ClassificationRank.STRAIGHT, calculateStraight(PokerHandUtils.STRAIGHT_TWO_TO_SIX));
        }

        return isSet();
    }

    private SortedSet<Card> calculateStraight(final List<Rank> ranks) {
        final SortedSet<Card> results = new TreeSet<>();
        final Map<Rank, List<Card>> rankGroup = this.rankGroup.getRankMap();
        for (final Rank rank : ranks) {
            final List<Card> values = rankGroup.get(rank);
            if (values != null) {
                results.add(values.iterator().next());
            }
        }
        return results;
    }

    private Classification detectWheel() {
        final List<Rank> wheelRanks = Arrays.asList(Rank.ACE, Rank.TWO, Rank.THREE, Rank.FOUR, Rank.FIVE);
        final Set<Rank> handRanks = new TreeSet<>(this.rankGroup.getRankMap().keySet());
        handRanks.retainAll(wheelRanks);
        if (handRanks.containsAll(wheelRanks)) {
            final SortedSet<Card> cards = new TreeSet<>();
            for (final Map.Entry<Rank, List<Card>> entry : this.rankGroup.getRankMap().entrySet()) {
                if (wheelRanks.contains(entry.getKey())) {
                    cards.add(entry.getValue().iterator().next());
                }
            }
            if (cards.size() != 5) {
                throw new RuntimeException("something went wrong!");
            }
            return new Classification(ClassificationRank.WHEEL, cards);
        }
        return detectNormalStraight();
    }

    private Classification detectFlush() {
        final Map<Suit, List<Card>> suitGroup = this.suitGroup.getSuitMap();
        for (Map.Entry<Suit, List<Card>> entry : suitGroup.entrySet()) {
            if (entry.getValue().size() == 5) {
                return new Classification(ClassificationRank.FLUSH, new TreeSet<>(entry.getValue()));
            }
        }
        return detectWheel();
    }

    private Classification detectFullHouse() {
        final Iterator<Map.Entry<Rank, List<Card>>> handRankIterator = this.rankGroup.iterator();
        final List<Card> first = handRankIterator.next().getValue();
        if (first.size() == 3) {
            final SortedSet<Card> daCards = new TreeSet<>();
            final List<Card> second = handRankIterator.next().getValue();
            if (second.size() == 3) {
                final Iterator<Card> it = second.iterator();
                daCards.addAll(first);
                daCards.add(it.next());
                daCards.add(it.next());
                return new Classification(ClassificationRank.FULL_HOUSE, daCards);
            } else if (second.size() == 2) {
                daCards.addAll(first);
                daCards.addAll(second);
                return new Classification(ClassificationRank.FULL_HOUSE, daCards);
            }
        }
        return detectFlush();
    }

    private static Card extractQuadKicker(final Iterator<Map.Entry<Rank, List<Card>>> rankGroup) {
        if (!rankGroup.hasNext()) {
            throw new RuntimeException("No kicker to extract!");
        }
        final SortedSet<Card> remainingCards = new TreeSet<>();
        rankGroup.forEachRemaining(rankListEntry -> remainingCards.addAll(rankListEntry.getValue()));
        return remainingCards.last();
    }

    private Classification detectFourOfAKind() {
        if (this.rankGroup.getQuadCount() == 1) {
            final Iterator<Map.Entry<Rank, List<Card>>> rankGroup = this.rankGroup.iterator();
            final SortedSet<Card> cards = new TreeSet<>();
            cards.addAll(rankGroup.next().getValue());
            cards.add(extractQuadKicker(rankGroup));
            return new Classification(ClassificationRank.FOUR_OF_A_KIND, cards);
        }
        return detectFullHouse();
    }

    private Classification detectStraightFlush() {
        final Map<Suit, List<Card>> suitGroup = this.suitGroup.getSuitMap();
        for (Map.Entry<Suit, List<Card>> entry : suitGroup.entrySet()) {
            if (entry.getValue().size() == 5) {
                final Card[] cardArray = entry.getValue().toArray(new Card[entry.getValue().size()]);
                for (int i = 0; i < cardArray.length - 1; i++) {
                    if (cardArray[i].getRank().getRankValue() != cardArray[i + 1].getRank().getRankValue() - 1) {
                        return detectFourOfAKind();
                    }
                }
                return new Classification(ClassificationRank.STRAIGHT_FLUSH, new TreeSet<>(entry.getValue()));
            }
        }
        return detectFourOfAKind();
    }

    private Classification detectSraightFlushWheel() {
        final List<Card> handCards = new ArrayList<>(this.cards);
        if (handCards.containsAll(PokerHandUtils.STRAIGHT_WHEEL_SPADES)) {
            handCards.retainAll(PokerHandUtils.STRAIGHT_WHEEL_SPADES);
            return new Classification(ClassificationRank.STRAIGHT_FLUSH_WHEEL, new TreeSet<>(handCards));
        } else if (handCards.containsAll(PokerHandUtils.STRAIGHT_WHEEL_HEARTS)) {
            handCards.retainAll(PokerHandUtils.STRAIGHT_WHEEL_HEARTS);
            return new Classification(ClassificationRank.STRAIGHT_FLUSH_WHEEL, new TreeSet<>(handCards));
        } else if (handCards.containsAll(PokerHandUtils.STRAIGHT_WHEEL_CLUBS)) {
            handCards.retainAll(PokerHandUtils.STRAIGHT_WHEEL_CLUBS);
            return new Classification(ClassificationRank.STRAIGHT_FLUSH_WHEEL, new TreeSet<>(handCards));
        } else if (handCards.containsAll(PokerHandUtils.STRAIGHT_WHEEL_DIAMONDS)) {
            handCards.retainAll(PokerHandUtils.STRAIGHT_WHEEL_DIAMONDS);
            return new Classification(ClassificationRank.STRAIGHT_FLUSH_WHEEL, new TreeSet<>(handCards));
        }
        return detectStraightFlush();
    }

    private Classification detectRoyalFlush() {
        final List<Card> handCards = new ArrayList<>(this.cards);
        if (handCards.containsAll(PokerHandUtils.ROYAL_FLUSH_SPADES)) {
            handCards.retainAll(PokerHandUtils.ROYAL_FLUSH_SPADES);
            return new Classification(ClassificationRank.ROYAL_FLUSH, new TreeSet<>(handCards));
        } else if (handCards.containsAll(PokerHandUtils.ROYAL_FLUSH_HEARTS)) {
            handCards.retainAll(PokerHandUtils.ROYAL_FLUSH_HEARTS);
            return new Classification(ClassificationRank.ROYAL_FLUSH, new TreeSet<>(handCards));
        } else if (handCards.containsAll(PokerHandUtils.ROYAL_FLUSH_CLUBS)) {
            handCards.retainAll(PokerHandUtils.ROYAL_FLUSH_CLUBS);
            return new Classification(ClassificationRank.ROYAL_FLUSH, new TreeSet<>(handCards));
        } else if (handCards.containsAll(PokerHandUtils.ROYAL_FLUSH_DIAMONDS)) {
            handCards.retainAll(PokerHandUtils.ROYAL_FLUSH_DIAMONDS);
            return new Classification(ClassificationRank.ROYAL_FLUSH, new TreeSet<>(handCards));
        }

        return detectSraightFlushWheel();
    }

    private Classification detectImpl() {
        return detectRoyalFlush();
    }

    @Override
    public Classification classifyHand() {
        final Classification result = detectImpl();
        if (result.getCards().size() != 5) {
            throw new RuntimeException("Invalid cards: " + this.cards);
        }
        return result;
    }
}
