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
    private Rectangle restartHitbox;
    private boolean playersTurn;
    private boolean dealersTurn;
    private int gamesPlayed;
    private int gamesWon;
    private int gamesLost;
    private int gamesTied;
    private int gameEndState;

    public DrawPanel() {
        d = new Deck();
        dealersHand = new Hand(d);
        playersHand = new Hand(d);
        playersTurn = true;
        dealersTurn = false;
        gameEndState = 0;
        gamesWon = 0;
        gamesLost = 0;
        gamesTied = 0;
        gamesPlayed = 0;

        this.addMouseListener(this);
    }

    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        int x = 200;
        int y = 50;
        int dealersTotal = dealersHand.getTotal();
        int playersTotal = playersHand.getTotal();
        Font serifBold = new Font("Serif", Font.BOLD, 20);
        g.setFont(serifBold);
        if (gameEndState == 0) {
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
            g.drawString("Dealer's Total: " + dealersTotal, 25, y);
            for (int i = 0; i < dealersHand.length(); i++) {
                g.drawImage(dealersHand.getCard(i).readImage(), x, y, null);
                x += 20;
            }
        }
        x = 200;
        y += 300;
        g.drawString("Players's Total: " + playersTotal, 25, y);
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
            String str;
            if (playersTotal > 21 && dealersTotal <= 21) {
                str = "You lost, try again.";
                gameEndState = 1;
            } else if (dealersTotal > 21 || dealersTotal < playersTotal) {
                str = "You won! Play again.";
                gameEndState = 2;
            } else if (dealersTotal > playersTotal) {
                str = "You lost, try again.";
                gameEndState = 1;
            } else {
                str = "Tie, try again.";
                gameEndState = 3;
            }
            g.drawString(str, 170, 220);
            g.drawString("RESTART", 200, 260);
            restartHitbox = new Rectangle(195, 240, 100, 30);
            g.drawRect((int) restartHitbox.getX(), (int) restartHitbox.getY(), (int) restartHitbox.getWidth(), (int) restartHitbox.getHeight());
        }

        g.drawString("Games played: " + gamesPlayed, 20, 150);
        g.drawString("Games won: " + gamesWon, 20, 180);
        g.drawString("Games lost: " + gamesLost, 20, 210);
        g.drawString("Games tied: " + gamesTied, 20, 240);

        g.drawString("HIT", 405, 220);
        standHitbox = new Rectangle(380, 240, 80, 25);
        g.drawString("STAND", 390, 260);
        hitHitbox = new Rectangle(380, 200, 80, 25);
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

        if (gameEndState != 0 && restartHitbox.contains(p)) {
            gamesPlayed++;
            if (gameEndState == 1) {
                gamesLost++;
            } else if (gameEndState == 2) {
                gamesWon++;
            } else if (gameEndState == 3) {
                gamesTied++;
            }
            if (d.getDeck().size() <= 104) {
                d = new Deck();
            }
            dealersHand = new Hand(d);
            playersHand = new Hand(d);
            dealersTurn = false;
            playersTurn = true;
            gameEndState = 0;
        }
    }

    public void mouseReleased(MouseEvent e) { }
    public void mouseEntered(MouseEvent e) { }
    public void mouseExited(MouseEvent e) { }
    public void mouseClicked(MouseEvent e) { }
}