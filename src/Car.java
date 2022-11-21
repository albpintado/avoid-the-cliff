package src;

import java.awt.event.KeyEvent;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.awt.image.ImageObserver;
import java.awt.Point;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

import static javax.swing.JOptionPane.showMessageDialog;

public class Car {
    private BufferedImage sprite;
    private Point position;
    private int lives;

    public Car() {
        loadSprite("images/car_up.png");

        this.position = new Point(6, 6);
        this.lives = 10;
    }

    private void loadSprite(String filePath) {
        try {
            sprite = ImageIO.read(new File(filePath));
        } catch (IOException e) {
            System.out.println("Error opening the sprite file: " + e.getMessage());
        }
    }

    public void draw(Graphics g, ImageObserver observer) {
        g.drawImage(sprite, (int) position.getX() * Board.TILE_SIZE, (int) position.getY() * Board.TILE_SIZE, observer);
    }

    public void move(KeyEvent e) {
        int keyPressed = e.getKeyCode();

        if (keyPressed == KeyEvent.VK_UP || keyPressed == KeyEvent.VK_W) {
            setPosition(0, -1);
            loadSprite("images/car_up.png");
        }
        if (keyPressed == KeyEvent.VK_RIGHT || keyPressed == KeyEvent.VK_D) {
            setPosition(1, 0);
            loadSprite("images/car_right.png");
        }
        if (keyPressed == KeyEvent.VK_DOWN || keyPressed == KeyEvent.VK_S) {
            setPosition(0, 1);
            loadSprite("images/car_down.png");
        }
        if (keyPressed == KeyEvent.VK_LEFT || keyPressed == KeyEvent.VK_A) {
            setPosition(-1, -0);
            loadSprite("images/car_left.png");
        }
    }

    public void toBeOnEdgeOfTheCliff() {
        if (isOnEdgeOfTheCliff()) {
            showMessageDialog(null, "Warning! You are on the edge of the cliff!");
        }
    }

    public boolean isOnEdgeOfTheCliff() {
        return (int) this.getPosition().getX() == 0 || (int) this.getPosition().getY() == 0 || (int) this.getPosition().getX() == Board.COLUMNS - 1 || this.getPosition().getY() == Board.ROWS - 1;
    }

    public void fallOffTheCliff() {
        if (isOutsideCoordinatesRange()) {
            this.die();
        }
    }

    public boolean isOutsideCoordinatesRange() {
        return (int) this.getPosition().getX() < 0 || (int) this.getPosition().getY() < 0 || (int) this.getPosition().getX() >= Board.COLUMNS || this.getPosition().getY() >= Board.ROWS;
    }

    public int getLives() {
        return this.lives;
    }

    public void loseLife() {
        this.lives--;
    }

    public void die() {
        this.lives = 0;
        showMessageDialog(null, "You have died");
        System.exit(0);
    }

    public Point getPosition() {
        return this.position;
    }

    public void setPosition(int x, int y) {
        this.position.translate(x, y);;
    }
}
