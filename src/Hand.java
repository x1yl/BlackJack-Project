import java.util.ArrayList;

public class Hand {
    private ArrayList<Card> hand;
    private int total;

    public Hand(Deck deck) {
        hand = new ArrayList<Card>();
        for (int i = 0; i < 2; i++) {
            hand.add(deck.getRandomCard());
//            if (cards[i][j].getValue().equals("A")) {
//                total += value + 1;
//            } else if (cards[i][j].getValue().equals("J") || cards[i][j].getValue().equals("Q") || cards[i][j].getValue().equals("K")) {
//                royals += cards[i][j].getValue();
//            } else {
//                total += value + Integer.parseInt(cards[i][j].getValue());
//            }
        }

    }

    public ArrayList<Card> getHand() {
        return hand;
    }

    public Card getCard(int n) {
        return hand.get(n);
    }

    public int length() {
        return hand.size();
    }
}
