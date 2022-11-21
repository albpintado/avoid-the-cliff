package src;

import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.Random;
import javax.swing.*;

import static javax.swing.JOptionPane.showMessageDialog;

public class Board extends JPanel implements ActionListener, KeyListener {
    private final int DELAY = 25;

    public static final int TILE_SIZE = 50;

    public static final int ROWS = 13;

    public static final int COLUMNS = 13;

    public static final int NUM_COINS = 20;

    private static final long serialVersionUID = 490905409104883233L;
    
    private Timer timer;

    private Car car;

    private ArrayList<Spikes> spikes;

    public Board() {
        this.setPreferredSize(new Dimension(TILE_SIZE * COLUMNS, TILE_SIZE * ROWS));
        this.setBackground(new Color(142, 116, 57));

        car = new Car();
        spikes = populateObstacles();

        timer = new Timer(DELAY, this);
        timer.start();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        car.fallOffTheCliff();

        collideObstacle();

        repaint();

        car.toBeOnEdgeOfTheCliff();
    }

    @Override
    public void paintComponent(Graphics graphics) {
        super.paintComponent(graphics);

        drawBackground(graphics);
        drawScore(graphics);
        for (Spikes spikes : this.spikes) {
            //if (isTileOccupiedBySpike(this.car.getPosition())) {
                spikes.draw(graphics, this);
            //}
        }
        car.draw(graphics, this);

        Toolkit.getDefaultToolkit().sync();
    }

    @Override
    public void keyTyped(KeyEvent e) {}

    @Override
    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_SPACE) {
            showMessageDialog(null, "There are still " + spikes.size() + " obstacles.", "Obstacles remaining", JOptionPane.INFORMATION_MESSAGE, null);
            return;
        }
        if ((e.getKeyCode() == KeyEvent.VK_UP || e.getKeyCode() == KeyEvent.VK_RIGHT || e.getKeyCode() == KeyEvent.VK_LEFT || e.getKeyCode() == KeyEvent.VK_DOWN) && isTileOccupied((int) car.getPosition().getX(), (int) car.getPosition().getY(), this.spikes)) {
            car.loseLife();
        }
        car.move(e);
    }

    @Override
    public void keyReleased(KeyEvent e) {}

    private void drawBackground(Graphics g) {
        g.setColor(new Color(214, 214, 214));
        for (int row = 0; row < ROWS; row++) {
            for (int col = 0; col < COLUMNS; col++) {
                if ((row + col) % 2 == 1) {
                    g.fillRect(
                        col * TILE_SIZE,
                        row * TILE_SIZE,
                        TILE_SIZE,
                        TILE_SIZE
                    );
                }
            }    
        }
    }

    private void drawScore(Graphics g) {
        String text = car.getLives() + " ❤";

        Graphics2D graphics2D = (Graphics2D) g;
        graphics2D.setRenderingHint(
            RenderingHints.KEY_TEXT_ANTIALIASING,
            RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
        graphics2D.setRenderingHint(
            RenderingHints.KEY_RENDERING,
            RenderingHints.VALUE_RENDER_QUALITY);
        graphics2D.setRenderingHint(
            RenderingHints.KEY_FRACTIONALMETRICS,
            RenderingHints.VALUE_FRACTIONALMETRICS_ON);

        graphics2D.setColor(new Color(255, 0, 0));
        graphics2D.setFont(new Font("Lato", Font.BOLD, 25));

        FontMetrics metrics = graphics2D.getFontMetrics(graphics2D.getFont());

        Rectangle rectangle = new Rectangle(0, TILE_SIZE * (ROWS - 1), TILE_SIZE * COLUMNS, TILE_SIZE);
        int rectangleX = rectangle.x + (rectangle.width - metrics.stringWidth(text)) / 2;
        int rectangleY = rectangle.y + ((rectangle.height - metrics.getHeight()) / 2) + metrics.getAscent();

        graphics2D.drawString(text, rectangleX, rectangleY);
    }

    private ArrayList<Spikes> populateObstacles() {
        ArrayList<Spikes> spikesList = new ArrayList<>();
        Random rand = new Random();
        spikesList.add(new Spikes(rand.nextInt(COLUMNS), rand.nextInt(ROWS)));

        for (int i = 0; i < NUM_COINS - 1; i++) {
            int coinX = rand.nextInt(COLUMNS);
            int coinY = rand.nextInt(ROWS);
            if (isTileOccupied(coinX, coinY, spikesList)) {
                i--;
            } else {
                spikesList.add(new Spikes(coinX, coinY));
            }
        }

        return spikesList;
    }

    public boolean isTileOccupiedBySpike(Point position) {
        for (Spikes spike : this.spikes) {
            if (spike.getPosition().equals(position)) {
                return true;
            }
        }
        return false;
    }

    public boolean isTileOccupied(int x, int y, ArrayList<Spikes> spikes) {
        for (Spikes spike : spikes) {
            if (spike.getPosition().equals(new Point(x, y)) || this.car.getPosition().equals(new Point(x, y))) {
                return true;
            }
        }
        return false;
    }

    private void collideObstacle() {
            if (isTileOccupiedBySpike(this.car.getPosition())) {
                car.loseLife();
        }
    }
}
