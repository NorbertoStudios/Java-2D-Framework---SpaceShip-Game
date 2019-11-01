package norbertostudios.engine.gameObjects;////

import norbertostudios.engine.math.Vector2D;

import java.awt.*;
import java.awt.image.BufferedImage;

////    Created     10/24/19, 7:23 PM
////    By:         Norberto Studios
////    
public abstract class GameObject
{
    protected BufferedImage texture;
    protected Vector2D position;

    public GameObject(Vector2D position, BufferedImage texture)
    {
        this.position = position;
        this.texture = texture;
    }

    public abstract void update();
    public abstract void  draw(Graphics g);

    public Vector2D getPosition() {
        return position;
    }

    public void setPosition(Vector2D position) {
        this.position = position;
    }
}
