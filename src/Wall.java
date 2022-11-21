package src;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.image.ImageObserver;
import java.io.File;
import java.io.IOException;

public class Wall implements Obstacle {

    private BufferedImage sprite;
    private Point position;

    public Wall(int x, int y) {
        loadImage();

        this.position = new Point(x, y);
    }

    private void loadImage() {
        try {
            sprite = ImageIO.read(new File("images/coin.png"));
        } catch (IOException exc) {
            System.out.println("Error opening the sprite file: " + exc.getMessage());
        }
    }

    public void draw(Graphics g, ImageObserver observer) {
        g.drawImage(this.sprite, this.position.x * Board.TILE_SIZE, this.position.y * Board.TILE_SIZE, observer);
    }

    public Point getPosition() {
        return this.position;
    }
}
