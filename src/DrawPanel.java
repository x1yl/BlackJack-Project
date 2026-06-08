import java.awt.*;
import java.awt.event.MouseListener;
import java.awt.event.MouseEvent;
import javax.imageio.ImageIO;
import javax.swing.JPanel;
import java.io.File;
import java.io.IOException;

class DrawPanel extends JPanel implements MouseListener {

    private Deck d;
    private Hand dealersHand;
    private Hand playersHand;
    private Rectangle hitHitbox;
    private Rectangle standHitbox;
    private boolean playersTurn;
    private boolean dealersTurn;
    private boolean gameOver;

    public DrawPanel() {
        d = new Deck();
        dealersHand = new Hand(d);
        playersHand = new Hand(d);
        playersTurn = true;
        dealersTurn = false;
        gameOver = false;

        this.addMouseListener(this);
    }

    /*
    * TODO:
    * Add button to wager
    * */

    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        int x = 200;
        int y = 50;
        int dealersTotal = dealersHand.getTotal();
        int playersTotal = playersHand.getTotal();
        if (!gameOver) {
            g.drawImage(dealersHand.getCard(0).getImage(), x, y, null);
            x += 20;
            for (int i = 1; i < dealersHand.length(); i++) {
                try {
                    g.drawImage(ImageIO.read(new File("images/card_back.png")), x, y, null);
                    x += 20;
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        } else {
            g.drawString("Dealer's Total: " + dealersTotal, 50, y);
            for (int i = 0; i < dealersHand.length(); i++) {
                g.drawImage(dealersHand.getCard(i).readImage(), x, y, null);
                x += 20;
            }
        }
        x = 200;
        y += 300;
        g.drawString("Players's Total: " + playersTotal, 50, y);
        for (int i = 0; i < playersHand.length(); i++) {
            g.drawImage(playersHand.getCard(i).readImage(), x, y, null);
            x += 20;
        }

        if (playersTotal > 21) {
            playersTurn = false;
        }

        if (dealersTurn && dealersTotal < 17) {
            dealersHand.hit(d);
        } else if (dealersTotal >= 17) {
            dealersTurn = false;
        }

        if (!dealersTurn && !playersTurn) {
            gameOver = true;
            if (playersTotal > 21 && dealersTotal <= 21) {
                System.out.println("you lost");
            } else if (dealersTotal > 21 || dealersTotal < playersTotal) {
                System.out.println("you won");
            } else if (dealersTotal > playersTotal) {
                System.out.println("you lost");
            } else {
                System.out.println("tie");
            }
        }

        Font serifBold = new Font("Serif", Font.BOLD, 20);
        g.setFont(serifBold);
        g.drawString("HIT", 355, 220);
        standHitbox = new Rectangle(330, 240, 80, 25);
        g.drawString("STAND", 340, 260);
        hitHitbox = new Rectangle(330, 200, 80, 25);
        g.drawRect((int) hitHitbox.getX(), (int) hitHitbox.getY(), (int) hitHitbox.getWidth(), (int) hitHitbox.getHeight());
        g.drawRect((int) standHitbox.getX(), (int) standHitbox.getY(), (int) standHitbox.getWidth(), (int) standHitbox.getHeight());


    }

    public void mousePressed(MouseEvent e) {

        Point p = e.getPoint();
        int button = e.getButton();

        if (playersTurn && button == 1) {
            if (hitHitbox.contains(p)) {
                playersHand.hit(d);
            }
            if (standHitbox.contains(p)) {
                playersTurn = false;
                dealersTurn = true;
            }
        }
    }

    public void mouseReleased(MouseEvent e) { }
    public void mouseEntered(MouseEvent e) { }
    public void mouseExited(MouseEvent e) { }
    public void mouseClicked(MouseEvent e) { }
}