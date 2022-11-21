package src;


import java.awt.*;
import java.awt.image.ImageObserver;

interface Obstacle {
    private void loadImage() {};

    public void draw(Graphics g, ImageObserver observer);

    public Point getPosition();
}
