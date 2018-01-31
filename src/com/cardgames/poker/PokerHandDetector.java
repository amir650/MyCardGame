package com.cardgames.poker;

import com.cardgames.HandDetector;
import com.cardgames.cards.Card;
import com.cardgames.cards.Rank;
import com.cardgames.cards.Suit;

import java.util.*;
import java.util.stream.Collectors;

public class PokerHandDetector implements HandDetector {

    private final HandAnalyzer handAnalyzer;

    public PokerHandDetector(final HandAnalyzer handAnalyzer) {
        this.handAnalyzer = handAnalyzer;
    }

    private SortedSet<Card> calculateHighCards() {
        return new TreeSet<>(this.handAnalyzer.getCards().stream().limit(5).collect(Collectors.toSet()));
    }

    private Classification isPair() {
        if(this.handAnalyzer.getPairCount() == 1) {
            final Iterator<Map.Entry<Rank, List<Card>>> rankGroup = this.handAnalyzer.getRankGroup().entrySet().iterator();
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
        if(this.handAnalyzer.getPairCount() == 2) {
            final Iterator<Map.Entry<Rank, List<Card>>> rankGroup = this.handAnalyzer.getRankGroup().entrySet().iterator();
            final SortedSet<Card> cards = new TreeSet<>();
            cards.addAll(rankGroup.next().getValue());
            cards.addAll(rankGroup.next().getValue());
            cards.addAll(rankGroup.next().getValue());
            return new Classification(ClassificationRank.TWO_PAIR, cards);
        }
        return isPair();
    }

    private Classification isSet() {
        if(this.handAnalyzer.getSetCount() == 1) {
            final Iterator<Map.Entry<Rank, List<Card>>> rankGroup = this.handAnalyzer.getRankGroup().entrySet().iterator();
            final SortedSet<Card> cards = new TreeSet<>();
            cards.addAll(rankGroup.next().getValue());
            cards.addAll(rankGroup.next().getValue());
            cards.addAll(rankGroup.next().getValue());
            return new Classification(ClassificationRank.SET, cards);
        }
        return detectTwoPair();
    }

    private Classification detectNormalStraight() {
        final List<Rank> cardRanks = this.handAnalyzer.getCards().stream().map(Card::getRank).collect(Collectors.toList());

        if(cardRanks.containsAll(PokerHandUtils.STRAIGHT_TWO_TO_SIX)) {
            return new Classification(ClassificationRank.STRAIGHT, buildIt(PokerHandUtils.STRAIGHT_TWO_TO_SIX));
        } else if(cardRanks.containsAll(PokerHandUtils.STRAIGHT_THREE_TO_SEVEN)) {
            return new Classification(ClassificationRank.STRAIGHT, buildIt(PokerHandUtils.STRAIGHT_THREE_TO_SEVEN));
        } else if(cardRanks.containsAll(PokerHandUtils.STRAIGHT_FOUR_TO_EIGHT)) {
            return new Classification(ClassificationRank.STRAIGHT, buildIt(PokerHandUtils.STRAIGHT_FOUR_TO_EIGHT));
        } else if(cardRanks.containsAll(PokerHandUtils.STRAIGHT_FIVE_TO_NINE)) {
            return new Classification(ClassificationRank.STRAIGHT, buildIt(PokerHandUtils.STRAIGHT_FIVE_TO_NINE));
        } else if(cardRanks.containsAll(PokerHandUtils.STRAIGHT_SIX_TO_TEN)) {
            return new Classification(ClassificationRank.STRAIGHT, buildIt(PokerHandUtils.STRAIGHT_SIX_TO_TEN));
        } else if(cardRanks.containsAll(PokerHandUtils.STRAIGHT_SEVEN_TO_JACK)) {
            return new Classification(ClassificationRank.STRAIGHT, buildIt(PokerHandUtils.STRAIGHT_SEVEN_TO_JACK));
        } else if(cardRanks.containsAll(PokerHandUtils.STRAIGHT_EIGHT_TO_QUEEN)) {
            return new Classification(ClassificationRank.STRAIGHT, buildIt(PokerHandUtils.STRAIGHT_EIGHT_TO_QUEEN));
        } else if(cardRanks.containsAll(PokerHandUtils.STRAIGHT_NINE_TO_KING)) {
            return new Classification(ClassificationRank.STRAIGHT, buildIt(PokerHandUtils.STRAIGHT_TWO_TO_SIX));
        } else if(cardRanks.containsAll(PokerHandUtils.STRAIGHT_TEN_TO_ACE)) {
            return new Classification(ClassificationRank.STRAIGHT, buildIt(PokerHandUtils.STRAIGHT_TEN_TO_ACE));
        }

        return isSet();
    }

    private SortedSet<Card> buildIt(final List<Rank> ranks) {
        final SortedSet<Card> results = new TreeSet<>();
        final Map<Rank, List<Card>> rankGroup = this.handAnalyzer.getRankGroup();
        for(final Rank rank : ranks) {
            final List<Card> values = rankGroup.get(rank);
            if(values != null) {
                results.add(values.iterator().next());
            }
        }
        return results;
    }

    private Classification detectWheel() {
        final List<Rank> ranks = this.handAnalyzer.getCards().stream().map(Card::getRank).collect(Collectors.toList());
        return ranks.containsAll(Arrays.asList(Rank.ACE, Rank.TWO, Rank.THREE, Rank.FOUR, Rank.FIVE)) ?
                new Classification(ClassificationRank.WHEEL, this.handAnalyzer.getCards()) : detectNormalStraight();
    }

    private Classification detectFlush() {
        final Map<Suit, List<Card>> suitGroup = this.handAnalyzer.getSuitGroup();
        for(Map.Entry<Suit, List<Card>> entry : suitGroup.entrySet()) {
            if(entry.getValue().size() == 5) {
                return new Classification(ClassificationRank.FLUSH, new TreeSet<>(entry.getValue()));
            }
        }
        return detectWheel();
    }

    private Classification detectFullHouse() {
        if(this.handAnalyzer.getPairCount() == 1 && this.handAnalyzer.getSetCount() == 1) {
            final Iterator<Map.Entry<Rank, List<Card>>> rankGroup = this.handAnalyzer.getRankGroup().entrySet().iterator();
            final SortedSet<Card> cards = new TreeSet<>();
            cards.addAll(rankGroup.next().getValue());
            cards.addAll(rankGroup.next().getValue());
            return new Classification(ClassificationRank.FULL_HOUSE, cards);
        }
        return detectFlush();
    }

    private Classification detectFourOfAKind() {
        if(this.handAnalyzer.getQuadCount() == 1) {
            final Iterator<Map.Entry<Rank, List<Card>>> rankGroup = this.handAnalyzer.getRankGroup().entrySet().iterator();
            final SortedSet<Card> cards = new TreeSet<>();
            cards.addAll(rankGroup.next().getValue());
            cards.addAll(rankGroup.next().getValue());
            return new Classification(ClassificationRank.FOUR_OF_A_KIND, cards);
        }
        return detectFullHouse();
    }

    private Classification detectStraightFlush() {
        final Map<Suit, List<Card>> suitGroup = this.handAnalyzer.getSuitGroup();
        for(Map.Entry<Suit, List<Card>> entry : suitGroup.entrySet()) {
            if(entry.getValue().size() == 5) {
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
        final List<Card> handRanks = new ArrayList<>(this.handAnalyzer.getCards());
        return  handRanks.containsAll(PokerHandUtils.STRAIGHT_WHEEL_SPADES) ||
                handRanks.containsAll(PokerHandUtils.STRAIGHT_WHEEL_HEARTS) ||
                handRanks.containsAll(PokerHandUtils.STRAIGHT_WHEEL_CLUBS)  ||
                handRanks.containsAll(PokerHandUtils.STRAIGHT_WHEEL_DIAMONDS) ?
                new Classification(ClassificationRank.STRAIGHT_FLUSH_WHEEL, this.handAnalyzer.getCards()) : detectStraightFlush();
    }

    private Classification detectRoyalFlush() {
        final List<Card> handRanks = new ArrayList<>(this.handAnalyzer.getCards());
        return  handRanks.containsAll(PokerHandUtils.ROYAL_FLUSH_SPADES) ||
                handRanks.containsAll(PokerHandUtils.ROYAL_FLUSH_HEARTS) ||
                handRanks.containsAll(PokerHandUtils.ROYAL_FLUSH_CLUBS) ||
                handRanks.containsAll(PokerHandUtils.ROYAL_FLUSH_DIAMONDS) ?
                new Classification(ClassificationRank.ROYAL_FLUSH, this.handAnalyzer.getCards()) : detectSraightFlushWheel();
    }

    private Classification detectImpl() {
        return detectRoyalFlush();
    }

    @Override
    public Classification detect() {
        return detectImpl();
    }
}
