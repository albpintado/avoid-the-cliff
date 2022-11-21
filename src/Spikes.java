package src;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.awt.image.ImageObserver;
import java.awt.Point;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

public class Spikes implements Obstacle {
    
    private BufferedImage sprite;
    private Point position;

    public Spikes(int x, int y) {
        loadImage();

        this.position = new Point(x, y);
    }

    private void loadImage() {
        try {
            sprite = ImageIO.read(new File("images/spikes.png"));
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
