import java.util.ArrayList;

public class Hand {
    private final ArrayList<Card> hand;

    public Hand(Deck deck) {
        hand = new ArrayList<>();
        for (int i = 0; i < 2; i++) {
            hand.add(deck.getRandomCard());
        }

    }

    public Card getCard(int n) {
        return hand.get(n);
    }

    public int length() {
        return hand.size();
    }

    public int getTotal() {
        int total = 0;
        for (int i = 0; i < hand.size(); i++) {
            if (getCard(i).getValue().equals("J") || getCard(i).getValue().equals("Q") || getCard(i).getValue().equals("K")) {
                total += 10;
            } else if (!getCard(i).getValue().equals("A")){
                total += Integer.parseInt(getCard(i).getValue());
            }
        }
        for (int i = 0; i < hand.size(); i++) {
            if (getCard(i).getValue().equals("A")) {
                if (total <= 10) {
                    total += 11;
                } else {
                    total += 1;
                }
            }
        }
        return total;
    }

    public void hit(Deck deck) {
        hand.add(deck.getRandomCard());
    }
}
