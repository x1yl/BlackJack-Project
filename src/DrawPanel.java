import java.awt.event.MouseListener;
import java.awt.event.MouseEvent;
import javax.imageio.ImageIO;
import javax.swing.JPanel;
import java.awt.Graphics;
import java.awt.Point;
import java.io.File;
import java.io.IOException;

class DrawPanel extends JPanel implements MouseListener {

    private Hand dealersHand;
    private Hand playersHand;

    public DrawPanel() {
        Deck d = new Deck();
        dealersHand = new Hand(d);
        playersHand = new Hand(d);

        this.addMouseListener(this);
    }

    /*
    * TODO:
    * Add button for hit
    * Add button for stand
    * Add button to wager
    * */

    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        int x = 200;
        int y = 50;
        int dealersTotal = dealersHand.getTotal();
        int playersTotal = playersHand.getTotal();
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
        x = 200;
        y += 300;
        g.drawString("Players's Total: " + playersTotal, 50, y);
        for (int i = 0; i < playersHand.length(); i++) {
            g.drawImage(playersHand.getCard(i).readImage(), x, y, null);
            x += 20;
        }

    }

    public void mousePressed(MouseEvent e) {

        Point p = e.getPoint();
        int button = e.getButton();



    }

    public void mouseReleased(MouseEvent e) { }
    public void mouseEntered(MouseEvent e) { }
    public void mouseExited(MouseEvent e) { }
    public void mouseClicked(MouseEvent e) { }
}